package me.thedivazo.libs.database.configsource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public interface MultiDatabaseConfig extends DatabaseConfig {
    String[] getHosts();
    String[] getPorts();
}
