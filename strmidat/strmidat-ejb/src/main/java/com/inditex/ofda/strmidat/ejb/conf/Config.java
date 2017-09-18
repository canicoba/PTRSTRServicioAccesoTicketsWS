/**
 * Config.java 16-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb.conf;

import com.inditex.ofda.strlibco.lib.commons_configuration.CompositeConfig;
import com.inditex.ofda.strlibco.lib.commons_configuration.ICompositeConfig;
import com.inditex.ofda.strlibco.lib.utils.Project;

/**
 * The Class Config.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
public class Config {

    private Config() {
        throw new UnsupportedOperationException();
    }

    private static final ICompositeConfig CONFIG = CompositeConfig.getInstance();

    /**
     * Obtiene string.
     *
     * @param key
     *            key
     * @return string
     */
    public static String getString(final String key) {
        return CONFIG.getString(Project.STRMIDAT, key);
    }

}