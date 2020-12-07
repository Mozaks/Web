package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;


@Entity
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private Customer worker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Customer author;

    public Vacancy() {
    }

    public Vacancy(Vacancy vacancyCopy) {
        this.id = vacancyCopy.id;
        this.title = vacancyCopy.title;
        this.text = vacancyCopy.text;
        this.worker = vacancyCopy.worker;
        this.author = vacancyCopy.author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "заказчика нет";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getWorker() {
        return worker;
    }

    public void setWorker(Customer worker) {
        this.worker = worker;
    }

    public String getWorkerName() {
        return worker != null ? worker.getUsername() : "исполнителя нет";
    }

    public static Builder Builder() {
        return new Vacancy().Builder();
    }

    public static class Builder {

        Vacancy vacancy;

        private Builder() {
            this.vacancy = new Vacancy();
        }

        public Builder setTitle(String title) {
            this.vacancy.setTitle(title);
            return this;
        }

        public Builder setText(String text) {
            this.vacancy.setText(text);

            return this;
        }

        public Builder setAuthor(Customer author) {
            this.vacancy.setAuthor(author);

            return this;
        }

        public Builder setWorker(Customer worker) {
            this.vacancy.setAuthor(worker);

            return this;
        }

        public Vacancy build() {
            return new Vacancy(this.vacancy);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id) &&
                Objects.equals(title, vacancy.title) &&
                Objects.equals(text, vacancy.text) &&
                Objects.equals(worker, vacancy.worker) &&
                Objects.equals(author, vacancy.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, worker, author);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", worker=" + worker +
                ", author=" + author +
                '}';
    }
}
