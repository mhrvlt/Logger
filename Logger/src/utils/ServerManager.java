package utils;

import constants.LoggerConstants;

public class ServerManager {
	
	public String SERVERNAME;
	public String USERNAME;
	public String PASSWORD;
	public int PORT;
	public String REMOTE_DIR;
	

	public String getREMOTE_DIR() {
		return REMOTE_DIR;
	}

	public void setREMOTE_DIR(String rEMOTE_DIR) {
		REMOTE_DIR = rEMOTE_DIR;
	}

	public String getSERVERNAME() {
		return SERVERNAME;
	}

	public void setSERVERNAME(String sERVERNAME) {
		SERVERNAME = sERVERNAME;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	
	public void setEnvironmentDetails(String env) throws IllegalArgumentException, IllegalAccessException{
		
		String SERVER_NAME = LoggerConstants.getStringValue(env + "_NAME");
		String REMOTE_DIR = LoggerConstants.getStringValue(env + "_REMOTE_DIR");
		
		this.setUSERNAME(LoggerConstants.SERVER_USERNAME);
		this.setPASSWORD(LoggerConstants.SERVER_PASSWORD);
		this.setPORT(LoggerConstants.SERVER_PORT);
		this.setSERVERNAME(SERVER_NAME);
		this.setREMOTE_DIR(REMOTE_DIR);
		
	}
	
}
