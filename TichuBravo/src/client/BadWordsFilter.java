package client;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Loris
 *
 */
public class BadWordsFilter {
	public  ArrayList<String> badWordsList;
	public ArrayList<String>checkList;
	
	
	public BadWordsFilter() {
		this.badWordsList=new ArrayList<>();
		this.checkList=new ArrayList<>();
		
		this.badWordsList.add("Arschloch");
		this.badWordsList.add("Dummkopf");
		this.badWordsList.add("Hoe");
		this.badWordsList.add("Wixxer");
		//If we want to check for more words add a list
	}
	
	public boolean  checkInput(ArrayList<String> inputList) {
		boolean b = false;
		for(String s:inputList) {
			if(this.badWordsList.contains(s)) {
				checkList.add(s);
				b=true;
			}
		}
		return b;
		
	}

}