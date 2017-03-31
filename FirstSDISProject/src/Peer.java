import information.AppInfo;
import information.PeerInfo;
import rmi.RMIStorage;
import threads.ThreadManager;

public class Peer {	
	public static void main(String[] args) {
		PeerInfo.createPeerInfo(args[0], args[2], Integer.parseInt(args[1]));
		
		AppInfo.init();
		ThreadManager.initListenerThreads();
		
		RMIStorage.initRMI();
		RMIStorage.getRMI().bind();
	}
}
