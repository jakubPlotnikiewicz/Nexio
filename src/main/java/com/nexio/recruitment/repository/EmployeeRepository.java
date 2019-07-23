package com.nexio.recruitment.repository;

import com.nexio.recruitment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query("SELECT e FROM Employee e Where e.position = :position")
    List<Employee> findByPosition(@Param("position") Employee.Position position);

    @Query("SELECT e FROM Employee e Where e.position = :position AND e.id != :id")
    List<Employee> findByPositionUnlessEqualId(@Param("position") Employee.Position positionm, @Param("id") Long id);

    @Query("SELECT e FROM Employee e Where e.firstName LIKE :phrase OR e.lastName LIKE :phrase OR e.salary LIKE :phrase OR e.position LIKE :phrase")
    List<Employee> findByPhrase(@Param("phrase") String phrase);


}
