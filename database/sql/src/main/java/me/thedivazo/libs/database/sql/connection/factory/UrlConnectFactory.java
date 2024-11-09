package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
public interface UrlConnectFactory<T extends SQLDatabaseConfig> {
    String createUrlConnection(T config);
    String getDriverClassName();
}
