package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
