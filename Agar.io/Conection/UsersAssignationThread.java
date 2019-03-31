package Conection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.User;

public class UsersAssignationThread extends Thread{
	/**
	 * Servidor del juego
	 */
	private Server server;
	/**
	 * Tiempo de vida de lobby para la conexión con el servidor
	 */
	private long lobbyTtl;
	/**
	 * Permite crear un hilo que escucha solicitudes de clientes durante el tiempo de lobby
	 * @param server servidor del juego
	 * @param lobbyTtl Tiempo de vida de lobby para la conexión con el servidor
	 */
	public UsersAssignationThread(Server server, long lobbyTtl)
	{
		this.server = server;
		this.lobbyTtl = lobbyTtl;
	}
	@Override
	public void run()
	{
		DataInputStream in;
		DataOutputStream out;
		int contador = 0;
		try 
		{	
			do
			{
				// 2. Esperar la conexion
				System.out.println ("Esperando conexión");
				
				
				Socket socket = server.getServerSocket().accept();

				System.out.println ("Conexión recibida de" + socket.getInetAddress ().getHostName());
				// 3. obtener flujos de entrada y salida
				out = new DataOutputStream(socket.getOutputStream ());				  
				in = new DataInputStream(socket.getInputStream());
				
				if(server.getGame().getArrUsers().size() < 5)
				{					
					System.out.println("conexión exitosa");
					
					//String[] userData = in.readUTF().split(",");
					System.out.println("3");
					String nickname = in.readUTF();
					System.out.println("4");
					String e_mail = in.readUTF();
					System.out.println("5");
					String contraseña = in.readUTF();
					System.out.println("6");
					
					User newUser = new User(nickname, e_mail, contraseña);
					server.getGame().getArrUsers().add(newUser);
					System.out.println("7");
					if(contador == 0)
					{
						server.setLobby(new LobbyTtlThread(lobbyTtl));
						server.getLobby().start();
						contador++;
					}
					ServerClientComunication clientThread = new ServerClientComunication(server, socket, newUser);
					System.out.println("8");
					server.getClientsThreads().add(clientThread);
					clientThread.start();
					System.out.println("9");
					out.writeUTF("You're connected");
					System.out.println("10");
					String respuesta = "Nuevo jugador conectado, su nickname es: " + nickname + ", su e-mail: " + e_mail + " y su contraseña: " + contraseña;
					System.out.println(respuesta);
					
				}
				else
				{
					out.writeUTF("Se ha llegado al máximo de jugadores conectados, no puedes jugar");
				}
				
				
			}while(server.getLobby().isAlive()) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.getGame().assignUsersBalls();
//		for (ServerClientComunication clientThread : server.getClientsThreads()) 
//		{
//			clientThread.start();
//		}
	}
}
