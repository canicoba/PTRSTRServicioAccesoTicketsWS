/**
 * TicketRequest.java 09-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.inditex.ofda.strmidat.ejb.model.initiator.GetTicketRequestType;

/**
 * <p>
 * Java class for getTicketType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getTicketType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="folder" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="getTicketRequest" type="{urn:com:inditex:ofda:ofdastresbaa:domain:1:datainitiator}getTicketRequestType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTicketType", namespace = "urn:com:inditex:ofda:ofdastresbaa:ticket", propOrder = { "uuid", "folder", "getTicketRequest" })
public class TicketRequest {

    /** The uuid. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:ticket", required = true)
    protected String uuid;

    /** The folder. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:ticket", required = true)
    protected String folder;

    /** The get ticket request. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:ticket", required = true)
    protected GetTicketRequestType getTicketRequest;

    /**
     * Gets the value of the uuid property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * Sets the value of the uuid property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setUuid(final String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the folder property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFolder() {
        return this.folder;
    }

    /**
     * Sets the value of the folder property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setFolder(final String value) {
        this.folder = value;
    }

    /**
     * Gets the value of the getTicketRequest property.
     *
     * @return possible object is {@link GetTicketRequestType }
     *
     */
    public GetTicketRequestType getGetTicketRequest() {
        return this.getTicketRequest;
    }

    /**
     * Sets the value of the getTicketRequest property.
     *
     * @param value
     *            allowed object is {@link GetTicketRequestType }
     *
     */
    public void setGetTicketRequest(final GetTicketRequestType value) {
        this.getTicketRequest = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TicketRequest [uuid=" + this.uuid + ", folder=" + this.folder + ", getTicketRequest=" + this.getTicketRequest + "]";
    }

}