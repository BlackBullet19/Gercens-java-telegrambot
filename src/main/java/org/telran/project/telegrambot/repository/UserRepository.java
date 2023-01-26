package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserId(long userId);

    User findByUserId(long userId);
}
