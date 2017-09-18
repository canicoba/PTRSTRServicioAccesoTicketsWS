/**
 * Ticket.java 01-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model.response;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for getTicketResponseType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTicketResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="file" type="{urn:com:inditex:ofda:ofdastresbaa:domain:1:base}swaRef" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTicketResponseType", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder", propOrder = { "name", "file" })
@XmlRootElement(name = "getTicketResponse")
public class Ticket {

    /** The name. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder")
    protected String name;
    
    /** The file. */
    @XmlElement(namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:dataresponder")
    @XmlSchemaType(name = "anyURI")
    @XmlAttachmentRef
    protected DataHandler file;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setName(final String value) {
        this.name = value;
    }

    /**
     * Gets the value of the file property.
     *
     * @return possible object is {@link String }
     *
     */
    public DataHandler getFile() {
        return this.file;
    }

    /**
     * Sets the value of the file property.
     *
     * @param value
     *            allowed object is {@link String }
     *
     */
    public void setFile(final DataHandler value) {
        this.file = value;
    }

}
