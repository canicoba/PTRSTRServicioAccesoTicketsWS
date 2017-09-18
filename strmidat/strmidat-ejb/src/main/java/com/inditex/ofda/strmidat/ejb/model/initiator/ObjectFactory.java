/**
 * ObjectFactory.java 01-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.initiator;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.inditex.ofda.strmidat.ejb.model.initiator package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML
 * content can consist of schema derived interfaces and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _FindTicketsRequest_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator",
            "findTicketsRequest");
    private static final QName _GetTicketRequest_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", "getTicketRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * com.inditex.ofda.strmidat.ejb.model.initiator
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindTicketsRequest }.
     *
     * @return the find tickets request
     */
    public FindTicketsRequest createFindTicketRequest() {
        return new FindTicketsRequest();
    }

    /**
     * Create an instance of {@link GetTicketRequestType }.
     *
     * @return the obtiene ticket request type
     */
    public GetTicketRequestType createGetTicketRequestType() {
        return new GetTicketRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindTicketsRequest }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", name = "findTicketsRequest", substitutionHeadNamespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    public JAXBElement<FindTicketsRequest> createFindTicketsRequest(final FindTicketsRequest value) {
        return new JAXBElement<FindTicketsRequest>(_FindTicketsRequest_QNAME, FindTicketsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTicketRequestType }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", name = "getTicketRequest", substitutionHeadNamespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    public JAXBElement<GetTicketRequestType> createGetTicketRequest(final GetTicketRequestType value) {
        return new JAXBElement<GetTicketRequestType>(_GetTicketRequest_QNAME, GetTicketRequestType.class, null, value);
    }

}
