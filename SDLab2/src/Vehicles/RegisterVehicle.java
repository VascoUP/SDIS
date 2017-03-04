package Vehicles;

public class RegisterVehicle {
	private String name;
	private String plate_no;
	
	public RegisterVehicle(String name, String plate_no) {
		this.name = name;
		this.plate_no = plate_no;
	}

	public String getName() {
		return name;
	}

	public String getPlate_no() {
		return plate_no;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (!(other instanceof RegisterVehicle))return false;
	    
	    RegisterVehicle reg = (RegisterVehicle)other;
	    
	    if( this.plate_no.equals(reg.getPlate_no()) ) return true;
	    return false;
	}
}
