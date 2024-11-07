package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.MultiDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SourceFactoryPostgreSQL<T extends SQLDatabaseConfig & MultiDatabaseConfig> implements DataSourceFactory<T> {
    @Override
    public DataSource toDataSource(T config) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        if (config.explicitUrl()) {
            dataSource.setURL(config.getUrl());
        } else {
            dataSource.setServerNames(config.getHosts());
            dataSource.setPortNumbers(Arrays.stream(config.getPorts()).mapToInt(Integer::parseInt).toArray());
            dataSource.setDatabaseName(config.getDatabaseName());
        }
        dataSource.setUser(config.getUsername());
        dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
