package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.sql.connection.ConnectionPool;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public interface ConnectionPoolFactory {
    ConnectionPool create(DataSource dataSource);
}
