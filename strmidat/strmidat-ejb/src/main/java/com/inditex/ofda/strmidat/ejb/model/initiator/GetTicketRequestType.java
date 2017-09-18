/**
 * GetTicketRequestType.java 03-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.initiator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for getTicketRequestType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getTicketRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="store" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="register" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTicketRequestType", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", propOrder = { "store", "register",
        "operation", "date", "docType" })
public class GetTicketRequestType {

    /** The store. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    protected int store;

    /** The register. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    protected int register;

    /** The operation. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    protected long operation;

    /** The date. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    @XmlSchemaType(name = "date")
    protected String date;

    /** The doc type. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    private String docType;

    /**
     * Gets the value of the store property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be
     * present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the store property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getStore().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link int }
     *
     * @return store
     */
    public int getStore() {
        return this.store;
    }

    /**
     * Gets the value of the register property.
     *
     * @return register
     */
    public int getRegister() {
        return this.register;
    }

    /**
     * Sets the value of the register property.
     *
     * @param value
     *            nuevo register
     */
    public void setRegister(final int value) {
        this.register = value;
    }

    /**
     * Gets the value of the operation property.
     *
     * @return operation
     */
    public long getOperation() {
        return this.operation;
    }

    /**
     * Sets the value of the operation property.
     *
     * @param value
     *            nuevo operation
     */
    public void setOperation(final long value) {
        this.operation = value;
    }

    /**
     * Gets the value of the date property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setDate(final String value) {
        this.date = value;
    }

    /**
     * Obtiene doc type.
     *
     * @return doc type
     */
    public String getDocType() {
        return this.docType;
    }

    /**
     * Establece doc type.
     *
     * @param docType
     *            nuevo doc type
     */
    public void setDocType(final String docType) {
        this.docType = docType;
    }

}
