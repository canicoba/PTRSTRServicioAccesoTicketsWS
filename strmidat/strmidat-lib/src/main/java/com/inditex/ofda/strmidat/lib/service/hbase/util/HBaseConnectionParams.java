/**
 * HBaseConnectionParams.java 01-jul-2016
 *
 * Copyright 2016 INDITEX.
 * Departamento de Sistemas
 */
package com.inditex.ofda.strmidat.lib.service.hbase.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;

/**
 * The Class HBaseConnectionParams.
 *
 * @author <a href="dennis.federico@oracle.com">Dennis Federico</a>
 */
public class HBaseConnectionParams {

    private final String zookeeperQuorum;
    private final String zookeeperPort;

    private static final Configuration TEST_HBASE_CONNECTION_TEMPLATE = buildHBaseTestConnectionTemplate();
    private static final Configuration WRITE_HBASE_CONNECTION_TEMPLATE = buildHBaseWriteConnectionTemplate();
    private static final Configuration READ_HBASE_CONNECTION_TEMPLATE = buildHBaseReadConnectionTemplate();

    private static Configuration buildHBaseTestConnectionTemplate() {
        final Configuration conf = HBaseConfiguration.create();
        conf.clear();
        conf.set("zookeeper.recovery.retry", "0");
        conf.set(HConstants.ZK_SESSION_TIMEOUT, "5000");
        conf.set(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT, "2000");
        conf.set(HConstants.HBASE_CLIENT_RETRIES_NUMBER, "1");
        conf.set(HConstants.HBASE_CLIENT_PAUSE, "1000");
        return conf;
    }

    private static Configuration buildHBaseWriteConnectionTemplate() {
        final Configuration conf = HBaseConfiguration.create();
        conf.clear();
        conf.set("zookeeper.recovery.retry", "3");
        conf.set(HConstants.ZK_SESSION_TIMEOUT, "90000");
        conf.set(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT, "15000");
        conf.set(HConstants.HBASE_CLIENT_RETRIES_NUMBER, "10");
        conf.set(HConstants.HBASE_CLIENT_PAUSE, "500");
        return conf;
    }

    private static Configuration buildHBaseReadConnectionTemplate() {
        final Configuration conf = HBaseConfiguration.create();
        conf.clear();
        conf.set("zookeeper.recovery.retry", "3");
        conf.set(HConstants.ZK_SESSION_TIMEOUT, "90000");
        conf.set(HConstants.HBASE_CLIENT_OPERATION_TIMEOUT, "15000");
        conf.set(HConstants.HBASE_CLIENT_RETRIES_NUMBER, "10");
        conf.set(HConstants.HBASE_CLIENT_PAUSE, "1000");
        conf.set(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, "180000");
        return conf;
    }

    /**
     * Obtiene h base configuration for test.
     *
     * @return h base configuration for test
     */
    public Configuration getHBaseConfigurationForTest() {
        return getConfigurationForTemplate(TEST_HBASE_CONNECTION_TEMPLATE);
    }

    /**
     * Obtiene h base configuration for write.
     *
     * @return h base configuration for write
     */
    public Configuration getHBaseConfigurationForWrite() {
        return getConfigurationForTemplate(WRITE_HBASE_CONNECTION_TEMPLATE);
    }

    /**
     * Obtiene h base configuration for read.
     *
     * @return h base configuration for read
     */
    public Configuration getHBaseConfigurationForRead() {
        return getConfigurationForTemplate(READ_HBASE_CONNECTION_TEMPLATE);
    }

    private Configuration getConfigurationForTemplate(final Configuration template) {
        final Configuration config = HBaseConfiguration.create();
        config.clear();
        config.addResource(template);
        config.set(HConstants.ZOOKEEPER_QUORUM, this.zookeeperQuorum);
        config.set(HConstants.ZOOKEEPER_CLIENT_PORT, this.zookeeperPort);
        return config;
    }

    /**
     * Instancia un nuevo h base connection params.
     *
     * @param zookeeperQuorum
     *            zookeeper quorum
     * @param zookeeperPort
     *            zookeeper port
     */
    public HBaseConnectionParams(final String zookeeperQuorum, final String zookeeperPort) {
        this.zookeeperQuorum = zookeeperQuorum;
        this.zookeeperPort = zookeeperPort;
    }

    /**
     * Obtiene zookeeper quorum.
     *
     * @return zookeeper quorum
     */
    public String getZookeeperQuorum() {
        return this.zookeeperQuorum;
    }

    /**
     * Obtiene zookeeper port.
     *
     * @return zookeeper port
     */
    public String getZookeeperPort() {
        return this.zookeeperPort;
    }

    /**
     * Obtiene connection configuration.
     *
     * @param base
     *            base
     * @return connection configuration
     */
    public Configuration getConnectionConfiguration(final Configuration base) {
        base.set(HConstants.ZOOKEEPER_QUORUM, this.zookeeperQuorum);
        base.set(HConstants.ZOOKEEPER_CLIENT_PORT, this.zookeeperPort);
        return base;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "HBaseConnectionParams{" + "zookeeperQuorum=" + this.zookeeperQuorum + ", zookeeperPort=" + this.zookeeperPort + '}';
    }

}