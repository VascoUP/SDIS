import information.FileInfo;
import information.PeerInfo;
import rmi.RMIStorage;
import spacemanaging.SpaceManager;
import threads.ThreadManager;

/**
 * 
 * This class builds a peer
 *
 */
public class Peer {	
	/**
	 * This function initiates the file information, the thread manager and the RMI storage
	 */
	public static void initAll() {
		FileInfo.init();
		ThreadManager.initThreadManager();
		RMIStorage.initRMI();
		RMIStorage.getRMI().bind();
	}
	
	/**
	 * Main function to execute the peer's
	 * @param args Arguments passed by the terminal/command line
	 */
	public static void main(String[] args) {
		if( args.length != 3 )
			return ;
		//Creates the peer's information
		try {
			PeerInfo.createPeerInfo(args[0], args[2], Integer.parseInt(args[1]));
			SpaceManager.initializeManager();
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
		
		initAll();		
	}
}
