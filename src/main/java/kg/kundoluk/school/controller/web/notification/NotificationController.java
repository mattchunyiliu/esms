package kg.kundoluk.school.controller.web.notification;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kg.kundoluk.school.components.annotations.ApiPageable;
import kg.kundoluk.school.components.hateoas.assembler.NotificationResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.NotificationResource;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.variables.NotificationStatusType;
import kg.kundoluk.school.model.notification.Notification;
import kg.kundoluk.school.service.notification.NotificationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/web/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{user_id}")
    @ApiPageable
    public PagedModel<NotificationResource> getAllUserNotifications(
            @ApiIgnore @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<Notification> pagedAssembler,
            @PathVariable("user_id") Long userId
    ) {
        return pagedAssembler.toModel(
                notificationService.findAllByUser(userId, pageable),
                new NotificationResourceAssembler()
        );
    }

    @GetMapping("/get-user-unread-count")
    public Integer getAllMineUnreadNotifications(
            Authentication authentication,
            @RequestParam(name = "user_id", required = false) Long userId
    ) {
        return notificationService.countUnreadByUser(userId, authentication);
    }

    @PostMapping("/make-read")
    public ResponseEntity<?> makeRead(
            @RequestParam("notificationId") Long notificationId
    ) {
        notificationService.makeRead(notificationId);
        return new ResponseEntity<>(new ApiResponse(true, "Notification marked as read"), HttpStatus.OK);
    }

    @PostMapping("/make-read-all")
    public ResponseEntity<?> makeReadAll(
            @RequestParam("userId") Long userId
    ) {
        notificationService.makeReadAll(userId);
        return new ResponseEntity<>(new ApiResponse(true, "Notification marked as read"), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") Long id
    ) {
        notificationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
