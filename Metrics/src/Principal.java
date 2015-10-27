import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class Principal {

	private String nomeProjeto;
	static int interval, intervaloN;

	private Timer timer;

	public static void main(String[] args) {
		//Principal p = new Principal("Teste", 10);
//		IProgressMonitor progressMonitor = new NullProgressMonitor();
//
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//
//		IProject project = root.getProject("NovoProjeto");
//
//		project.create(progressMonitor);
//
//		project.open(progressMonitor);
//
//
//
//
//
//		IProjectDescription description = project.getDescription();
//
//		String[] natures = description.getNatureIds();
//
//		String[] newNatures = new String[natures.length + 1];
//
//		System.arraycopy(natures, 0, newNatures, 0, natures.length);
//
//		newNatures[natures.length] = JavaCore.NATURE_ID;
//
//		description.setNatureIds(newNatures);
//
//		project.setDescription(description, progressMonitor);
//
//
//
//
//		IJavaProject javaProject = JavaCore.create(project);
//
//
//
//
//		Set<IClasspathEntry> entries = new HashSet<IClasspathEntry>();
//
//		entries.addAll(Arrays.asList(javaProject.getRawClasspath()));
//
//		IVMInstall vmInstall= JavaRuntime.getDefaultVMInstall();
//
//		LibraryLocation[] locations= JavaRuntime.getLibraryLocations(vmInstall);
//
//		for (LibraryLocation element : locations) {
//
//		entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
//
//		}
//
//		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), progressMonitor);
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
