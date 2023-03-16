package com.plb.vinylmgt.web.rest;


import com.plb.vinylmgt.entity.Vinyl;
import com.plb.vinylmgt.service.VinylService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/vinyls")
public class VinylResource {

    private final VinylService vinylService;

    public VinylResource(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping
    public ResponseEntity<List<Vinyl>> findAll() {
        return ResponseEntity.ok(vinylService.findAll());
    }

    @GetMapping("/by-id")
    public ResponseEntity<Vinyl> findById(@RequestParam UUID uuid) {
        Optional<Vinyl> vinylById = vinylService.findById(uuid);
        return vinylById
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Vinyl> save(@Valid @RequestBody Vinyl vinyl) {
        return ResponseEntity.ok(vinylService.save(vinyl));
    }

    @PostMapping(value = "/save-by-author")
    public ResponseEntity<Vinyl> saveWithAuthor(@RequestBody Vinyl vinyl) {
        return ResponseEntity.ok(vinylService.save(vinyl));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam UUID uuid) {
        vinylService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
