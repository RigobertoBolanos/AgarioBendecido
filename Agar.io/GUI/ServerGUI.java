package GUI;

import javax.swing.JFrame;

import Conection.Server;

public class ServerGUI extends JFrame{

	private static Server server;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		server = new Server(10000);
		
	}

}
