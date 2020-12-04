package com.example.demo.repository;

import com.example.demo.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    List<Suggestion> findByAuthorId(int authorId);
    List<Suggestion> findByWorkerId(int workerId);
    List<Suggestion> findByVacancyId(int vacancyId);

}