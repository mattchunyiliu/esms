package kg.kundoluk.school.service.user;

import kg.kundoluk.school.dto.projection.StudentParentLastDate;
import kg.kundoluk.school.repository.UserTrackPageRepository;
import kg.kundoluk.school.security.jwt.JwtUser;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserTrackPageService {
    private final UserTrackPageRepository repository;

    public UserTrackPageService(UserTrackPageRepository repository) {
        this.repository = repository;
    }

    @Async("asyncExecutor")
    public void action(
            @NotNull Authentication auth,
            String ip,
            String page,
            Integer device
    ) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        repository.insertSQL(jwtUser.getId(), ip, page, device);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Integer> getAllByRangeCount(String start, String end){
        LocalDateTime startTime = LocalDateTime.parse(start, TimeHelper.DATE_TIME_FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, TimeHelper.DATE_TIME_FORMATTER);
        return CompletableFuture.completedFuture(repository.getAllByDateRangeCount(startTime, endTime));
    }

    public List<StudentParentLastDate> listByClassParentLastDate(Long classId){
        return repository.getClassParentLastDate(classId);
    }
}
