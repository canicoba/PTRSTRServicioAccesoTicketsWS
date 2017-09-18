/**
 * StoreSetType.java 09-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */

package com.inditex.ofda.strmidat.ejb.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for storeSetType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="storeSetType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MMTT"/&gt;
 *     &lt;enumeration value="TTTT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
@XmlType(name = "storeSetType", namespace = "urn:com:inditex:ofda:ofdastresbaa:domain:1:base")
@XmlEnum
public enum StoreSetType {

    /** The mmtt. */
    MMTT,
    
    /** The tttt. */
    TTTT;

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return name();
    }

    /**
     * From value.
     *
     * @param v v
     * @return the store set type
     */
    public static StoreSetType fromValue(String v) {
        return valueOf(v);
    }

}
