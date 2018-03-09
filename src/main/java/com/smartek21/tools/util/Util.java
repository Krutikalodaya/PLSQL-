package com.smartek21.tools.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	
	public static boolean  nameCheck(String fileName) throws Exception {
		Pattern lowercase = Pattern.compile("^[a-z_.]*$");
		Matcher lowercaseMatcher = lowercase.matcher(fileName);
		return lowercaseMatcher.matches();
	}
	
	public static boolean  extensionCheckPackage(String fileName) throws Exception {
		Pattern extension = Pattern.compile("([^\\s]+(\\.(?i)(pks|pkb))$)");
		Matcher extensionMatcher = extension.matcher(fileName);
		return extensionMatcher.matches();
		}
	
	public static boolean  extensionCheck(String fileName) throws Exception {
		Pattern extension = Pattern.compile("([^\\s]+(\\.(?i)(sql))$)");
		Matcher extensionMatcher = extension.matcher(fileName);
		return extensionMatcher.matches();
		
			
		}
	}
	
	

