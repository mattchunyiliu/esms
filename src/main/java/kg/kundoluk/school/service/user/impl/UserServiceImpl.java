package kg.kundoluk.school.service.user.impl;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.SchoolAdminViewDTO;
import kg.kundoluk.school.dto.projection.UserRayonDTO;
import kg.kundoluk.school.dto.user.UserProfileUpdateRequest;
import kg.kundoluk.school.dto.user.UserRequest;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.LanguageRepository;
import kg.kundoluk.school.repository.UserRepository;
import kg.kundoluk.school.service.user.UserService;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, LanguageRepository languageRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findFirstByPhone(phone);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> findAllBySchoolAndRole(Long schoolId, Long roleId) {
        return userRepository.findAllBySchoolsAndRoles(schoolId, roleId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User create(UserRequest userRequest) {
        // DEFAULT LANGUAGE FOR ALL USERS - RUSSIAN
        Language language = languageRepository.getByCode("ru");

        User user = new User();
        user.setEnabled(true);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userRequest.getPhone());
        user.setRoles(userRequest.getRoles());
        user.setLanguage(language);
        user.setRayons(userRequest.getRayons());
        return userRepository.save(user);
    }

    @CacheEvict(value = CacheService.USER, key = "#userRequest.username", condition = "#userRequest.username != null")
    @Override
    public User update(UserRequest userRequest, User user) {
        BeanUtils.copyProperties(userRequest, user, UpdateColumnUtil.getNullPropertyNames(userRequest));
        /*if (!StringUtils.isEmpty(userRequest.getFirstName()))
            user.setFirstName(userRequest.getFirstName());
        if (!StringUtils.isEmpty(userRequest.getLastName()))
            user.setLastName(userRequest.getLastName());
        if (!StringUtils.isEmpty(userRequest.getMiddleName()))
            user.setMiddleName(userRequest.getMiddleName());
        if (!StringUtils.isEmpty(userRequest.getPhone()))
            user.setPhone(userRequest.getPhone());
        if (userRequest.getRoles()!=null)
            user.setRoles(userRequest.getRoles());
        if (userRequest.getAvatar()!=null)
            user.setAvatar(userRequest.getAvatar());*/
        return userRepository.save(user);
    }

    @CacheEvict(value = CacheService.USER, key = "#userDto.username",condition = "#userDto.username != null")
    @Override
    public User update(UserProfileUpdateRequest userDto) {

        User user = userDto.getIsPhoneEdit()?findByPhone(userDto.getPhone()):findByUsername(userDto.getUsername());

        if (!StringUtils.isEmpty(userDto.getPhone()))
            user.setPhone(userDto.getPhone());

        if (!StringUtils.isEmpty(userDto.getFirstName()))
            user.setFirstName(userDto.getFirstName());
        if (!StringUtils.isEmpty(userDto.getLastName()))
            user.setLastName(userDto.getLastName());
        if (!StringUtils.isEmpty(userDto.getNewPhone()) && !existByPhone(userDto.getNewPhone()))
            user.setPhone(userDto.getNewPhone());

        //UPDATE PASSWORD
        if(Objects.nonNull(user) && !StringUtils.isEmpty(userDto.getPassword())){
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return this.userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public String generateUsername(Long schoolId, String roleCode) {

        String firstPart;
        String secondPart;

        switch (roleCode) {
            case "ROLE_PARENT":
                firstPart = String.valueOf(1);
                break;
            case "ROLE_INSTRUCTOR":
                firstPart = String.valueOf(2).concat(schoolId.toString());
                break;
            case "ROLE_STUDENT":
                firstPart = String.valueOf(3).concat(schoolId.toString());
                break;
            case "ROLE_ADMIN":
                firstPart = String.valueOf(4).concat(schoolId.toString());
                break;
            case "ROLE_RAYON_HEADER":
                firstPart = String.valueOf(8);
                break;
            default:
                firstPart = String.valueOf(schoolId).concat("4");
                break;
        }

        do {
            secondPart = getRandomNumberString();
        } while (this.existByUsername(firstPart.concat(secondPart)));

        return firstPart.concat(secondPart);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> searchUser(UserSearch userSearch) {
        String firstName = StringUtils.isEmpty(userSearch.getFirstName())?"-":userSearch.getFirstName();
        String lastName = userSearch.getLastName();
        String middleName = StringUtils.isEmpty(userSearch.getMiddleName())?"-":userSearch.getMiddleName();
        return userRepository.findByTitle(firstName.toLowerCase(), lastName.toLowerCase(), middleName.toLowerCase());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolAdminViewDTO> listSchoolAdmin() {
        return userRepository.listAdmin();
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolAdminViewDTO> listRayonAdmin() {
        return userRepository.listRayonAdmin();
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserRayonDTO> getUserRayons(Long userId) {
        return userRepository.listUserRayon(userId);
    }

    private static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
