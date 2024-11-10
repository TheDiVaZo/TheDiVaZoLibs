package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.MultiDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;
import me.thedivazo.libs.util.UrlDatabaseUtil;

import java.util.Objects;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class PostgreSqlJdbcUrlFactory<T extends SQLDatabaseConfig & MultiDatabaseConfig> implements JdbcUrlFactory<T> {
    protected static final String DEFAULT_PORT = "5432";

    protected boolean validConfig(T config) {
        if (config.explicitUrl()) return true;
        return config.getHosts() != null &&
                config.getPorts() != null &&
                config.getDatabaseName() != null;
    }

    @Override
    public String createUrlConnection(T config) {
        if (!validConfig(config)) {
            throw new IllegalArgumentException("Invalid PostgreSQL configuration");
        }
        String url = config.getUrl();
        if (!config.explicitUrl()) {
            StringBuilder urlBuilder = new StringBuilder("jdbc:postgresql://");

            String[] hosts = config.getHosts();
            String[] ports = config.getPorts();
            for (int i = 0; i < hosts.length; i++) {
                String host = hosts[i];
                String port = (ports != null && i < ports.length) ? ports[i] : DEFAULT_PORT;
                urlBuilder.append(host);
                if (!Objects.equals(port, DEFAULT_PORT)) {
                    urlBuilder.append(":").append(port);
                }
                if (i < hosts.length - 1) {
                    urlBuilder.append(",");
                }
            }

            urlBuilder.append("/").append(config.getDatabaseName());
            urlBuilder.append(UrlDatabaseUtil.generateUrlParams(config.getParams()));
            url = urlBuilder.toString();
        }

        return url;
    }

    @Override
    public String getDriverClassName() {
        return  "org.postgresql.Driver";
    }
}