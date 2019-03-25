package by.gstu.airline.controller.servlet.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Utility class.
 * Contains methods for creation html pages templates.
 */
public class HtmlTemplateEngine {
    private static final Logger log = LogManager.getLogger(HtmlTemplateEngine.class);

    private HtmlTemplateEngine() {}

    /**
     * Read html template from file and create html page as String.
     * @param fileName html template full file name.
     * @param map template parameters.
     * @return html page as String.
     */
    public static String getHtmlPage(String fileName, Map<String, String> map) {
        String page = readFile(fileName);
        for(Map.Entry<String, String> pair : map.entrySet()) {
            page = page.replaceAll(pair.getKey(), pair.getValue());
        }
        return page;
    }

    private static String readFile(String fileName) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                fileContent.append(reader.readLine());
            }
        } catch (IOException e) {
            log.error(e);
        }
        return fileContent.toString();
    }
}
