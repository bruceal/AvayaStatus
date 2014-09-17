import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class httpHandler {
	URL url;
	
	public httpHandler(String address){
		try{
		url = new URL(address);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String requestData(){
		String data = "";
		try {
            //URL url = new URL("http://10.8.124.79/cloud/?agent=bruceal");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                //System.out.println(strTemp);
                data+= strTemp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error";
        }
		
		if(data.equals("[]")){
			//System.out.println("Nothing");
			data = "[{\"reason\": \"0\", \"elapsed_time\": \"00:00:00\"}]";
		}
		//System.out.println(data);
		return data;
	}

}
