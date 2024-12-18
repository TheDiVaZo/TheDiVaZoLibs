package me.thedivazo.libs.database.jdbc.configsource;

import me.thedivazo.libs.database.configsource.DatabaseConfig;
import org.jetbrains.annotations.Nullable;

/**
 * @author TheDiVaZo
 * created on 27.10.2024
 *
 * Интерфейс конфигурации используемый для подключения к релиационным базам
 */
@Nullable
public interface SQLDatabaseConfig extends DatabaseConfig {
    String getDatabaseName();
}
