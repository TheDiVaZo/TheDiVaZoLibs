package me.thedivazo.libs.database.sql.connection.factory;

import me.thedivazo.libs.database.configsource.MonoDatabaseConfig;
import me.thedivazo.libs.database.configsource.SQLDatabaseConfig;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
public class H2UrlFactory<T extends SQLDatabaseConfig & MonoDatabaseConfig> implements UrlConnectFactory<T> {

    protected boolean validConfig(T config) {
        if (config.explicitUrl()) return true;
        return config.getDatabaseName() != null;
    }

    @Override
    public String createUrlConnection(T config) {
        if (!validConfig(config)) {
            throw new IllegalArgumentException("Invalid H2 configuration.");
        }
        StringBuilder url;
        if (config.explicitUrl()) {
            url = new StringBuilder(config.getUrl());
        }
        else if (config.getHost() != null && config.getPort() != null) {
            url = new StringBuilder(String.format("jdbc:h2:tcp://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabaseName()));
        }
        else {
            url = new StringBuilder(String.format("jdbc:h2:~/%s", config.getDatabaseName()));
        }

        config.getParams().forEach((key, value) -> url.append(String.format(";%s=%s", key, value)));
        return url.toString();
    }

    @Override
    public String getDriverClassName() {
        return "org.h2.Driver";
    }
}
