/**
 * TicketsRetriever.java 01-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb;

import javax.ejb.Local;

import com.inditex.ofda.strmidat.ejb.model.response.Ticket;
import com.inditex.ofda.strmidat.ejb.model.ticket.TicketRequest;

/**
 * The Interface TicketsRetriever.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@Local
public interface TicketsRetriever extends TicketsService {

    /**
     * Gets a single ticket using its "PK" that is in the GetTicketRequestType class.
     *
     * @param request
     *            request
     * @return ticket
     */
    Ticket getTicket(TicketRequest request);

}