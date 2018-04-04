package pl.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.mockito.BDDMockito.given;
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

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldAddWithDefaultRole() throws Exception {
        User newUser = new User();
        copyProperties(newValidUser, newUser);

        Optional<UserRole> expectedUserRole = Optional.ofNullable(expectedUserRoleDefault);
        given(this.userRoleRepository.findByRole(DEFAULT_ROLE)).willReturn(expectedUserRole);
        given(this.userRepository.save(newUser)).willReturn(newUser);

        Assert.isTrue(this.userService.addWithDefaultRole(newUser), "New user should be added");

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

    }

    @Test
    public void addWithTeacherRole() throws Exception {
    }

    @Test
    public void findUserByEmail() throws Exception {
    }

    @Test
    public void deleteUser() throws Exception {
    }


}