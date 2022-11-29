package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.person.*;
import kg.kundoluk.school.dto.projection.UserSchoolDTO;
import kg.kundoluk.school.dto.school.SchoolCourseDto;
import kg.kundoluk.school.dto.user.UserRequest;
import kg.kundoluk.school.dto.user.UserSchoolRequest;
import kg.kundoluk.school.dto.user.UserSearch;
import kg.kundoluk.school.endpoint.PersonEndpoint;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.PermissionException;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import kg.kundoluk.school.model.*;
import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.RoleRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.service.person.PersonService;
import kg.kundoluk.school.service.user.UserSchoolService;
import kg.kundoluk.school.service.user.UserService;
import kg.kundoluk.school.utils.TimeHelper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Endpoint
public class PersonEndpointImpl implements PersonEndpoint {
    private final PersonService personService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserSchoolService userSchoolService;
    private final SchoolRepository schoolRepository;

    public PersonEndpointImpl(PersonService personService, UserService userService, RoleRepository roleRepository, UserSchoolService userSchoolService, SchoolRepository schoolRepository) {
        this.personService = personService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userSchoolService = userSchoolService;
        this.schoolRepository = schoolRepository;
    }

    //@CacheEvict(value = CacheService.SCHOOL_INSTRUCTOR, key = "#personCreateRequest.schoolId",condition = "#personCreateRequest.schoolId != null")
    @Override
    public Person create(PersonCreateRequest personCreateRequest) {

        LocalDate birthday = TimeHelper.getDateOfBirth(personCreateRequest.getDateOfBirth());
        Gender gender = personCreateRequest.getGender()==null?Gender.FEMALE:Gender.values()[personCreateRequest.getGender()];

        User user = null;
        // CHECK PERSON
        if (!StringUtils.isEmpty(personCreateRequest.getPhone()) && userService.existByPhone(personCreateRequest.getPhone())){
            user = userService.findByPhone(personCreateRequest.getPhone());

            if (user.getRoles().stream().noneMatch(r->r.getCode().equalsIgnoreCase("ROLE_STUDENT") && r.getCode().equalsIgnoreCase("ROLE_ADMIN")))
                updateUserRoles(personCreateRequest.getRoleId(), user);

            Person person = personService.findByUserId(user.getId());
            if (person != null) {
                // CREATE USER SCHOOL
                if (personCreateRequest.getSchoolId() != null)
                    createUserSchool(user, personCreateRequest.getSchoolId());
                return person;
            }

        } else {
            List<Person> person = personService.findByTitle(personCreateRequest.getFirstName(), personCreateRequest.getLastName(), personCreateRequest.getMiddleName(), gender.ordinal(), birthday);
            if (person != null && person.size() != 0)
                return person.get(0);
        }

        if (user == null || user.getRoles().stream().anyMatch(r->r.getCode().equalsIgnoreCase("ROLE_STUDENT")) || user.getRoles().stream().anyMatch(r->r.getCode().equalsIgnoreCase("ROLE_ADMIN"))) {
            // CREATE AUTH USER
            user = createUser(personCreateRequest);
        }
        // CREATE USER SCHOOL
        if (personCreateRequest.getSchoolId() != null)
            createUserSchool(user, personCreateRequest.getSchoolId());

        // CREATE PERSON
        PersonRequest personRequest = PersonRequest
                .builder()
                .firstName(personCreateRequest.getFirstName())
                .lastName(personCreateRequest.getLastName())
                .middleName(personCreateRequest.getMiddleName())
                .phone(personCreateRequest.getPhone())
                .user(user)
                .gender(gender)
                .job(personCreateRequest.getJob())
                .jobPlace(personCreateRequest.getJobPlace())
                .birthday(birthday)
                .build();
        return personService.create(personRequest);
    }

    @SneakyThrows
    @Override
    public Person edit(PersonUpdateRequest personUpdateRequest, Person person) {
        // CHECK PERSON
        /*if (!StringUtils.isEmpty(personUpdateRequest.getPhone()) && !person.getUser().getPhone().equals(personUpdateRequest.getPhone()) && userService.existByPhone(personUpdateRequest.getPhone())){
            throw new AlreadyExistException(personUpdateRequest.getPhone());
        }*/

        PersonRequest personRequest = PersonRequest
                .builder()
                .firstName(personUpdateRequest.getFirstName())
                .lastName(personUpdateRequest.getLastName())
                .middleName(personUpdateRequest.getMiddleName())
                .phone(personUpdateRequest.getPhone())
                .gender(Gender.values()[personUpdateRequest.getGender()])
                .job(personUpdateRequest.getJob())
                .jobPlace(personUpdateRequest.getJobPlace())
                .birthday(TimeHelper.getDateOfBirth(personUpdateRequest.getDateOfBirth()))
                .build();

        updateUser(personUpdateRequest, person.getUser());

        return personService.edit(personRequest, person);
    }

    @Override
    public PersonUserDto findById(Long id) {
        return toPersonUserDto(personService.findById(id));
    }

    @Override
    public PersonUserDto findByUserId(Long userId) {
        Person person = personService.findByUserId(userId);
        if (person!=null)
            return toPersonUserDto(person);
        return null;
    }

    @Override
    public PersonUserDto findByUsername(String username) {
        Person person = personService.findByUsername(username);
        if (person!=null)
            return toPersonUserDto(person);
        return null;
    }

