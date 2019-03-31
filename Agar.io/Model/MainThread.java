package Model;

import GUI.ClientGUI;

public class MainThread extends Thread{
	
	private ClientGUI frame;
	private Game game;
	
	public MainThread(ClientGUI frame, Game game)
	{
		this.frame = frame;
		this.game = game;
	}
	
	
	
	public ClientGUI getFrame() {
		return frame;
	}



	public Game getGame() {
		return game;
	}



	public void setFrame(ClientGUI frame) {
		this.frame = frame;
	}



	public void setGame(Game game) {
		this.game = game;
	}



	@Override
	public void run()
	{
		
	}
	
}