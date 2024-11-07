package me.thedivazo.libs.database.configsource;

import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@AllArgsConstructor
@Getter
public class HikariConfigParamImpl implements HikariConfigParam {
    private final int minIdle;
    private final int maxPoolSize;
    private final long maxLifeTime;
    private final long connectionTimeOut;
    private final long validationTimeout;
    private final long idleTimeOut;
    private final boolean isAutoCommit;

    public HikariConfigParamImpl() {
        this.minIdle = 10;
        this.maxPoolSize = 10;
        this.maxLifeTime = TimeUnit.MINUTES.toMillis(30L);
        this.connectionTimeOut = TimeUnit.SECONDS.toMillis(30L);
        this.validationTimeout = TimeUnit.SECONDS.toMillis(5L);
        this.idleTimeOut = TimeUnit.MINUTES.toMillis(10L);
        this.isAutoCommit = true;
    }
}
