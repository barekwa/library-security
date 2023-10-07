package com.example.project.api.repository;

import com.example.project.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.book.id = :bookId")
    int countLoansForBook(@Param("bookId") Long bookId);
    List<Loan> findByUserName(String name);
}
