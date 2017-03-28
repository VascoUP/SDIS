package service.general;

import java.io.IOException;

public abstract class ContinuousService extends Service {
	public ContinuousService() {
		super();
	}
	
	@Override
	public void run() {
		while( !Thread.interrupted() ) {
			try {
				run_service();
			} catch (IOException | InterruptedException e) {
				return ;
			}
		}
	}
}
