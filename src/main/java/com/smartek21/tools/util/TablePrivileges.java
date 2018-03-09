package com.smartek21.tools.util;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.*;

public class TablePrivileges {
	private static final String FILENAME = "C:\\Users\\Loveshs\\Desktop\\Tables\\sfdc_lead_trueup.sql";

	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		FileReader fr = null;
		String extension = "";
		fr = new FileReader(FILENAME);
		br = new BufferedReader(fr);
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("CREATE TABLE");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				extension = lastOne.substring(lastOne.lastIndexOf("_") + 1);
				if (extension.equalsIgnoreCase("TRUEUP") || extension.equalsIgnoreCase("ST") || extension.equalsIgnoreCase("LAND")) {
					Pattern p1 = Pattern.compile("\\bTABLESPACE IDS_TBS\\b");
					String line;
					while ((line = br.readLine()) != null) {
						Matcher m1 = p1.matcher(line);
						if (m1.find())System.out.println("other");
					}
				} else {
					Pattern p2 = Pattern.compile("\\bTABLESPACE IDS_NOLOG_TBS\\b");
					String line;
					while ((line = br.readLine()) != null) {
						Matcher m1 = p2.matcher(line);
						if (m1.find())System.out.println("nolog");
					}

				}
			}
		}
	}
}
