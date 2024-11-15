package me.thedivazo.libs.database.sql.connection.factory;

import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;
import lombok.Setter;
import me.thedivazo.libs.database.configsource.HikariConfigParam;
import me.thedivazo.libs.database.sql.connection.ConnectionPool;
import me.thedivazo.libs.database.sql.connection.HikariConnectionPool;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
@AllArgsConstructor
@Setter
public class HikariConnectionPoolFactory implements ConnectionPoolFactory {
    private HikariConfigParam hikariConfigParam;

    @Override
    public ConnectionPool create(DataSource dataSource) {
        HikariConfig hikariConfig = hikariConfigParam.toHikari();
        hikariConfig.setDataSource(dataSource);
        return new HikariConnectionPool(hikariConfig);
    }

}