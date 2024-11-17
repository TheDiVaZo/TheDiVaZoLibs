package me.thedivazo.libs.database.sql.connection;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author TheDiVaZo
 * created on 06.11.2024
 */
public interface ConnectionPool {
    DataSource getPooledDataSource();
    Connection getConnection();
    void closePool();
}
