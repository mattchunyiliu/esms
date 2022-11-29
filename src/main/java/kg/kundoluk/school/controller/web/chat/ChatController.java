package kg.kundoluk.school.controller.web.chat;

import kg.kundoluk.school.dto.chat.ChatChannelInitializationRequest;
import kg.kundoluk.school.dto.chat.ChatMessageRequest;
import kg.kundoluk.school.dto.chat.EstablishedChatChannelRequest;
import kg.kundoluk.school.exception.IsSameUserException;
import kg.kundoluk.school.exception.UserNotFoundException;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.service.chat.UserChatMessageService;
import kg.kundoluk.school.service.user.UserService;
import kg.kundoluk.school.utils.JSONResponseHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatController {

    @Autowired
    private UserChatMessageService userChatMessageService;

    @Autowired
    private UserService userService;

    @MessageMapping("/private.chat.{channelId}")
    @SendTo("/topic/private.chat.{channelId}")
    public ChatMessageRequest chatMessage(
            @DestinationVariable String channelId, ChatMessageRequest message
    )  {
        return userChatMessageService.submitMessage(message.setChannelId(channelId));
    }

    @MessageMapping("/public.chat.{channelId}")
    @SendTo("/topic/public.chat.{channelId}")
    public ChatMessageRequest chatPublicMessage(
            @DestinationVariable String channelId,
            ChatMessageRequest message
    ) {
        return userChatMessageService.submitPublicMessage(message.setChannelId(channelId));
    }

    @RequestMapping(value = "/api/web/chat/create/channel", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> establishChatChannel(@RequestBody ChatChannelInitializationRequest chatChannelInitialization)
            throws IsSameUserException, UserNotFoundException {

        String channelUuid = userChatMessageService.establishChatSession(chatChannelInitialization);

        User userOne = userService.findById(chatChannelInitialization.getUserIdOne());
        User userTwo = userService.findById(chatChannelInitialization.getUserIdTwo());

        EstablishedChatChannelRequest establishedChatChannel = new EstablishedChatChannelRequest(
                channelUuid,
                userOne.getSelectorTitle(),
                userTwo.getSelectorTitle()
        );

        return JSONResponseHelper.createResponse(establishedChatChannel, HttpStatus.OK);
    }
}
