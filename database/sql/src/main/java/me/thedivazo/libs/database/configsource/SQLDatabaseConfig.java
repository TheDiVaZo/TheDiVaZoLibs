package me.thedivazo.libs.database.configsource;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс конфигурации используемый для подключения к релиационным базам
 */
public interface SQLDatabaseConfig extends DatabaseConfig {
    String getDatabaseName();
}