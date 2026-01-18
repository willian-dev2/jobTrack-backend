package com.vinicius.jobTrack.repository;

import com.vinicius.jobTrack.model.Userr;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Userr, Long> {

    boolean existsByEmail(String email);

    Optional<Userr> findByEmail(String email);

    Optional<Userr> findById(Long id);

    @Transactional
    void deleteByEmail(String email);

}
