package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class SourceFactoryH2<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements DataSourceFactory<T>  {
    @Override
    public DataSource toDataSource(T config) {
        JdbcDataSource dataSource = new JdbcDataSource();
        String url;
        if (config.explicitUrl()) {
            url = config.getUrl();
        }
        else if (config.getHost() != null && config.getPort() != null) {
            url = String.format("jdbc:h2:tcp://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabaseName());
        }
        else {
            url = String.format("jdbc:h2:~/%s", config.getDatabaseName());
        }
        dataSource.setURL(url);
        dataSource.setUser(config.getUsername());
        dataSource.setPassword(config.getPassword());
        return dataSource;
    }
}
