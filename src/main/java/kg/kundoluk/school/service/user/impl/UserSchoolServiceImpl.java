package kg.kundoluk.school.service.user.impl;

import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.user.UserSchoolRequest;
import kg.kundoluk.school.model.user.UserSchool;
import kg.kundoluk.school.repository.UserSchoolRepository;
import kg.kundoluk.school.service.user.UserSchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSchoolServiceImpl implements UserSchoolService {
    private final UserSchoolRepository userSchoolRepository;

    public UserSchoolServiceImpl(UserSchoolRepository userSchoolRepository) {
        this.userSchoolRepository = userSchoolRepository;
    }

    @Override
    public UserSchool create(UserSchoolRequest userSchoolRequest) {
        if (!userSchoolRepository.existsAllByUserAndSchool(userSchoolRequest.getUser(), userSchoolRequest.getSchool())) {
            UserSchool userSchool = UserSchool.builder()
                    .user(userSchoolRequest.getUser())
                    .school(userSchoolRequest.getSchool())
                    .active(userSchoolRequest.getActive())
                    .startYear(userSchoolRequest.getStartYear())
                    .endYear(userSchoolRequest.getEndYear())
                    .build();
            return userSchoolRepository.save(userSchool);
        } return null;
    }

    @Override
    public void delete(Long id) {
        userSchoolRepository.deleteById(id);
    }

    @Override
    public void delete(Long userId, Long schoolId) {
        userSchoolRepository.deleteByUserSchool(userId, schoolId);
    }

    @Override
    public List<UserSchoolDTO> getUserSchool(Long userId) {
        return userSchoolRepository.getUserSchoolByUserId(userId);
    }
}
