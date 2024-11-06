package me.thedivazo.libs.database.configsource;

import com.zaxxer.hikari.HikariConfig;

/**
 * @author TheDiVaZo
 * created on 31.10.2024
 * <p>
 * Интерфейс для получения конфигурации HikariCP
 */
public interface HikariConfigSource {
    HikariConfig toHikari();

    int getMinIdle();

    int getMaxPoolSize();

    long getMaxLifeTime();

    long getConnectionTimeOut();

    long getValidationTimeout();

    long getIdleTimeOut();

    boolean isAutoCommit();
}
