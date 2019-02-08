package by.gstu.airline.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Singleton.
 * The class allows read *.property files.
 */
public enum ConfigurationManager {
    INSTANCE;
    private static final Logger log = LogManager.getLogger(ConfigurationManager.class);
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

    /**
     * Read localized string by key from resource/locale.property file.
     *
     * @param key the key for the desired localized string
     * @return localized string
     */
    public String getText(String key) {
        if (localeBundle.containsKey(key)) {
            return convert(localeBundle.getString(key));
        }
        log.error("missing property \"" + key + "\" in file: resource/locale.properties");
        return "";
    }

    /**
     * Read SQL query by key from resource/sql.properties file.
     *
     * @param key the key for the desired localized query
     * @return SQL query string
     */
    public String getQuery(String key) {
        try {
            return sqlBundle.getString(key);
        } catch (MissingResourceException e) {
            log.error("missing property \"" + key + "\" in file: resource/sql.properties", e);
            throw e;
        }
    }

    /**
     * Read database connection pframeters from resources/database.properties.
     *
     * @param key the key for the desired property
     * @return database connection property
     */
    public String getProperty(String key) {
        try {
            return databaseBundle.getString(key);
        } catch (MissingResourceException e) {
            log.error("missing property \"" + key + "\" in file: resource/database.properties", e);
            throw e;
        }
    }

    private String convert(String text) {
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
