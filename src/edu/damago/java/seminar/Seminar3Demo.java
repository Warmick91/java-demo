package edu.damago.java.seminar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import edu.damago.java.tool.JSON;
import edu.damago.java.tool.RelationalDatabases;


/**
 * Facade for the text line input based Seminar demo, based on
 * a weakly typed model and tool class RelationalDatabases.
 * @author Sascha Baumeister
 */
public class Seminar3Demo {
	static private final String QUERY_SEMINAR = "SELECT * FROM Seminar WHERE SemNr = ?";
	static private final String QUERY_SEMINARS = "SELECT * FROM Seminar WHERE " 
		+ "(? IS NULL OR Thema = ?) AND "
		+ "(? IS NULL OR LOWER(Beschreibung) LIKE ?) AND "
		+ "(? IS NULL OR SemNr >= ?) AND "
		+ "(? IS NULL OR SemNr <= ?)";

	static private final String INSERT_SEMINAR = "INSERT INTO Seminar VALUES (?,?,?)";
	static private final String DELETE_SEMINAR = "DELETE FROM Seminar WHERE SemNr = ?";
	static private final String UPDATE_SEMINAR = "UPDATE Seminar SET Thema=?,Beschreibung=? WHERE SemNr = ?";


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem
	 * @throws SQLException if there is an SQL related problem
	 */
	static public void main (final String[] args) throws IOException, SQLException {
		final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		try (Connection jdbcConnection = newJdbcConnection(DatabaseType.ORACLE_DB)) {
			System.out.println("Connected: " + jdbcConnection.getMetaData().getDatabaseProductName());

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
						case "query-seminars":
							processQuerySeminarsCommand(jdbcConnection, arguments);
							break;
						case "query-seminar":
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
		System.out.println("- query-seminars [title];[description];[lower-id];[upper-id]: display the seminars");
		System.out.println("- query-seminar <id>: display the seminar matching the given id");
		System.out.println("- insert-seminar <id-or-zero>;<title>;<description>: inserts a new seminar");
		System.out.println("- update-seminar <id>;<title>;<description>: updates the seminar matching the given id");
		System.out.println("- delete-seminar <id>: deletes the seminar matching the given id");
		System.out.println("- quit: terminates this program");
	}


	/**
	 * Best practice: Processes displaying seminars.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processQuerySeminarsCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		String title = null, description = null;
		Long lowerID = null, upperID = null;

		if (!arguments.trim().isEmpty()) {
			final String[] parameters = arguments.split(";");
			for (int index = 0; index < parameters.length; ++index)
				parameters[index] = parameters[index].trim();

			if (parameters.length > 0 && !parameters[0].isEmpty()) title = parameters[0];
			if (parameters.length > 1 && !parameters[1].isEmpty()) description = '%' + parameters[1].toLowerCase() + '%';
			if (parameters.length > 2 && !parameters[2].isEmpty()) lowerID = Long.parseLong(parameters[2]);
			if (parameters.length > 3 && !parameters[3].isEmpty()) upperID = Long.parseLong(parameters[3]);
		}

		final List<Map<String,Object>> rowMaps = RelationalDatabases.executeQuery(jdbcConnection, QUERY_SEMINARS, title, title, description, description, lowerID, lowerID, upperID, upperID);
		for (final Map<String,Object> rowMap : rowMaps)
			System.out.println(JSON.stringify(rowMap));
	}


	/**
	 * Best practice: Processes displaying one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processQuerySeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final long id = Long.parseLong(arguments);

		final List<Map<String,Object>> rowMaps = RelationalDatabases.executeQuery(jdbcConnection, QUERY_SEMINAR, id);
		if (rowMaps.isEmpty())
			System.out.println("no results!");
		else
			System.out.println(JSON.stringify(rowMaps.get(0)));
	}


	/**
	 * Best practice: Processes inserting one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processInsertSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final int delimiterPositionA = arguments.indexOf(';');
		final int delimiterPositionB = arguments.indexOf(';', delimiterPositionA + 1);
		if (delimiterPositionA == -1 | delimiterPositionB == -1) throw new IllegalArgumentException("illegal argument syntax!");		

		long id = Long.parseLong(arguments.substring(0, delimiterPositionA).trim());
		final String title = arguments.substring(delimiterPositionA + 1, delimiterPositionB).trim();
		final String description = arguments.substring(delimiterPositionB + 1).trim();

		final long rowCount;
		if (id == 0) {
			final long[][] generatedValues = RelationalDatabases.executeChange2(jdbcConnection, INSERT_SEMINAR, id, title, description);
			final long generatedValue = generatedValues[0][0];
			System.out.println("generated SemNr = " + generatedValue);

			rowCount = generatedValues.length;
		} else {
			rowCount = RelationalDatabases.executeChange1(jdbcConnection, INSERT_SEMINAR, id, title, description);
		}

		if (rowCount != 1) throw new IllegalStateException("insert command failed!");
		System.out.println("ok.");
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

		final long rowCount = RelationalDatabases.executeChange1(jdbcConnection, UPDATE_SEMINAR, title, description, id);
		if (rowCount != 1) throw new IllegalStateException("update command failed!");

		System.out.println("ok.");	
	}


	/**
	 * Best practice: Processes deleting one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processDeleteSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final long id = Long.parseLong(arguments);

		final long rowCount = RelationalDatabases.executeChange1(jdbcConnection, DELETE_SEMINAR, id);
		if (rowCount != 1) throw new IllegalStateException("delete command failed!");

		System.out.println("ok.");	
	}


	/**
	 * Connects to the given database type, and returns the resulting JDBC connection.
	 * @param databaseType the database type
	 * @return the JDBC connection created
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws SQLException if there is an SQL related problem
	 */
	static private Connection newJdbcConnection (final DatabaseType type) throws NullPointerException, SQLException {
		switch (type) {
			case MYSQL:
				return DriverManager.getConnection("jdbc:mysql://localhost:3306/seminarverwaltung", "root", "root");
			case MARIA_DB:
				return DriverManager.getConnection("jdbc:mariadb://localhost:3306/seminarverwaltung", "root", "root");
			case ORACLE_DB:
				return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "root");
			case MS_ACCESS:
				return DriverManager.getConnection("jdbc:ucanaccess://D:/db1.accdb", "Administrator", "damago");
			case ODBC:
				return DriverManager.getConnection("jdbc:odbc:seminarverwaltung", "Administrator", "damago");
			default:
				throw new AssertionError();
		}
	}
}