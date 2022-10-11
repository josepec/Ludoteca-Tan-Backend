package com.ccsw.tutorial.loan.model;

import java.util.Date;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

/**
 * @author ccsw
 */
public class LoanDto {

    private Long id;

    private ClientDto client;

    private GameDto game;

    private Date start_date;

    private Date end_date;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date startDate) {
        this.start_date = startDate;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date endDate) {
        this.end_date = endDate;
    }

}
