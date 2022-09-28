package edu.damago.java.seminar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import edu.damago.java.tool.JSON;


/**
 * Facade for the text line input based Seminar demo, based on
 * a weakly typed model and tool class RelationalDatabases.
 * @author Sascha Baumeister
 */
public class Seminar4Demo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem
	 * @throws SQLException if there is an SQL related problem
	 */
	static public void main (final String[] args) throws IOException, SQLException {
		final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		try (Connection jdbcConnection = newJdbcConnection(DatabaseType.MYSQL)) {
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
	 * Processes displaying seminars.
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

		final List<Seminar4> seminars = Seminar4.querySeminars(jdbcConnection, title, description, lowerID, upperID);
		for (final Seminar4 seminar : seminars)
			System.out.println(JSON.stringify(seminar.toMap()));
	}


	/**
	 * Processes displaying one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processQuerySeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final Seminar4 seminar = new Seminar4(jdbcConnection);
		seminar.setIdentity(Long.parseLong(arguments));

		try {
			System.out.println(JSON.stringify(seminar.toMap()));
		} catch (IllegalStateException | SQLException e) {
			System.out.println("no results!");
		}
	}


	/**
	 * Processes inserting one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processInsertSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final int delimiterPositionA = arguments.indexOf(';');
		final int delimiterPositionB = arguments.indexOf(';', delimiterPositionA + 1);
		if (delimiterPositionA == -1 | delimiterPositionB == -1) throw new IllegalArgumentException("illegal argument syntax!");		

		final Seminar4 seminar = new Seminar4(jdbcConnection);
		seminar.setIdentity(Long.parseLong(arguments.substring(0, delimiterPositionA).trim()));
		seminar.insert();

		seminar.setTitle(arguments.substring(delimiterPositionA + 1, delimiterPositionB).trim());
		seminar.setDescription(arguments.substring(delimiterPositionB + 1).trim());

		System.out.println("inserted seminar " + seminar.getIdentity());
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

		final Seminar4 seminar = new Seminar4(jdbcConnection);
		seminar.setIdentity(Long.parseLong(arguments.substring(0, delimiterPositionA).trim()));
		seminar.setTitle(arguments.substring(delimiterPositionA + 1, delimiterPositionB).trim());
		seminar.setDescription(arguments.substring(delimiterPositionB + 1).trim());

		System.out.println("ok.");	
	}


	/**
	 * Processes deleting one seminar.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processDeleteSeminarCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final Seminar4 seminar = new Seminar4(jdbcConnection);
		seminar.setIdentity(Long.parseLong(arguments));
		seminar.delete();

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