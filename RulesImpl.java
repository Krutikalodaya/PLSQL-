package com.smartek21.tools.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RulesImpl implements Rules {
	BufferedReader br = null;
	FileReader fr = null;

	@Override
	public String analyzeOwnerName(String Path, String fileName) throws Exception {

		String extension = "";

		fr = new FileReader(Path + fileName);
		br = new BufferedReader(fr);
		String sCurrentLine;

		while ((sCurrentLine = br.readLine()) != null) {

			int index = sCurrentLine.toUpperCase().indexOf("CREATE TABLE");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				extension = lastOne.split("(\\.)")[0];
				br.close();
				break;
			}
		}

		fr = new FileReader(Path + fileName);
		br = new BufferedReader(fr);

		while ((sCurrentLine = br.readLine()) != null) {
			String[] keyword = new String[] { "EXECUTE", "CREATE INDEX", "ALTER TABLE", "GRANT SELECT ON" };
			for (int i = 0; i < 4; i++) {
				int index = sCurrentLine.toUpperCase().indexOf(keyword[i]);
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
						return "Owner name is not correct in file on line " + sCurrentLine;
					}
					i++;
				}
			}
		}
		return null;
	}
	@Override
	public String analyzeOwnerNamePackage(String Path, String fileName) throws Exception {

		String extension = "";

		fr = new FileReader(Path + fileName);
		br = new BufferedReader(fr);
		String sCurrentLine;

		while ((sCurrentLine = br.readLine()) != null) {

			int index = sCurrentLine.toUpperCase().indexOf("CREATE TABLE");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				extension = lastOne.split("(\\.)")[0];
				br.close();
				break;
			}
		}

		fr = new FileReader(Path + fileName);
		br = new BufferedReader(fr);

		while ((sCurrentLine = br.readLine()) != null) {
			String[] keyword = new String[] {"CREATE INDEX", "ALTER TABLE", "GRANT SELECT ON" };
			for (int i = 0; i < 3; i++) {
				int index = sCurrentLine.toUpperCase().indexOf(keyword[i]);
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
						return "Owner name is not correct in file on line " + sCurrentLine;
					}
					i++;
				}
			}
		}
		return null;
	}
	
	@Override
	public String analyzeDropTableScript(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String line1;
		boolean createIndexFound = false;
		Pattern p1 = Pattern.compile("END;");
		while ((line1 = br.readLine()) != null) {
			Matcher m1 = p1.matcher(line1);
			if (!createIndexFound)
				createIndexFound = m1.find();
			if (createIndexFound) {
				Pattern p2 = Pattern.compile("/");
				Matcher m2 = p2.matcher(line1);
				if (m2.find()) {
					return null;
				}
			}
		}
		br.close();
		return "error in drop table script " + line1;
	}

	@Override
	public String analyzeDataTypes(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String line;
		Pattern p1 = Pattern.compile("([VARCHAR2(0-9)])+\\w+\\d");
		Pattern p2 = Pattern.compile("\\W+[N][U][M][B][E][R]");
		Pattern p3 = Pattern.compile("\\W+[T][I][M][E][S][T][A][M][P]");
		Pattern p4 = Pattern.compile("\\bDATE\\b");
		Pattern p5 = Pattern.compile("\\bCLOB\\b");

		boolean createIndexFound = false;
		while ((line = br.readLine()) != null) {
			int index = line.toUpperCase().indexOf("CREATE TABLE");
			if (index != -1) {
				createIndexFound = true;
				continue;
			}
			if (createIndexFound) {
				if (line.equalsIgnoreCase("(")) {
					continue;
				} else if (line.indexOf(")") != -1) {
					break;
				} else {
					Matcher m1 = p1.matcher(line);
					Matcher m2 = p2.matcher(line);
					Matcher m3 = p3.matcher(line);
					Matcher m4 = p4.matcher(line);
					Matcher m5 = p5.matcher(line);
					if (!(m1.find() || m2.find() || m3.find() || m4.find() || m5.find())) {
						return "Not a valid datattype " + line;
					}
				}
			}
		}
		br.close();
		return null;
	}

	// return true if tablespaces are right
	@Override
	public String analyzeTableSpaces(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		String extension = "";
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.toUpperCase().indexOf("CREATE TABLE");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				extension = lastOne.substring(lastOne.lastIndexOf("_") + 1);
				if (extension.equalsIgnoreCase("TRUEUP") || extension.equalsIgnoreCase("ST")
						|| extension.equalsIgnoreCase("LAND")) {
					Pattern p1 = Pattern.compile("\\bTABLESPACE IDS_TBS\\b");
					String line;
					while ((line = br.readLine()) != null) {
						Matcher m1 = p1.matcher(line);
						if (m1.find())
							return null;
					}
					return "According to rule TABLESPACE IDS_TBS missing";
				} else {
					Pattern p2 = Pattern.compile("\\bTABLESPACE IDS_NOLOG_TBS\\b");
					String line;
					while ((line = br.readLine()) != null) {
						Matcher m1 = p2.matcher(line);
						if (m1.find())
							return null;
					}
					return "According to rule TABLESPACE IDS_NOLOG_TBS missing";
				}
			}
		}
		br.close();
		return null;
	}

	// return true if privileges are right
	@Override
	public String analyzeTablePrivileges(String Path, String fileName) throws Exception {
		boolean SCMSA_READ_SELECT = false, SCMSA_ARCHI = false, IDS_INFOUSR = false;
		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.toUpperCase().indexOf("CREATE TABLE");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				if (lastOne.substring(lastOne.lastIndexOf("_") + 1).equals("TRUEUP")) {
					while ((sCurrentLine = br.readLine()) != null) {
						int index1 = sCurrentLine.indexOf("GRANT SELECT ON");
						if (index1 != -1) {
							String[] words1 = sCurrentLine.split(" ");
							String lastOne1 = words1[words1.length - 1];
							String extension1 = lastOne1.substring(lastOne1.lastIndexOf(" ") + 1);
							if (extension1.equalsIgnoreCase("SCMSA_READ_SELECT;"))
								SCMSA_READ_SELECT = true;
							if (extension1.equalsIgnoreCase("SCMSA_ARCHI;"))
								SCMSA_ARCHI = true;
							if (extension1.equalsIgnoreCase("IDS_INFOUSR;"))
								IDS_INFOUSR = true;
						}
						if (SCMSA_READ_SELECT && SCMSA_ARCHI && IDS_INFOUSR)
							return null;
					}
				} else {
					while ((sCurrentLine = br.readLine()) != null) {
						int index1 = sCurrentLine.indexOf("GRANT SELECT ON");
						if (index1 != -1) {
							String[] words1 = sCurrentLine.split(" ");
							String lastOne1 = words1[words1.length - 1];
							if (lastOne1.substring(lastOne1.lastIndexOf(" ") + 1).equals("SCMSA_READ_SELECT;")) {
								return null;
							}
						}

					}
				}
			}

		}
		br.close();
		return "Table privileges missing";
	}

	// return true if Trigger Name is right
	@Override
	public String analyzeTriggerName(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		String extension1 = "";

		while ((sCurrentLine = br.readLine()) != null) {

			int index = sCurrentLine.toUpperCase().indexOf("CREATE OR REPLACE TRIGGER");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				extension1 = lastOne.split("(\\.)")[1];
				String[] extension2 = extension1.split("_");
				String lastOne1 = extension2[extension2.length - 1];
				if (lastOne1.toUpperCase().equals("TRG")) {
					return null;
				} else
					return "Error in " + sCurrentLine;
			}

		}
		return null;

	}
	
	// return true if Package Name is right
	public String analyzePackageName(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		String extension1 = "";

		while ((sCurrentLine = br.readLine()) != null) {

			int index = sCurrentLine.toUpperCase().indexOf("CREATE OR REPLACE PACKAGE");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 1];
				extension1 = lastOne.split("(\\.)")[1];
				String[] extension2 = extension1.split("_");
				String lastOne1 = extension2[extension2.length - 1];
				if (lastOne1.toUpperCase().equals("PKG")) {
					return null;
				} else
					return "Error in " + sCurrentLine;
			}

		}
		return null;

	}
	// return true if comment is right for trigger
	@Override
	public String analyzeCommentTrigger(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;

		int count = 0;
		String[] keywords = new String[] { "NAME", "PURPOSE", "REVISIONS", "Ver", "Date", "Author", "Description" };
		int k = keywords.length;
		boolean createIndexFound = false;

		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("DECLARE");
			if (index != -1) {
				createIndexFound = true;
				continue;
			}
			if (createIndexFound) {
				if (sCurrentLine.equalsIgnoreCase("\\*")) {
					continue;
				} else if (sCurrentLine.indexOf("*/") != -1) {
					if (count != k) {
						return "Comment section not in correct format";
					}

					break;
				} else {
					for (int j = 0; j < k; j++) {

						int index1 = sCurrentLine.indexOf(keywords[j]);
						if (index1 != -1) {
							count++;
						}
					}
				}

			}

		}
		return null;
	}
	
	//return true if comment is right for Packages
	@Override
	public String analyzeCommentPackage(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;

		int count = 0;
		String[] keywords = new String[] { "NAME", "PURPOSE", "REVISIONS", "Ver", "Date", "Author", "Description" };
		int k = keywords.length;
		boolean createIndexFound = false;

		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("AS");
			if (index != -1) {
				createIndexFound = true;
				continue;
			}
			if (createIndexFound) {
				if (sCurrentLine.equalsIgnoreCase("\\*")) {
					continue;
				} else if (sCurrentLine.indexOf("*/") != -1) {
					if (count != k) {
						return "Comment section not in correct format";
					}

					break;
				} else {
					for (int j = 0; j < k; j++) {

						int index1 = sCurrentLine.indexOf(keywords[j]);
						if (index1 != -1) {
							count++;
						}
					}
				}

			}

		}
		return null;
	}
	
	@Override
	//No Delete Statement Table
	public String analyzeTableDelete(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.toUpperCase().indexOf("DELETE");
			if (index != -1) {
				return "DELETE Statement Found";
				}
			}
		return null;
	}

	@Override
	//Commit Statement
	public String analyzeTableCommit(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		int count = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.toUpperCase().indexOf("COMMIT");
			if (index != -1) {
				count++;
				if(count > 1) {
				return "More than one commit found";
				}
			}
		}
		return null;
	}
	// return true if Exception Handling is present
	@Override
	public String analyzeExceptionHandling(String Path, String fileName) throws Exception {
		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;

		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("EXCEPTION");
			if (index != -1) {

				if (sCurrentLine.equals("EXCEPTION ")) {
					String line = br.readLine();
					int index1 = line.indexOf("WHEN");
					if (index1 != -1) {
						return null;
					} else {
						return "No Exception Handling found";
					}
				} 
			}
		}
		return null;
	}

	@Override
	public String analyzeCaseSensitivity(String Path, String fileName) throws Exception {
		String sCurrentLine;
		br = new BufferedReader(new FileReader(Path + fileName));
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("DROP TABLE");
			if (index != -1) {
				for (int i = 0; i < sCurrentLine.length(); i++) {
					char c = sCurrentLine.charAt(i);
					if (!(c >= 97 && c <= 122)) {
						return "Lower case letters not allowed in drop script";
					}
				}
			}
			break;
		}
		br.close();
		return null;
	}

	// Naming standard of synonym file
	@Override
	public String analyzeSynonymName(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));

		String extension1 = "";
		String extension2 = "";
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {

			int index = sCurrentLine.indexOf("CREATE OR REPLACE SYNONYM");
			if (index != -1) {
				String[] words = sCurrentLine.split(" ");
				String lastOne = words[words.length - 3];
				extension1 = lastOne.split("(\\.)")[1];
				extension2 = extension1.split("_")[0];
			if (!extension2.equals("SYN")) {
					return "Naming Standard dosen't match for Synonym" + sCurrentLine;
					
				} else {
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public String analyzeSynonymCreation(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("CREATE");
			if (index != -1) {

				int index1 = sCurrentLine.indexOf("CREATE OR REPLACE SYNONYM");
				if (index1 == -1) {
					return "Synonym creation syntax is not in correct format";
				}
			}
		}

		return null;
	}
	//Trigger Creation Syntax
	@Override
	public String analyzeTriggerCreation(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("CREATE OR");
			if (index != -1) {

				int index1 = sCurrentLine.indexOf("CREATE OR REPLACE TRIGGER");
				if (index1 == -1) {
					return "Trigger creation syntax is not in correct format";
				}
			}
		}

		return null;
	}
	
	@Override
	public String analyzePackageBodyCreation(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("CREATE OR");
			if (index != -1) {

				int index1 = sCurrentLine.indexOf("CREATE OR REPLACE PACKAGE BODY");
				if (index1 == -1) {
					return "package Body creation syntax is not in correct format";
				}
			}
		}

		return null;
	}

	@Override
	public String analyzePackageCreation(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			int index = sCurrentLine.indexOf("CREATE");
			if (index != -1) {

				int index1 = sCurrentLine.indexOf("CREATE OR REPLACE PACKAGE");
				if (index1 == -1) {
					return "Package creation syntax is not in correct format";
				}
			}
		}

		return null;
	}
	
	@Override
	public String analyzeNoDrop(String Path, String fileName) throws Exception {

		br = new BufferedReader(new FileReader(Path + fileName));
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			Pattern p = Pattern.compile("\\bDROP\\b");
			Matcher m = p.matcher(sCurrentLine);
			if (m.find())
				return "Drop statement Should not be there";
			}
		return null;
	}

}
