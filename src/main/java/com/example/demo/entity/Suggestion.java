package com.example.demo.entity;

import javax.persistence.*;

@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Customer author;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private Customer worker;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public Customer getWorker() {
        return worker;
    }

    public void setWorker(Customer worker) {
        this.worker = worker;
    }
}
