package me.thedivazo.libs.database.sql.factory;

import com.mysql.cj.jdbc.MysqlDataSource;
import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SourceFactoryMySQL<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements DataSourceFactory<T> {

    @Override
    public DataSource toDataSource(T config) {
        MysqlDataSource dataSource = new MysqlDataSource();
        if (config.explicitUrl()) {
            dataSource.setURL(config.getUrl());
        }
        else {
            dataSource.setServerName(config.getHost());
            dataSource.setPort(Integer.parseInt(config.getPort()));
            dataSource.setDatabaseName(config.getDatabaseName());
        }
            dataSource.setUser(config.getUsername());
            dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
