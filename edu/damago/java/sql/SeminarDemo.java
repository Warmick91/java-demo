package edu.damago.java.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Facade for the text line input based Seminar demo.
 */
public class SeminarDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem
	 * @throws SQLException if there is an SQL related problem
	 */
	static public void main (final String[] args) throws IOException, SQLException {
		final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		try (Connection jdbcConnection = newJdbcConnection()) {
			System.out.println("connected to " + jdbcConnection.getMetaData().getDatabaseProductName());

			while (true) {
				System.out.print("> ");
				final String line = consoleReader.readLine().trim();
				final int delimiterPosition = line.indexOf(' ');
				final String command = (delimiterPosition == -1 ? line : line.substring(0, delimiterPosition)).trim();
				final String arguments = (delimiterPosition == -1 ? "" : line.substring(delimiterPosition + 1)).trim();

				try {
					switch (command.toLowerCase()) {
						case "quit":
							System.out.println("bye bye!");
							return;
						case "display-seminars":
							processQuerySeminarsACommand(jdbcConnection, arguments);
							break;
						case "display-seminar":
							processQuerySeminarCommand(jdbcConnection, arguments);
							break;
						case "insert-seminar":
							processInsertSeminarCommand(jdbcConnection, arguments);
							break;
						case "update-seminar":
							processUpdateSeminarCommand(jdbcConnection, arguments);
							break;
						case "delete-seminar":
							processDeleteSeminarCommand(jdbcConnection, arguments);
							break;
						default:
							processHelpCommand(arguments);
							break;
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * Displays the available commands.
	 * @param arguments the arguments
	 */
	static public void processHelpCommand (final String arguments) {
		System.out.println("List of available commands:");
		System.out.println("- help: displays this help");
		System.out.println("- display-seminars: display the seminars");
		System.out.println("- display-seminar <id>: display the seminar matching the given id");
		System.out.println("- insert-seminar <title>;<description>: inserts a new seminar");
		System.out.println("- update-seminar <id>;<title>;<description>: updates the seminar matching the given id");
		System.out.println("- delete-seminar <id>: deletes the seminar matching the given id");
		System.out.println("- quit: terminates this program");
	}


	/**
	 * Returns a connection either with MS Access, MySQL or MariaDB.
	 * @return the database connection created
	 * @throws SQLException if there is neither an MS Access, MySQL nor MariaDB available
	 */
	static private Connection newJdbcConnection () throws SQLException {

		// only required until Java 1.8 (inclusive), and for the JavaDB certification
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (Exception e) {
			// do nothing
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			// do nothing
		}

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception e) {
			// do nothing
		}

		try {
			return DriverManager.getConnection("jdbc:ucanaccess://d:/path/db1.accdb", "Administrator", "damago");
		} catch (final SQLException e) {
			try {
				return DriverManager.getConnection("jdbc:mysql://localhost:3306/seminarverwaltung", "root", "root");
			} catch (final SQLException ex) {
				return DriverManager.getConnection("jdbc:mariadb://localhost:3306/seminarverwaltung", "root", "root");
			}
		}
	}


	/**
	 * Best practice: Processes displaying seminars.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processQuerySeminarsACommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final String sql = "SELECT * FROM Seminar";
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql)) {
			try (ResultSet resultSet = jdbcStatement.executeQuery()) {
				while (resultSet.next()) {
					System.out.format("%s, '%s', '%s'%n", resultSet.getString("SemNr"), resultSet.getString("Thema"), resultSet.getString("Beschreibung"));
				}
			}
		}
	}


	/**
	 * OK practice: Processes displaying seminars.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	@SuppressWarnings("unused")
	static private void processQuerySeminarsBCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final String sql = "SELECT * FROM Seminar";
		final PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql);
		try {
			final ResultSet resultSet = jdbcStatement.executeQuery();
			try  {
				while (resultSet.next()) {
					System.out.format("%s, '%s', '%s'%n", resultSet.getString("SemNr"), resultSet.getString("Thema"), resultSet.getString("Beschreibung"));
				}
			} finally {
				resultSet.close();
			}
		} finally {
			jdbcStatement.close();
		}
	}


	/**
	 * Certification variant: Processes displaying seminars.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	@SuppressWarnings("unused")
	static private void processQuerySeminarsCCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final String sql = "SELECT * FROM Seminar";
		final PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql);
		final ResultSet resultSet = jdbcStatement.executeQuery();
		while (resultSet.next()) {
			System.out.format("%s, '%s', '%s'%n", resultSet.getString("SemNr"), resultSet.getString("Thema"), resultSet.getString("Beschreibung"));
		}
	}


	/**
	 * Best practice: Processes displaying one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processQuerySeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final long id = Long.parseLong(arguments);
		final String sql = "SELECT * FROM Seminar WHERE SemNr=?";
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql)) {
			jdbcStatement.setLong(1, id);

			try (ResultSet resultSet = jdbcStatement.executeQuery()) {
				if (resultSet.next()) {
					System.out.format("%s, '%s', '%s'%n", resultSet.getString("SemNr"), resultSet.getString("Thema"), resultSet.getString("Beschreibung"));
				} else {
					System.out.println("no results!");
				}
			}
		}
	}


	/**
	 * Best practice: Processes inserting one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processInsertSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final int delimiterPosition = arguments.indexOf(';');
		if (delimiterPosition == -1) throw new IllegalArgumentException("illegal argument syntax!");		

		final String title = arguments.substring(0, delimiterPosition).trim();
		final String description = arguments.substring(delimiterPosition + 1).trim();

		final String sql = "INSERT INTO Seminar VALUES (0,?,?)";
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			jdbcStatement.setString(1, title);
			jdbcStatement.setString(2, description);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException("insert command failed!");

			try (ResultSet generatedValueCursor = jdbcStatement.getGeneratedKeys()) {
				if (!generatedValueCursor.next()) throw new IllegalStateException("key generation failed!");

				final long id = generatedValueCursor.getLong(1);
				System.out.println("inserted row with SemNr = " + id);	
			}
		}
	}


	/**
	 * Best practice: Processes deleting one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processDeleteSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final long id = Long.parseLong(arguments);

		final String sql = "DELETE FROM Seminar WHERE SemNr = ?";
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql)) {
			jdbcStatement.setLong(1, id);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException("delete command failed!");

			System.out.println("ok.");	
		}
	}


	/**
	 * Best practice: Processes updating one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processUpdateSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final int delimiterPositionA = arguments.indexOf(';');
		final int delimiterPositionB = arguments.indexOf(';', delimiterPositionA + 1);
		if (delimiterPositionA == -1 | delimiterPositionB == -1) throw new IllegalArgumentException("illegal argument syntax!");		

		final long id = Long.parseLong(arguments.substring(0, delimiterPositionA).trim());
		final String title = arguments.substring(delimiterPositionA + 1, delimiterPositionB).trim();
		final String description = arguments.substring(delimiterPositionB + 1).trim();

		final String sql = "UPDATE Seminar SET Thema=?,Beschreibung=? WHERE SemNr = ?";
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql)) {
			jdbcStatement.setString(1, title);
			jdbcStatement.setString(2, description);
			jdbcStatement.setLong(3, id);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException("update command failed!");

			System.out.println("ok.");	
		}
	}
}