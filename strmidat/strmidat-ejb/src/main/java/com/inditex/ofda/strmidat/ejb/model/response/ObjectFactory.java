/**
 * ObjectFactory.java 01-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.response;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.inditex.ofda.strmidat.ejb.model.FaultType;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.inditex.ofda.strmidat.ejb.model.response package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML
 * content can consist of schema derived interfaces and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _FindSalesByCriteriaFault_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder",
            "findSalesByCriteriaFault");
    private static final QName _GetTicketResponse_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder", "getTicketResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * com.inditex.ofda.strmidat.ejb.model.response
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Ticket }.
     *
     * @return the ticket
     */
    public Ticket createTicket() {
        return new Ticket();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultType }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder", name = "findSalesByCriteriaFault", substitutionHeadNamespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    public JAXBElement<FaultType> createFindSalesByCriteriaFault(final FaultType value) {
        return new JAXBElement<FaultType>(_FindSalesByCriteriaFault_QNAME, FaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ticket }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder", name = "getTicketResponse", substitutionHeadNamespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    public JAXBElement<Ticket> createGetTicketResponse(final Ticket value) {
        return new JAXBElement<Ticket>(_GetTicketResponse_QNAME, Ticket.class, null, value);
    }

}
