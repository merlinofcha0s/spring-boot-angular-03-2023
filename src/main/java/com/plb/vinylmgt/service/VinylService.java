package com.plb.vinylmgt.service;

import com.plb.vinylmgt.entity.Vinyl;
import com.plb.vinylmgt.repository.VinylRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VinylService {

    private final VinylRepository vinylRepository;

    public VinylService(VinylRepository vinylRepository) {
        this.vinylRepository = vinylRepository;
    }

    public List<Vinyl> findAll() {
        return vinylRepository.findAll();
    }

    public List<Vinyl> findAllByEmail(String email) {
        return vinylRepository.findByUserEmail(email);
    }

    public Optional<Vinyl> findById(UUID uuid) {
        return vinylRepository.findById(uuid);
    }

    public Vinyl save(Vinyl vinyl) {
        return vinylRepository.save(vinyl);
    }

    public void delete(UUID uuid) {
        vinylRepository.deleteById(uuid);
    }
}
