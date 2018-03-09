package com.smartek21.tools.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OwnerName {
	private static final String FILENAME = "C:\\Users\\Loveshs\\Desktop\\Tables\\sfdc_order_trueup.sql";

	public static void main(String[] args) {
		BufferedReader br = null;
		FileReader fr = null;
		String extension = "";

		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {

				int index = sCurrentLine.indexOf("CREATE TABLE");
				if (index != -1) {
					String[] words = sCurrentLine.split(" ");
					String lastOne = words[words.length - 1];
					extension = lastOne.split("(\\.)")[0];
					br.close();
					break;
				}
			}

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			while ((sCurrentLine = br.readLine()) != null) {
				String[] keyword = new String[] { "EXECUTE", "CREATE INDEX", "ALTER TABLE", "GRANT SELECT ON" };
				for (int i = 0; i < 4; i++) {
					int index = sCurrentLine.indexOf(keyword[i]);
					if (index != -1) {

						String[] words = sCurrentLine.split(" ");
						int k = words.length;

						int count1 = 0;
						for (int j = 0; j < k; j++) {
							String a = words[j];

							Pattern pattern = Pattern.compile(extension + ".*");
							Matcher matcher = pattern.matcher(a);
							boolean matches = matcher.matches();

							if (matches == true) {
								count1++;
							}
						}
						if (count1 == 0) {
							System.out.println("Owner name is not correct in file "+""+"on line "+ sCurrentLine);
						} 
						i++;
					}
				}
			}
		}

		catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
}
