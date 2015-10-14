import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Principal {

	private String nomeProjeto;
	static int interval, intervaloN;

	static Timer timer;

	public static void main(String[] args) {
		Principal p = new Principal("Teste", 10);
		
	}
	
	public Principal(String nome, int min) {
		this.nomeProjeto = nome;
		intervaloN = min;
		reset();

	}

	public void reset() {

		int delay = 1000;
		int period = 1000;
		timer = new Timer(this.nomeProjeto);
		interval = intervaloN;
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				System.out.println(setInterval());

			}
		}, delay, period);

	}

	private final int setInterval() {
		if (interval == 1)
		{
			System.out.println("Parou");
			timer.cancel();
		}
		return --interval;
	}

	public static int getInterval() {
		return interval;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

//		
//				//MetricsController.getInstance().uploadFile(null);
//				
//				File f = new File("C:\\selfiecode\\src2015-54-21-22-54-023.zip");
//				JSch jsch = new JSch();
//		        Session session = null;
//		        try {
//		            //session = jsch.getSession("fileSaverFTP", "192.168.112.129", 22);
//		        	jsch.addIdentity("C:\\Users\\pedro-banali\\Downloads\\AWSKey.pem");
//		        	session = jsch.getSession("ubuntu", "ec2-52-88-229-56.us-west-2.compute.amazonaws.com", 22);
//		            session.setConfig("StrictHostKeyChecking", "no");
//		            //session.setPassword("file@051526");
//		            session.connect();
//
//		            Channel channel = session.openChannel("sftp");
//		            channel.connect();
//		            ChannelSftp sftpChannel = (ChannelSftp) channel;
//		            sftpChannel.put("C:\\selfiecode\\src2015-54-21-22-54-023.zip", "www/" + f.getName());
//		            sftpChannel.exit();
//		            session.disconnect();
//		        } catch (JSchException e) {
//		            e.printStackTrace();  
//		        } catch (SftpException e) {
//		            e.printStackTrace();
//		        }
//		
//		
//
//	}	
	
	
}
