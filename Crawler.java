import java.io.*;
import java.util.*;

public class Crawler {
	public static String run() throws Exception
	{
		String startPath = "D:\\";
		int MAX_DEPTH = 1000;
		int currentDepth = 0;
		
		String fileSearch = "Megaman";
		String txtSearch = "no idea";
		
		File[] demFiles = new File(startPath).listFiles();
		Map<String, String> fileIndex = new HashMap<String, String>();
		Map<String, String> textIndex = new HashMap<String, String>();
		
		//To get things started
		for(File f: demFiles){
			//System.out.println(f.getName());
			System.out.println(f.getPath());
			if(f.isHidden()){
				continue;	//Don't index hidden files
			}else{
				fileIndex.put(f.getName(), f.getPath());
				if(f.getName().contains(".txt")){
					textIndex.put(f.getName(), f.getAbsolutePath());
				}
			}
			
		}
		
		//The actual crawling begins
		Map<String, String> temp = new HashMap<String, String>(); //workaround for concurrent access and modification
		
		for(Map.Entry<String, String> it : fileIndex.entrySet()){
			//Don't go over the limit
			if(currentDepth==MAX_DEPTH)
				break;	
			
			String p = it.getKey(); //Current key in consideration
			if(p.contains(".txt")){
				textIndex.put(p, fileIndex.get(p));
			}else{
				File[] localFiles = new File(fileIndex.get(p)).listFiles();
				
				for(File f : localFiles){
					if(f.isHidden()){
						continue;
					}else{
						temp.put(f.getName(), f.getAbsolutePath());
					}
				}
				currentDepth++;
			}
		}
		
		fileIndex.putAll(temp); //put back all the temporary entries into the main index
		
		//Print hash map for checking
		//System.out.println(fileIndex);
		
		//Searching file
		boolean fileFound = false;
		for(String k : fileIndex.keySet()){
			if(fileIndex.get(k).contains(fileSearch)){
				System.out.println("Found at: " +fileIndex.get(k));
				fileFound = true;
			}
		}
		
		if(fileFound==false){
			System.out.println("File not found, or at least at this depth");
		}
		
		//Searching text
		boolean txtFound = false;
		for(String k : textIndex.keySet()){
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(textIndex.get(k))); //Open each text file to search
				
				String buffer;
				while ((buffer = in.readLine()) != null) {
					if(buffer.contains(txtSearch)){
						System.out.println("Text found in file: " +textIndex.get(k));
						txtFound = true;
						return textIndex.get(k);
						
					}
				}
				if(txtFound == false){
					System.out.println("Text not found in any file, or at least at this depth");
					
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return "Not found";
	}
	
	public static void main(String[] args) throws Exception {
		run();
	}
}
