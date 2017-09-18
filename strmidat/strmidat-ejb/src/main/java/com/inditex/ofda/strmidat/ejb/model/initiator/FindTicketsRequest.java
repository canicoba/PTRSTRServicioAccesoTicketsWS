/**
 * FindTicketRequest.java 02-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.initiator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for findTicketsRequestType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="findTicketsRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="brand" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="store" type="{urn:com:inditex:ofda:ofdastresbaa:domain:1:base}intListType" minOccurs="0"/&gt;
 *         &lt;element name="register" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="dateFrom" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dateTo" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findTicketsRequestType", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", propOrder = { "store", "dateFrom",
        "dateTo", "docType" })
@XmlRootElement(name = "findTicketsRequest")
public class FindTicketsRequest {

    /** The store. */
    @XmlList
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", type = String.class, required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected List<String> store;

    /** The date from. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    @XmlSchemaType(name = "date")
    protected String dateFrom;

    /** The date to. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator", required = true)
    @XmlSchemaType(name = "date")
    protected String dateTo;

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
     * Objects of the following type(s) are allowed in the list {@link Integer }
     *
     * @return store
     */
    public List<String> getStore() {
        if (this.store == null) {
            this.store = new ArrayList<String>();
        }
        return this.store;
    }

    /**
     * Obtiene store array.
     *
     * @return store array
     */
    public Integer[] getStoreArray() {
        if (this.store != null) {
            final Integer[] array = new Integer[this.store.size()];
            for (int i = 0; i < this.store.size(); i++) {
                array[i] = Integer.parseInt(this.store.get(i));
            }
            return array;
        }
        return new Integer[0];
    }

    /**
     * Gets the value of the dateFrom property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDateFrom() {
        return this.dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setDateFrom(final String value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDateTo() {
        return this.dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setDateTo(final String value) {
        this.dateTo = value;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FindTicketsRequest [store=" + this.store + ", dateFrom=" + this.dateFrom + ", dateTo=" + this.dateTo + ", docType=" + this.docType
                + "]";
    }

}
