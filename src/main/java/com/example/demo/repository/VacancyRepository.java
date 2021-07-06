package com.example.demo.repository;

import com.example.demo.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findByAuthorId(int authorId);

    List<Vacancy> findByWorkerId(int workerId);

    List<Vacancy> findByTitle(String title);
}
