/**
 * Params.java 03-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb.conf;

/**
 * Configuration parameters used by the module.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
public enum Param {

    /** Location of the files generated. */
    FILE_LOCATION("ticket.bundle.location");

    private final String key;

    /**
     * Instancia un nuevo param.
     *
     * @param key
     *            key
     */
    Param(final String key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.key;
    }

}