package me.thedivazo.libs.database.jdbc.sql.connection.factory;

import lombok.NoArgsConstructor;
import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.jdbc.configsource.SQLDatabaseConfig;
import me.thedivazo.libs.database.jdbc.connection.DataSourceFactory;
import me.thedivazo.libs.database.jdbc.connection.JdbcSource;
import me.thedivazo.libs.database.util.UrlDatabaseUtil;

/**
 * @author TheDiVaZo
 * created on 18.11.2024
 */
@NoArgsConstructor
public class MysqlDataSourceFactory<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements DataSourceFactory<T> {

    protected boolean validConfig(T config) {
        if (config.explicitUrl()) return true;
        return config.getHost() != null &&
                config.getPort() != null &&
                config.getDatabaseName() != null;
    }

    public String createUrlConnection(T config) {
        if (!validConfig(config)) {
            throw new IllegalArgumentException("Invalid MySQL configuration");
        }
        String url = config.getUrl();
        if (!config.explicitUrl()) {
            url = String.format("jdbc:mysql://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabaseName()) + UrlDatabaseUtil.generateUrlParams(config.getParams());
        }
        return url;
    }


    @Override
    public JdbcSource createDaoSource(T config) {
        MysqlDataSource jdbcSource = new MysqlDataSource();
        jdbcSource.setUrl(createUrlConnection(config));
        return jdbcSource;
    }
}
