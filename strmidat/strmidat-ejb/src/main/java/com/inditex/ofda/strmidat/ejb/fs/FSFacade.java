/**
 * HDFSFacade.java 03-mar-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.ejb.fs;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inditex.ofda.strmidat.ejb.TicketsFinder;
import com.inditex.ofda.strmidat.ejb.TicketsRetriever;
import com.inditex.ofda.strmidat.ejb.conf.Config;
import com.inditex.ofda.strmidat.ejb.model.initiator.FindTicketsRequest;
import com.inditex.ofda.strmidat.ejb.model.initiator.GetTicketRequestType;
import com.inditex.ofda.strmidat.ejb.model.response.Ticket;
import com.inditex.ofda.strmidat.ejb.model.ticket.FindTickets;
import com.inditex.ofda.strmidat.ejb.model.ticket.TicketRequest;
import com.inditex.ofda.strmidat.ejb.util.DateUtil;
import com.inditex.ofda.strmidat.lib.model.InputParamsDTO;
import com.inditex.ofda.strmidat.lib.service.DocumentRetrieverService;
import com.inditex.ofda.strmidat.lib.service.hbase.HBaseDocumentRetrieverService;

/**
 * Business façade to handle the access to the underlying file system. It is assumed that the files created in a folder (typically async calls) will
 * be cleaned up by an external process. On the other hand documents stored in file (synch calls) are returned and removed immediately after the
 * response. This is the main difference at this point between the two kinds of uses of the functionality: find multiple tickets and store them in a
 * folder or get a single ticket in a zip file. Apart from this the class is not aware of the kind of call it receives and it behaves as a normal sync
 * call in all senses.
 *
 * @author <a href="luis.manuel.diaz@oracle.com">Luis Diaz</a>
 */
public class FSFacade implements TicketsFinder, TicketsRetriever {

    private static final Logger LOGGER = LoggerFactory.getLogger(FSFacade.class.getName());

    /**
     * The Constant FILE_MIME_TYPE.
     */
    private static final String FILE_MIME_TYPE = "application/zip";

    /**
     * The Constant FILE_EXTENSION.
     */
    private static final String FILE_EXTENSION = "zip";

    private String getAbsoluteFolder(final String date) {
        return Config.getString(com.inditex.ofda.strmidat.ejb.conf.Param.FILE_LOCATION.toString()) + "/" + date + "/";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket getTicket(final TicketRequest params) {
        final Ticket ticket = new Ticket();
        if (params != null) {
            final GetTicketRequestType ticketRequest = params.getGetTicketRequest();
            if (ticketRequest != null) {
                final InputParamsDTO.TipoDocumento docType = InputParamsDTO.TipoDocumento.valueOf(ticketRequest.getDocType());

                final InputParamsDTO hdfsFilter = InputParamsDTO.Builder.newSpecificInputParamsDTOFor(docType).buildWithParams(
                        ticketRequest.getStore(), ticketRequest.getRegister(), ticketRequest.getOperation(),
                        DateUtil.getDate(ticketRequest.getDate()));

                final Path tempPath = getTicketsBundle(hdfsFilter, getAbsoluteFolder(params.getFolder()), params.getUuid());
                FileInputStream stream = null;
                ByteArrayDataSource ds = null;
                try {
                    stream = new FileInputStream(tempPath.toFile());
                    ds = new ByteArrayDataSource(stream, FILE_MIME_TYPE);
                    final DataHandler dataHandler = new DataHandler(ds);

                    ticket.setFile(dataHandler);
                } catch (final IOException e) {
                    LOGGER.error("Error populating XML object", e);
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (final IOException e) {
                            LOGGER.error("Error closing stream", e);
                        }
                    }
                }

                try {
                    Files.delete(tempPath);
                } catch (final IOException e) {
                    LOGGER.error("Error deleting temp file {}", tempPath, e);
                }
            }
            ticket.setName(params.getUuid() + "." + FILE_EXTENSION);
        } else {
            LOGGER.error("Null request received");
        }
        return ticket;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void findTickets(final FindTickets params) {
        if (params != null) {
            final FindTicketsRequest request = params.getGetTicketRequest();
            final InputParamsDTO.TipoDocumento docType = InputParamsDTO.TipoDocumento.valueOf(request.getDocType());

            final InputParamsDTO hdfsFilter = InputParamsDTO.Builder.newRangeInputParamsDTOFor(docType)
                    .withRange(DateUtil.getDate(request.getDateFrom()), DateUtil.getDate(request.getDateTo())).forTiendas(request.getStoreArray())
                    .build();
            final String absoluteFolder = getAbsoluteFolder(params.getFolder()) + params.getUuid() + "/";
            getTicketsBundle(hdfsFilter, absoluteFolder, null);
        }

    }

    /**
     * Gets the tickets synchronously using the utility library. The location of the tickets is always a temporary file that should be moved to the
     * final location or removed when no longer needed.
     *
     * @param params
     *            params
     * @return tickets
     */
    private Path getTicketsBundle(final InputParamsDTO params, final String absoluteFolder, final String fileName) {
        final Properties hbaseProps = loadConfig();

        try {
            Files.createDirectories(FileSystems.getDefault().getPath(absoluteFolder));
        } catch (final IOException e1) {
            LOGGER.error("Error creating directory dir {} ", absoluteFolder, e1);
        }

        Path outputPath = null;
        try {
            final HBaseDocumentRetrieverService service = new com.inditex.ofda.strmidat.lib.service.hbase.HBaseDocumentRetrieverService(hbaseProps);
            if (fileName != null) {
                outputPath = FileSystems.getDefault().getPath(absoluteFolder, fileName + "." + FILE_EXTENSION);
                service.retrieveDocumentsToFile(params, outputPath);
            } else {
                outputPath = FileSystems.getDefault().getPath(absoluteFolder);
                service.retrieveDocumentsToFolder(params, outputPath);
            }
        } catch (final IOException e) {
            LOGGER.error("Error retrieving document from HBase", e);
        }
        return outputPath;
    }

    private Properties loadConfig() {
        final Properties hbaseProperties = new Properties();
        for (final DocumentRetrieverService.Param param : DocumentRetrieverService.Param.values()) {
            final String key = param.toString();
            final String value = Config.getString(key);
            if (value != null) {
                hbaseProperties.put(key, value);
            }
        }
        return hbaseProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeResources() {
    }
}
