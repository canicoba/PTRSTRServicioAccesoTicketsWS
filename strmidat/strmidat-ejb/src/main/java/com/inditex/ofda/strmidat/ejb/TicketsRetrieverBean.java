/**
 * TicketsBean.java 01-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inditex.ofda.strmidat.ejb.fs.FSFacade;
import com.inditex.ofda.strmidat.ejb.model.response.Ticket;
import com.inditex.ofda.strmidat.ejb.model.ticket.TicketRequest;;

/**
 * Stateless bean that provides the synchronous entry point to the find ticket functionality. One single ticket is returned in this case (although
 * multiple versions of that single ticket may be present in the zip file returned). The functionality is published as a web service that will return
 * XML with attachemnt.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@WebService(targetNamespace = "urn:com:inditex:ofda:ofdastresbaa:ticket")
@Stateless
@Local(TicketsRetriever.class)
public class TicketsRetrieverBean implements TicketsRetriever {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketsRetrieverBean.class.getName());

    private TicketsRetriever retriever;

    /**
     * Default constructor.
     */
    public TicketsRetrieverBean() {
    }

    /**
     * Gets a ticket from the underlying HDFS API and returns it as an XML with attachment. The multipart message generated will have two parts: the
     * XML response and a reference to the binary part. That part has content-type: application/zip and it is transferred in binary format (i.e.:
     * Content-Transfer-Encoding:binary)
     *
     * @param request
     *            request
     * @return ticket XML with attachment that contains the ticket.
     * @see TicketsRetriever#getTicket(int)
     */
    @Override
    @WebMethod
    public Ticket getTicket(final TicketRequest request) {
        LOGGER.debug("Retrieving ticket, request filter: {}", request);
        return this.retriever.getTicket(request);
    }

    @PostConstruct
    private void init() {
        this.retriever = new FSFacade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreDestroy
    public void freeResources() {
        if (this.retriever != null) {
            this.retriever.freeResources();
        }
    }

}