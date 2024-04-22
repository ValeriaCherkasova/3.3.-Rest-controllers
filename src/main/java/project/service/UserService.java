package project.service;

import project.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean saveUser(User user);

    void deleteUser(Long userId);

    List<User> getAllUsers();

    User findByUsername(String name);

    void updateUser(User user);

    Optional<User> findById(Long id);
}
