package me.thedivazo.libs.util;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UrlDatabaseUtilTest {

    @Test
    void generateUrlParams() {
        Map<String, String> urlParams = new LinkedHashMap<>(4);
        urlParams.put("ssl", "true");
        urlParams.put("sslmode", "require");
        urlParams.put("connectionTimeout", "10");
        urlParams.put("targetServerType", "primary");
        urlParams.put("fuckParam@#$%", "%$#@");

        String expected = "?ssl=true&sslmode=require&connectionTimeout=10&targetServerType=primary&fuckParam%40%23%24%25=%25%24%23%40";

        assertEquals(expected, UrlDatabaseUtil.generateUrlParams(urlParams));
    }
}