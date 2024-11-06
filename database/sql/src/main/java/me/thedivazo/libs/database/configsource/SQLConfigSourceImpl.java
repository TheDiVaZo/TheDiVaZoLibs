package me.thedivazo.libs.database.configsource;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 31.10.2024
 **/
@Getter
@AllArgsConstructor
public abstract class SQLConfigSourceImpl implements SQLConfigSource {
    private final String databaseName;
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final Map<String, String> params;
}
