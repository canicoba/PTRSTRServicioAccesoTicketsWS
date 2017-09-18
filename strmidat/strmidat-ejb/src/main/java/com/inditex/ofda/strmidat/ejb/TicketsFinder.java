/**
 * TicketsFinder.java 01-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb;

import com.inditex.ofda.strmidat.ejb.model.ticket.FindTickets;

/**
 * The Interface TicketsFinder.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
public interface TicketsFinder extends TicketsService {

    /**
     * Find multiple tickets searching them by the condition in the filter.
     *
     * @param conditions
     *            conditions
     */
    void findTickets(FindTickets conditions);

}