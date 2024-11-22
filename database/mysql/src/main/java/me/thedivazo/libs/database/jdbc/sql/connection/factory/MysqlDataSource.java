package me.thedivazo.libs.database.jdbc.sql.connection.factory;

import me.thedivazo.libs.database.jdbc.connection.JdbcSourceImpl;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class MysqlDataSource extends JdbcSourceImpl {
    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }
}
