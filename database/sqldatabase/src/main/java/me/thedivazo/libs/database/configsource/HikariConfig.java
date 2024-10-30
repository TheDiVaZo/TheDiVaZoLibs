package me.thedivazo.libs.database.configsource;

import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@AllArgsConstructor
public class HikariConfig {
    private final int minIdle;
    private final int maxPoolSize;
    private final long maxLifeTime;
    private final long connectionTimeOut;
    private final long validationTimeout;
    private final long idleTimeOut;
    private final boolean isAutoCommit;

    public HikariConfig() {
        this.minIdle = 10;
        this.maxPoolSize = 10;
        this.maxLifeTime = TimeUnit.MINUTES.toMillis(30L);
        this.connectionTimeOut = TimeUnit.SECONDS.toMillis(30L);
        this.validationTimeout = TimeUnit.SECONDS.toMillis(5L);
        this.idleTimeOut = TimeUnit.MINUTES.toMillis(10L);
        this.isAutoCommit = true;
    }

    public com.zaxxer.hikari.HikariConfig toHikari() {
        com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
        config.setAutoCommit(this.isAutoCommit);
        config.setMinimumIdle(this.minIdle);
        config.setMaximumPoolSize(this.maxPoolSize);
        config.setMaxLifetime(this.maxLifeTime);
        config.setConnectionTimeout(this.connectionTimeOut);
        config.setValidationTimeout(this.validationTimeout);
        config.setIdleTimeout(this.idleTimeOut);
        return config;
    }
}
