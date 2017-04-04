package rmi;

import information.Chunk;
import information.PeerInfo;
import message.MessageInfoGetChunk;
import message.MessageInfoPutChunk;
import threads.ThreadManager;

public class RMIRunner {
	public static void backUp(String path, int rep_degree) {
		System.out.println("Backup");
		ThreadManager.initBackUp(
				new MessageInfoPutChunk(
						PeerInfo.peerInfo.getVersionProtocol(), 
						PeerInfo.peerInfo.getServerID(),
						Chunk.getFileId(path), 
						1,
						rep_degree, 
						new byte[0]));
	}
	
	public static void close() {
		System.out.println("Close");	
		ThreadManager.closeThreads();
		System.exit(0);
		//RMIStorage.getRMI().unbind();
	}
	
	public static void parseArgs(String[] rmiArgs) {
		if( rmiArgs.length < 1 )
			return ;
		
		String protocol = rmiArgs[0];
		String path;
		int rep_degree;
		
		switch(protocol) {
		case "BACKUP":
			if( rmiArgs.length != 3 )
				return ;
			path = rmiArgs[1];
			rep_degree = Integer.parseInt(rmiArgs[2]);
			backUp(path, rep_degree);
			break;
		case "RESTORE":
			if( rmiArgs.length != 2 )
				return ;
			path = rmiArgs[1];
			restore(path);
			break;
		case "CLOSE":
			if( rmiArgs.length != 1 )
				return ;
			close();
		}
		return ;
	}
	
	public static void restore(String path) {
		System.out.println("Restore");		
		ThreadManager.initRestore(
				new MessageInfoGetChunk(
						PeerInfo.peerInfo.getVersionProtocol(), 
						PeerInfo.peerInfo.getServerID(),
						Chunk.getFileId(path), 
						0));
	}
}
