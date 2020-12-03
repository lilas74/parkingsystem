package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Data base config.
 *
 * @author lilas
 */
public class DataBaseConfig {
	/**
	 * This is the central interface in the log4j package. Most logging
	 * operations,except configuration, are done through this interface.
	 */
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	/**
	 * Gets connection.
	 *
	 * @return the connection
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the sql exception
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/prod?tz=useLegacyDatetimeCode=false&serverTimezone=Europe/Paris", "root",
				"rootroot");
	}

	/**
	 * Close connection. Calling the method close on a Connection object
	 *
	 * @param con
	 *            connection to close
	 */
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
	 * @param ps
	 *            the preparedStatement to close
	 */
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
	 * @param rs
	 *            the resultSet to close
	 */
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
