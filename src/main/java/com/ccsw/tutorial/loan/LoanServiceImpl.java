package com.ccsw.tutorial.loan;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.exception.ClientWithActiveLoanException;
import com.ccsw.tutorial.common.exception.GameWithActiveLoanException;
import com.ccsw.tutorial.common.exception.WrongDateRangeException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

/**
 * @author ccsw
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    /**
     * {@inheritDoc}
     * 
     * @throws WrongDateRangeException
     */
    @Override
    public void save(LoanDto dto)
            throws WrongDateRangeException, GameWithActiveLoanException, ClientWithActiveLoanException {

        /*
         * VALIDATIONS: La fecha de fin NO podrá ser anterior a la fecha de inicio El
         * periodo de préstamo máximo solo podrá ser de 14 días. Si el usuario quiere un
         * préstamo para más de 14 días la aplicación no debe permitirlo mostrando una
         * alerta al intentar guardar. El mismo juego no puede estar prestado a dos
         * clientes distintos en un mismo día. OJO que los préstamos tienen fecha de
         * inicio y fecha fin, el juego no puede estar prestado a más de un cliente para
         * ninguno de los días que contemplan las fechas actuales del formulario. Un
         * mismo cliente no puede tener prestados más de 2 juegos en un mismo día. OJO
         * que los préstamos tienen fecha de inicio y fecha fin, el cliente no puede
         * tener más de dos préstamos para ninguno de los días que contemplan las fechas
         * actuales del formulario.
         */

        Loan loan = new Loan();

        BeanUtils.copyProperties(dto, loan, "id", "client", "game", "start_date", "end_date");
        loan.setClient(clientService.get(dto.getClient().getId()));
        loan.setGame(gameService.get(dto.getGame().getId()));

        long loanDays = TimeUnit.DAYS.convert(loan.getEndDate().getTime() - loan.getStartDate().getTime(),
                TimeUnit.MILLISECONDS) + 1;

        if (loanDays > 14 || loanDays <= 0) {
            throw new WrongDateRangeException();
        }

        if (loanRepository.existsLoanOfGameBetweenDates(loan.getGame().getId(), loan.getStartDate(),
                loan.getEndDate())) {
            throw new GameWithActiveLoanException();
        }

        if (loanRepository.existsClientWithLoanBetweenDates(loan.getClient().getId(), loan.getStartDate(),
                loan.getEndDate())) {
            throw new ClientWithActiveLoanException();
        }

        this.loanRepository.save(loan);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {

        this.loanRepository.deleteById(id);

    }

    @Override
    public Page<Loan> findPage(String title, Long idClient, Date date, LoanSearchDto dto) {
        return this.loanRepository.find(title, idClient, date, dto.getPageable());
    }

}
