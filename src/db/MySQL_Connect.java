package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_Connect {
	private Connection connection;
	private static final String DB_NAME = "proiect_policlinica";
	private static final String TIMEZONE = "serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "admin123";

	@SuppressWarnings("deprecation")
	public MySQL_Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			System.err.println("An Exception occured during JDBC Driver loading." + " Details are provided below:");
			ex.printStackTrace(System.err);
		}
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("jdbc:mysql://localhost:3306/");
			sb.append(DB_NAME);
			sb.append("?");
			sb.append(TIMEZONE);
			sb.append("&&");
			sb.append("user=");
			sb.append(USER);
			sb.append("&&");
			sb.append("password=");
			sb.append(PASSWORD);
			connection = DriverManager.getConnection(sb.toString());

		} catch (SQLException sqlex) {
			System.err.println("An SQL Exception occured. Details are provided below:");
			sqlex.printStackTrace(System.err);
		}

	}

	public Connection getConnection() {
		return connection;
	}
}