    //@CacheEvict(value = CacheService.SCHOOL_INSTRUCTOR, key = "#schoolId",condition = "#schoolId != null")
    @Override
    public void delete(Person person, Long schoolId) throws PermissionException {
        User user = person.getUser();
        if (user.getRoles().stream().anyMatch(r->r.getCode().equals("ROLE_ADMIN")))
            throw new PermissionException("YOU CAN NOT DELETE PERSON ADMIN");

        if (user.getRoles().stream().anyMatch(r->r.getCode().equals("ROLE_INSTRUCTOR"))) {
            List<UserSchoolDTO> userSchools = userSchoolService.getUserSchool(user.getId());
            if (userSchools.size() > 1 && schoolId != null) { // WORKS ON TWO SCHOOL
                deleteUserSchool(user, schoolId);
                return;
            }
        }
        Boolean status = personService.delete(person);
        if (!status)
            userService.save(user.setEnabled(false));
        else
            userService.delete(user);

    }

    //@Cacheable(value = CacheService.SCHOOL_INSTRUCTOR, key = "#schoolId")
    @Override
    public List<InstructorDto> instructorList(Long schoolId, Boolean archived) {
        List<Person> personList = personService.instructorList(schoolId, archived);
        return personList.stream().map(p-> toInstructorDto(p,schoolId)).collect(Collectors.toList());
    }

    @Override
    public List<PersonUserDto> findAllBySchoolAndRole(Long schoolId, Long roleId) {
        List<Person> personList = personService.findAllBySchoolAndRole(schoolId, roleId);
        return personList.stream().map(this::toPersonUserDto).collect(Collectors.toList());
    }

    @Override
    public void activate(Person person) {
        person.setArchived(false);
        personService.save(person);
        userService.save(person.getUser().setEnabled(true));
    }

    @Override
    public List<PersonSearchResult> searchPerson(UserSearch userSearch) throws ResourceNotFoundException {
        String middleName = "-";
        if (!StringUtils.isEmpty(userSearch.getMiddleName()))
            middleName = userSearch.getMiddleName();
        List<Person> personList = personService.findByTitle(userSearch.getFirstName(), userSearch.getLastName(), middleName);
        if (personList == null)
            throw new ResourceNotFoundException("INSTRUCTOR_NOT_FOUND");
        return personList.stream().map(this::toPersonSearchResult).collect(Collectors.toList());
    }

    private InstructorDto toInstructorDto(Person person, Long schoolId){
        InstructorDto instructorDto = new InstructorDto(person.getId(), person.getSelectorTitle(), person.getUser().getPhone(), person.getUser().getId(), person.getUser().getUsername());
        instructorDto.setInstructorCourseTitleDtoList(person.getCourses().stream().filter(c->c.getSchool().getId().equals(schoolId)).map(this::toSchoolCourseDto).collect(Collectors.toList()));
        return instructorDto;
    }

    private SchoolCourseDto toSchoolCourseDto(SchoolCourse schoolCourse){
        return new SchoolCourseDto(schoolCourse.getId(), schoolCourse.getCourse().getTitle(), schoolCourse.getCourse().getTitleKg(), schoolCourse.getCourse().getTitleRu());
    }

    private User createUser(PersonCreateRequest personCreateRequest){
        Set<Role> roles = roleRepository.findAllByIdIn(Collections.singletonList(personCreateRequest.getRoleId()));

        String username = getUsername(personCreateRequest, roles);

        UserRequest userRequest = UserRequest.builder()
                .firstName(personCreateRequest.getFirstName())
                .lastName(personCreateRequest.getLastName())
                .middleName(personCreateRequest.getMiddleName())
                .phone(personCreateRequest.getPhone())
                .roles(roles)
                .username(username)
                .password(username)
                .build();
        return userService.create(userRequest);
    }

    private String getUsername(PersonCreateRequest personCreateRequest, Set<Role> roles) {
        return userService.generateUsername(personCreateRequest.getSchoolId(), roles.stream().findFirst().get().getCode());
    }

    private void updateUser(PersonUpdateRequest personUpdateRequest, User user){
        UserRequest userRequest = UserRequest.builder()
                .firstName(personUpdateRequest.getFirstName())
                .lastName(personUpdateRequest.getLastName())
                .middleName(personUpdateRequest.getMiddleName())
                .phone(personUpdateRequest.getPhone())
                .build();
        userService.update(userRequest, user);
    }

    private void updateUserRoles(Long roleId, User user){
        Set<Role> userRoles = user.getRoles();
        userRoles.addAll(roleRepository.findAllByIdIn(Collections.singletonList(roleId)));
        UserRequest userRequest = UserRequest.builder().roles(userRoles).build();
        userService.update(userRequest, user);
    }


    private void createUserSchool(User user, Long schoolId){
        School school = schoolRepository.getOne(schoolId);
        UserSchoolRequest userSchoolRequest = UserSchoolRequest.builder().school(school).user(user).active(true).build();
        userSchoolService.create(userSchoolRequest);
    }

    private void deleteUserSchool(User user, Long schoolId){
        userSchoolService.delete(user.getId(), schoolId);
    }

    private PersonUserDto toPersonUserDto(@NotNull Person person){
        PersonUserDto personAbstractDto = new PersonUserDto();
        personAbstractDto.setId(person.getId());
        if (person.getBirthday()!=null)
            personAbstractDto.setDateOfBirth(TimeHelper.DATE_REVERSE_FORMATTER.format(person.getBirthday()));
        personAbstractDto.setFirstName(person.getFirstName());
        personAbstractDto.setLastName(person.getLastName());
        personAbstractDto.setMiddleName(person.getMiddleName());
        personAbstractDto.setGender(person.getGender().ordinal());
        personAbstractDto.setPhone(person.getUser().getPhone());
        personAbstractDto.setUserId(person.getUser().getId());
        personAbstractDto.setAvatar(person.getUser().getAvatar());
        personAbstractDto.setJob(person.getJob());
        personAbstractDto.setJobPlace(person.getJobPlace());
        return personAbstractDto;
    }

    private PersonSearchResult toPersonSearchResult(Person person){
        return new PersonSearchResult(person);
    }
}
