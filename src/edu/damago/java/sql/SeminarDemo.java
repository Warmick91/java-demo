package edu.damago.java.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

		try (Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminarverwaltung", "root", "root")) {
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
							processQuerySeminarsCommand(jdbcConnection, arguments);
							break;
						default:
							processHelpCommand(arguments);
							break;
					}
				} catch (final Exception e) {
					// e.printStackTrace();
					processHelpCommand(arguments);
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
		System.out.println("- quit: terminates this program");
	}


	/**
	 * Processes displaying seminars.
	 * @param jdbcConnection the JDBC connection
	 * @param arguments the arguments
	 * @throws SQLException if there is an SQL related problem
	 */
	static private void processQuerySeminarsCommand (final Connection jdbcConnection, final String arguments) throws SQLException {
		final String sql = "SELECT * FROM Seminar";
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(sql)) {
			try (ResultSet resultSet = jdbcStatement.executeQuery()) {
				while (resultSet.next()) {
					System.out.format("%s, '%s', '%s'%n", resultSet.getString("SemNr"), resultSet.getString("Thema"), resultSet.getString("Beschreibung"));
				}
			}
		}

	}
}