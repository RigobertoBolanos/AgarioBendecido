package Conection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.SSLSocketFactory;

import GUI.ClientGUI;
import Model.Game;
import Model.User;
/**
 * Representa al cliente, jugador que solicitará conectarse al servidor de agario
 * @author Juan Diego
 *
 */
public class Client {
	
//	public static final String TRUSTTORE_LOCATION = "C:/Users/Asus/Documents/keystore.jks";
	
	/**
	 * Representa la ip local del cliente
	 */
	public static final String SERVER_IP = "localhost";
	/**
	 * Puerto por el cuál se comunicará el cliente con el servidor
	 */
	public static final int PORT = 7000;
	/**
	 * Permite establecer un punto para la comunicación con el servidor
	 */
	private Socket socket;
	/**
	 * Permite la construcción de un cliente
	 */
	private User user;
	
	private Game game;
	
	private boolean isConnected;
	
	private ClientServerComunication serverThread;
	
	private ClientGUI clientFrame;
//	private char[] password = {'v','i','e','j','i','t', 'o'};
	
	public Client(ClientGUI clientFrame, String nickname, String e_mail, String password)
	{
		try
		{
			this.clientFrame = clientFrame;
//			System.setProperty("javax.net.ssl.trustStore", TRUSTTORE_LOCATION);
			
//			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
//			
//			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//			keyStore.load(new FileInputStream(TRUSTTORE_LOCATION), password);
//			socket = sf.createSocket(SERVER_IP, PORT);
			
			socket = new Socket(SERVER_IP, PORT);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream( socket.getInputStream());
			out.writeUTF(nickname);
			out.writeUTF(e_mail);
			out.writeUTF(password);
			System.out.println("Se enviaron los datos del cliente");
			String respond = in.readUTF();
			System.out.println(respond);
			if(respond.equals("You're connected"))
			{
				isConnected = true;
				System.out.println("1");

				serverThread = new ClientServerComunication(this, nickname);
				System.out.println("2");

				serverThread.start();
			}

			
			
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public User getUser() {
		return user;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ClientServerComunication getServerThread() {
		return serverThread;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public void setServerThread(ClientServerComunication serverThread) {
		this.serverThread = serverThread;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	
	
	
	
}