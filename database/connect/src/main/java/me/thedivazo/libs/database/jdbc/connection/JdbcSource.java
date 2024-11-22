package me.thedivazo.libs.database.jdbc.connection;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 17.11.2024
 */
public interface JdbcSource extends DataSource {
    String getDriverClassName();
    void installDriver();
}
