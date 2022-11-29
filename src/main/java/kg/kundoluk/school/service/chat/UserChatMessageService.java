package kg.kundoluk.school.service.chat;

import kg.kundoluk.school.dto.chat.ChatChannelInitializationRequest;
import kg.kundoluk.school.dto.chat.ChatMessageRequest;
import kg.kundoluk.school.dto.chat.UserChannelDTO;
import kg.kundoluk.school.dto.chat.UserParentUnreadDTO;
import kg.kundoluk.school.exception.IsSameUserException;
import kg.kundoluk.school.exception.UserNotFoundException;
import kg.kundoluk.school.model.chat.UserChatMessage;
import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

public interface UserChatMessageService {

    String establishChatSession(ChatChannelInitializationRequest chatChannelInitializationRequest)
            throws IsSameUserException, BeansException, UserNotFoundException;

    Optional<UserChatMessage> getById(Long id);

    ChatMessageRequest submitMessage(ChatMessageRequest chatMessageRequest);

    ChatMessageRequest submitPublicMessage(ChatMessageRequest chatMessageDTO);

    Page<UserChatMessage> getChatMessagesByStatus(String channelUuid, Integer status, Pageable pageable);

    void updateChatMessageStatus(Long chatId, Integer status);

    Integer getUnread(Long userId);

    Page<UserChatMessage> getChatPublicMessagesByChannel(String channelUuid, Pageable pageable);

    void deleteChat(Long chatId);

    List<UserChannelDTO> getAllUserChannel(Long userId);

    List<UserParentUnreadDTO> getAllParentsUnreadChatCount(Long classId, Long userId);
}
