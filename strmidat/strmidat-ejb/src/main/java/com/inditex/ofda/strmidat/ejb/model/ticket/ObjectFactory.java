/**
 * ObjectFactory.java 02-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.ticket;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.inditex.ofda.strmidat.ejb.model.ticket package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML
 * content can consist of schema derived interfaces and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _GetTicket_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:ticket", "getTicket");
    private static final QName _FindTickets_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:ticket", "findTickets");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * com.inditex.ofda.strmidat.ejb.model.ticket
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TicketRequest }.
     *
     * @return the ticket request
     */
    public TicketRequest createTicketRequest() {
        return new TicketRequest();
    }

    /**
     * Create an instance of {@link FindTickets }.
     *
     * @return the find tickets
     */
    public FindTickets createFindTickets() {
        return new FindTickets();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TicketRequest }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:ticket", name = "getTicket", substitutionHeadNamespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    public JAXBElement<TicketRequest> createGetTicket(final TicketRequest value) {
        return new JAXBElement<TicketRequest>(_GetTicket_QNAME, TicketRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindTickets }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:ticket", name = "findTickets", substitutionHeadNamespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    public JAXBElement<FindTickets> createFindTickets(final FindTickets value) {
        return new JAXBElement<FindTickets>(_FindTickets_QNAME, FindTickets.class, null, value);
    }

}
