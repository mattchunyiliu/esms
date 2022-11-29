package kg.kundoluk.school.controller.web.user;

import kg.kundoluk.school.dto.projection.StudentParentLastDate;
import kg.kundoluk.school.service.user.UserTrackPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/user-track-page")
public class UserTrackController {
    private final UserTrackPageService userTrackPageService;

    public UserTrackController(UserTrackPageService userTrackPageService) {
        this.userTrackPageService = userTrackPageService;
    }

    @GetMapping("/list-range-count")
    public CompletableFuture<Integer> listByDateRangeCount(
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end
    ) {
        return this.userTrackPageService.getAllByRangeCount(start, end);
    }

    @GetMapping("/class-last-date")
    public List<StudentParentLastDate> listByClassParentLastDate(
            @RequestParam(name = "classId") Long classId
    ) {
        return this.userTrackPageService.listByClassParentLastDate(classId);
    }
}
