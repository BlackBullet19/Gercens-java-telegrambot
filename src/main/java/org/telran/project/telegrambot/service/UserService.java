package org.telran.project.telegrambot.service;

import org.telran.project.telegrambot.model.User;
import java.util.List;

/**
 * UserService interface to implement to work with User entities,
 * have basic CRUD operations
 *
 * @author Olegs Gercens
 */
public interface UserService {

    /**
     * Returns a list of all User entities
     *
     * @return a list of all User entities
     */
    List<User> list();

    /**
     * Registers to save User entity and returns this entity
     *
     * @param user entity to save
     * @return saved entity
     */
    User createUser(User user);

    /**
     * Deletes specific User entity by identifier
     *
     * @param id User identifier
     */
    void deleteUser(int id);

    /**
     * Returns user entity by User identifier
     *
     * @param id User identifier
     * @return User entity
     */
    User getUser(int id);

    /**
     * Returns true if User with specific identifier is present
     *
     * @param id User identifier
     * @return true if user is present
     */
    boolean existsById(int id);

    /**
     * Updates data for specific User entity using data from User entity given ir arguments
     *
     * @param id   User identifier
     * @param user entity containing changes
     * @return updated User entity
     */
    User updateUser(int id, User user);
}
