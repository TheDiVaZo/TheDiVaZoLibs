package me.thedivazo.libs.database.jdbc.connection;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public interface DataSourceFactory<T extends SQLDatabaseConfig> {
    JdbcSource createDaoSource(T config);
}
