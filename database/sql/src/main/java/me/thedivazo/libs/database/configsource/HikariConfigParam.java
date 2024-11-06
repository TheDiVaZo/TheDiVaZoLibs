package me.thedivazo.libs.database.configsource;

import com.zaxxer.hikari.HikariConfig;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 31.10.2024
 * <p>
 * Интерфейс для получения конфигурации HikariCP
 */
public interface HikariConfigParam {
    HikariConfig toHikari(DataSource dataSource);

    int getMinIdle();

    int getMaxPoolSize();

    long getMaxLifeTime();

    long getConnectionTimeOut();

    long getValidationTimeout();

    long getIdleTimeOut();

    boolean isAutoCommit();
}
