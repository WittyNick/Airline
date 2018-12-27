package by.gstu.airline.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ConfigurationManager {
    INSTANCE;
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
        return convert(localeBundle.getString(key));
    }

    public String getQuery(String key) {
        return sqlBundle.getString(key);
    }

    public String getProperty(String key) {
        return databaseBundle.getString(key);
    }

    private String convert(String text) {
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
