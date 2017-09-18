/**
 * TicketFinderBean.java 02-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb;

import java.io.StringReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inditex.ofda.strmidat.ejb.fs.FSFacade;
import com.inditex.ofda.strmidat.ejb.model.ticket.FindTickets;

/**
 * Message-Driven Bean that provides the asynchronous entry point for the SAS Ticket functionality.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") }, mappedName = "jms/Q/OFDASTRMIDAT-DQJMSAccesoTicket01")
public class TicketFinderBean implements MessageListener, TicketsFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketFinderBean.class.getName());

    private TicketsFinder finder;

    /**
     * Default constructor.
     */
    public TicketFinderBean() {
    }

    @PostConstruct
    private void init() {
        this.finder = new FSFacade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreDestroy
    public void freeResources() {
        if (this.finder != null) {
            this.finder.freeResources();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(final javax.jms.Message inMessage) {
        LOGGER.debug("Message received, message: {}", inMessage);
        TextMessage msg = null;

        try {
            if (inMessage instanceof TextMessage) {
                msg = (TextMessage) inMessage;
                LOGGER.debug("Text message received, message text: {}", msg.getText());
                final FindTickets conditions = unmarshalMessage(msg);
                if (conditions != null) {
                    findTickets(conditions);
                } else {
                    LOGGER.error("Null search conditions received: {}", conditions);
                }
            } else {
                LOGGER.error("Message of wrong type: " + inMessage.getClass().getName());
            }
        } catch (final Exception te) {
            LOGGER.error("Error processing message", te);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void findTickets(final FindTickets conditions) {
        this.finder.findTickets(conditions);
    }

    private FindTickets unmarshalMessage(final TextMessage msg) throws JAXBException, JMSException {
        FindTickets response = null;
        final JAXBContext jaxbContext = JAXBContext.newInstance(
                "com.inditex.ofda.strmidat.ejb.model.ticket:com.inditex.ofda.strmidat.ejb.model:com.inditex.ofda.strmidat.ejb.model.initiator:com.inditex.ofda.strmidat.ejb.model.response");
        final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        final Source source = new StreamSource(new StringReader(msg.getText()));
        final JAXBElement<FindTickets> root = jaxbUnmarshaller.unmarshal(source, FindTickets.class);
        if (root != null) {
            response = root.getValue();
        }
        return response;
    }

}
