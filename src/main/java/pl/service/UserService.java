package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.model.User;
import pl.model.UserRole;
import pl.repository.UserRepository;
import pl.repository.UserRoleRepository;

import java.util.Optional;

@Service
public class UserService {
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String TEACHER_ROLE = "ROLE_TEACHER";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository roleRepository;

    public boolean addWithDefaultRole(User user) {
        return addUser(user, DEFAULT_ROLE);
    }


    private boolean addUser(User user, final String role) {
        try {
            Optional<UserRole> userRole = roleRepository.findByRole(role);
            if (userRole.isPresent()) {
                user.getRoles().add(userRole.get());
                userRepository.save(user);
            } else {
                System.out.println("No such user role found for: " + role);
                return false;
            }

        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    public boolean deactivateUser(Long idUser) {
        try {
            Optional<User> user = userRepository.findById(idUser);
            if (user.isPresent()) {
                user.get().setActive(false);
                userRepository.save(user.get());
            } else {
                System.out.println("No user with exists with id:" + idUser);
                return false;
            }
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return false;
        }

        return true;
    }


}