import java.io.*;


public class FileHandler {
	
	BufferedWriter writer;
	BufferedReader reader;
	File f;
	
	public FileHandler(){
		f = new File("./Data.bin");
		try{
			if(!f.exists())
				f.createNewFile();
		} catch (Exception e){
			System.err.println("Cannot create file: " + e);
		}
	}
	
	public void write(String content){
		try{
			writer= new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
			writer.write(content);
			writer.close();
		} catch (Exception e){
			System.err.println("Write Error: " + e);
		}
	}
	
	public void writeRecord(String content){
		try{
			File r = new File("./Log.bin");
			if(!r.exists())
				r.createNewFile();
			
			writer = new BufferedWriter(new FileWriter(r.getAbsoluteFile(), true));
			writer.write(content);
			writer.close();
		} catch (Exception e){
			System.err.println("Write Error: " + e);
		}
	}
	
	public String read(){
		String contents = "";
		try{
			reader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
			String temp = reader.readLine();
			while(temp != null){
				contents += temp;
				temp = reader.readLine();
			}
			
		} catch (Exception e){
			System.err.println("Read Error: " + e);
		}
		
		return contents;
	}
	
	public void writeOptions(String content){
		try {
		File o = new File("./Options.ini");
		if(o.exists())
			o.createNewFile();
			writer = new BufferedWriter(new FileWriter(o.getAbsoluteFile(), true));
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			System.err.println("Option write Error: " + e);
		}
	}
	
	public String getOptions(){
		String options = "";
		
		File o = new File("./Options.ini");
		
		if(o.exists()){
			try{
				reader = new BufferedReader(new FileReader(o.getAbsoluteFile()));
				String temp = reader.readLine();
				while(temp != null){
					options += temp;
					temp = reader.readLine();
				}
			} catch (Exception e){
				System.err.println("Read Error in options: " + e);
			}
		}
		
		return options;
	}

}
