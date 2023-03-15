package com.plb.vinylmgt.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "vinyl")
public class Vinyl {

    @Id
    @Column(name = "id", nullable = false)
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
