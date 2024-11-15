package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import me.thedivazo.libs.database.util.UrlDatabaseUtil;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class MysqlJdbcUrlFactory<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements JdbcUrlFactory<T> {

    protected boolean validConfig(T config) {
        if (config.explicitUrl()) return true;
        return config.getHost() != null &&
                config.getPort() != null &&
                config.getDatabaseName() != null;
    }

    @Override
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
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }
}
