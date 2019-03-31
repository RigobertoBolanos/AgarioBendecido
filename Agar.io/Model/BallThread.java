package Model;

import GUI.ClientGUI;

public class BallThread extends Thread{
	private ClientGUI game;
	private Ball ball;
	
	public BallThread(ClientGUI game,Ball ball) {
		this.game=game;
		this.ball=ball;
	}
	
	@Override
	public void run() {
		super.run();
		while(true) {
			game.upDate();
			ball.move(game.mousePos());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
