package com.smartek21.tools.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartek21.tools.model.Response;
import com.smartek21.tools.util.Rules;
import com.smartek21.tools.util.RulesImpl;
import com.smartek21.tools.util.Util;

@Controller
public class SqlController {

	public final static String PATH = "D:\\PLSQL\\";
	public final static String TABLE = "Tables";
	public final static String SEQUENCES = "Sequences";
	public final static String PACKAGES = "Packages";
	public final static String PACKAGESBODIES = "Packagebodies";
	public final static String TRIGGERS = "Triggers";
	public final static String SYNONYMS = "Synonyms";
	
	@RequestMapping("/")
	public String viewHome() {
		return "index";
	}
	
	@RequestMapping("/analyze")
	public String analyze(ModelMap model,@RequestParam("folder_input") String folderPath) throws Exception {
		List<Response> responses = new ArrayList<Response>();
		String resultant;
		Rules rules = new RulesImpl();
		
		File[] directories = new File(PATH).listFiles(File::isDirectory);
		for (File folder : directories) {
			if (folder.getName().equalsIgnoreCase(TABLE)) {
				for (File file : folder.listFiles()) {
					if (file.isFile()) {
						String fileName = file.getName();
						List<String> comments = new ArrayList<String>();
						Response response = new Response();
						response.setSqlFile(fileName);
						System.out.println(PATH+fileName);
						if(!Util.nameCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Name should be in lower case")));
							responses.add(response);
							}
						if(!Util.extensionCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Extension of the file is incorrect")));
							responses.add(response);
						}
							
						//owner name analysis
						resultant = rules.analyzeOwnerName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
							
						//drop table
						resultant = rules.analyzeDropTableScript(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//upper case
						resultant = rules.analyzeCaseSensitivity(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeTableSpaces
						resultant = rules.analyzeTableSpaces(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeDataTypes
						resultant = rules.analyzeDataTypes(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeTablePrivileges
						resultant = rules.analyzeTablePrivileges(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						response.setComments(comments);
						
						if(response.getComments().size() == 0) {
							response.setStatus("PASS");
						}else {
							response.setStatus("FAIL");
						}
						responses.add(response);
					}
				}
			}
			
			if (folder.getName().equalsIgnoreCase(SYNONYMS)) {
				for (File file : folder.listFiles()) {
					if (file.isFile()) {
						List<String> comments = new ArrayList<String>();
						Response response = new Response();
						String fileName = file.getName();
						response.setSqlFile(fileName);
						
						if(!Util.nameCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Name should be in lower case")));
							responses.add(response);
							}
						if(!Util.extensionCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Extension of the file is incorrect")));
							responses.add(response);
						}
						
						//analyzeSynonymName
						resultant = rules.analyzeSynonymName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeSynonymCreation
						resultant = rules.analyzeSynonymCreation(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//owner name analysis
						resultant = rules.analyzeOwnerName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						response.setComments(comments);
						
						if(response.getComments().size() == 0) {
							response.setStatus("PASS");
						}else {
							response.setStatus("FAIL");
						}
						responses.add(response);
					}
				}
			}
			
			if (folder.getName().equalsIgnoreCase(TRIGGERS)) {
				for (File file : folder.listFiles()) {
					if (file.isFile()) {
						List<String> comments = new ArrayList<String>();
						Response response = new Response();
						String fileName = file.getName();
						response.setSqlFile(fileName);
						
						if(!Util.nameCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Name should be in lower case")));
							responses.add(response);
							}
						if(!Util.extensionCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Extension of the file is incorrect")));
							responses.add(response);
						}
						//owner name analysis
						resultant = rules.analyzeOwnerName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeTriggerName
						resultant = rules.analyzeTriggerName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeComment
						resultant = rules.analyzeCommentTrigger(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeTriggerCreation
						resultant = rules.analyzeTriggerCreation(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeExceptionHandling
						resultant = rules.analyzeExceptionHandling(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeNoDrop
						resultant = rules.analyzeNoDrop(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						response.setComments(comments);
						
						if(response.getComments().size() == 0) {
							response.setStatus("PASS");
						}else {
							response.setStatus("FAIL");
						}
						responses.add(response);
					}
				}
			}
			
			if (folder.getName().equalsIgnoreCase(PACKAGES) || folder.getName().equalsIgnoreCase(PACKAGESBODIES)) {
				for (File file : folder.listFiles()) {
					if (file.isFile()) {
						List<String> comments = new ArrayList<String>();
						Response response = new Response();
						String fileName = file.getName();
						response.setSqlFile(fileName);
						
						if(!Util.nameCheck(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Name should be in lower case")));
							responses.add(response);
							}
						if(!Util.extensionCheckPackage(fileName)) {
							response.setStatus("FAIL");
							response.setComments(new ArrayList<String>(Arrays.asList("Extension of the file is incorrect")));
							responses.add(response);
						}	
						
						if(folder.getName().equalsIgnoreCase(PACKAGES)) {
							resultant = rules.analyzePackageCreation(PATH+folder.getName()+"\\", fileName);
							if( resultant != null)comments.add(resultant);
						}else {
							resultant = rules.analyzePackageBodyCreation(PATH+folder.getName()+"\\", fileName);
							if( resultant != null)comments.add(resultant);
						}
						
						//owner name analysis
						resultant = rules.analyzeOwnerName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeComment
						resultant = rules.analyzeCommentPackage(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzeExceptionHandling
						resultant = rules.analyzeExceptionHandling(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						//analyzePackageName
						resultant = rules.analyzePackageName(PATH+folder.getName()+"\\", fileName);
						if( resultant != null)comments.add(resultant);
						
						response.setComments(comments);
						
						if(response.getComments().size() == 0) {
							response.setStatus("PASS");
						}else {
							response.setStatus("FAIL");
						}
						responses.add(response);
					}
				}
			}
		}
		
		model.put("data", responses);
		return "result";
	}
	
}