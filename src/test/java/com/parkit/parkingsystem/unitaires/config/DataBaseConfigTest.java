package com.parkit.parkingsystem.unitaires.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Data base config test.
 *
 * @author lilas
 * @see DataBaseConfig
 */
@SuppressWarnings("UnusedAssignment")
class DataBaseConfigTest {

    /**
	 * The test config.
	 */
    private static DataBaseConfig testConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
	testConfig = new DataBaseConfig();
    }

    /**
     * Gets connection test.
     *
     * @return the connection test
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     */
    @SuppressWarnings("UnusedAssignment")
    @DisplayName("Test to verify the connection has made to a database")
    @Test
    void getConnectionTest() throws ClassNotFoundException, SQLException {
	/*
	 * Given : an attempt connection
	 */
	Connection conn = null;
	/*
	 * When we try to get the connection
	 */
	conn = testConfig.getConnection();

	/*
	 * Then the connection shouldn't be null
	 */
	assertNotNull(conn);
    }

    /**
     * Close connection test.
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     */
    @SuppressWarnings("UnusedAssignment")
    @DisplayName("Test to verify the close Connection function, the test should return a true closed connection")
    @Test
    void closeConnectionTest() throws ClassNotFoundException, SQLException {
	/*
	 * Given : an attempt connection and connected
	 */
	Connection conn = null;
	conn = testConfig.getConnection();
	/*
	 * When we try to close the connection
	 */
	testConfig.closeConnection(conn);

	/*
	 * Then the connection should be closed
	 */
	assertTrue(conn.isClosed());
    }

    /**
     * Close prepared statement test.
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     */
    @DisplayName("Test to verify that the prepare statement is closed after the closePreparedStatement function")
    @Test
    void closePreparedStatementTest() throws ClassNotFoundException, SQLException {
	/*
	 * Given : a database connection with a prepared statement
	 */
	Connection conn = null;
	conn = testConfig.getConnection();
	PreparedStatement ps = null;
	ps = conn.prepareStatement(DBConstants.SAVE_TICKET);

	/*
	 * When we try to close the prepared statement and to close the connection
	 */
	testConfig.closePreparedStatement(ps);
	testConfig.closeConnection(conn);
	/*
	 * Then the prepared statement should return to be closed
	 */
	assertTrue(ps.isClosed());
    }

    /**
     * Close result set test.
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws SQLException           the sql exception
     */
    @DisplayName("Test to verify that the result set is closed after the calling of closeResultSet method")
    @Test
    void closeResultSetTest() throws ClassNotFoundException, SQLException {
	/*
	 * Given : a database connection
	 */
	Connection conn = null;
	conn = testConfig.getConnection();
	PreparedStatement ps = null;
	ps = conn.prepareStatement(DBConstants.GET_TICKET);
	ps.setString(1, "1");
	ResultSet rs = ps.executeQuery();
	/*
	 * When we try to close the result set
	 */
	testConfig.closeResultSet(rs);
	testConfig.closePreparedStatement(ps);
	testConfig.closeConnection(conn);

	/*
	 * Then the resultSet should return to be closed
	 */
	assertTrue(rs.isClosed());

    }
}