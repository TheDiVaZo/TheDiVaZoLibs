package me.thedivazo.libs.database.configsource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public interface MonoDatabaseConfig extends MultiDatabaseConfig {
    String getHost();
    String getPort();
}
