package me.thedivazo.libs.database.configsource;

import org.jetbrains.annotations.Nullable;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
@Nullable
public interface MultiDatabaseConfig extends DatabaseConfig {
    String[] getHosts();
    String[] getPorts();
}
