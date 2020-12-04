package com.parkit.parkingsystem.integration.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * The type Data base test config.
 */
public class DataBaseTestConfig extends DataBaseConfig {

    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger("DataBaseTestConfig");

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the SQL exception
     */
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
	logger.info("Create DB connection");
	Class.forName("com.mysql.cj.jdbc.Driver");
	return DriverManager.getConnection(
		"jdbc:mysql://localhost:3306/test?tz=useLegacyDatetimeCode=false&serverTimezone=Europe/Paris", "root",
		"rootroot");
    }

    /**
     * Close connection.
     *
     * @param con the con
     */
    @Override
    public void closeConnection(Connection con) {
	if (con != null) {
	    try {
		con.close();
		logger.info("Closing DB connection");
	    } catch (SQLException e) {
		logger.error("Error while closing connection", e);
	    }
	}
    }

    /**
     * Close prepared statement.
     *
     * @param ps the ps
     */
    @Override
    public void closePreparedStatement(PreparedStatement ps) {
	if (ps != null) {
	    try {
		ps.close();
		logger.info("Closing Prepared Statement");
	    } catch (SQLException e) {
		logger.error("Error while closing prepared statement", e);
	    }
	}
    }

    /**
     * Close result set.
     *
     * @param rs the rs
     */
    @Override
    public void closeResultSet(ResultSet rs) {
	if (rs != null) {
	    try {
		rs.close();
		logger.info("Closing Result Set");
	    } catch (SQLException e) {
		logger.error("Error while closing result set", e);
	    }
	}
    }
}
