import java.util.ArrayList;

public class Variable {
	String adbPath;
	String port;
	int nowDevice;
	ArrayList<DeviceItem> deviceItems;
	
	public Variable(){
		adbPath="";
		port="5555";
		nowDevice=0;
		deviceItems=new ArrayList<>();
	}
	public void cleanDeviceItem(){
		deviceItems=null;
		deviceItems=new ArrayList<>();
	}
	public void getDeviceWifi_size(){
		
	}
}

