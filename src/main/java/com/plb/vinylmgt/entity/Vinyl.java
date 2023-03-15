package com.plb.vinylmgt.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "vinyl")
public class Vinyl {

    public Vinyl() {
    }

    public Vinyl(String songName, LocalDate releaseDate, Author author, User user) {
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.user = user;
        this.author = author;
    }

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    @GeneratedValue
    private UUID id;

    @Column(name = "song_name", nullable = false)
    private String songName;

    @Column(name = "release_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Author author;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vinyl vinyl = (Vinyl) o;
        return Objects.equals(songName, vinyl.songName) && Objects.equals(releaseDate, vinyl.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songName, releaseDate);
    }
}
