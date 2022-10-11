package com.ccsw.tutorial.loan;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.loan.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    @Query("select l from Loan l where (:title is null or l.game.title like '%'||:title||'%') and (:client is null or l.client.id = :client) and (:date is null or :date BETWEEN l.start_date AND l.end_date)")
    Page<Loan> find(@Param("title") String title, @Param("client") Long client, @Param("date") Date date,
            Pageable pageable);

    @Query("select case when count(l)> 0 then true else false end from Loan l where (l.game.id = :gameId) and ((:startDate between l.start_date and l.end_date) or (:endDate between l.start_date and l.end_date) or (l.start_date between :startDate and :endDate) or (l.end_date between :startDate and :endDate))")
    boolean existsLoanOfGameBetweenDates(@Param("gameId") Long gameId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("select case when count(l)> 0 then true else false end from Loan l where (l.client.id = :clientId) and ((:startDate between l.start_date and l.end_date) or (:endDate between l.start_date and l.end_date) or (l.start_date between :startDate and :endDate) or (l.end_date between :startDate and :endDate))")
    boolean existsClientWithLoanBetweenDates(@Param("clientId") Long clientId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}