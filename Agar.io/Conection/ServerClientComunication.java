package Conection;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import GUI.ClientGUI;
import Model.Ball;
import Model.User;
import Model.Vec2;
/**
 * Representa el hilo que se le asigna a un cliente para la comunicación con el servidor
 * @author Juan Diego
 *
 */
public class ServerClientComunication extends Thread{
	/**
	 * Servidor del juego
	 */
	private Server server;
	/**
	 * Socket que permite recibir comunicaciones del cliente
	 */
	private Socket socket;
	//private Socket socketRecieve;
	/**
	 * Booleano que indica que el cliente está conectado por tanto se debe constantemente recibiendo sus mensajes
	 */
	private boolean isConected;
	/**
	 * Usuario al cual le corresponde este hilo de comunicación
	 */
	private User user;
	/**
	 * Permite crear un hilo para atender las solicitudes de un cliente
	 * @param server servidor del juego
	 * @param socket Socket que permite la comunicación con el cliente
	 * @param user Usuario al cual le corresponde este hilo de comunicación
	 */
	public ServerClientComunication(Server server, Socket socket, User user)
	{
		this.server = server;
		//this.socketRecieve = socketRecieve;
		this.socket = socket;
		this.user = user;
		this.isConected = true;
		System.out.println("10.1");
	}
	
	@Override
	public void run()
	{
		
		DataInputStream in;
		DataOutputStream out;
//		ObjectInputStream in;
//		ObjectOutputStream out;
		try 
		{
			server.getUsersAsignation().join();
			while(isConected) 
			{
				while(server.getLobby().isAlive())
				{
					
				}
//				File localFile = server.writeGameFile();
//				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile));
//				BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
//				out = new DataOutputStream(socket.getOutputStream());
//				//Se envia el nombre del fichero
//				out.writeUTF(localFile.getName());
//				//Enviamos el fichero
//				byte[] byteArray = new byte[8192];
//				int inn;
//				while ((inn = bis.read(byteArray)) != -1)
//				{
//					bos.write(byteArray,0,inn);
//				}
////				bos.flush();
//				out = new ObjectOutputStream(socket.getOutputStream());
//				out.writeObject(server.getGame());
//				out.flush();
//				Envío de usuarios
				out = new DataOutputStream(socket.getOutputStream());
				int usersNumber = server.getGame().getArrUsers().size();
				out.writeInt(usersNumber);
				for (int i = 0; i < usersNumber; i++) 
				{
					//User
					User actualUser = server.getGame().getArrUsers().get(i);
					out.writeUTF(actualUser.getNickName());
					out.writeUTF(actualUser.getE_mail());
					out.writeUTF(actualUser.getPassword());
					//BallUser
					Ball ballActualUser = actualUser.getBall();
					out.writeDouble(ballActualUser.getPos().getX());
					out.writeDouble(ballActualUser.getPos().getY());
					out.writeInt(ballActualUser.getColor().getRGB());
					out.writeDouble(ballActualUser.getRadius());
					out.writeDouble(ballActualUser.getSpeed());
				}
				//Envío de comida
				int foodNumber = server.getGame().getArrFood().size();
				out.writeInt(foodNumber);
				for (int i = 0; i < foodNumber; i++) 
				{
					//Ball
					Ball actualFood = server.getGame().getArrFood().get(i);
					out.writeDouble(actualFood.getPos().getX());
					out.writeDouble(actualFood.getPos().getY());
					out.writeInt(actualFood.getColor().getRGB());
					out.writeDouble(actualFood.getRadius());
					out.writeDouble(actualFood.getSpeed());
				}
				in = new DataInputStream(socket.getInputStream());	
//				String[] posUser = in.readUTF().split(",");	
//				System.out.println(posUser);
//				user.getBall().setPos(new Vec2((Double.parseDouble(posUser[0])), Double.parseDouble(posUser[1]))); 
//				//Bolas comidas
//				int eatenFood = in.readInt();
//				ArrayList<Ball> comidaServer = server.getGame().getArrFood();
//				synchronized (comidaServer) 
//				{
//					for (int i = 0; i < eatenFood; i++)
//					{
//						//Ball 
//						Vec2 pos = new Vec2(in.readDouble(), in.readDouble());
//						Color color = new Color(in.readInt());
//						Double radius = in.readDouble();
//						Double speed = in.readDouble();
//						
//						Ball actualBall = new Ball(pos.getX(), pos.getY(), color, radius);
//						comidaServer.remove(actualBall);
//					}
//				}
				synchronized (server.getGame().getArrFood())
				{
					int ModifiedFoodNumber = in.readInt();
					ArrayList<Ball> newArrFood = new ArrayList<>();
					for (int i = 0; i < ModifiedFoodNumber; i++)
					{
						//Ball 
						Vec2 pos = new Vec2(in.readDouble(), in.readDouble());
						Color color = new Color(in.readInt());
						Double radius = in.readDouble();
						Double speed = in.readDouble();
						
						Ball actualBall = new Ball(pos.getX(), pos.getY(), color, radius);
						
					}
					ArrayList<Ball> serverFood = server.getGame().getArrFood();
					for (int i = 0; i < serverFood.size(); i++) 
					{
						boolean estaEnElCliente = false;
						int indexRemoval = 0;
						for (int j = 0; j < newArrFood.size() && !estaEnElCliente; j++)
						{
							if(serverFood.get(i).getPos().getX() == newArrFood.get(j).getPos().getX() 
								&& serverFood.get(i).getPos().getY() == newArrFood.get(j).getPos().getY())
							{
								estaEnElCliente = true;
								indexRemoval = j;
							}
						}
						if(!estaEnElCliente)
						{
							serverFood.remove(newArrFood.get(indexRemoval));
						}
					}
				}
//				System.out.println("11");
//				System.out.println("12");
//				in = new ObjectInputStream(socket.getInputStream());
//				System.out.println("13");
//				out = new ObjectOutputStream(socket.getOutputStream());
//				System.out.println("14");
//				out.writeObject(server.getGame());
//				System.out.println("Se mandó el mundo");
//				System.out.println("15");
//				String posUser = in.readUTF();
//				System.out.println("16");
//				user.getBall().setPos(new Vec2(posUser.charAt(0), posUser.charAt(1)));
//				System.out.println("17");

				
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}