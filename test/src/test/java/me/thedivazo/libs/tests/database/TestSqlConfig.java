package me.thedivazo.libs.tests.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.jdbc.configsource.SQLDatabaseConfig;

import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
@Getter
@AllArgsConstructor
public class TestSqlConfig implements SQLDatabaseConfig, MonoDatabaseConfig {
    private final String databaseName;
    private final String url;
    private final String username;
    private final String password;
    private final String[] hosts;
    private final String[] ports;
    private final String host;
    private final String port;
    private final Map<String, String> params;
}
