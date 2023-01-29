package org.telran.project.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.project.telegrambot.model.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
    //
}
