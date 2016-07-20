import java.util.ArrayList;

public class Variable {
	String adbPath;
	String port;
	ArrayList<DeviceItem> deviceItems;
	
	public Variable(){
		adbPath="";
		port="5555";
		deviceItems=new ArrayList<>();
	}
	public void cleanDeviceItem(){
		deviceItems=null;
		deviceItems=new ArrayList<>();
	}
}

