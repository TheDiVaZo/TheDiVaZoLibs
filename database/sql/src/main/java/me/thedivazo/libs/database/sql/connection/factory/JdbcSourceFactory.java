package me.thedivazo.libs.database.sql.connection.factory;

import lombok.AllArgsConstructor;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import me.thedivazo.libs.database.sql.connection.DefaultJDBCSource;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 09.11.2024
 */
@AllArgsConstructor
public class JdbcSourceFactory<T extends SQLDatabaseConfig> implements DataSourceFactory<T> {
    private final JdbcUrlFactory<T> jdbcUrlFactory;

    @Override
    public DataSource toDataSource(T config) {
        DefaultJDBCSource dataSource = new DefaultJDBCSource(jdbcUrlFactory.getDriverClassName());
        String url = jdbcUrlFactory.createUrlConnection(config);
        dataSource.setUrl(url);
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
