package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.model.User;
import pl.model.UserRole;
import pl.repository.UserRepository;
import pl.repository.UserRoleRepository;

import java.util.List;
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
                user.setActive(true);
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

    public void deleteUserByID(Long idUser) {
        userRepository.deleteById(idUser);
    }

    public void addWithTeacherRole(User user) {
        UserRole defaultRole = roleRepository.findByRole(TEACHER_ROLE).get();
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public Optional<UserRole> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public User findUserById(Long id) {
        return userRepository.getOne(id);
    }

    public int changeEmail(String newEmail, String oldEmail) {
        return userRepository.changeEmail(newEmail, oldEmail);
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

    public boolean verifyMail(String email) {
        boolean isMailCorrect = false;
        int j = 0;

        // mail nie moze byc psuty
        if (email.length() == 0) {
            return isMailCorrect;
        }

        if (email.charAt(0) != '@' && email.charAt(0) != '.' && email.charAt(0) != '_' && email.charAt(0) != '-'
                && email.charAt(0) > '9' || email.charAt(0) < '0') {
            for (int i = 1; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    j = i;
                    break;
                }
            }

            if (j != 0) {
                for (j++; j < email.length(); j++) {

                    if (!(email.charAt(j) >= 'a' && email.charAt(j) <= 'z'
                            || email.charAt(j) >= '0' && email.charAt(j) <= '9' || email.charAt(j) <= '.')) {
                        isMailCorrect = false;
                        break;
                    }
                    // cyfra po . np ktos@gmail.c2m
                    if (isMailCorrect && email.charAt(j) >= '0' && email.charAt(j) <= '9') {
                        isMailCorrect = false;
                        break;
                    }

                    if (email.charAt(j - 2) != '.' && email.charAt(j - 1) == '.' && email.charAt(j) != '.') {
                        if (!isMailCorrect)
                            // cyfra zaraz po kropce
                            if (email.charAt(j) >= '0' && email.charAt(j) <= '9') {
                                isMailCorrect = false;
                                break;
                            } else
                                isMailCorrect = true;
                    }
                }
                if (isMailCorrect && email.charAt(email.length() - 1) == '.')
                    isMailCorrect = false;
            }
        }

        return isMailCorrect;
    }

    public boolean verifyName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) >= 'a' && name.charAt(i) <= 'z' || name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'
                    || name.charAt(i) == 'ł' || name.charAt(i) == 'ą' || name.charAt(i) == 'ę' || name.charAt(i) == 'ć'
                    || name.charAt(i) == 'Ł' || name.charAt(i) == 'Ć' || name.charAt(i) == 'ż' || name.charAt(i) == 'Ż'
                    || name.charAt(i) == 'ź' || name.charAt(i) == 'Ż' || name.charAt(i) == 'ń' || name.charAt(i) == 'ó'
                    || name.charAt(i) == 'Ó' || name.charAt(i) == 'ś' || name.charAt(i) == 'Ś') {

            } else {
                return false;
            }
        }
        return true;
    }


}