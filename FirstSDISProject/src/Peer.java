import information.FileInfo;
import information.PeerInfo;
import rmi.RMIStorage;
import threads.ThreadManager;

public class Peer {	
	public static void main(String[] args) {
		if( args.length != 3 )
			return ;
		PeerInfo.createPeerInfo(args[0], args[2], Integer.parseInt(args[1]));
		initAll();		
	}
	
	public static void initAll() {
		FileInfo.init();
		ThreadManager.initThreadManager();
		RMIStorage.initRMI();
		RMIStorage.getRMI().bind();
	}
}
