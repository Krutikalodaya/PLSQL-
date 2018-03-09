package com.smartek21.tools.util;


public interface Rules {

	public String analyzeOwnerName(String Path,String fileName) throws Exception;
	
	public String analyzeDataTypes(String Path,String fileName) throws Exception;
	
	public String analyzeTableSpaces(String Path,String fileName) throws Exception;
	
	public String analyzeTablePrivileges(String Path,String fileName) throws Exception;
	
	public String analyzeTableDelete(String Path, String fileName) throws Exception;
	
	public String analyzeTableCommit(String Path, String fileName) throws Exception;
	
	public String analyzeDropTableScript(String Path,String fileName) throws Exception;
	
	public String analyzeCaseSensitivity(String Path,String fileName) throws Exception;
	
	public String analyzeTriggerName (String Path, String fileName) throws Exception;
	
	public String analyzePackageName (String Path, String fileName) throws Exception;
	
	public String analyzeCommentTrigger (String Path, String fileName) throws Exception;
	
	public String analyzeCommentPackage (String Path, String fileName) throws Exception;
	
	public String analyzeExceptionHandling (String Path, String fileName) throws Exception;
	
	public String analyzeSynonymName(String Path, String fileName) throws Exception;
	
	public String analyzeSynonymCreation(String Path, String fileName) throws Exception;
	
	public String analyzeTriggerCreation(String Path, String fileName) throws Exception;
	
	public String analyzeNoDrop(String Path, String fileName) throws Exception;
	
	public String analyzePackageCreation (String Path, String fileName) throws Exception;
	
	public String analyzePackageBodyCreation (String Path, String fileName) throws Exception;
	 
}
