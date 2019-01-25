package by.gstu.airline.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ConfigurationManager {
    INSTANCE;
    private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);
    private final String localePropertyFile = "resource.locale";
    private final String sqlPropertyFile = "resource.sql";
    private final String databasePropertyFile = "resource.database";
    private ResourceBundle localeBundle;
    private ResourceBundle sqlBundle;
    private ResourceBundle databaseBundle;

    ConfigurationManager() {
        localeBundle = ResourceBundle.getBundle(localePropertyFile, Locale.getDefault());
        sqlBundle = ResourceBundle.getBundle(sqlPropertyFile);
        databaseBundle = ResourceBundle.getBundle(databasePropertyFile);
    }

    public void changeLocale(Locale locale) {
        localeBundle = ResourceBundle.getBundle(localePropertyFile, locale);
    }

    public String getText(String key) {
        if (localeBundle.containsKey(key)) {
            return convert(localeBundle.getString(key));
        }
        LOG.warn("missing property \"" + key + "\" in file: resource/locale.properties");
        return "";
    }

    public String getQuery(String key) {
        if (sqlBundle.containsKey(key)) {
            return sqlBundle.getString(key);
        }
        LOG.error("missing property \"" + key + "\" in file: resource/sql.properties");
        return "";
    }

    public String getProperty(String key) {
        if (databaseBundle.containsKey(key)) {
            return databaseBundle.getString(key);
        }
        LOG.error("missing property \"" + key + "\" in file: resource/database.properties");
        return "";
    }

    private String convert(String text) {
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
