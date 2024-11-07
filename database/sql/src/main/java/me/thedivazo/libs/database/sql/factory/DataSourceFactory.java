package me.thedivazo.libs.database.sql.factory;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public interface DataSourceFactory<T extends SQLDatabaseConfig> {
    DataSource toDataSource(T config);
}
