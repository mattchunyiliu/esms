package kg.kundoluk.school.controller.web.chat;

import kg.kundoluk.school.components.annotations.ApiPageable;
import kg.kundoluk.school.components.hateoas.assembler.ChatResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.ChatResource;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.chat.UserChannelDTO;
import kg.kundoluk.school.dto.chat.UserParentUnreadDTO;
import kg.kundoluk.school.model.chat.UserChatMessage;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.service.chat.UserChatMessageService;
import kg.kundoluk.school.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("api/web/v1/chat")
public class ChatRestController {

    private final UserChatMessageService chatMessageService;
    private final UserService userService;

    public ChatRestController(UserChatMessageService chatMessageService, UserService userService) {
        this.chatMessageService = chatMessageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/private-chat/chat-status/{channelUuid}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ApiPageable
    public PagedModel<ChatResource> getChatMessagesByStatus(
            PagedResourcesAssembler<UserChatMessage> pagedAssembler,
            @PathVariable("channelUuid") String channelUuid,
            @RequestParam("status") Integer status,
            @ApiIgnore  @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return pagedAssembler.toModel(
                chatMessageService.getChatMessagesByStatus(channelUuid, status, pageable),
                new ChatResourceAssembler()
        );
    }

    @RequestMapping(value = "/public-chat/{channelUuid}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ApiPageable
    public PagedModel<ChatResource> getChatPublicMessagesByChannel(
            PagedResourcesAssembler<UserChatMessage> pagedAssembler,
            @PathVariable("channelUuid") String channelUuid,
            @ApiIgnore @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return pagedAssembler.toModel(
                chatMessageService.getChatPublicMessagesByChannel(channelUuid, pageable),
                new ChatResourceAssembler()
        );
    }

    @RequestMapping(value = "/private-chat/edit/{chatId}", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> changeChatMessageStatus(@PathVariable("chatId") Long chatId) {

        chatMessageService.updateChatMessageStatus(chatId, 2);

        return new ResponseEntity<>(new ApiResponse(true, "Chat status changed"), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-unread", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public Integer getUnread(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return chatMessageService.getUnread(user.getId());
    }

    @DeleteMapping(value = "/chat/{chatId}")
    public Boolean deleteChat(@PathVariable("chatId") Long chatId) {
        chatMessageService.deleteChat(chatId);
        return true;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public List<UserChannelDTO> getUserChannelList(@PathVariable("userId") Long userId) {
        return chatMessageService.getAllUserChannel(userId);
    }

    @RequestMapping(value = "/private-chat/class/{classId}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public List<UserParentUnreadDTO> getParentListWithUnreadChatCount(
            @PathVariable("classId") Long classId,
            @RequestParam("userId") Long userId
    ) {
        return chatMessageService.getAllParentsUnreadChatCount(classId, userId);
    }
}
