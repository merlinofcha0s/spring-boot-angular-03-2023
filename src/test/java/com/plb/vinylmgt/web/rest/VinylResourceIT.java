package com.plb.vinylmgt.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plb.vinylmgt.entity.Author;
import com.plb.vinylmgt.entity.Vinyl;
import com.plb.vinylmgt.repository.AuthorRepository;
import com.plb.vinylmgt.repository.VinylRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Etape 1 : Annotation de notre classe avec les deux annotations ci dessous
@SpringBootTest
@AutoConfigureMockMvc
public class VinylResourceIT {

    // Etape 2 : Quelques constantes pour nos entites de test.
    public static final String DEFAULT_SONG_NAME = "SongName";
    public static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.now();
    public static final String DEFAULT_AUTHOR_NAME = "name";
    public static final String DEFAULT_AUTHOR_FIRSTNAME = "firstname";
    public static final LocalDate DEFAULT_AUTHOR_BIRTHDATE = LocalDate.now();

    // Etape 3 : Injection de services / repositories dont on va avoir besoin pour manipuler les donnees
    @Autowired
    private VinylRepository vinylRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MockMvc restVinylMockMVC;

    // Etape 4 : Objet de test qui est reinitialisé a chaque fois.
    private Vinyl vinyl;

    @Autowired
    private ObjectMapper objectMapper;

    private Author author;

    // Etape 5 : Methode qui est appele avant chaque test
    @BeforeEach
    public void init() {
        vinylRepository.deleteAll();
        authorRepository.deleteAll();
        vinyl = createEntity();
        author = createAuthorEntity();
    }

    private Vinyl createEntity() {
        Vinyl vinyl = new Vinyl();
        vinyl.setSongName(DEFAULT_SONG_NAME);
        vinyl.setReleaseDate(DEFAULT_RELEASE_DATE);
        return vinyl;
    }

    public static Author createAuthorEntity() {
        Author author = new Author();
        author.setName(DEFAULT_AUTHOR_NAME);
        author.setFirstname(DEFAULT_AUTHOR_FIRSTNAME);
        author.setBirthDate(DEFAULT_AUTHOR_BIRTHDATE);
        return author;
    }

    @Test
    public void findAllShouldWork() throws Exception {
        //Etape 1 : Preparer nos donnees
        authorRepository.save(author);
        vinyl.setAuthor(author);
        vinylRepository.save(vinyl);

        Vinyl secondVinyl = createEntity();
        secondVinyl.setAuthor(author);
        vinylRepository.save(secondVinyl);

        //Etape 2 et 3 : Appeler notre endpoint + Valider le retour du endpoint.
        restVinylMockMVC.perform(get("/api/vinyls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].songName")
                        .value(hasItems(vinyl.getSongName(), secondVinyl.getSongName())))
                .andExpect(jsonPath("$.[*].releaseddddDate")
                        .value(hasItems(vinyl.getReleaseDate().toString(),
                                secondVinyl.getReleaseDate().toString())));
    }

    @Test
    public void saveShouldWork() throws Exception {
        //Preparer nos donnees
        int databaseSizeCreate = vinylRepository.findAll().size();

        // Appeler notre endpoint
        restVinylMockMVC.perform(post("/api/vinyls")
                        .content(objectMapper.writeValueAsString(vinyl))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Vinyl> allVinyls = vinylRepository.findAll();
        assertThat(allVinyls.size()).isEqualTo(databaseSizeCreate + 1);
    }
}
