/**
 * DateUtil.java 03-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb.util;

import com.inditex.ofda.strmidat.lib.model.InputParamsDTO;

/**
 * The Class DateUtil.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
public class DateUtil {

    private DateUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Obtiene date.
     *
     * @param xmlDate
     *            xml date
     * @return date
     */
    public static InputParamsDTO.Fecha getDate(final String xmlDate) {
        String tmpDate = xmlDate;
        if (tmpDate != null) {
            tmpDate = tmpDate.trim();
            if (tmpDate.length() > 10) {
                tmpDate = tmpDate.substring(0, 10);
            }
            final String[] tokens = tmpDate.split("-");

            return new InputParamsDTO.Fecha(tokens[0], tokens[1], tokens[2]);
        }
        return null;
    }

}