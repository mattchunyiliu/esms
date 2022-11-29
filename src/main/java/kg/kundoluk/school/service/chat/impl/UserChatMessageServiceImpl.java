package kg.kundoluk.school.service.chat.impl;

import kg.kundoluk.school.dto.chat.*;
import kg.kundoluk.school.exception.IsSameUserException;
import kg.kundoluk.school.exception.UserNotFoundException;
import kg.kundoluk.school.model.chat.UserChatMessage;
import kg.kundoluk.school.model.enums.ChatMessageType;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.UserChatMessageRepository;
import kg.kundoluk.school.repository.UserChatRoomRepository;
import kg.kundoluk.school.service.chat.UserChatMessageService;
import kg.kundoluk.school.service.notification.SendNotificationService;
import kg.kundoluk.school.service.rabbit.RabbitMessageProducer;
import kg.kundoluk.school.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserChatMessageServiceImpl implements UserChatMessageService {

    private final UserChatMessageRepository chatMessageRepository;
    private final UserChatRoomRepository chatChannelRepository;
    private final UserService userService;
    private final RabbitMessageProducer rabbitMessageProducer;
    private static final Logger logger = LoggerFactory.getLogger(UserChatMessageServiceImpl.class);

    public UserChatMessageServiceImpl(UserChatMessageRepository chatMessageRepository, UserChatRoomRepository chatChannelRepository, UserService userService, RabbitMessageProducer rabbitMessageProducer) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatChannelRepository = chatChannelRepository;
        this.userService = userService;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    @Override
    public String establishChatSession(ChatChannelInitializationRequest chatChannelInitializationRequest) throws IsSameUserException, BeansException, UserNotFoundException {
        if (chatChannelInitializationRequest.getUserIdOne().equals(chatChannelInitializationRequest.getUserIdTwo())) {
            throw new IsSameUserException();
        }

        String uuid = getExistingChannel(chatChannelInitializationRequest);

        return !StringUtils.isEmpty(uuid) ? uuid : newChatSession(chatChannelInitializationRequest);
    }

    @Override
    public Optional<UserChatMessage> getById(Long id) {
        return chatMessageRepository.findById(id);

    }

    @Transactional
    @Override
    public ChatMessageRequest submitMessage(ChatMessageRequest chatMessageRequest){

        if(chatMessageRequest.getFlag() == null) {

            User recipient = userService.getOne(chatMessageRequest.getRecipientId());
            User author = userService.getOne(chatMessageRequest.getSenderId());

            UserChatMessage chatMessage = new UserChatMessage(
                    author,
                    recipient,
                    chatMessageRequest.getContents()
            );

            chatMessage.setStatus(1);//set as unread
            chatMessage.setChatRoom(chatMessageRequest.getChannelId());
            chatMessage.setMessageType(chatMessageRequest.getMessageType());

            if (chatMessageRequest.getMessageType().equals(ChatMessageType.ATTACHMENT)){
                chatMessage.setAttachmentUrl(chatMessageRequest.getAttachmentUrl());
                chatMessage.setAttachmentType(chatMessageRequest.getAttachmentType());
            }

            UserChatMessage userChatMessage = chatMessageRepository.save(chatMessage);
            chatMessageRequest.setChatId(userChatMessage.getId());

            // PUSH NOTIFICATION
            ChatChannel chatChannel = ChatChannel.builder()
                    .channelId(chatMessageRequest.getChannelId())
                    .chatId(userChatMessage.getId())
                    .senderId(author.getId())
                    .senderTitle(author.getSelectorTitle())
                    .recipientId(recipient.getId())
                    .content(chatMessageRequest.getContents())
                    .build();

            rabbitMessageProducer.sendChatNotification(chatChannel);

        }

        return chatMessageRequest;
    }

    @Override
    public ChatMessageRequest submitPublicMessage(ChatMessageRequest chatMessageDTO) {
        if (chatMessageDTO.getActionType() == 0) {
            UserChatMessage chatMessage = new UserChatMessage()
                    .setContents(chatMessageDTO.getContents())
                    .setChatRoom(chatMessageDTO.getChannelId())
                    .setStatus(1)
                    .setMessageType(chatMessageDTO.getMessageType());

            User user = userService.findById(chatMessageDTO.getSenderId());
            if (user != null) {
                chatMessage.setSenderUser(user);
                chatMessageDTO.setSenderFullName(user.getSelectorTitle());
            }

            if (chatMessageDTO.getMessageType().equals(ChatMessageType.ATTACHMENT)){
                chatMessage.setAttachmentUrl(chatMessageDTO.getAttachmentUrl());
                chatMessage.setAttachmentType(chatMessageDTO.getAttachmentType());
            }
            UserChatMessage savedChat = chatMessageRepository.save(chatMessage);

            // PUSH NOTIFICATION

            return chatMessageDTO.setChatId(savedChat.getId());
        }

        return chatMessageDTO;
    }

    @Override
    public Page<UserChatMessage> getChatMessagesByStatus(String channelUuid, Integer status, Pageable pageable) {
        return chatMessageRepository.findAllByChatRoomAndStatus(channelUuid, status, pageable);
    }

    @Override
    public void updateChatMessageStatus(Long chatId, Integer status) {
        chatMessageRepository.updateStatus(chatId, status);
    }

    @Override
    public Integer getUnread(Long userId) {
        return chatMessageRepository.getCountByRecipient(userId);
    }

    @Override
    public Page<UserChatMessage> getChatPublicMessagesByChannel(String channelUuid, Pageable pageable) {
        return chatMessageRepository.findAllByChatRoom(channelUuid, pageable);
    }

    @Override
    public void deleteChat(Long chatId) {
        chatMessageRepository.deleteById(chatId);
    }

    @Override
    public List<UserChannelDTO> getAllUserChannel(Long userId) {
        return chatChannelRepository.findAllChannelByUserInterface(userId);
    }

    @Override
    public List<UserParentUnreadDTO> getAllParentsUnreadChatCount(Long classId, Long userId) {
        return chatMessageRepository.findAllByParentListByClass(classId, userId);
    }

    private String getExistingChannel(ChatChannelInitializationRequest chatChannelInitializationRequest) {

        List<Long> ids = new ArrayList<>();
        ids.add(chatChannelInitializationRequest.getUserIdOne());
        ids.add(chatChannelInitializationRequest.getUserIdTwo());

        return chatChannelRepository.findAllByUserOneIdInAndUserTwoIdInSQL(ids);
    }

    private String newChatSession(ChatChannelInitializationRequest chatChannelInitializationRequest)
            throws BeansException {

        String uuid = UUID.randomUUID().toString();
        chatChannelRepository.saveChannel(uuid, chatChannelInitializationRequest.getUserIdOne(), chatChannelInitializationRequest.getUserIdTwo());

        return uuid;
    }
}
