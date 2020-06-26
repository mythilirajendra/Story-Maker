import edu.duke.*;
import java.util.*;

public class GladLib {
	private ArrayList<String> adjectiveList; //
	private ArrayList<String> cliList; //
	private ArrayList<String> placeList; //
	private ArrayList<String> nameList; //
	private ArrayList<String> animalList; //
	private ArrayList<String> yearList; //
	private ArrayList<String> foodList; //
	private ArrayList<String> relList; //
	private ArrayList<String> seaList; //
	private ArrayList<String> tasteList; //
	private ArrayList<String> objList; //
	private ArrayList<String> whoList; //
	
	private Random myRandom;
	
	private static String dataSourceURL = "C:/Users/rajen/Downloads/GladLib/data";
	private static String dataSourceDirectory = "data";
	
	public GladLib(){
		initializeFromSource(dataSourceDirectory);
		myRandom = new Random();
	}
	
	public GladLib(String source){
		initializeFromSource(source);
		myRandom = new Random();
	}
	
	private void initializeFromSource(String source) {
		adjectiveList= readIt(source+"/tadj.txt");	
		cliList= readIt(source+"/cli.txt");
		placeList= readIt(source+"/place.txt");
		nameList= readIt(source+"/name.txt");
		animalList= readIt(source+"/ani.txt");
		yearList= readIt(source+"/year.txt");
		foodList= readIt(source+"/food.txt");
		relList= readIt(source+"/relation.txt");
		seaList= readIt(source+"/season.txt");
		tasteList= readIt(source+"/taste.txt");
		objList= readIt(source+"/obj.txt");
		whoList= readIt(source+"/who.txt");
	}
	
	private String randomFrom(ArrayList<String> source){
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}
	
	private String getSubstitute(String label) {
		if (label.equals("ani")) {
			return randomFrom(animalList);
		}
		if (label.equals("cli")){
			return randomFrom(cliList);
		}
		if (label.equals("food")){
			return randomFrom(foodList);
		}
		if (label.equals("name")){
			return randomFrom(nameList);
		}
		if (label.equals("obj")){
			return randomFrom(objList);
		}
		if (label.equals("place")){
			return randomFrom(placeList);
		}
		if (label.equals("relation")){
			return randomFrom(relList);
		}
		if (label.equals("season")){
			return randomFrom(seaList);
		}
		if (label.equals("tadj")){
			return randomFrom(adjectiveList);
		}
		if (label.equals("taste")){
			return randomFrom(tasteList);
		}
		if (label.equals("who")){
			return randomFrom(whoList);
		}
		if (label.equals("year")){
			return randomFrom(yearList);
		}
		return "**UNKNOWN**";
	}
	
	private String processWord(String w){
		int first = w.indexOf("<");
		int last = w.indexOf(">",first);
		if (first == -1 || last == -1){
			return w;
		}
		String prefix = w.substring(0,first);
		String suffix = w.substring(last+1);
		String sub = getSubstitute(w.substring(first+1,last));
		return prefix+sub+suffix;
	}
	
	private void printOut(String s, int lineWidth){
		int charsWritten = 0;
		for(String w : s.split("\\s+")){
			if (charsWritten + w.length() > lineWidth){
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w+" ");
			charsWritten += w.length() + 1;
		}
	}
	
	private String fromTemplate(String source){
		String story = "";
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		return story;
	}
	
	private ArrayList<String> readIt(String source){
		ArrayList<String> list = new ArrayList<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		return list;
	}
	
	public void makeStory(){
	    System.out.println("\n");
		String story = fromTemplate("storytemp.txt");
		printOut(story, 60);
	}
	


}
