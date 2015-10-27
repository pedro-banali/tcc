package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.sourceforge.metrics.builder.MetricsBuilder.CalculatorThread;


public class LoginDialog {
	
	//private static Text txt_Password;
	//private static Text txt_Username;
	
	private static CalculatorThread thread = null;
	
	private Text txt_Password;
	private Text txt_Username;
	private Display display;
	
	public LoginDialog(Display display) {
		this.display = display;
	}
	
	
	public void createContents(int status) {
		final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
		shell.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 1;
		shell.setLayout(fillLayout);
		
		final Composite composite = new Composite(shell, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		composite.setLayout(gridLayout);
        
		final Label img_Label = setImage(composite);   //Image is set
		final Composite cmp_Login = new Composite(composite, SWT.NONE);
		cmp_Login.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		final RowLayout rowLayout = new RowLayout();
		rowLayout.fill = true;
		cmp_Login.setLayout(rowLayout);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.widthHint = 196;
		cmp_Login.setLayoutData(gridData);
		
		createLoginLabel(cmp_Login);
		createUserNameLabel(cmp_Login);	
	    createUserNameText(cmp_Login);	
	    createPwdLabel(cmp_Login);
	    createPwdText(cmp_Login);
	    
		final Composite cmp_ButtonBar = new Composite(cmp_Login, SWT.NONE);
	    
	    final Label loginMessage = new Label(cmp_ButtonBar, SWT.NONE);
	    loginMessage.setText("                                               ");
	    loginMessage.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
	    loginMessage.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	   // final Region region = new Region();
	    
	    //Button actions
	    final Button btn_login = createLoginButton(cmp_ButtonBar);
	    
	    if(status == 1)
	    {
		    final Label errorMessage = new Label(cmp_Login, SWT.NONE);
		    errorMessage.setText("Login Inválido");
		    final RowData rowNew = new RowData();
		    rowNew.width = 260;
		    errorMessage.setForeground(display.getSystemColor(SWT.COLOR_RED));
		    errorMessage.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		    //errorMessage.setVisible(false);
		    errorMessage.setLayoutData(rowNew);
	    }

	    final Region region = new Region();
	    
	    btn_login.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String user;
				String password;
				
				user = getTxt_Username().getText();
				password = getTxt_Password().getText();
				
				try {

						int i = MetricsController.getInstance().login(user, password);
						if(i == 1 )
						{
							//JOptionPane.showMessageDialog(null, "Login inválido tente novamente");
							shell.close();
							createContents(1);
							
							loginMessage.setText("Login Inválido");
							//shell.redraw();
						}
						else if( i == 2)
						{
							shell.close();
							createContents(1);
							loginMessage.setText("Gerente não pode utilizar o SelfieCode");
							
						}
						else
						{
							//MetricsController.getInstance().uploadFile(null);
							shell.close();

						}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		final CLabel clbl_Message = new CLabel(cmp_Login, SWT.NONE);
		clbl_Message.setAlignment(SWT.RIGHT);

		clbl_Message.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		final RowData rowData_6 = new RowData();
		rowData_6.width = 188;
		clbl_Message.setLayoutData(rowData_6);
		clbl_Message.setText("SelfieCode");
		
		Rectangle pixel = new Rectangle(1, 1, 388, 182);
		region.add(pixel); 
		shell.setRegion(region);
		addDragListener(composite, img_Label, shell);
		shell.setLocation(320,290);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		region.dispose();
	}
	
	
	private Label setImage(Composite composite){
		Label img_Label = new Label(composite, SWT.NONE);
		composite.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		img_Label.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		//img_Label.setImage(ResourceManager.getPluginImage(MetricsPlugin.getDefault(), "img_login.bmp"));
		img_Label.setLayoutData(new GridData(195, 181));
		return img_Label;
    }
	
	private void createLoginLabel(Composite cmp_Login){
		final CLabel clbl_UserLogin = new CLabel(cmp_Login, SWT.NONE);
		final RowData rowData = new RowData();
		rowData.width = 180;
		clbl_UserLogin.setLayoutData(rowData);
		clbl_UserLogin.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		clbl_UserLogin.setText("User Login");
	}
	
	private void createUserNameLabel(Composite cmp_Login){
		final CLabel clbl_Username = new CLabel(cmp_Login, SWT.NONE);
		final RowData rowData_1 = new RowData();
		rowData_1.width = 180;
		clbl_Username.setLayoutData(rowData_1);
		clbl_Username.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		clbl_Username.setText("Username");
	}
	
	private void createUserNameText(Composite cmp_Login)
	{
		txt_Username = new Text(cmp_Login, SWT.BORDER);
		final RowData rowData_2 = new RowData();
		rowData_2.width = 170;
		txt_Username.setLayoutData(rowData_2);
		txt_Username.setText("DEVELO21");
	}

	private void createPwdLabel(Composite cmp_Login)
	{
		final CLabel clbl_Password = new CLabel(cmp_Login, SWT.NONE);
		final RowData rowData_3 = new RowData();
		rowData_3.width = 180;
		clbl_Password.setLayoutData(rowData_3);
		clbl_Password.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		clbl_Password.setText("Password");
	}

    
	private void createPwdText(Composite cmp_Login)
	{
		txt_Password = new Text(cmp_Login, SWT.BORDER);
		final RowData rowData_4 = new RowData();
		rowData_4.width = 170;
		txt_Password.setLayoutData(rowData_4);
		txt_Password.setText("teste@123");
		txt_Password.setEchoChar('*');
	}
	
	private Button createLoginButton(Composite cmp_ButtonBar){
		final RowData rowData_5 = new RowData();
		rowData_5.height = 38;
		rowData_5.width = 185;
		cmp_ButtonBar.setLayoutData(rowData_5);
		cmp_ButtonBar.setLayout(new FormLayout());
		cmp_ButtonBar.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		
		final Button btn_login = new Button(cmp_ButtonBar, SWT.FLAT);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 28);
		formData.top = new FormAttachment(0, 5);
		formData.right = new FormAttachment(100, -3);
		formData.left = new FormAttachment(100, -40);
		btn_login.setLayoutData(formData);
		btn_login.setText("Login");
		
		return btn_login;
	}
	
	
	public void addDragListener(Composite composite, Label img_Label, final Shell shell){
		Listener l = new Listener() {
			Point origin;
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.MouseDown:
					origin = new Point(e.x, e.y);
					break;
				case SWT.MouseUp:
					origin = null;
					break;
				case SWT.MouseMove:
					if (origin != null) {
						Point p = display.map(shell, null, e.x, e.y);
						shell.setLocation(p.x - origin.x, p.y - origin.y);
					}
					break;
				}
			}
		};
		
		composite.addListener(SWT.MouseDown, l);
		composite.addListener(SWT.MouseUp, l);
		composite.addListener(SWT.MouseMove, l);
		img_Label.addListener(SWT.MouseDown, l);
		img_Label.addListener(SWT.MouseUp, l);
		img_Label.addListener(SWT.MouseMove, l);
	}


	public Text getTxt_Password() {
		return txt_Password;
	}


	public void setTxt_Password(Text txt_Password) {
		this.txt_Password = txt_Password;
	}


	public Text getTxt_Username() {
		return txt_Username;
	}


	public void setTxt_Username(Text txt_Username) {
		this.txt_Username = txt_Username;
	}
	
	private static void setThread() {
		
	    for (Thread t : Thread.getAllStackTraces().keySet()) {
	        if (t.getName().equals("Metrics Calculator Thread")) 
	        	System.out.println(t.getName());
	        	//t.start();
	    }
	}
	
}
