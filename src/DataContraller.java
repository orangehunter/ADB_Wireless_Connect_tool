import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class DataContraller {
	
	final String keyFilePath="ADB_PATH";
	final String keyConnectPort="CONNECT_PORT";

	public JSONObject jsObject;
	public String path;
	public WriteFile writeFile;
	
	public DataContraller() {
		jsObject=new JSONObject();
		getPath();
		writeFile=new WriteFile();
	}
	public void chkFile(){
		if(!WriteFile.exists(path)){
			WriteFile.createNewFile(path);
			//System.out.println("create file");
		}
	}
	
	public void getPath(){
		File file=new File(System.getProperty("user.dir"));
		path=(file.getPath()+"\\ProfileDatas.save");
	}
	public boolean saveData(Variable dtats){
		chkFile();
		try {
			jsObject.put(keyFilePath, dtats.adbPath);
			jsObject.put(keyConnectPort, dtats.port);
			String outs=jsObject.toString();
			//System.out.println("JSON:"+outs);
			writeFile.writeText_UTF8(outs, path);
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
	public Variable readData(){
		Variable read=new Variable();
		chkFile();
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String ins="";
			while ((strLine = br.readLine()) != null) {
				ins+=strLine;
			}
			in.close();
			if (!ins.equals("")) {
			//System.out.println(ins);
			JSONObject readJObject=new JSONObject(ins);
			read.adbPath=readJObject.optString(keyFilePath, "");
			read.port=readJObject.optString(keyConnectPort,"");
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return read;
	}
}
