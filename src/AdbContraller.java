import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.magiclen.magiccommand.Command;
import org.magiclen.magiccommand.CommandListener;

public class AdbContraller {
	
	public AdbContraller(){
	}
	
	public String getFullCommandString(String command){
		return Main.variable.adbPath+command;
	}
	public void analysisDevice(String line){
		String deviceName="";
		int start=line.indexOf("model:");
		start+=6;
		int i=0;

		while(true){

			if (line.substring(start+i, start+i+1).equals(" ")) {
				break;
			}else {
				deviceName+=line.substring(start+i, start+i+1);
				i++;
			}
		}
		DeviceItem item=new DeviceItem();
		item.name=deviceName;
		
		for(int k=0;k<5;k++){
			if (line.substring(k, k+1).equals(".")) {
				item.isConnectWithWifi=true;
				break;
			}
		}
		
		if (item.isConnectWithWifi) {
			for(int k=0;k<5;k++){
				if (line.substring(k, k+1).equals(":")) {
					break;
				}else {
					item.ip+=line.substring(k, k+1);
				}
			}
		}
		
		Main.variable.deviceItems.add(item);
	}
	
	public void getDevices() {
		ArrayList<String> returnLines;
		returnLines=new ArrayList<>();
		//執行 設定回傳值編碼方式
		Command command=new Command(getFullCommandString(" devices -l"));
		command.setCommandListener(new CommandListener() {

			@Override
			public void commandStart(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void commandRunning(String arg0, String arg1, boolean arg2) {
				returnLines.add(arg1);
			}
			
			@Override
			public void commandException(String arg0, Exception arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void commandEnd(String arg0, int arg1) {
				// TODO Auto-generated method stub
				if (returnLines.size()>3) {
					for(int i=1;i<returnLines.size()-1;i++){
					analysisDevice(returnLines.get(1));
					}
				}else {
					Main.appendConslone("No device attached.", true);
				}
			}
		});
		command.runAsync("test");
	}
}
