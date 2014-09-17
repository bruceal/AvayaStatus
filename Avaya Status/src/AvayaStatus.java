import java.util.*;
import org.json.*;
public class AvayaStatus {
	
	static Timer t;
	static Timer clock;
	static httpHandler test;
	static Gui g;
	static FileHandler f;
	boolean flag = false;
	public AvayaStatus(){
		 g = new Gui();
		 test = new httpHandler("http://10.8.124.79/cloud/?agent=bruceal");
		 
		 f = new FileHandler();
		 java.util.Date date= new java.util.Date();
		 f.write("{\"Start\":\"" + date.toString() + "\"}");
		 this.attachShutdownHook();
		
		t = new Timer();
		t.schedule(new httpTimer(),0, 60000);
		
		clock = new Timer();
		clock.schedule(new workTimer(), 1000, 1000);
		
		//Gui.UpdateMenu("20", "GeoWork");
	}
	
	public void attachShutdownHook(){
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				//ShutDown stuff
				java.util.Date date= new java.util.Date();
				//JSONArray temp = new JSONArray(f.read());
				//JSONObject j = new JSONObject(temp.get(0));
				JSONObject j = new JSONObject(f.read());
				
				String status = g.getStatus();
				//System.out.println(status);
				String temp = "";
				for(int i =8; i < status.length(); i++){
					temp += status.charAt(i);
				}
				
				String elapsed = g.getTime();
				String temptime = "";
				for(int i =6; i < elapsed.length(); i++){
					temptime += elapsed.charAt(i);
				}
				String[] hms = temptime.split(":");
				int milliseconds = (Integer.parseInt(hms[0])*3600 + Integer.parseInt(hms[1])*60 + Integer.parseInt(hms[2])) * 1000;
				
				j.put(temp, milliseconds);
				j.put("end", date.toString());
				f.writeRecord(j.toString());
				f.writeRecord(System.getProperty("line.separator"));
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AvayaStatus();
	}
	
	class workTimer extends TimerTask {
		public void run(){
			String time = g.getTime();
			String temp = "";
			for(int i =6; i < time.length(); i++){
				temp += time.charAt(i);
			}
			
			//System.out.println(temp);
			
			
			String[] hms = temp.split(":");
			
			//System.out.println(hms.length);
			
			int milliseconds = (Integer.parseInt(hms[0])*3600 + Integer.parseInt(hms[1])*60 + Integer.parseInt(hms[2])) * 1000;
			//System.out.println(milliseconds);
			milliseconds += 1000;
			
			int second = (milliseconds / 1000) % 60;
			int minute = (milliseconds / (1000 * 60)) % 60;
			int hour = (milliseconds / (1000 * 60 * 60)) % 24;
			
			String newTime;
			String s, h, m;
			if(second < 10){
				s= "0" + second;
			} else {
				s= "" + second;
			}
			if(minute < 10){
				m= "0" + minute;
			} else {
				m= "" + minute;
			}
			if(hour < 10){
				h= "0" + hour;
			} else {
				h= "" + hour;
			}
			newTime = h + ":" + m + ":" + s;
			//System.out.println(newTime);
			Gui.UpdateTime(newTime);
			g.updatePopup(g.getStatus() + " " + g.getTime());
			//Gui.showWarning();
			
			}
	}
	
	class httpTimer extends TimerTask {
		public void run(){
			//System.out.println("working");
			
			String result = test.requestData();
			
			JSONArray j = new JSONArray (result);
			String code = j.getJSONObject(0).getString("reason");
			String time = j.getJSONObject(0).getString("elapsed_time");
			
			switch (code){
			case "0":
				code = "Logged out";
			case "5":
				code = "Break";
				f.writeRecord(result);
				break;
			case "6":
				code = "Lunch";
				break;
			case "7":
				code = "Personal";
				break;
			case "68":
				code = "Geo Work";
				break;
			default:
				code = "Other";

			}
			
			String status = g.getStatus();
			//System.out.println(status);
			String temp = "";
			for(int i =8; i < status.length(); i++){
				temp += status.charAt(i);
			}
			
			
			
			//System.out.println(temp + " : " + code);
			if(!temp.equals(code) && flag){
				//Status Change
				JSONObject r = new JSONObject(f.read());
				String elapsed = g.getTime();
				String temptime = "";
				for(int i =6; i < elapsed.length(); i++){
					temptime += elapsed.charAt(i);
				}
				String[] hms = temptime.split(":");
				int milliseconds = (Integer.parseInt(hms[0])*3600 + Integer.parseInt(hms[1])*60 + Integer.parseInt(hms[2])) * 1000;
				
				
				if(r.has(temp)){
					int recordedTime = r.getInt(temp);
					r.put(temp, milliseconds+recordedTime);
				} else {
					r.put(temp, milliseconds);
				}
				f.write(r.toString());
				
				if(code.equals("Break") || code.equals("Lunch") || code.equals("Personal")){
					g.StatusPopup();
				} else {
					g.closePopup();
				}
				
			}
			
			Gui.UpdateMenu(time, code);
			flag = true;
		}
		
	}

}
