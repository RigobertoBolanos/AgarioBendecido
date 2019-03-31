package Conection;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.SSLServerSocketFactory;

import GUI.ClientGUI;
import Model.Ball;
import Model.Game;
import Model.MainThread;
import Model.User;
import Model.Vec2;

public class Server {
	
//	public static final String KEYSTORE_LOCATION = "C:/Users/Juan Diego/keystore.jks";
//	public static final String KEYSTORE_PASSWORD = "viejito";
	/**
	 * Puerto por el cuál se atenderán las solicitudes de clientes
	 */
	public static final int PORT = 7000;
	/**
	 * Hilo que permite escuchar las solicitudes de conexión al servidor y la asignación, durante el tiempo de lobby del juego
	 */
	private UsersAssignationThread usersAsignation;
	/**
	 * Lista de hilos que tienen el fin de permitir la comunicación entre los clientes y el servidor
	 */
	private ArrayList<ServerClientComunication> clientsThreads;
	/**
	 * Permite la creación de un socket individual para cada cliente, para asegurar la comunicación
	 */
	private ServerSocket serverSocket;
	/**
	 * Interfaz Gráfica del usuario
	 */
	private ClientGUI frame;
	/**
	 * Juego
	 */
	private Game game;
	private LobbyTtlThread lobby;
	private ServerThread thread;
	/**
	 * Permite la creación de un servidor para el juego
	 * @param lobbyTtl Tiempo de vida de lobby para la conexión al servidor
	 */
	public Server(long lobbyTtl)
	{
		try
		{
			game = new Game();
			game.generateFood();
			thread=new ServerThread(this);
//			System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
//			System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);
			
			// 1. creando un socket de servidor
//			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//			serverSocket =	ssf.createServerSocket(PORT);
			serverSocket = new ServerSocket(PORT);
			clientsThreads = new ArrayList<>();
			//System.out.println("1");
			usersAsignation = new UsersAssignationThread(this, lobbyTtl);
			usersAsignation.start();
			thread.start();
			//System.out.println("2");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Realiza la construccion de el archivo que debe enviarle al cliente para que posteriormente este 
	 * interprete los datos 
	 * 
	 * @throws IOException
	 */
	public File writeGameFile() throws IOException {
		File f = new File ("Información_juego");
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
//		
//		User[]esferasUsuarios = game.assignUsersBalls();
//		
//		ArrayList<Ball> comida =  game.getArrFood();
//		
//		for (int i = 0; i < esferasUsuarios.length; i++) {
//			Ball esferaUser = esferasUsuarios[i].getBall();
//			wr.write(esferaUser.getPosX()+";"+esferaUser.getPosY()+";"+esferaUser.getColor()+";"+esferaUser.getRadius()+";"+esferaUser.getSpeed()+"\n");
//		}
//		wr.write("------\n");
//		for (int i = 0; i < comida.size(); i++) {
//			Ball esferaComida = comida.get(i);
//			wr.write(esferaComida.getPosX()+";"+esferaComida.getPosY()+";"+esferaComida.getColor()+";"+esferaComida.getRadius()+";"+esferaComida.getSpeed()+"\n");
//		}
		out.writeObject(game);
		
		return f;
	}
	
	public void eat() {
		ArrayList<User> players=game.getArrUsers();
		ArrayList<Ball> food=game.getArrFood();
		for(int i=0; i<players.size();i++) {
			User user=players.get(i);
			//System.out.println("Hay jugadores");
			Ball player=user.getBall();
			if(player!=null) {
				//System.out.println("Not null");
				for(int j=0;j<food.size();j++) {
					Ball cell=food.get(j);
					Vec2 actual=player.getPos().sub(cell.getPos());
					//System.out.println(player.getPosX()+"-"+player.getPosY());
					if((actual.getLength()+player.getRadius()/2)<player.getRadius()+cell.getRadius()){
						//System.out.println("remove");
						food.remove(cell);
						double sum=(Math.PI*player.getRadius()*player.getRadius())+(Math.PI*cell.getRadius()*cell.getRadius());
						double newRadius=Math.sqrt(sum/Math.PI);
						player.setRadius(newRadius);
						if(player.getRadius()<=1) {
							player.setSpeed(1);
						}
						else {
							
							player.setSpeed(player.getSpeed()-0.01);
						}
					}
					
				}
				
			}
		}
	}

	public UsersAssignationThread getUsersAsignation() {
		return usersAsignation;
	}

	public ArrayList<ServerClientComunication> getClientsThreads() {
		return clientsThreads;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public ClientGUI getFrame() {
		return frame;
	}

	public Game getGame() {
		return game;
	}

	public void setUsersAsignation(UsersAssignationThread usersAsignation) {
		this.usersAsignation = usersAsignation;
	}

	public void setClientsThreads(ArrayList<ServerClientComunication> clientsThreads) {
		this.clientsThreads = clientsThreads;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void setFrame(ClientGUI frame) {
		this.frame = frame;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public LobbyTtlThread getLobby() {
		return lobby;
	}

	public void setLobby(LobbyTtlThread lobby) {
		this.lobby = lobby;
	}
	
	

}