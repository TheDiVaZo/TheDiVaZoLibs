package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SourceFactorySQLite<T extends SQLDatabaseConfig> implements DataSourceFactory<T> {
    @Override
    public DataSource toDataSource(T config) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        if (config.explicitUrl()) {
            String url = String.format("jdbc:sqlite:%s", config.getDatabaseName());
            dataSource.setUrl(url);
        }
        else {
            dataSource.setDatabaseName(config.getDatabaseName());
        }
        return dataSource;
    }
}
