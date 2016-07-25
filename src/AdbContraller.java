import java.util.ArrayList;

import org.magiclen.magiccommand.Command;
import org.magiclen.magiccommand.CommandListener;

public class AdbContraller {
	final int WIFI=0;
	final int USB=1;
	String tmpIP="";

	public AdbContraller(){
	}

	public String getFullCommandString(String command){
		return Main.variable.adbPath+command;
	}
	public void analysisDevice(String line){
		String deviceName="";
		int start=line.indexOf("device:");
		start+=7;
		int i=0;
		while(true){
			if (start+i>=line.length()) {
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
			for(int k=0;k<line.length();k++){
				if (line.substring(k, k+1).equals(" ")) {
					break;
				}else {
					item.ip+=line.substring(k, k+1);
				}
			}
		}
		Main.variable.deviceItems.add(item);
	}

	public void changeConnectType(int type){
		String comm="";
		switch (type) {
		case WIFI:
			comm=" tcpip "+Main.variable.port;
			break;
		case USB:
			comm=" usb";
			break;

		default:
			break;
		}
		ArrayList<String> returnLines;
		returnLines=new ArrayList<>();
		Command command=new Command(getFullCommandString(comm));
		command.setCommandListener(new CommandListener() {

			@Override
			public void commandStart(String arg0) {
			}

			@Override
			public void commandRunning(String arg0, String arg1, boolean arg2) {
				returnLines.add(arg1);				
			}

			@Override
			public void commandException(String arg0, Exception arg1) {
				afterChangeConnectType(returnLines,type);
			}

			@Override
			public void commandEnd(String arg0, int arg1) {
				afterChangeConnectType(returnLines,type);
			}
		});
		command.runAsync("changeConnectType");
	}
	//設定完模式後輸出 並導入下一個指令
	public void afterChangeConnectType(ArrayList<String> returnLines,int type){
		boolean pass=true;
		if (returnLines.size()==0) {
			switch (type) {
			case WIFI:
				Main.appendConslone("restarting in TCP mode port: "+Main.variable.port, true);
				break;
			case USB:
				Main.appendConslone("restarting in USB mode", true);
				break;
			}
		}else {
			if (returnLines.get(0).substring(0,5).equals("error")) {
				pass=false;
			}
				Main.appendConslone(returnLines.get(0), true);
		}

		if (pass&&type==WIFI) {
			connectToDevices(true);
		}
	}
	public void getDevicesIP(){
		String comm=" shell netcfg";
		ArrayList<String> returnLines;
		returnLines=new ArrayList<>();
		Command command=new Command(getFullCommandString(comm));
		command.setCommandListener(new CommandListener() {

			@Override
			public void commandStart(String arg0) {
			}

			@Override
			public void commandRunning(String arg0, String arg1, boolean arg2) {
				if (arg1.length()>0) {
					returnLines.add(arg1);		
				}		
			}

			@Override
			public void commandException(String arg0, Exception arg1) {
			}

			@Override
			public void commandEnd(String arg0, int arg1) {
				boolean isWifiInterface=false;
				for(int i=0;i<returnLines.size();i++){
					if (returnLines.get(i).substring(0, 5).equals("wlan0")) {//尋找WIFI介面
						isWifiInterface=true;
						for (int j = 5; j < returnLines.get(i).length(); j++) {
							if (returnLines.get(i).substring(j, j+2).equals("UP")) {//確認WIFI運作中
								boolean startFlag=false;
								int start=0,end=0;
								for (int k = j+5; k <returnLines.get(i).length(); k++) {//尋找IP
									if (!returnLines.get(i).substring(k,k+1).equals(" ")&&!startFlag) {
										start=k;
										startFlag=true;
									}
									if (returnLines.get(i).substring(k,k+1).equals("/")) {
										end=k;
										break;
									}									
								}
								String ip= returnLines.get(i).substring(start,end);
								Main.appendConslone("Device IP:"+ip, true);
								if (ip.equals("0.0.0.0")) {
									Main.appendConslone("There is no wifi connection on the device.", true);
									Main.appendConslone("Maby the device wifi is down?", true);
								}else {
									tmpIP=ip;
									changeConnectType(WIFI);
								}
								break;
							}else if (returnLines.get(i).substring(j, j+2).equals("DO")) {
								Main.appendConslone("There is no wifi connection on the device.", true);
								Main.appendConslone("Maby the device wifi is down?", true);
								break;
							}
						}
						break;
					}
				}
				if(!isWifiInterface){
					Main.appendConslone("There is no wifi interface on the device.", true);
					Main.appendConslone("/========================================", false);
					for(int i=0;i<returnLines.size();i++){
						Main.appendConslone(returnLines.get(i), false);
					}
					Main.appendConslone("========================================/", false);
					Main.appendConslone("Please copy log and send to me(tesoldat@gmail.com) thank you.", false);
				}
			}
		});
		command.runAsync("getDevicesIP");
	}
	public void connectToDevices(boolean showLog){
		if (!tmpIP.equals("")) {
			String comm=" connect "+tmpIP;
			ArrayList<String> returnLines;
			returnLines=new ArrayList<>();
			Command command=new Command(getFullCommandString(comm));
			command.setCommandListener(new CommandListener() {

				@Override
				public void commandStart(String arg0) {
				}

				@Override
				public void commandRunning(String arg0, String arg1, boolean arg2) {
					returnLines.add(arg1);				
				}

				@Override
				public void commandException(String arg0, Exception arg1) {
				}

				@Override
				public void commandEnd(String arg0, int arg1) {
					if (showLog) {
						Main.appendConslone(returnLines.get(0), true);
						Main.appendConslone("You can unplug usb cable now.", false);
						Main.appendConslone("Enjoy debug without wire!", false);
						//getDevices(false);
					}
				}
			});
			command.runAsync("connectToDevices");
			tmpIP="";
		}
	}

	public void getDevices(boolean showLog) {
		ArrayList<String> returnLines;
		returnLines=new ArrayList<>();
		//執行 設定回傳值編碼方式
		Command command=new Command(getFullCommandString(" devices -l"));
		command.setCommandListener(new CommandListener() {

			@Override
			public void commandStart(String arg0) {

			}

			@Override
			public void commandRunning(String arg0, String arg1, boolean arg2) {
				returnLines.add(arg1);
			}

			@Override
			public void commandException(String arg0, Exception arg1) {

			}

			@Override
			public void commandEnd(String arg0, int arg1) {

				if (returnLines.size()>2) {
					Main.variable.cleanDeviceItem();//清除裝置資料
					Main.comboBox.removeAllItems();
					for(int i=1;i<returnLines.size()-1;i++){
						analysisDevice(returnLines.get(i));
					}
					for (int i = 0; i < Main.variable.deviceItems.size(); i++) {
						DeviceItem deviceItem=Main.variable.deviceItems.get(i);
						String isWifi,ip;
						if (deviceItem.isConnectWithWifi) {
							isWifi="(Wifi Mode)";
							ip=deviceItem.ip;
						}else {
							isWifi="(USB Mode)";
							ip="";
						}
						if (showLog) {
							Main.appendConslone("Devices("+(i+1)+"):"+deviceItem.name+isWifi+ip, true);
						}
						Main.comboBox.addItem(deviceItem.name+isWifi);
					}
					Main.appendConslone("", false);
				}else {
					Main.appendConslone("No device attached.", true);
				}
			}
		});
		command.runAsync("getDevices");
	}
}
