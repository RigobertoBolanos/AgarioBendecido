package Model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Ball implements Serializable{

	public static final double FOOD_RADIOUS = 20;
	public static final double FOOD_SPEED = 0;
	public static final double USER_RADIOUS = 40;
	public static final double USER_SPEED = 15;
	
	private Vec2 pos;
	private Color color;
	private double radius;
	private double speed;
	
	public Ball(double posX, double posY, Color color, double radius) {
		super();
		pos=new Vec2(posX, posY);
		this.color = color;
		this.radius = radius;
		this.speed = 3;
	}
	
	public void move(Point e) {
		Vec2 v=new Vec2(e.getX(), e.getY());
		//System.out.println("V: "+v.getX()+"-"+v.getY());
		//System.out.println("Pos: "+pos.getX()+"-"+pos.getY());
		v=v.sub(pos);
		//System.out.println(v.getLength());
		if(v.getLength()>=2){
			
			v.mag(speed);
			pos=pos.add(v);
		}
	}
	
	public boolean eat(Vec2 actualPos,Ball other){
		boolean eat=false;
		Vec2 actual=actualPos.sub(other.pos);
		if((actual.getLength()+radius/2)<radius+other.getRadius()){
			eat=true;
			double sum=(Math.PI*radius*radius)+(Math.PI*other.radius*other.radius);
			double newRadius=Math.sqrt(sum/Math.PI);
			radius=newRadius;
			if(radius<=1) {
				speed=1;
			}
			else {
				
				speed-=0.01;
			}
		}
		return eat;
	}
	
	public double getPosX() {
		return pos.getX();
	}
	public double getPosY() {
		return pos.getY();
	}
	public Color getColor() {
		return color;
	}
	public double getRadius() {
		return radius;
	}
	public double getSpeed() {
		return speed;
	}
	public void setPosX(double posX) {
		this.pos.setX(posX);
	}
	public void setPosY(double posY) {
		this.pos.setY(posY);
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Vec2 getPos() {
		return pos;
	}

	public void setPos(Vec2 pos) {
		this.pos = pos;
	}
	
	
	

}