package com.ccsw.tutorial.loan;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.common.exception.ClientWithActiveLoanException;
import com.ccsw.tutorial.common.exception.GameWithActiveLoanException;
import com.ccsw.tutorial.common.exception.WrongDateRangeException;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

/**
 * @author ccsw
 */
public interface LoanService {

    /**
     * Recupera los prestamos filtrando opcionalmente por título y/o cliente y/o
     * fecha
     * 
     * @param title
     * @param idClient
     * @param date
     * @return
     */
    Page<Loan> findPage(String title, Long idClient, Date date, LoanSearchDto dto);

    /**
     * Guarda o modifica un prestamo, dependiendo de si el id está o no informado
     * 
     * @param id
     * @param dto
     * @throws WrongDateRangeException
     */
    void save(LoanDto dto) throws WrongDateRangeException, GameWithActiveLoanException, ClientWithActiveLoanException;

    /**
     * Método para borrar un {@link com.ccsw.tutorial.author.model.Loan}
     * 
     * @param id
     */
    void delete(Long id);

}
