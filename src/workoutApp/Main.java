package workoutApp;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		System.out.println("halla gutta");
		
		ArrayList<ArrayList<String>> test = UseDB.getTable("SELECT * FROM exercise");
		System.out.println(test);
		
		UseDB.addRow("exercise", "test", "test");
		
		ArrayList<ArrayList<String>> test2 = UseDB.getTable("SELECT * FROM exercise");
		System.out.println(test2);
	}

}
