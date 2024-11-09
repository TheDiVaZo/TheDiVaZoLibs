package me.thedivazo.libs.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlDatabaseUtil {
    public static String generateUrlParams(Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            urlBuilder.append("?");
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) {
                    urlBuilder.append("&");
                } else {
                    first = false;
                }
                String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
                String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
                urlBuilder.append(key).append("=").append(value);
            }
        }
        return urlBuilder.toString();
    }
}
