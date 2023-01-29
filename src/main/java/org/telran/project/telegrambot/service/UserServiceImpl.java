package org.telran.project.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.repository.UserRepository;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * UserService implementation to work with User entities,
 * have basic CRUD operations
 *
 * @author Olegs Gercens
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns a list of all User entities
     *
     * @return a list of all User entities
     */
    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Registers to save User entity and returns this entity
     *
     * @param user entity to save
     * @return saved entity
     * @throws IllegalArgumentException      - given User entities userName is null
     * @throws UnsupportedOperationException - if user already exists
     */
    @Override
    public User createUser(User user) {
        if (user.getName() == null) {
            throw new IllegalArgumentException("At least one entered parameter was 0 or empty");
        }
        if (userRepository.existsByName(user.getName())) {
            throw new UnsupportedOperationException("User with entered parameters already exists");
        }
        User newUser = new User(user.getName());
        return userRepository.save(newUser);
    }

    /**
     * Deletes specific User entity by identifier
     *
     * @param id User identifier
     * @throws IllegalArgumentException - if user identifier was 0
     * @throws NoSuchElementException   - if user not found
     */
    @Override
    public void deleteUser(int id) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with ID " + id + " not found");
        }
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
        }
    }

    /**
     * Returns user entity by User identifier
     *
     * @param id User identifier
     * @return User entity
     * @throws IllegalArgumentException - if user identifier was 0
     * @throws NoSuchElementException   - if user not found
     */
    @Override
    public User getUser(int id) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with ID " + id + " not found");
        }
        return userRepository.findById(id).get();
    }

    /**
     * Updates data for specific User entity using data from User entity given ir arguments
     *
     * @param id   User identifier
     * @param user entity containing changes
     * @return updated User entity
     * @throws IllegalArgumentException  - if user identifier was 0
     * @throws NoSuchElementException    - if user not found
     * @throws InvalidParameterException - given User entities userName is null
     */
    @Override
    public User updateUser(int id, User user) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with ID " + id + " not found");
        }
        if (user.getName() == null) {
            throw new InvalidParameterException("At least one entered parameter was 0 or empty");
        }
        User existingUser = userRepository.findById(id).get();
        existingUser.setName(user.getName());
        return userRepository.save(existingUser);

    }

    /**
     * Returns true if User with specific identifier is present
     *
     * @param id User identifier
     * @return true if user is present
     * @throws IllegalArgumentException - if user identifier was 0
     */
    @Override
    public boolean existsById(int id) {
        if (id == 0) throw new IllegalArgumentException("Entered value can not be 0");
        return userRepository.existsById(id);
    }
}
