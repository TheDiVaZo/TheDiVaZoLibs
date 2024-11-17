package me.thedivazo.libs.database.sql.connection;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public interface DataSourceFactory<T extends SQLDatabaseConfig> {
    JdbcSource createDaoSource(T config);
}
