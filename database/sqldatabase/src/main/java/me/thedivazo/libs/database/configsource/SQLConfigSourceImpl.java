package me.thedivazo.libs.database.configsource;

import lombok.AllArgsConstructor;

/**
 * @author TheDiVaZo
 * created on 31.10.2024
 **/
@AllArgsConstructor
public abstract class SQLConfigSourceImpl implements SQLConfigSource {
    private final String databaseName;
    private final String host;
    private final String port;
    private final String username;
    private final String password;

    @Override
    public String getDatabaseName() {
        return "";
    }

    @Override
    public String getHost() {
        return "";
    }

    @Override
    public String getPort() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }
}
