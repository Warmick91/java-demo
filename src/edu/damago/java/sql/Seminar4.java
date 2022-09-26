package edu.damago.java.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Strictly typed seminar model class representing seminars; the properties
 * of instances are directly fetched from and updated within the database.
 * @author Sascha Baumeister
 */
public class Seminar4 {
	static private final String INSERT_SEMINAR = "INSERT INTO Seminar VALUES (?,?,?)";
	static private final String DELETE_SEMINAR = "DELETE FROM Seminar WHERE SemNr = ?";
	static private final String QUERY_SEMINAR_TITLE = "SELECT Thema FROM Seminar WHERE SemNr = ?";
	static private final String UPDATE_SEMINAR_TITLE = "UPDATE Seminar SET Thema = ? WHERE SemNr = ?";
	static private final String QUERY_SEMINAR_DESCRIPTION = "SELECT Beschreibung FROM Seminar WHERE SemNr = ?";
	static private final String UPDATE_SEMINAR_DESCRIPTION = "UPDATE Seminar SET Beschreibung = ? WHERE SemNr = ?";
	static private final String QUERY_SEMINARS = "SELECT SemNr FROM Seminar WHERE " 
		+ "(? IS NULL OR Thema = ?) AND "
		+ "(? IS NULL OR LOWER(Beschreibung) LIKE ?) AND "
		+ "(? IS NULL OR SemNr >= ?) AND "
		+ "(? IS NULL OR SemNr <= ?)";

	private final Connection jdbcConnection;
	private long identity;


	/**
	 * Initializes a new instance.
	 * @param jdbcConnection the JDBC connection
	 * @throws NullPointerException if the given JDBC connection is {@code null}
	 */
	public Seminar4 (final Connection jdbcConnection) throws NullPointerException {
		this.jdbcConnection = Objects.requireNonNull(jdbcConnection);
	}


	/**
	 * Returns the identity.
	 * @return the identity
	 */
	public long getIdentity () {
		return this.identity;
	}


	/**
	 * Sets the identity.
	 * @param identity the identity
	 */
	public void setIdentity (final long identity) {
		this.identity = identity;
	}


	/**
	 * Returns the title.
	 * @return the title
	 * @throws IllegalStateException if this instance is not persistent
	 * @throws SQLException if there is an SQL related problem
	 */
	public String getTitle () throws IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = this.jdbcConnection.prepareStatement(QUERY_SEMINAR_TITLE)) {
			jdbcStatement.setLong(1, this.identity);

			try (ResultSet tableCursor = jdbcStatement.executeQuery()) {
				if (!tableCursor.next()) throw new IllegalStateException();
				return tableCursor.getString(1);
			}
		}
	}


	/**
	 * Sets the title.
	 * @param title the title
	 * @throws IllegalStateException if the SQL update doesn't alter any rows
	 * @throws SQLException if there is an SQL related problem
	 */
	public void setTitle (final String title) throws IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = this.jdbcConnection.prepareStatement(UPDATE_SEMINAR_TITLE)) {
			jdbcStatement.setString(1, title);
			jdbcStatement.setLong(2, this.identity);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException();
		}
	}


	/**
	 * Returns the description.
	 * @return the description
	 * @throws IllegalStateException if this instance is not persistent
	 * @throws SQLException if there is an SQL related problem
	 */
	public String getDescription () throws IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = this.jdbcConnection.prepareStatement(QUERY_SEMINAR_DESCRIPTION)) {
			jdbcStatement.setLong(1, this.identity);

			try (ResultSet tableCursor = jdbcStatement.executeQuery()) {
				if (!tableCursor.next()) throw new IllegalStateException();
				return tableCursor.getString(1);
			}
		}
	}


	/**
	 * Sets the description.
	 * @param title the description
	 * @throws IllegalStateException if the SQL update doesn't alter any rows
	 * @throws SQLException if there is an SQL related problem
	 */
	public void setDescription (final String description) throws IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = this.jdbcConnection.prepareStatement(UPDATE_SEMINAR_DESCRIPTION)) {
			jdbcStatement.setString(1, description);
			jdbcStatement.setLong(2, this.identity);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException();
		}
	}


	/**
	 * Deletes this instance within the database.
	 * @throws IllegalStateException if the SQL update doesn't delete any rows
	 * @throws SQLException if there is an SQL related problem
	 */
	public void delete () throws IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = this.jdbcConnection.prepareStatement(DELETE_SEMINAR)) {
			jdbcStatement.setLong(1, this.identity);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException();
		}
	}


	/**
	 * Inserts this instance into the database.
	 * @throws IllegalStateException if the associated SQL command doesn't insert one row
	 * @throws SQLException if there is an SQL related problem
	 */
	public void insert () throws IllegalStateException, SQLException {
		final int mode = this.identity == 0 ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
		try (PreparedStatement jdbcStatement = this.jdbcConnection.prepareStatement(INSERT_SEMINAR, mode)) {
			jdbcStatement.setString(1, "changeit");
			jdbcStatement.setString(2, "changeit");
			jdbcStatement.setLong(3, this.identity);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException();

			if (mode == Statement.RETURN_GENERATED_KEYS) {
				try (ResultSet tableCursor = jdbcStatement.getGeneratedKeys()) {
					if (!tableCursor.next()) throw new IllegalStateException();
					this.identity = tableCursor.getLong(1);
				}
			}
		}
	}


	/**
	 * Converts this seminar into a map.
	 * @throws IllegalStateException if the associated SQL command doesn't query one row
	 * @throws SQLException if there is an SQL related problem
	 */
	public Map<String,Object> toMap () throws IllegalStateException, SQLException {
		final Map<String,Object> map = new HashMap<>();
		map.put("identity", this.identity);
		map.put("title", this.getTitle());
		map.put("description", this.getDescription());

		return map;
	}


	/**
	 * Queries the instances matching the given filter criteria.
	 * @param jdbcConnection the JDBC connection
	 * @param title the title, or {@code null} for none
	 * @param description the description, or {@code null} for none
	 * @param lowerIdentity the minimum identity, or {@code null} for none
	 * @param upperIdentity the maximum identity, or {@code null} for none
	 * @return the matching seminars
	 * @throws NullPointerException if the given JDBC connection is {@code null}
	 * @throws SQLException if there is an SQL related problem
	 */
	static public List<Seminar4> querySeminars (final Connection jdbcConnection, final String title, final String description, final Long lowerIdentity, final Long upperIdentity) throws NullPointerException, SQLException {
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(QUERY_SEMINARS)) {
			jdbcStatement.setString(1, title);
			jdbcStatement.setString(2, title);
			jdbcStatement.setString(3, description);
			jdbcStatement.setString(4, description == null ? null : '%' + description.toLowerCase() + '%');
			jdbcStatement.setObject(5, lowerIdentity);
			jdbcStatement.setObject(6, lowerIdentity);
			jdbcStatement.setObject(7, upperIdentity);
			jdbcStatement.setObject(8, upperIdentity);

			try (ResultSet tableCursor = jdbcStatement.executeQuery()) {
				final List<Seminar4> seminars = new ArrayList<>();
				while (tableCursor.next()) {
					final Seminar4 seminar = new Seminar4(jdbcConnection);
					seminars.add(seminar);

					seminar.setIdentity(tableCursor.getLong("SemNr"));
				}

				return seminars;
			}
		}
	}
}