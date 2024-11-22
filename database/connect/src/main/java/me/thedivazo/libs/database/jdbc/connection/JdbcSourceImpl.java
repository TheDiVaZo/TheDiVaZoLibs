package me.thedivazo.libs.database.jdbc.connection;

import lombok.Getter;
import lombok.Setter;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 07.11.2024
 */
@Getter
@Setter
public abstract class JdbcSourceImpl implements JdbcSource {
    private PrintWriter logWriter;
    protected int loginTimeout;
    private String username;
    private String password;
    private String url;

    protected void writeLog(String message) {
        if (logWriter != null) {
            logWriter.println(message);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        if (username != null || this.username != null) {
            properties.setProperty("user", username == null ? this.username : username);
        }
        if (password != null || this.password != null) {
            properties.setProperty("password", password == null ? this.password : password);
        }

        DriverManager.setLoginTimeout(this.loginTimeout);

        Connection connection = DriverManager.getConnection(this.url, properties);
        connection.setAutoCommit(true);
        return connection;
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        } else {
            throw new SQLException("Wrapped DataSource is not an instance of " + iface);
        }
    }

    public boolean isWrapperFor(Class<?> iface) {
        return iface.isInstance(this);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void installDriver() {
        writeLog("Loading driver connector " + getDriverClassName());
        try {
            this.getClass().getClassLoader().loadClass(getDriverClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load driver class: " + getDriverClassName(), e);
        }
        writeLog("Driver connector " + getDriverClassName() + " has been loaded with success");
    }
}
