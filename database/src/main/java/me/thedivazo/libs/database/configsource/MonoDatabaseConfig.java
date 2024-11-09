package me.thedivazo.libs.database.configsource;

import org.jetbrains.annotations.Nullable;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
@Nullable
public interface MonoDatabaseConfig extends MultiDatabaseConfig {
    String getHost();
    String getPort();
}
