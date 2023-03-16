package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.Author;
import com.plb.vinylmgt.entity.User;
import com.plb.vinylmgt.entity.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VinylRepository extends JpaRepository<Vinyl, UUID> {
    List<Vinyl> findByUserEmail(String email);
}
