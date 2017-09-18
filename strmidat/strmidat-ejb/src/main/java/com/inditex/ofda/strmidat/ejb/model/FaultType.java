/**
 * FaultType.java 09-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Tipo base para las excepciones
 * 
 * <p>Java class for FaultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FaultCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="FaultMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultType", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base", propOrder = {
    "faultCode",
    "faultMessage"
})
public class FaultType {

    /** The fault code. */
    @XmlElement(name = "FaultCode", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    protected int faultCode;
    
    /** The fault message. */
    @XmlElement(name = "FaultMessage", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
    protected String faultMessage;

    /**
     * Gets the value of the faultCode property.
     *
     * @return fault code
     */
    public int getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the value of the faultCode property.
     *
     * @param value nuevo fault code
     */
    public void setFaultCode(int value) {
        this.faultCode = value;
    }

    /**
     * Gets the value of the faultMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * Sets the value of the faultMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultMessage(String value) {
        this.faultMessage = value;
    }

}
