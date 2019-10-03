package Starwar;

import java.net.HttpURLConnection;
import java.net.URL;

import Utility.Log;

public class Starwar_Utility {

	// The below function verifyLink(String urlLink) verifies any broken links
	// and return the server status.
	public static int verifyLink(String urlLink) {
		// Sometimes we may face exception "java.net.MalformedURLException".
		// Keep the code in try catch block to continue the broken link analysis
		try {
			// Use URL Class - Create object of the URL Class and pass the
			// urlLink as parameter
			URL link = new URL(urlLink);
			// Create a connection using URL object (i.e., link)
			HttpURLConnection httpConn = (HttpURLConnection) link
					.openConnection();
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(2000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				Log.info(urlLink + " - "
						+ httpConn.getResponseMessage());
				return 1;
			}
			else if (httpConn.getResponseCode() == 404) {
				Log.info(urlLink + " - "
						+ httpConn.getResponseMessage());
				return 0;
			}
			else if (httpConn.getResponseCode() == 400 ) {
				Log.info(urlLink + " - "
						+ httpConn.getResponseMessage());
				return 0;
			}
			else if (httpConn.getResponseCode() == 401 ) {
				Log.info(urlLink + " - "
						+ httpConn.getResponseMessage());
				return 0;
			}
			else if (httpConn.getResponseCode() == 500 ) {
				Log.info(urlLink + " - "
						+ httpConn.getResponseMessage());
				return 0;
			}
			else
			{
				Log.info(urlLink + " - "
						+ httpConn.getResponseMessage());
				return 0;
			}
			
		}
		// getResponseCode method returns = IOException - if an error occurred
		// connecting to the server.
		catch (Exception e) {
			// e.printStackTrace();
		}
		return 0;
	}

}
