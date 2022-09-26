package edu.damago.java.objectbased;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import edu.damago.java.tool.Ressources;

/**
 * Demo for exception handling.
 * @author Sascha Baumeister
 */
public class ExceptionDemo {

	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 */
	static public void main (final String[] args) {
		final Integer[] values = new Integer[1];
		final Object[] objects = values;			// auto-cast

		// values[0] = "abc";						// compiler sees that this is not possible -> Compile Error
		// objects[0] = "abc";						// compiler doesn't see that this is not possible -> ArrayStoreException 

		System.out.println(Arrays.toString(objects));
		System.out.println();

		final URI unifiedResourceIdentifier1;
		try {
			unifiedResourceIdentifier1 = new URI("http://localhost:8001/");
		} catch (final URISyntaxException e) {	// ugly and bad!
			throw new AssertionError(e);
		}

		final URI unifiedResourceIdentifier2 = URI.create("http://localhost:8001/");
		System.out.println(unifiedResourceIdentifier1);
		System.out.println(unifiedResourceIdentifier2);
		System.out.println();

		final URL unifiedResourceLocator1;
		try {
			unifiedResourceLocator1 = new URL("http://localhost:8001/");
		} catch (final MalformedURLException e) {	// ugly and bad!
			throw new AssertionError(e);
		}

		final URL unifiedResourceLocator2 = Ressources.newURL("http://localhost:8001/");
		System.out.println(unifiedResourceLocator1);
		System.out.println(unifiedResourceLocator2);
	}
}