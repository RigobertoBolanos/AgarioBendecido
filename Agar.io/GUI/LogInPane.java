package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import Conection.Client;

public class LogInPane extends JPanel implements ActionListener{
	
	public final static String START="Start";
	public final static String LOGS = "./Logs.txt";
	ClientGUI main;
	JPanel aux;
	JPanel aux2;
	JPanel aux3;
	JPanel aux4;
	JPanel aux5;
	JLabel lblMail;
	JTextField txtMail;
	JLabel lblPass;
	JTextField txtPass;
	JButton btnStart;
	JLabel lblNick;
	JTextField txtNick;
	
	public LogInPane(ClientGUI main) {
		this.main=main;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500,500));
		aux=new JPanel();
		aux2=new JPanel();
		aux3=new JPanel();
		aux4=new JPanel();
		aux5=new JPanel();
		aux4.setLayout(new GridLayout(4, 1));
		lblMail=new JLabel("Correo:");
		txtMail=new JTextField();
		txtMail.setPreferredSize(new Dimension(100, 20));
		lblPass=new JLabel("Password:");
		txtPass=new JTextField();
		txtPass.setPreferredSize(new Dimension(100, 20));
		btnStart=new JButton("Start");
		btnStart.addActionListener(this);
		btnStart.setEnabled(false);
		btnStart.setActionCommand(START);
		lblNick=new JLabel("NickName:");
		txtNick=new JTextField();
		aux.add(btnStart);
		aux2.add(lblMail);
		aux2.add(txtMail);
		aux3.add(lblPass);
		aux3.add(txtPass);
		aux5.add(lblNick);
		aux5.add(txtNick);
		txtNick.setPreferredSize(new Dimension(100, 20));
		aux4.add(aux2);
		aux4.add(aux3);
		aux4.add(aux5);
		aux4.add(aux);
		txtNick.setEnabled(false);
		add(aux4,BorderLayout.CENTER);
		
	}
	
	public String login(String email, String password) throws IOException{
		String permit = "";
		txtNick.setEnabled(false);
		File logs = new File(LOGS);
		BufferedReader br = new BufferedReader(new FileReader(logs));
		String account = br.readLine();
		while(account != null)
		{
			String[] data = account.split(",");
			if(email.equals(data[0]) && password.equals(data[1]));
			{
				permit = data[2];
			}
		}
		return permit;
	}
	public void signIn(String email, String password, String nickname) throws IOException
	{
		File logs = new File(LOGS);
		BufferedWriter bw = new BufferedWriter(new FileWriter(logs, true));
		bw.write(email + "," + password + "," + nickname);
	}
	
	public void register(){
		txtNick.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando=e.getActionCommand();
		if(comando.equals(START))
		{
			main.startGame();
		}
		
	}

	public JTextField getTxtMail() {
		return txtMail;
	}

	public JTextField getTxtPass() {
		return txtPass;
	}

	public JTextField getTxtNick() {
		return txtNick;
	}

	public void setTxtMail(JTextField txtMail) {
		this.txtMail = txtMail;
	}

	public void setTxtPass(JTextField txtPass) {
		this.txtPass = txtPass;
	}

	public void setTxtNick(JTextField txtNick) {
		this.txtNick = txtNick;
	}

	public JButton getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(JButton btnStart) {
		this.btnStart = btnStart;
	}
	

	
}
