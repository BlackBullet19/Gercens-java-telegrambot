package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telran.project.telegrambot.model.User;
import org.telran.project.telegrambot.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Return boolean value depending on user with this userName is present or not
     *
     * @param username Username
     * @return true or false
     */
    boolean existsByName(String username);

    /**
     * Change a value of user status, USER or ADMIN
     *
     * @param status - UserRole
     * @param id     - user identifier
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update User u SET u.status =:status WHERE u.id =:id")
    void changeUserStatus(@Param("status") UserRole status, @Param("id") int id);
}
