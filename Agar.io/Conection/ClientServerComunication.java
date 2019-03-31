package Conection;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

import Model.Ball;
import Model.Game;
import Model.User;
import Model.Vec2;

public class ClientServerComunication extends Thread{
	
	private Client client;
	private String nickname;
	private boolean isGameReady;
	
	public ClientServerComunication(Client client, String nickname)
	{
		this.client = client;
		this.nickname = nickname;
		
	}
	
	@Override
	public void run()
	{
		
		while(client.isConnected())
		{
			DataOutputStream out;
			DataInputStream in;
//			ObjectInputStream in;
//			ObjectOutputStream out;
			try 
			{
//				DataOutputStream out;
//				BufferedInputStream bis;
//				BufferedOutputStream bos;
//				
//				byte[] receivedData;
//				int in;
//				String file;
//				//Servidor Socket en el puerto 5000
//					
//				//Buffer de 1024 bytes
//				receivedData = new byte[4000];
//				bis = new BufferedInputStream(client.getSocket().getInputStream());
//				DataInputStream dis=new DataInputStream(client.getSocket().getInputStream());
//				//Recibimos el nombre del fichero
//				file = dis.readUTF();
//				//Para guardar fichero recibido
//				bos = new BufferedOutputStream(new FileOutputStream("./Información_juego.txt"));
//				while ((in = bis.read(receivedData)) != -1)
//				{
//					bos.write(receivedData,0,in);
//				}
				in = new DataInputStream(client.getSocket().getInputStream());
				out = new DataOutputStream(client.getSocket().getOutputStream());
				//System.out.println("6");

				//client.setGame((Game) in.readObject());
				Game game = new Game();
				ArrayList<User> arrUsers = new ArrayList<>();
				ArrayList<Ball> food = new ArrayList<>();
				//System.out.println("7");
				int usersNumber= in.readInt();
				//System.out.println("8");

				for (int i = 0; i < usersNumber; i++)
				{
					//System.out.println("Creando usuario");

					//User
					String userNickName = in.readUTF();
					String userE_Mail = in.readUTF();
					String userPassword = in.readUTF();
					//BallUser
					Vec2 pos = new Vec2(in.readDouble(), in.readDouble());
					Color color = new Color(in.readInt());
					Double radius = in.readDouble();
					Double speed = in.readDouble();
					
					User actualUser = new User(userNickName, userE_Mail, userPassword);
					actualUser.setBall(new Ball(pos.getX(), pos.getY(), color, radius));
					actualUser.getBall().setSpeed(speed);
					arrUsers.add(actualUser);
				}
				//System.out.println("7");
				int foodNumber = in.readInt();
				for (int i = 0; i < foodNumber; i++)
				{
					//Ball 
					Vec2 pos = new Vec2(in.readDouble(), in.readDouble());
					Color color = new Color(in.readInt());
					Double radius = in.readDouble();
					Double speed = in.readDouble();
					
					Ball actualBall = new Ball(pos.getX(), pos.getY(), color, radius);
					food.add(actualBall);
				}
				//System.out.println("8");
				game.setArrFood(food);
				game.setArrUsers(arrUsers);
				client.setGame(game);
				System.out.println("9");
				//System.out.println("Se recibee el mundo");
				for (User b : client.getGame().getArrUsers()) 
				{
					if(b.getNickName().equals(nickname))
					{
						client.setUser(b);
					}
				}
				//System.out.println("10");
				isGameReady = true;
				//System.out.println("3");
//				in = new ObjectInputStream(client.getSocket().getInputStream());
//				System.out.println("4");
//				out = new ObjectOutputStream(client.getSocket().getOutputStream());
//				System.out.println("5");
//				Game game = (Game) in.readObject();
//				System.out.println("6");
//				client.setGame(game);
//				System.out.println("7");
				out.writeUTF(client.getUser().getBall().getPos().getX() + "" + client.getUser().getBall().getPos().getY());
				//System.out.println("8");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Client getClient() {
		return client;
	}
	public String getNickname() {
		return nickname;
	}

	public boolean isGameReady() {
		return isGameReady;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setGameReady(boolean isGameReady) {
		this.isGameReady = isGameReady;
	}
	

}
