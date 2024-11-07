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
    int getMinIdle();

    int getMaxPoolSize();

    long getMaxLifeTime();

    long getConnectionTimeOut();

    long getValidationTimeout();

    long getIdleTimeOut();

    boolean isAutoCommit();

    default HikariConfig toHikari() {
        HikariConfig config = new HikariConfig();
        config.setAutoCommit(isAutoCommit());
        config.setMinimumIdle(getMinIdle());
        config.setMaximumPoolSize(getMaxPoolSize());
        config.setMaxLifetime(getMaxLifeTime());
        config.setConnectionTimeout(getConnectionTimeOut());
        config.setValidationTimeout(getValidationTimeout());
        config.setIdleTimeout(getIdleTimeOut());
        return config;
    }
}
