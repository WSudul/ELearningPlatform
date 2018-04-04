package pl.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import pl.model.User;
import pl.model.UserRole;
import pl.repository.UserRepository;
import pl.repository.UserRoleRepository;
import pl.service.config.TestConfig;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.beans.BeanUtils.copyProperties;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class UserServiceTest {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String TEACHER_ROLE = "ROLE_TEACHER";
    private UserRole expectedUserRoleDefault;
    private UserRole expectedUserRoleTeacher;
    private User newValidUser;
    private User existingUser;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

        expectedUserRoleDefault = new UserRole();
        expectedUserRoleDefault.setId(1L);
        expectedUserRoleDefault.setRole(DEFAULT_ROLE);
        expectedUserRoleDefault.setDescription("Description 1 ");

        expectedUserRoleTeacher = new UserRole();
        expectedUserRoleTeacher.setId(2L);
        expectedUserRoleTeacher.setRole(TEACHER_ROLE);
        expectedUserRoleTeacher.setDescription("Description 2 ");

        newValidUser = new User();
        newValidUser.setEmail("test@test.com");
        newValidUser.setFirstName("Joe");
        newValidUser.setLastName("Kavinsky");
        newValidUser.setPassword("samplepass123");
        newValidUser.setActive(true);

        existingUser = new User();
        existingUser.setEmail("test@test.com");
        existingUser.setFirstName("Joe");
        existingUser.setLastName("Kavinsky");
        existingUser.setPassword("samplepass123");
        existingUser.setId(123L);
        existingUser.setRoles(Stream.of(expectedUserRoleDefault).collect(Collectors.toSet()));
        existingUser.setActive(true);

    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userRoleRepository);
    }

    @Test
    public void shouldAddWithDefaultRole() throws Exception {
        User newUser = new User();
        copyProperties(newValidUser, newUser);

        Optional<UserRole> expectedUserRole = Optional.ofNullable(expectedUserRoleDefault);
        given(this.userRoleRepository.findByRole(DEFAULT_ROLE)).willReturn(expectedUserRole);
        given(this.userRepository.save(newUser)).willReturn(newUser);

        Assert.isTrue(this.userService.addWithDefaultRole(newUser), "New user should be added");
        Mockito.verify(userRoleRepository, Mockito.times(1)).findByRole(DEFAULT_ROLE);
        Mockito.verify(userRepository, Mockito.times(1)).save(newUser);
    }

    @Test
    public void shouldNotAddWithDefaultRoleWhenSaveFails() throws Exception {
        User newUser = new User();
        copyProperties(newValidUser, newUser);

        Optional<UserRole> expectedUserRole = Optional.ofNullable(expectedUserRoleDefault);
        given(this.userRoleRepository.findByRole(DEFAULT_ROLE)).willReturn(expectedUserRole);
        given(this.userRepository.save(newUser))
                .willThrow(new DataIntegrityViolationException("Exception description"));

        Assert.isTrue(!this.userService.addWithDefaultRole(newUser), "New user should not be added");
        Mockito.verify(userRoleRepository, Mockito.times(1)).findByRole(DEFAULT_ROLE);
        Mockito.verify(userRepository, Mockito.times(1)).save(newUser);

    }

    @Test
    public void findUserByEmailShouldReturnUser() throws Exception {
        User foundUser = new User();
        copyProperties(existingUser, foundUser);

        Optional<User> expectedExistingUser = Optional.of(foundUser);
        given(this.userRepository.findByEmail(foundUser.getEmail()))
                .willReturn(expectedExistingUser);
        Optional<User> returnedValue = this.userService.findUserByEmail(existingUser.getEmail());

        Assert.notNull(returnedValue, "Returned user should not be null");
        Assert.isTrue(returnedValue.equals(expectedExistingUser), "Returned user should be same as provided");

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(existingUser.getEmail());

    }

    @Test
    public void shouldDeactivateUser() throws Exception {
        User updatedUser = new User();
        copyProperties(existingUser, updatedUser);
        updatedUser.setActive(false);

        Optional<User> expectedExistingUser = Optional.ofNullable(existingUser);
        given(this.userRepository.findById(existingUser.getId()))
                .willReturn(expectedExistingUser);
        given(this.userRepository.save(updatedUser)).willReturn(updatedUser);

        Assert.isTrue(this.userService.deactivateUser(existingUser.getId()), "User should be deactivated");
        Mockito.verify(userRepository, Mockito.times(1)).findById(existingUser.getId());
        Mockito.verify(userRepository, Mockito.times(1)).save(updatedUser);
    }

    @Test
    public void ShouldNotDeactivateNotExistingUser() throws Exception {
        final Long incorrectId = 9999L;

        Optional<User> emptyResult = Optional.empty();
        given(this.userRepository.findById(incorrectId))
                .willReturn(emptyResult);

        Assert.isTrue(!this.userService.deactivateUser(incorrectId), "Not existing user should not be deactivated");
        Mockito.verify(userRepository, Mockito.times(1)).findById(incorrectId);
        Mockito.verify(userRepository, never()).save(Mockito.any(User.class));

    }


}