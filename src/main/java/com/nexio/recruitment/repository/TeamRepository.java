package com.nexio.recruitment.repository;
import com.nexio.recruitment.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TeamRepository  extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t Where t.id IN :ids")
    List<Team> findByIds(@Param("ids") List<Long> ids);

    @Query("SELECT t FROM Team t Where t.teamName LIKE :phrase OR t.projectName LIKE :phrase " +
            "OR t.projectOwner.firstName LIKE :phrase OR t.projectOwner.lastName LIKE :phrase " +
            "OR t.projectManager.firstName LIKE :phrase OR t.projectManager.lastName LIKE :phrase " +
            "OR t.scrumMaster.firstName LIKE :phrase OR t.scrumMaster.lastName LIKE :phrase")
    List<Team> findByPhrase(@Param("phrase") String phrase);
}
