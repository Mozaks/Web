//package com.example.demo.repository;
//
//import com.example.demo.entity.Suggestion;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
//    List<Suggestion> findByWorker_Id(int worker_id);
//    Suggestion findByAuthor_Id(int author_id);
//
//}