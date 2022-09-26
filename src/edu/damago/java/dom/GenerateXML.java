package edu.damago.java.dom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;


/**
 * FernUni Hagen: Beispiel GenerateXML ported 
 * @author TN
 *
 */
public class GenerateXML {

	static public void main (final String[] argv) throws SQLException, DOMException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		String sql;

		// Class.forName("com.mysql.jdbc.Driver");
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminarverwaltung?useSSL=false", "root", "root")) {
			final Document XMLdoc = new DocumentImpl();
			final Element seminare = XMLdoc.createElement("Seminarverwaltung");
			XMLdoc.appendChild(seminare);

			try (Statement s1 = c.createStatement()) {
				sql = "SELECT SemNr, Thema, Beschreibung FROM Seminar";
				System.out.println(sql);

				try (ResultSet rs1 = s1.executeQuery(sql)) {
					while (rs1.next()) {
						final Element seminar = XMLdoc.createElement("Seminar");
						final Element thema = XMLdoc.createElement("Thema");
						final Element beschr = XMLdoc.createElement("Beschreibung");
						final String nr = rs1.getString(1);

						thema.appendChild(XMLdoc.createTextNode(rs1.getString(2)));
						beschr.appendChild(XMLdoc.createTextNode(rs1.getString(3)));

						seminar.setAttribute("SemNr", nr);
						seminar.appendChild(thema);
						seminar.appendChild(beschr);
						seminare.appendChild(seminar);

						try (Statement s2 = c.createStatement()) {
							final SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy");
							final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

							sql = "SELECT VerID, Ort, Termin FROM Veranstaltungen WHERE SemNr = " + nr;
							System.out.println(sql);

							try (ResultSet rs2 = s2.executeQuery(sql)) {
								while (rs2.next()) {
									final Element v = XMLdoc.createElement("Veranstaltung");
									final Element ort = XMLdoc.createElement("Ort");
									final Element termin = XMLdoc.createElement("Termin");

									ort.appendChild(XMLdoc.createTextNode(rs2.getString(2)));
									if (rs2.getString(3) != null) termin.appendChild(XMLdoc.createTextNode(df1.format(df2.parse(rs2.getString(3)))));

									v.setAttribute("VerID", rs2.getString(1));
									v.appendChild(ort);
									v.appendChild(termin);

									seminar.appendChild(v);
								}
							}
						}
					}

				}

				sql = "SELECT VerID, Namen FROM Teilnehmer";
				System.out.println(sql);

				try (ResultSet rs = s1.executeQuery(sql)) {
					if (rs.next()) {
						final Element tt = XMLdoc.createElement("Teilnehmerliste");
						seminare.appendChild(tt);

						do {
							final Element t = XMLdoc.createElement("Teilnehmer");
							final Element n = XMLdoc.createElement("Name");
							final Element v = XMLdoc.createElement("Veranstaltung_Ref");

							v.setAttribute("VerID", rs.getString(1));
							n.appendChild(XMLdoc.createTextNode(rs.getString(2)));

							t.appendChild(n);
							t.appendChild(v);

							tt.appendChild(t);
						} while (rs.next());
					}
				}
			}
			System.out.println();

			// File f = new File("D:/seminare.xml");
			// FileOutputStream fos = new FileOutputStream(f);
			final DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
			final DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
			final LSSerializer serializer = impl.createLSSerializer();
			final LSOutput lso = impl.createLSOutput();
			lso.setEncoding("ISO-8859-1");
			lso.setByteStream(System.out);
			serializer.write(XMLdoc, lso);
		}
	}
}