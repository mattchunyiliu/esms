package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.components.hateoas.resource.ChatResource;
import kg.kundoluk.school.controller.web.chat.ChatRestController;
import kg.kundoluk.school.model.chat.UserChatMessage;
import kg.kundoluk.school.model.enums.ChatMessageType;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.stereotype.Component;

@Component
public class ChatResourceAssembler extends DataTableResourceAssembler<UserChatMessage, ChatResource>{
    public ChatResourceAssembler() {
        super(ChatRestController.class, ChatResource.class);
    }

    @Override
    public ChatResource toModel(UserChatMessage entity) {
        ChatResource resource = createModelWithId(entity.getId(), entity);
        resource.setChatRoom(entity.getChatRoom());
        resource.setContents(entity.getContents());
        resource.setId(entity.getId());
        resource.setMessageType(entity.getMessageType());
        if (entity.getMessageType().equals(ChatMessageType.ATTACHMENT)) {
            resource.setAttachmentUrl(entity.getAttachmentUrl());
            resource.setAttachmentType(entity.getAttachmentType());
        }
        if (entity.getRecipientUser()!=null) {
            resource.setRecipientId(entity.getRecipientUser().getId());
            resource.setRecipientName(entity.getRecipientUser().getSelectorTitle());
        }
        if (entity.getSenderUser()!=null) {
            resource.setSenderId(entity.getSenderUser().getId());
            resource.setSenderName(entity.getSenderUser().getSelectorTitle());
        }
        resource.setSentDate(TimeHelper.DATE_TIME_FORMATTER.format(entity.getCreatedAt()));
        resource.setStatus(entity.getStatus());
        return resource;
    }
}
