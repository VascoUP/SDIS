package service.general;

public abstract class PontualService extends Service {
	public PontualService() {
		super();
	}	
	
	public void run_service() {
	}
	
	@Override
	public void run() {
		run_service();
		end_service();
	}
}
