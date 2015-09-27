import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.pucpr.br.bsi2015.tcc.selfiecode.controller.MetricsController;

public class Principal {
	public static void main(String[] args) {
		
				//MetricsController.getInstance().uploadFile(null);
				
				File f = new File("C:\\selfiecode\\src2015-54-21-22-54-023.zip");
				JSch jsch = new JSch();
		        Session session = null;
		        try {
		            //session = jsch.getSession("fileSaverFTP", "192.168.112.129", 22);
		        	jsch.addIdentity("C:\\Users\\pedro-banali\\Downloads\\AWSKey.pem");
		        	session = jsch.getSession("ubuntu", "ec2-52-88-229-56.us-west-2.compute.amazonaws.com", 22);
		            session.setConfig("StrictHostKeyChecking", "no");
		            //session.setPassword("file@051526");
		            session.connect();

		            Channel channel = session.openChannel("sftp");
		            channel.connect();
		            ChannelSftp sftpChannel = (ChannelSftp) channel;
		            sftpChannel.put("C:\\selfiecode\\src2015-54-21-22-54-023.zip", "www/" + f.getName());
		            sftpChannel.exit();
		            session.disconnect();
		        } catch (JSchException e) {
		            e.printStackTrace();  
		        } catch (SftpException e) {
		            e.printStackTrace();
		        }
		
		

	}	
}
