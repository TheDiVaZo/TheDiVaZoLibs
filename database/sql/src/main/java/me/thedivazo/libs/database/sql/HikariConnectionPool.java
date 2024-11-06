package me.thedivazo.libs.database.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.thedivazo.libs.database.configsource.HikariConfigParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author TheDiVaZo
 * created on 06.11.2024
 */
public class HikariConnectionPool implements ConnectionPool {
    protected final HikariDataSource dataSource;

    public HikariConnectionPool(HikariConfig hikariConfig) {
        this.dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("An exception found due getting connection:", e);
        }
    }

    @Override
    public void close() {
        dataSource.close();
    }
}