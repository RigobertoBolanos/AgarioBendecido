package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.*;

import Conection.Client;
import Conection.Server;
import Model.Game;
import Model.User;

public class ClientGUI extends JFrame implements ActionListener{
	public final static int ANCHO=1421;
	public final static int LARGO=1000;
	public final static String LOGIN="Log In";
	public final static String REGISTER="Register";
	public final static String SIGN_IN ="Sign In";

	private Draw draw;
	private JPanel aux;
	private JButton btnLogIn;
	private JButton btnRegister;
	private LogInPane logIn;
	private Client client;

	

	public ClientGUI() {
		setTitle("Agar.io");
		//setLayout(new BorderLayout());
		setSize(ANCHO,LARGO);
		setResizable(false);
		//draw=new Draw(this);
		//add(draw);
		aux=new JPanel();
		btnLogIn=new JButton("Log in");
		btnRegister=new JButton(REGISTER);
		aux.add(btnLogIn);
		aux.add(btnRegister);
		logIn=new LogInPane(this);
		//draw.getRootPane().setGlassPane(logIn);
		//draw.getRootPane().getGlassPane().setVisible(true);
		btnRegister.addActionListener(this);
		
		btnRegister.setActionCommand(REGISTER);
		btnLogIn.addActionListener(this);
		btnLogIn.setActionCommand(LOGIN);
		add(logIn,BorderLayout.CENTER);
		add(aux,BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
	}
	public void initialization(String signInorRegister) {
		setTitle("Agar.io");
		//setLayout(new BorderLayout());
		setSize(ANCHO,LARGO);
		setResizable(false);
		//draw=new Draw(this);
		//add(draw);
		aux=new JPanel();
		btnLogIn=new JButton("Log in");
		btnRegister=new JButton(signInorRegister);
		aux.add(btnLogIn);
		aux.add(btnRegister);
		logIn=new LogInPane(this);
		//draw.getRootPane().setGlassPane(logIn);
		//draw.getRootPane().getGlassPane().setVisible(true);
		btnRegister.addActionListener(this);
		
		btnRegister.setActionCommand(signInorRegister);
		btnLogIn.addActionListener(this);
		btnLogIn.setActionCommand(LOGIN);
		add(logIn,BorderLayout.CENTER);
		add(aux,BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();

	}
	
	public void upDate() {
		draw.repaint();
	}
	
	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI();
		clientGUI.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comand=e.getActionCommand();
		if(comand.equals(REGISTER)){
			logIn.register();
			btnRegister.setText(SIGN_IN);
			btnRegister.setActionCommand(SIGN_IN);
			pack();
			
		}
		else if(comand.equals(LOGIN))
		{
			try 
			{
				
				String permit = logIn.login(logIn.getTxtMail().getText(), logIn.getTxtPass().getText());
				if(!permit.equals(""))
				{
					logIn.txtNick.setText(permit);
					logIn.btnStart.setEnabled(true);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			};
		}
		else if(comand.equals(SIGN_IN))
		{
			try
			{
				
				logIn.signIn(logIn.getTxtMail().getText(), logIn.getTxtPass().getText(), logIn.getTxtNick().getText());
				btnRegister.setEnabled(false);
				logIn.getBtnStart().setEnabled(true);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public void startGame(){
		
		System.out.println("se trata de crear cliente");
		Client client = new Client(this, logIn.getTxtNick().getText(), logIn.getTxtMail().getText(), logIn.getTxtPass().getText());
		while(client.getServerThread() == null) 
		{
			
		}
		while(!client.getServerThread().isGameReady())
		{
			System.out.println("Cargando mundo");
		}
		System.out.println("creó cliente");
		this.client = client;
		System.out.println("se asignó el cliente a la interfaz");
		
		remove(logIn);
		remove(aux);
		draw=new Draw(this);
		add(draw,BorderLayout.CENTER);
		draw.setVisible(true);

		pack();
		setSize(ANCHO,LARGO);
	}
	public Point mousePos() {
		return draw.mousePos();
	}

	public Draw getDraw() {
		return draw;
	}

	public JPanel getAux() {
		return aux;
	}

	public JButton getBtnLogIn() {
		return btnLogIn;
	}

	public JButton getBtnRegister() {
		return btnRegister;
	}

	public LogInPane getLogIn() {
		return logIn;
	}

	public Client getClient() {
		return client;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

	public void setAux(JPanel aux) {
		this.aux = aux;
	}

	public void setBtnLogIn(JButton btnLogIn) {
		this.btnLogIn = btnLogIn;
	}

	public void setBtnRegister(JButton btnRegister) {
		this.btnRegister = btnRegister;
	}

	public void setLogIn(LogInPane logIn) {
		this.logIn = logIn;
	}
	public void setClient(Client client) {
		this.client = client;
	}


}
