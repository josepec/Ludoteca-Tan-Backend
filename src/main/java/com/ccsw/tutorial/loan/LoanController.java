package com.ccsw.tutorial.loan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.common.exception.ClientWithActiveLoanException;
import com.ccsw.tutorial.common.exception.GameWithActiveLoanException;
import com.ccsw.tutorial.common.exception.WrongDateRangeException;
import com.ccsw.tutorial.config.mapper.BeanMapper;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

/**
 * @author ccsw
 */
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    BeanMapper beanMapper;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> find(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "idClient", required = false) Long idClient,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestBody LoanSearchDto dto) {

        return this.beanMapper.mapPage(this.loanService.findPage(title, idClient, date, dto), LoanDto.class);
    }

    @RequestMapping(path = { "" }, method = RequestMethod.PUT)
    public void save(@RequestBody LoanDto dto)
            throws WrongDateRangeException, GameWithActiveLoanException, ClientWithActiveLoanException {

        loanService.save(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {

        this.loanService.delete(id);
    }

}
