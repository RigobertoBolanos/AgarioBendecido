package Conection;

import GUI.ServerGUI;

public class ServerThread extends Thread{
	
	private Server server;
	
	public ServerThread(Server server) {
		this.server=server;
	}
	
	@Override
	public void run() {
		super.run();
		while(true) {
			server.eat();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("running");
		}
	}

}
