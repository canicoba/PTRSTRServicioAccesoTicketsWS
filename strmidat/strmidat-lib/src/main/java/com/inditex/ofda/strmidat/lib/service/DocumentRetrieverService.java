/**
 * DocumentRetrieverService.java 29-feb-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.lib.service;

import java.io.IOException;
import java.nio.file.Path;

import com.inditex.ofda.strmidat.lib.model.InputParamsDTO;

/**
 * The Class HDFSDocumentRetrieverServicer.
 *
 * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
 */
public interface DocumentRetrieverService {

    /**
     * The Enum Param.
     *
     * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
     */
    enum Param {

        /** The list of zookeeper nodes. */
        HBASE_ZOOKEEPERS_HOSTS("hbase.zookeper.hosts"),

        /** The list of zookeepeer ports. */
        HBASE_ZOOKEEPERS_PORTS("hbase.zookeper.ports"),

        /** HBase namespace. */
        HBASE_NAMESPACE("hbase.namespace"),

        /** The zip part size. */
        ZIP_PART_SIZE("zip.part.size.bytes");

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

    /**
     * Writes the requested document to specified file path.
     *
     * @param params
     *            params
     * @param dest
     *            dest
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void retrieveDocumentsToFile(InputParamsDTO params, Path dest) throws IOException;

    /**
     * Writes the requested documents to the directory specified in the path.
     *
     * @param params
     *            params
     * @param dest
     *            dest
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void retrieveDocumentsToFolder(InputParamsDTO params, Path dest) throws IOException;

}