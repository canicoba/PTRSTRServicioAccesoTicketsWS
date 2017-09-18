/**
 * ObjectFactory.java 02-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.inditex.ofda.strmidat.ejb.model package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML
 * content can consist of schema derived interfaces and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Validator_QNAME = new QName("urn:com:inditex:ofda:ofdastresbaa:domain:1:base", "Validator");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.inditex.ofda.strmidat.ejb.model
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FaultType }.
     *
     * @return the fault type
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}.
     *
     * @param value
     *            value
     * @return the JAXB element
     */
    @XmlElementDecl(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base", name = "Validator")
    public JAXBElement<Object> createValidator(final Object value) {
        return new JAXBElement<Object>(_Validator_QNAME, Object.class, null, value);
    }

}
