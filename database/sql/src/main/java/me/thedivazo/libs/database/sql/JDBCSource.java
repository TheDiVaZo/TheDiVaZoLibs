package me.thedivazo.libs.database.sql;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 31.10.2024
 **/
public interface JDBCSource extends DataSource {

    Logger getParentLogger() throws SQLFeatureNotSupportedException;

    <T> T unwrap(Class<T> iface) throws SQLException;

    boolean isWrapperFor(Class<?> iface) throws SQLException;
}
