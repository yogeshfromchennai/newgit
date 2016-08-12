package com.hexaware.jsoftwarefactory.sourcecontroltools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.hexaware.framework.config.ConfigHandler;
import com.hexaware.framework.logger.LogFactory;
import com.hexaware.framework.logger.Logger;
import com.hexaware.jsoftwarefactory.plugin.utils.HJSFTemplate;

public class Git implements VersionControlTool {

	Logger log = LogFactory.getLogger(Git.class);

	@Override
	public String generateBatFile(String localRepoPath, String remoteRepo, String userName, String password, String projectName) throws Exception {
		log.info("generatebatFile method Starts...");
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("localRepoPath", localRepoPath);
		data.put("userName", userName);
		data.put("password", password);
		data.put("projectName", projectName);
		data.put("remoteRepo", remoteRepo.substring(remoteRepo.indexOf("://") + 3));
		data.put("drive", localRepoPath.substring(0, localRepoPath.indexOf(":/") + 1));
		data.put("schema", remoteRepo.substring(0, remoteRepo.indexOf("://")));

		String templatePath = ConfigHandler.getInstance().getSystemProperty("template.location");
		HJSFTemplate.copyTemplate(templatePath + "/Source Code Control System/Git/git-run.bat.ftl", localRepoPath + "/git-run.bat", data);

		log.info("generatebatFile method Ends...");
		return localRepoPath + "/git-run.bat";
	}

	@Override
	public void execute(String batFileLocation) throws Exception {
		log.info("execute Method Starts...");
		log.debug("bat File Location : " + batFileLocation);
		StringBuffer output = new StringBuffer();
		StringBuffer errOutput = new StringBuffer();

		Process p;
		p = Runtime.getRuntime().exec("cmd /c start " + batFileLocation);
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String line = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line + "\n");
		}
		log.info(output.toString());

		BufferedReader errReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		String errLine = "";
		while ((errLine = errReader.readLine()) != null) {
			System.out.println(errLine + "\n");
		}

		log.error(errOutput.toString());
		if (errOutput.toString().contains("'git' is not recognized as an internal or external command")) {
			throw new Exception("Git is not installed in this machine. Please Install git.");
		}

		log.info("execute method Ends...");
	}

	public static void main(String[] args) {
		Git git = new Git();
		String batFilePLocation;
		try {
			//batFilePLocation = git.generateBatFile("D:/Jeffer/WorkSpaces/runtime-EclipseApplication","http://172.25.164.110:9000/OEG-ADM/HexaJavaFactoryTesting", "25206", "as009318", "SampleDemoService5");
			batFilePLocation = "D:/Jeffer/WorkSpaces/runtime-EclipseApplication/git-run.bat";
			git.execute(batFilePLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
