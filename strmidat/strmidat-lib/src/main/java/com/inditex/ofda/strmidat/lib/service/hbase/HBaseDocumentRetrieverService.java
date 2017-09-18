/**
 * HBaseDocumentRetrieverService.java 01-abr-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.lib.service.hbase;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ServiceException;
import com.inditex.ofda.strmidat.lib.model.InputParamsDTO;
import com.inditex.ofda.strmidat.lib.model.InputParamsDTO.Fecha;
import com.inditex.ofda.strmidat.lib.model.InputParamsDTO.TipoDocumento;
import com.inditex.ofda.strmidat.lib.service.DocumentRetrieverService;
import com.inditex.ofda.strmidat.lib.service.hbase.util.HBaseConnectionParams;

/**
 * The Class HDFSDocumentRetrieverService.
 *
 * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
 */
public class HBaseDocumentRetrieverService implements DocumentRetrieverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseDocumentRetrieverService.class.getName());
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String MANIFEST_FILENAME = "manifest.txt";
    private static final String NO_FILES_FOUND_MESSAGE = "No se encontraron ficheros para el criterio de búsqueda OK!!!";
    private static final byte[] COLUMN_FAMILY_XMLS = "xmls".getBytes();
    private static final String COMPONENT_SEPARATOR = "/";
    private static final String XML_DOCUMENT_QUALIFIER_PREFIX = "D/";

    private final Properties hbaseProperties;
    private final TableName tableName;
    private final long zipPartSizeBytes;

    /**
     * Metodo Main.
     *
     * @param args
     *            argumentos de entrada
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void main(final String[] args) throws IOException {
        final Properties props = System.getProperties();
        props.setProperty(Param.ZIP_PART_SIZE.toString(), "500000");

        final String startYear = args[0].substring(0, 4);
        final String startMonth = args[0].substring(4, 6);
        final String startDay = args[0].substring(6, 8);

        final String endYear = args[1].substring(0, 4);
        final String endMonth = args[1].substring(4, 6);
        final String endDate = args[1].substring(6, 8);

        final String[] strTiendas = args[2].split(",");
        final List<Integer> tiendas = new ArrayList<>(strTiendas.length);
        for (final String strTienda : strTiendas) {
            tiendas.add(Integer.parseInt(strTienda));
        }

        if (args.length >= 4) {
            props.setProperty(Param.HBASE_ZOOKEEPERS_HOSTS.toString(),
                    "axinbda1node01.central.inditex.grp,axinbda1node02.central.inditex.grp,axinbda1node03.central.inditex.grp"
                            + "#axinbda2node01.central.inditex.grp,axinbda2node02.central.inditex.grp,axinbda2node03.central.inditex.grp");
            props.setProperty(Param.HBASE_ZOOKEEPERS_PORTS.toString(), "2181#2181");
            props.setProperty(Param.HBASE_NAMESPACE.toString(), "operations");
        } else {
            props.setProperty(Param.HBASE_ZOOKEEPERS_HOSTS.toString(),
                    "axinbda3node01.central.inditex.grp,axinbda3node02.central.inditex.grp,axinbda3node03.central.inditex.grp");
            props.setProperty(Param.HBASE_ZOOKEEPERS_PORTS.toString(), "2181");
            props.setProperty(Param.HBASE_NAMESPACE.toString(), "operations_des");
        }

        // InputParamsDTO params = InputParamsDTO.Builder.newSpecificInputParamsDTOFor(InputParamsDTO.TipoDocumento.VENTA_STOCK)
        // .buildWithParams(13, 2, 56781234L, new InputParamsDTO.Fecha("2015", "06", "29"));
        final InputParamsDTO params = InputParamsDTO.Builder.newRangeInputParamsDTOFor(InputParamsDTO.TipoDocumento.VENTA_STOCK)
                .withRange(new Fecha(startYear, startMonth, startDay), new Fecha(endYear, endMonth, endDate))
                .forTiendas(tiendas.toArray(new Integer[] {})).build();

        final HBaseDocumentRetrieverService service = new HBaseDocumentRetrieverService(props);
        service.retrieveDocumentsToFolder(params, Paths.get(".", "folder"));
        service.retrieveDocumentsToFile(params, Paths.get(".", "tickets.zip"));

    }

    /**
     * Instancia un nuevo hbase document retriever service.
     *
     * @param properties
     *            properties
     */
    public HBaseDocumentRetrieverService(final Properties properties) {
        this.hbaseProperties = properties;
        final int temp = Integer.parseInt(properties.getProperty(Param.ZIP_PART_SIZE.toString(), "0"));
        this.tableName = TableName.valueOf(properties.getProperty(Param.HBASE_NAMESPACE.toString()), "RAW_OPERACIONES");
        this.zipPartSizeBytes = temp <= 0 ? Integer.MAX_VALUE : temp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void retrieveDocumentsToFile(final InputParamsDTO params, final Path dest) throws IOException {
        retrieveDocuments(params, dest, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void retrieveDocumentsToFolder(final InputParamsDTO params, final Path dest) throws IOException {
        retrieveDocuments(params, dest, true);
    }

    private void retrieveDocuments(final InputParamsDTO params, final Path dest, final boolean isMultiFile) throws IOException {
        final long startMillis = System.currentTimeMillis();

        LOGGER.info("START DOCUMENT RETRIEVE @{} FOR -> {} - zip into {}", new Date(), params, dest.toString());
        LOGGER.info("OPERATION TYPE {}", params.getTipoDocumento());

        final Scan[] scanners = prepareScanners(params);

        boolean finished = false;
        final HBaseConnectionParams[] connParameses = getConfiguredConnectionParams(this.hbaseProperties);
        for (final HBaseConnectionParams connParams : connParameses) {
            LOGGER.info("CHECKING AVAILABLE CLUSTER AVAILABILITY {}", connParams);
            if (isClusterAvailable(connParams)) {
                LOGGER.info("SELECTED CLUSTER {}", connParams);
                try (MultiPartZipOutputStream zos = isMultiFile ? MultiPartZipOutputStream.newMultiPartOutputStream(dest, this.zipPartSizeBytes)
                        : MultiPartZipOutputStream.newSingleFileMultiPartZipOutputStream(dest)) {
                    final StatsAndManifest manifest = new StatsAndManifest();
                    manifest.appendToManifest(params.toString());

                    try (Connection connection = ConnectionFactory.createConnection(connParams.getHBaseConfigurationForRead());
                            Table table = connection.getTable(this.tableName)) {
                        for (final Scan scan : scanners) {
                            try (ResultScanner scanResult = table.getScanner(scan)) {
                                zipScannerContent(zos, scanResult, manifest);
                            }
                        }

                        if (manifest.getProcessedEntries() == 0) {
                            manifest.appendToManifest(NO_FILES_FOUND_MESSAGE);
                        } else {
                            manifest.appendToManifest(String.format("Found %d entries & Zipped in %d files with %d ms duration",
                                    manifest.getProcessedEntries(), zos.getCurrentZipPart(), System.currentTimeMillis() - startMillis));
                        }
                        finished = true;
                        if (isMultiFile) {
                            writeManifest(dest, manifest);
                        } else {
                            zipManifest(zos, manifest);
                        }
                        LOGGER.info("RETRIEVE ENDED @{} in {} ms - {}", new Date(), System.currentTimeMillis() - startMillis, manifest);
                        break;
                    } catch (final RetriesExhaustedException ex) {
                        LOGGER.error("CONNECTION RETIRES EXHAUSTED", ex);
                        LOGGER.error("LOST CONNECTION WITH CLUSTER {}");
                        LOGGER.error("WILL RETRY WITH NEXT AVAILABLE CLUSTER");
                        finished = false;
                    }
                }
            }
        }

        if (!finished) {
            LOGGER.error("THERE WERE NO CLUSTERS AVAILABLE TO FULFILL THE REQUEST");
            final StatsAndManifest manifest = new StatsAndManifest();
            manifest.appendToManifest(params.toString());
            final String message = String.format("DATA REPOSITORIES BECAME UNAVAILABLE... %n" + "DATA MIGHT BE INCOMPLETE %n"
                    + "PLEASE TRY AGAIN LATER OR CONTACT YOUR SYSTEM ADMINISTRATOR");
            manifest.appendToManifest(message);
            if (isMultiFile) {
                writeManifest(dest, manifest);
            } else {
                try (MultiPartZipOutputStream zos = MultiPartZipOutputStream.newSingleFileMultiPartZipOutputStream(dest)) {
                    zipManifest(zos, manifest);
                }
            }
            LOGGER.info("RETRIEVE ENDED WITH ERRORS @{} in {} ms", new Date(), System.currentTimeMillis() - startMillis);
        }
    }

    private static HBaseConnectionParams[] getConfiguredConnectionParams(final Properties hbaseProperties) {
        final String[] zookeepers = hbaseProperties.getProperty(Param.HBASE_ZOOKEEPERS_HOSTS.toString(), "").split("#");
        final String[] ports = hbaseProperties.getProperty(Param.HBASE_ZOOKEEPERS_PORTS.toString(), "").split("#");

        if (zookeepers.length == 0 || ports.length == 0 || zookeepers.length != ports.length) {
            throw new IllegalArgumentException("Missing HBase Zookper configuration, check properties");
        }

        final HBaseConnectionParams[] connParams = new HBaseConnectionParams[zookeepers.length];
        for (int i = 0; i < zookeepers.length; i++) {
            connParams[i] = new HBaseConnectionParams(zookeepers[i], ports[i]);
        }
        return connParams;
    }

    /**
     * Chequea si cluster available.
     *
     * @param params
     *            params
     * @return true, si cluster available
     */
    public static boolean isClusterAvailable(final HBaseConnectionParams params) {
        final Configuration hConf = params.getHBaseConfigurationForTest();
        try {
            HBaseAdmin.checkHBaseAvailable(hConf);
        } catch (IOException | ServiceException ex) {
            return false;
        }
        return true;
    }

    private Scan[] prepareScanners(final InputParamsDTO params) {
        LOGGER.info("PREPARING SCANNERS");
        short tipoDoc;
        try {
            final TipoDocumento td = TipoDocumento.valueOf(params.getTipoDocumento());
            tipoDoc = td.getCodeValue();
        } catch (final IllegalArgumentException e) {
            tipoDoc = 99;
        }

        Scan[] scanners;
        if (InputParamsDTO.isOperationSpecific(params)) {
            scanners = getScannersForOperation(tipoDoc, params.getCodTiendas().toArray(new Integer[0])[0], params.getCaja(),
                    params.getFechaDesde().formattedAs("yyyyMMdd"), params.getCodOperacion());
        } else {
            scanners = getScannersForStoresAndDateRange(tipoDoc, params.getFechaDesde().formattedAs("yyyyMMdd"),
                    params.getFechaHasta().formattedAs("yyyyMMdd"), params.getCodTiendas().toArray(new Integer[0]));
        }
        return scanners;
    }

    private Scan[] getScannersForOperation(final short tipoDoc, final int store, final int register, final String date, final long operation) {
        LOGGER.info("Preparing specific Operation Scanner for store {}, register {}, date {} and with cod {}", store, register, date, operation);
        final String rowKeyStr = String.format("%02d/%06d/%s/%02d", tipoDoc, store, date, register);
        LOGGER.info("RowKey value {}", rowKeyStr);
        final byte[] rowKey = rowKeyStr.getBytes();
        final Scan scan = new Scan(new Get(rowKey));
        scan.addFamily(COLUMN_FAMILY_XMLS);
        scan.setFilter(new ColumnPrefixFilter((XML_DOCUMENT_QUALIFIER_PREFIX + Long.toString(operation) + COMPONENT_SEPARATOR).getBytes()));
        return new Scan[] { scan };
    }

    private Scan[] getScannersForStoresAndDateRange(final short tipoDoc, final String dateStart, final String dateEnd, final Integer... stores) {
        LOGGER.info("Multiple Operation Fectch for date range [{}-{}], for stores {}", dateStart, dateEnd, Arrays.toString(stores));
        final List<Scan> scanners = new ArrayList<>();
        for (final int store : stores) {
            final String startRowKey = String.format("%02d/%06d/%s/%d", tipoDoc, store, dateStart, 00);
            final String stopRowKey = String.format("%02d/%06d/%s/%d", tipoDoc, store, dateEnd, 99);
            LOGGER.info("Preparing scan for keyRange [{} - {}]", startRowKey, stopRowKey);
            final Scan scan = new Scan(startRowKey.getBytes(), Bytes.add(stopRowKey.getBytes(), new byte[] { 0x00 })).addFamily(COLUMN_FAMILY_XMLS);
            // RegexStringComparator comp = new RegexStringComparator("D/.*");
            // scan.setFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL, comp));
            scan.setFilter(new ColumnPrefixFilter(XML_DOCUMENT_QUALIFIER_PREFIX.getBytes()));
            scanners.add(scan);
        }
        return scanners.toArray(new Scan[0]);
    }

    private void zipScannerContent(final MultiPartZipOutputStream zos, final ResultScanner scanner, final StatsAndManifest manifest)
            throws IOException {
        // TD/TIENDA/CAJA/FD(yyyy)/FD(MM)/FD(dd)/TIENDA_CAJA_ID_FI(yyyyMMddHHmmss.SSSS).xml

        Result next = scanner.next();
        while (next != null) {
            final byte[] row = next.getRow();
            final String[] keyParts = new String(row).split(COMPONENT_SEPARATOR);
            // REMOVES PADDING
            final String tipoDoc = TipoDocumento.fromCode(Short.parseShort(keyParts[0])).toString();
            final int store = Integer.parseInt(keyParts[1]);
            final int year = Integer.parseInt(keyParts[2].substring(0, 4));
            final int month = Integer.parseInt(keyParts[2].substring(4, 6));
            final int day = Integer.parseInt(keyParts[2].substring(6));
            final int register = Integer.parseInt(keyParts[3]);
            final Path folderPath = Paths.get(tipoDoc, "" + store, "" + register, "" + year, "" + month, "" + day);

            final NavigableMap<byte[], byte[]> xmls = next.getFamilyMap(COLUMN_FAMILY_XMLS);
            for (final Entry<byte[], byte[]> entry : xmls.entrySet()) {
                final String[] qualifierParts = new String(entry.getKey()).split(COMPONENT_SEPARATOR);
                final StringBuilder sb = new StringBuilder();
                sb.append(store).append("_").append(register).append("_").append(qualifierParts[1]).append("_").append(qualifierParts[2])
                        .append(".xml");
                final java.nio.file.Path filePath = folderPath.resolve(sb.toString());
                zipContent(zos, filePath.toString(), entry.getValue(), manifest);
            }
            next = scanner.next();
        }
    }

    private void zipContent(final MultiPartZipOutputStream zip, final String filePath, final byte[] content, final StatsAndManifest manifest)
            throws IOException {
        final ZipEntry entry = new ZipEntry(filePath);
        zip.zipEntry(entry, content);
        manifest.appendFileEntry(entry);
    }

    private void zipManifest(final MultiPartZipOutputStream zip, final StatsAndManifest manifest) throws IOException {
        final ZipEntry entry = new ZipEntry(MANIFEST_FILENAME);
        entry.setComment("Contains the list of files found");
        manifest.appendFileEntry(entry);
        zip.zipEntry(entry, manifest.getCurrentManifest().getBytes(UTF8));
    }

    private void writeManifest(final Path dest, final StatsAndManifest manifest) throws IOException {
        try (OutputStream os = Files.newOutputStream(dest.resolve(MANIFEST_FILENAME), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            os.write(manifest.getCurrentManifest().getBytes(UTF8));
        }
    }

    private static class MultiPartZipOutputStream implements AutoCloseable {

        private static final int DEFAULT_COMPRESSION_LEVEL = Deflater.BEST_COMPRESSION;
        private static final int BUFFER_SIZE = 8_000 * 4;
        private static final String PART_FILENAME_PREFIX = "zip-part-";
        private static final String PART_FILENAME_EXT = ".zip";
        private final long partSizeBytes;
        private final Path destFolder;
        private int nextPartCounter = 0;
        private long currentPartSize = 0;
        private final boolean isMultiPart;
        private ZipOutputStream currentZipPart;

        public static MultiPartZipOutputStream newSingleFileMultiPartZipOutputStream(final Path file) throws IOException {
            if (Files.isDirectory(file)) {
                throw new IllegalArgumentException("The provided path must not be a directory");
            }
            final MultiPartZipOutputStream stream = new MultiPartZipOutputStream(Integer.MAX_VALUE, file, false);
            final OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            final BufferedOutputStream bos = new BufferedOutputStream(os, BUFFER_SIZE);
            stream.currentZipPart = new ZipOutputStream(bos);
            stream.currentZipPart.setLevel(DEFAULT_COMPRESSION_LEVEL);
            stream.nextPartCounter++;
            return stream;
        }

        public static MultiPartZipOutputStream newMultiPartOutputStream(final Path folder, final long partSizeBytes) throws IOException {
            if (!Files.isDirectory(folder)) {
                throw new IllegalArgumentException("The provided path must be a directory");
            }
            final MultiPartZipOutputStream stream = new MultiPartZipOutputStream(partSizeBytes, folder, true);
            stream.currentZipPart = MultiPartZipOutputStream.openNewZipPart(folder, stream.nextPartCounter++);
            return stream;
        }

        private MultiPartZipOutputStream(final long partSizeBytes, final Path folder, final boolean isMultiPart) {
            this.partSizeBytes = partSizeBytes;
            this.destFolder = folder;
            this.isMultiPart = isMultiPart;
        }

        private static ZipOutputStream openNewZipPart(final Path folder, final int counter) throws IOException {
            final Path file = folder.resolve(String.format("%s%04d%s", PART_FILENAME_PREFIX, counter, PART_FILENAME_EXT));
            final OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            final BufferedOutputStream bos = new BufferedOutputStream(os, BUFFER_SIZE);
            final ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
            zipOutputStream.setLevel(DEFAULT_COMPRESSION_LEVEL);
            return zipOutputStream;
        }

        public void zipEntry(final ZipEntry entry, final byte[] content) throws IOException {
            if (this.isMultiPart && this.currentPartSize >= this.partSizeBytes) {
                close();
                this.currentZipPart = openNewZipPart(this.destFolder, this.nextPartCounter++);
                this.currentPartSize = 0;
            }

            this.currentZipPart.putNextEntry(entry);
            this.currentZipPart.write(content);
            this.currentZipPart.closeEntry();

            if (this.isMultiPart) {
                this.currentPartSize += entry.getSize();
            }
        }

        @Override
        public void close() throws IOException {
            this.currentZipPart.flush();
            this.currentZipPart.close();
        }

        public int getCurrentZipPart() {
            // 0-based counter
            return this.nextPartCounter;
        }
    }

    private static class StatsAndManifest {

        private static final String RETURN_CARRIAGE = String.format("%n");
        private long processedFileEntries;
        private final StringBuilder manifest = new StringBuilder();
        private long unCompressedSize;
        private long compressedSize;

        public void appendFileEntry(final ZipEntry entry) {
            this.processedFileEntries++;
            appendToManifest(entry.getName());
            this.compressedSize += entry.getCompressedSize();
            this.unCompressedSize += entry.getSize();
        }

        public void appendToManifest(final String message) {
            this.manifest.append(message).append(RETURN_CARRIAGE);
        }

        public String getCurrentManifest() {
            return this.manifest.toString();
        }

        public long getUnCompressedBytes() {
            return this.unCompressedSize;
        }

        public long getCompressedBytes() {
            return this.compressedSize;
        }

        public long getProcessedEntries() {
            return this.processedFileEntries;
        }

        @Override
        public String toString() {
            return "{" + "processedFileEntries=" + this.processedFileEntries + ", unCompressedSize=" + this.unCompressedSize + ", compressedSize="
                    + this.compressedSize + '}';
        }
    }

}