package me.thedivazo.libs.database.sql;

import java.sql.Connection;

/**
 * @author TheDiVaZo
 * created on 06.11.2024
 */
public interface ConnectionPool extends AutoCloseable {
    Connection getConnection();
}
