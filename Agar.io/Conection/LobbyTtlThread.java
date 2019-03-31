package Conection;

/**
 * Hilo que permite la verificación del tiempo de lobby del juego
 * @author Juan Diego
 *
 */
public class LobbyTtlThread extends Thread{
	/**
	 * Tiempo de vida de lobby para conectarse al servidor del juego
	 */
	private long ttl;
	/**
	 * Permite crear un hilo que está vivo por el tiempo de lobby del juego
	 * @param ttl
	 */
	public LobbyTtlThread(long ttl)
	{
		this.ttl = ttl;
	}
	
	@Override
	public void run()
	{
		try {
			Thread.sleep(ttl);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
