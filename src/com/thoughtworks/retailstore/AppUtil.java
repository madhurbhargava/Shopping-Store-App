package com.thoughtworks.retailstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Small Utility class to facilitate other classes
 * @author mbhargava
 *
 */
public class AppUtil 
{
	
		/**
		 * Converts InputStream to String
		 * @param is, inputstream to be converted to string
		 * @return converted String
		 */
		public static String getStringFromInputStream(InputStream is) 
		{
	 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
		
		/**
		 * Checks if string is an integer
		 * @param s, string to be checked for integer
		 * @return true if string is integer, false otherwise
		 */
		public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		}
		
		/**
		 * Checks if string is an double
		 * @param s, string to be checked for double
		 * @return true if string is double, false otherwise
		 */
		public static boolean isDouble(String s) {
		    try { 
		        Double.parseDouble(s);
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		}
}
