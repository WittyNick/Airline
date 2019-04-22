package by.gstu.airline.config;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Singleton.
 * The class allows read *.property files.
 */
public enum ConfigurationManager {
    INSTANCE;
    private final String localePropertyFile = "resource.locale";
    private final String sqlPropertyFile = "resource.sql";
    private final String databasePropertyFile = "resource.database";
    private final ResourceBundle sqlBundle = ResourceBundle.getBundle(sqlPropertyFile);
    private final ResourceBundle databaseBundle = ResourceBundle.getBundle(databasePropertyFile);

    /**
     * Reads SQL query by key from resource/sql.properties file.
     *
     * @param key the key for the desired localized query
     * @return SQL query string
     */
    public String getQuery(String key) {
        return sqlBundle.getString(key);
    }

    /**
     * Reads database connection parameters from resources/database.properties.
     *
     * @param key the key for the desired property
     * @return database connection property
     */
    public String getProperty(String key) {
        return databaseBundle.getString(key);
    }

    /**
     * Reads localized word from resource file and returns in as String.
     *
     * @param locale current user locale
     * @param key key-word that needs to be localized
     * @return localized word
     */
    public String getText(Locale locale, String key) {
        return getTextMap(locale, key).get(key);
    }

    /**
     * Reads localized words from resource file and returns Mat<String, String>
     *     where key is key-word, value is localized word.
     *
     * @param locale locale of current user
     * @param keys array of key-words that needs to be localized
     * @return Map of localized words
     */
    public Map<String, String> getTextMap(Locale locale, String... keys) {
        ResourceBundle localeBundle = ResourceBundle.getBundle(localePropertyFile, locale, new UTF8Control());
        Map<String, String> map = new HashMap<>();
        for (String key : keys) {
            String text = "";
            if (localeBundle.containsKey(key)) {
//                text = convert(localeBundle.getString(key));
                text = localeBundle.getString(key);
            }
            map.put(key, text);
        }
        return map;
    }

    private String convert(String text) {
        return new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
