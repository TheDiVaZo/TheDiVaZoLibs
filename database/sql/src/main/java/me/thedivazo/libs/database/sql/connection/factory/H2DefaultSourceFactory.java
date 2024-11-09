package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class H2DefaultSourceFactory<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements DataSourceFactory<T> {
    @Override
    public DataSource toDataSource(T config) {
        JdbcDataSource dataSource = new JdbcDataSource();
        StringBuilder url;
        if (config.explicitUrl()) {
            url = new StringBuilder(config.getUrl());
        }
        else if (config.getHost() != null && config.getPort() != null) {
            url = new StringBuilder(String.format("jdbc:h2:tcp://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabaseName()));
        }
        else {
            url = new StringBuilder(String.format("jdbc:h2:~/%s", config.getDatabaseName()));
        }

        config.getParams().forEach((key, value) -> url.append(String.format(";%s=%s", key, value)));

        dataSource.setURL(url.toString());
        dataSource.setUser(config.getUsername());
        dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
