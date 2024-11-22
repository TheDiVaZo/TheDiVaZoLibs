package me.thedivazo.libs.database.jdbc.sql.connection.factory;

import me.thedivazo.libs.database.jdbc.connection.JdbcSourceImpl;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
public class PostgreSqlDataSource extends JdbcSourceImpl {

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }
}
