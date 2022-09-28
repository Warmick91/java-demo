package edu.damago.java.seminar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Strictly typed seminar model class representing seminars; the properties
 * of instances are cached within the RAM.
 * @author Sascha Baumeister
 */
public class Seminar5 {
	static private final String INSERT_SEMINAR = "INSERT INTO Seminar VALUES (?,?,?)";
	static private final String UPDATE_SEMINAR = "UPDATE Seminar SET Thema = ?, Beschreibung = ? WHERE SemNr = ?";
	static private final String DELETE_SEMINAR = "DELETE FROM Seminar WHERE SemNr = ?";
	static private final String QUERY_SEMINAR = "SELECT * FROM Seminar WHERE SemNr = ?";
	static private final String QUERY_SEMINARS = "SELECT * FROM Seminar WHERE " 
		+ "(? IS NULL OR Thema = ?) AND "
		+ "(? IS NULL OR LOWER(Beschreibung) LIKE ?) AND "
		+ "(? IS NULL OR SemNr >= ?) AND "
		+ "(? IS NULL OR SemNr <= ?)";

	private long identity;
	private String title;
	private String description;


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
	 */
	public String getTitle () {
		return this.title;
	}


	/**
	 * Sets the title.
	 * @param title the title
	 */
	public void setTitle (final String title) {
		this.title = title;
	}


	/**
	 * Returns the description.
	 * @return the description
	 */
	public String getDescription () {
		return this.description;
	}


	/**
	 * Sets the description.
	 * @param title the description
	 */
	public void setDescription (final String description) {
		this.description = description;
	}


	/**
	 * Inserts this instance into the database.
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalStateException if the associated SQL command doesn't insert one row
	 * @throws SQLException if there is an SQL related problem
	 */
	public void insert (final Connection jdbcConnection) throws NullPointerException, IllegalStateException, SQLException {
		final int mode = this.identity == 0 ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(INSERT_SEMINAR, mode)) {
			jdbcStatement.setLong(1, this.identity);
			jdbcStatement.setString(2, this.title);
			jdbcStatement.setString(3, this.description);

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
	 * Updates this instance from the database.
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalStateException if the associated SQL command doesn't update one row
	 * @throws SQLException if there is an SQL related problem
	 */
	public void update (final Connection jdbcConnection) throws NullPointerException, IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(UPDATE_SEMINAR)) {
			jdbcStatement.setString(1, this.title);
			jdbcStatement.setString(2, this.description);
			jdbcStatement.setLong(3, this.identity);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException();
		}
	}


	/**
	 * Deletes this instance from the database.
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalStateException if the associated SQL command doesn't delete one row
	 * @throws SQLException if there is an SQL related problem
	 */
	public void delete (final Connection jdbcConnection) throws NullPointerException, IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(DELETE_SEMINAR)) {
			jdbcStatement.setLong(1, this.identity);

			final int rowCount = jdbcStatement.executeUpdate();
			if (rowCount != 1) throw new IllegalStateException();
		}
	}


	/**
	 * DeRefreshes this instance from the database.
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalStateException if the associated SQL command doesn't return one row
	 * @throws SQLException if there is an SQL related problem
	 */
	public void refresh (final Connection jdbcConnection) throws NullPointerException, IllegalStateException, SQLException {
		try (PreparedStatement jdbcStatement = jdbcConnection.prepareStatement(QUERY_SEMINAR)) {
			jdbcStatement.setLong(1, this.identity);

			try (ResultSet tableCursor = jdbcStatement.executeQuery()) {
				if (!tableCursor.next()) throw new IllegalStateException();
				this.identity = tableCursor.getLong("SemNr");
				this.title = tableCursor.getString("Thema");
				this.description = tableCursor.getString("Beschreibung");
			}
		}
	}


	/**
	 * Converts this seminar into a map.
	 */
	public Map<String,Object> toMap () {
		final Map<String,Object> map = new HashMap<>();
		map.put("identity", this.identity);
		map.put("title", this.title);
		map.put("description", this.description);

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
	static public List<Seminar5> querySeminars (final Connection jdbcConnection, final String title, final String description, final Long lowerIdentity, final Long upperIdentity) throws NullPointerException, SQLException {
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
				final List<Seminar5> seminars = new ArrayList<>();
				while (tableCursor.next()) {
					final Seminar5 seminar = new Seminar5();
					seminars.add(seminar);

					seminar.identity = tableCursor.getLong("SemNr");
					seminar.title = tableCursor.getString("Thema");
					seminar.description = tableCursor.getString("Beschreibung");
				}

				return seminars;
			}
		}
	}
}