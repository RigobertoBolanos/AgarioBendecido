package Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.PlainDocument;

import GUI.ClientGUI;

public class Game implements Serializable {
	
	//Constantes
	public final static int MAX_FOOD=100;
	
	
	public static long MATCH_TTL = 5000*60000;
	public static int MAX_PLAYERS = 5;
	public static int MIN_PLAYERS = 2;
	public static long MAX_LOBBY_TTL = 2000*60000;
	
	//Atributos
	
	//Relaciones
	//relación que contiene las esferas que son comida
	private ArrayList<Ball> food;
	
	//relación que contiene a los jugadores
	private ArrayList<User> arrUsers;
		
	//Constructor
	
	
	//métodos
	public Game() {
		
		food = new ArrayList<>();
		arrUsers = new ArrayList<>();
		
	}
	/**
	 * Genera 10.000 puntos de comida, cada uno con una posición al azar según el maximo largo y ancho
	 * del screen que manejará el servidor.
	 */
	public void generateFood () {
		food = new ArrayList<>();

		for (int i = 0; i < MAX_FOOD; i++) {		
			double posX = (Math.random() * ClientGUI.ANCHO)+1;
			double posY = (Math.random() * ClientGUI.LARGO)+1;
			
			Random rand = new Random(); 
			float r = rand.nextFloat(); 
			float g = rand.nextFloat(); 
			float b = rand.nextFloat(); 
			Color color = new Color (r,g,b);
			
			double radius = Ball.FOOD_RADIOUS;
			
			food.add( new Ball(posX, posY, color, radius));			
		}
		
	}
	
	public void addFood() {
		
			
				double posX = (Math.random() * ClientGUI.ANCHO)+1;
				double posY = (Math.random() * ClientGUI.LARGO)+1;
				
				Random rand = new Random(); 
				float r = rand.nextFloat(); 
				float g = rand.nextFloat(); 
				float b = rand.nextFloat(); 
				Color color = new Color (r,g,b);
				
				double radius = Ball.FOOD_RADIOUS;
				
				food.add( new Ball(posX, posY, color, radius));	
			
		
	}
	
	/**
	 * Le asigna a cada uno de los usuarios una esfera que está en una posicion al azar según 
	 * el largo y ancho del screen que manejará el servidor
	 */
	public ArrayList<User> assignUsersBalls() {		
		for (int i = 0; i < arrUsers.size(); i++) {
			double posX = (Math.random() * ClientGUI.ANCHO)+1;
			double posY = (Math.random() * ClientGUI.LARGO)+1;
			
			Random rand = new Random(); 
			float r = rand.nextFloat(); 
			float g = rand.nextFloat(); 
			float b = rand.nextFloat(); 
			Color color = new Color (r,g,b);
			
			double radius = Ball.USER_RADIOUS;
			double speed = Ball.USER_SPEED;
			
			Ball esfera = new Ball(posX, posY, color, radius);
			arrUsers.get(i).setBall(esfera);
		}
		return arrUsers;
	}

	public ArrayList<Ball> getArrFood() {
		return food;
	}



	public void setArrFood(ArrayList<Ball> arrFood) {
		this.food = arrFood;
	}
	
	public ArrayList<Ball> getFood() {
		return food;
	}
	public ArrayList<User> getArrUsers() {
		return arrUsers;
	}
	public void setFood(ArrayList<Ball> food) {
		this.food = food;
	}
	public void setArrUsers(ArrayList<User> arrUsers) {
		this.arrUsers = arrUsers;
	}
	
	
	

	
	

}