package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import constants.LoggerConstants;

public class SftpUtility {

	private String sftpHost ;
	private int    sftpPort ;
	private String sftpUser ;
	private String sftpPassword ;
	private String sftpDir ;

	public SftpUtility(String sftpHost, int sftpPort, String sftpUser,
			String sftpPassword, String sftpDir) {
		super();
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;
		this.sftpUser = sftpUser;
		this.sftpPassword = sftpPassword;
		this.sftpDir = sftpDir;
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}

	public String getSftpUser() {
		return sftpUser;
	}

	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getSftpDir() {
		return sftpDir;
	}

	public void setSftpDir(String sftpDir) {
		this.sftpDir = sftpDir;
	}

	public boolean moveFileToDir(String localFilePath){
		return moveFileToDir(localFilePath, null, null, true);
	}
	public boolean moveFileToDir(String localFilePath, String remoteDirPath){
		return moveFileToDir(localFilePath, remoteDirPath, null, true);
	}
	public boolean moveFileToDir(String localFilePath, String remoteDirPath, String remoteFileName){
		return moveFileToDir(localFilePath, remoteDirPath, remoteFileName, true);
	}

	public boolean copyFileToDir(String localFilePath){
		return moveFileToDir(localFilePath, null, null, false);
	}
	public boolean copyFileToDir(String localFilePath, String remoteDirPath){
		return moveFileToDir(localFilePath, remoteDirPath, null, false);
	}
	public boolean copyFileToDir(String localFilePath, String remoteDirPath, String remoteFileName){
		return moveFileToDir(localFilePath, remoteDirPath, remoteFileName, false);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath, String remoteFileName, boolean isDelete){
		boolean returnResult = false;
		boolean deleteSuccess = false;
		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;

		try{
			JSch jsch = new JSch();
			session = jsch.getSession(this.sftpUser,this.sftpHost,this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;
			if(null != remoteDirPath)
				channelSftp.cd(remoteDirPath);
			else
				channelSftp.cd(this.sftpDir);

			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(channelSftp.get(LoggerConstants.SERVER_FILE_NAME));

			File f = new File(LoggerConstants.LOCAL_FULL_LOG_FILE_PATH);
			OutputStream os = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			while( (readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();
			
			//Disconnecting the channel
			channel.disconnect();
			//Disconnecting the session
			session.disconnect();
			if(isDelete){
				deleteSuccess = f.delete();
			}else{
				deleteSuccess = true;
			}
			
			System.out.println("Remote Log File successfully moved to local.");
			
			returnResult = deleteSuccess;
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return returnResult;
	}

}
