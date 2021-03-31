package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64Helper {

    private static final Pattern DATA_URL_PATTERN = Pattern.compile("^data:image/(.+?);base64,\\s*", Pattern.CASE_INSENSITIVE);

    static boolean isBase64 (String path) {
        if (path.startsWith("data:")) {
            final Matcher m = DATA_URL_PATTERN.matcher(path);
            if (m.find()) {
                String imageType = m.group(1);
                String base64 = path.substring(m.end());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
