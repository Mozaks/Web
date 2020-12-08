package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

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

    public Suggestion() {
    }

    public Suggestion(Suggestion suggestion) {
        this.id = suggestion.id;
        this.vacancy = suggestion.vacancy;
        this.author = suggestion.author;
        this.worker = suggestion.worker;
    }

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

    public static class Builder {

        private Suggestion suggestion;

        public Builder() {
            this.suggestion = new Suggestion();
        }

        public Builder setVacancy(Vacancy vacancy) {
            this.suggestion.setVacancy(vacancy);
            return this;
        }

        public Builder setAuthor(Customer author) {
            this.suggestion.setAuthor(author);
            return this;
        }

        public Builder setWorker(Customer worker) {
            this.suggestion.setWorker(worker);
            return this;
        }

        public Suggestion build() {
            return new Suggestion(this.suggestion);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suggestion that = (Suggestion) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(vacancy, that.vacancy) &&
                Objects.equals(author, that.author) &&
                Objects.equals(worker, that.worker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vacancy, author, worker);
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "id=" + id +
                ", vacancy=" + vacancy +
                ", author=" + author +
                ", worker=" + worker +
                '}';
    }
}
