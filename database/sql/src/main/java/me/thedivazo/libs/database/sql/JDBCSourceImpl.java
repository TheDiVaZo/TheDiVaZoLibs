package me.thedivazo.libs.database.sql;

import me.thedivazo.libs.database.configsource.SQLConfigSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class JDBCSourceImpl implements JDBCSource {
    private final SQLConfigSource source;
    private final String url;
    private Integer timeOut;
    private PrintWriter writer;

    public JDBCSourceImpl(SQLConfigSource source, Integer timeOut, PrintWriter writer) {
        this.source = source;
        this.url = source.toURL();
        this.timeOut = timeOut;
        this.writer = writer;
        this.source.getDriverLoader().loadDriver();
    }

    public JDBCSourceImpl(SQLConfigSource source) {
        this(source, null, System.console().writer());
    }

    public Connection getConnection() throws SQLException {
        return this.getConnection(source.getUsername(), source.getPassword());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        if (username != null) {
            properties.setProperty("user", username);
        }

        if (username != null && password != null) {
            properties.setProperty("password", password);
        }

        if (timeOut != null) {
            DriverManager.setLoginTimeout(timeOut);
        }

        Connection connection = DriverManager.getConnection(this.url, properties);
        connection.setAutoCommit(true);
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.writer;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.writer = out;
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        this.timeOut = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.timeOut;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        } else {
            throw new SQLException("Wrapped DataSource is not an instance of " + iface);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}
