package com.vinicius.jobTrack.repository;

import com.vinicius.jobTrack.model.Job;
import com.vinicius.jobTrack.model.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Buscar job por UsuarioID
    List<Job> findByUserId(Long id);

    List<Job> findByUser(Userr user);

    boolean existsByIdAndUserId(Long jobId, Long user);

    boolean existsByUserAndCompanyNameAndJobTitle(Userr user, String companyName, String jobTitle);


}
