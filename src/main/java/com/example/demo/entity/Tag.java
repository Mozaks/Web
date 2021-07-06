package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    private String tag;

    public Tag() {
    }

    public Tag(Tag tagCopy) {
        this.id = tagCopy.id;
        this.vacancy = tagCopy.vacancy;
        this.tag = tagCopy.tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static class Builder {

        private final Tag tag;

        public Builder() {
            this.tag = new Tag();
        }

        public Builder setVacancy(Vacancy vacancy) {
            this.tag.setVacancy(vacancy);
            return this;
        }

        public Builder setTag(String tag) {
            this.tag.setTag(tag);
            return this;
        }

        public Tag build() {
            return new Tag(this.tag);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return Objects.equals(id, tag1.id) &&
                Objects.equals(vacancy, tag1.vacancy) &&
                Objects.equals(tag, tag1.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vacancy, tag);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", vacancy=" + vacancy +
                ", tag='" + tag + '\'' +
                '}';
    }
}
