package org.motechproject.server.config.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.util.Arrays;
import java.util.Properties;

import org.motechproject.server.config.service.PlatformSettingsService;

public class ConfigFileSettings implements MotechSettings {

    private byte[] md5checkSum;
    private URL fileURL;
    private Properties motechSettings = new Properties();
    private Properties activemq = new Properties();

    @Override
    public String getLanguage() {
        return motechSettings.getProperty(LANGUAGE);
    }

    @Override
    public String getStatusMsgTimeout() {
        return motechSettings.getProperty(STATUS_MSG_TIMEOUT);
    }

    @Override
    public String getLoginMode() {
        return motechSettings.getProperty(LOGINMODE);
    }

    @Override
    public String getProviderName() {
        return motechSettings.getProperty(PROVIDER_NAME);
    }

    @Override
    public String getProviderUrl() {
        return motechSettings.getProperty(PROVIDER_URL);
    }

    @Override
    public String getServerUrl() {
        return motechSettings.getProperty(SERVER_URL);
    }

    public byte[] getMd5checkSum() {
        return Arrays.copyOf(md5checkSum, md5checkSum.length);
    }

    public URL getFileURL() {
        return fileURL;
    }

    public void setFileURL(URL fileURL) {
        this.fileURL = fileURL;
    }

    public String getPath() {
        return getFileURL().getPath();
    }

    public synchronized void load(DigestInputStream inStream) throws IOException {
        motechSettings.load(inStream);
    }

    public synchronized void loadActiveMq(InputStream is) throws IOException {
        activemq.load(is);
    }

    @Override
    public Properties getActivemqProperties() {
        Properties activemqProperties = new Properties();
        activemqProperties.putAll(activemq);
        return activemqProperties;
    }

    @Override
    public Properties getQuartzProperties() {
        Properties quartzProperties = new Properties();

        putPropertyIfNotNull(quartzProperties, "org.quartz.scheduler.instanceName", motechSettings.getProperty(QUARTZ_SCHEDULER_NAME));
        putPropertyIfNotNull(quartzProperties, "org.quartz.threadPool.class", motechSettings.getProperty(QUARTZ_THREAD_POOL_CLASS));
        putPropertyIfNotNull(quartzProperties, "org.quartz.threadPool.threadCount", motechSettings.getProperty(QUARTZ_THREAD_POOL_THREAD_COUNT));
        putPropertyIfNotNull(quartzProperties, "org.quartz.jobStore.class", motechSettings.getProperty(QUARTZ_JOB_STORE_CLASS));

        return quartzProperties;
    }

    @Override
    public Properties getMetricsProperties() {
        Properties metricsProperties = new Properties();

        putPropertyIfNotNull(metricsProperties, GRAPHITE_URL, motechSettings.getProperty(GRAPHITE_URL));

        return metricsProperties;
    }

    @Override
    public Properties getSchedulerProperties() {
        Properties schedulerProperties = new Properties();

        putPropertyIfNotNull(schedulerProperties, SCHEDULER_URL, motechSettings.getProperty(SCHEDULER_URL));

        return schedulerProperties;
    }

    private static void putPropertyIfNotNull(Properties properties, String key, Object value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    public void setMd5checksum(byte[] digest) {
        this.md5checkSum = Arrays.copyOf(digest, digest.length);
    }

    public void saveMotechSetting(String key, String value) {
        motechSettings.put(key, value);
    }

    public void storeMotechSettings() throws FileNotFoundException, IOException {
        File file = new File(getPath() + File.separator + PlatformSettingsService.SETTINGS_FILE_NAME);
        motechSettings.store(new FileOutputStream(file), null);
    }

    public Properties getAll() {
        Properties copy = new Properties();
        copy.putAll(motechSettings);
        copy.putAll(activemq);

        return copy;
    }

    public void saveActiveMqSetting(String key, String value) {
        activemq.put(key, value);
    }

    public Properties getMotechSettings() {
        return motechSettings;
    }

    public void storeActiveMqSettings() throws FileNotFoundException, IOException {
        File file = new File(getPath() + File.separator + PlatformSettingsService.ACTIVEMQ_FILE_NAME);
        activemq.store(new FileOutputStream(file), null);
    }
}
