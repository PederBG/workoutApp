package workoutApp;


import java.util.ArrayList;
import java.util.Scanner; 

import org.joda.time.DateTime;
import org.joda.time.LocalTime;


public class Main {
	
	//--------------------------------------------------------------------------------------
	public void registerEquipment(String name,String description) { //oppg 1
		UseDB.addRow("equipment",name,description);
	}
	public void registerExercise(String name,String description) { //oppg 1
		UseDB.addRow("exercise",name,description);
	}
	public void registerWorkout(int year, int month, int day,int hour,int minute, int second,String info,int shape,int performance,String note) {
		DateTime date = new DateTime(year,month,day,0,0,0);   //oppg 1 (litt usikker p� om mySQL godtar date og time)
		LocalTime time = new LocalTime(hour,minute,second);
		
		UseDB.addRow("workout",date,time,info,shape,performance,note);
	}
	public void registerGroup(String name,String description) { //oppg 4
		UseDB.addRow("ex_group",name,description);
	}
	
	public ArrayList<ArrayList<String>> latestWorkouts(int n) { //oppg 2
		ArrayList<ArrayList<String>> latestWorkouts = UseDB.getTable("SELECT * FROM workout ORDER by (date_time) DESC limit 0,"+n+";"); //Funker, men burde legge til flere workouts for � v�re sikker
		return latestWorkouts;
	}
	public ArrayList<ArrayList<String>> resultLogs(String exercise,int year1,int month1,int day1,int year2,int month2,int day2) { //oppg 3
		DateTime date1 = new DateTime(year1,month1,day1,0,0,0); 
		DateTime date2 = new DateTime(year2,month2,day2,0,0,0);
		ArrayList<ArrayList<String>> resultLogs = UseDB.getTable("SELECT performance FROM ((workout join ex_in_workout on workout.datetime = ex_in_workout.workout_date_time) join exercise on ex.ex_name = exercise.name) WHERE ex_name="+exercise+" ORDER by (date_time) DESC where date_time > "+date1+" AND date_time < "+date2); //feil sql syntax
		return resultLogs;
	}
	public ArrayList<ArrayList<String>> similarExercises() { //oppg 4
		 ArrayList<ArrayList<String>> similarExercises = UseDB.getTable("SQL sp�rring"); //feil sql syntax
			return similarExercises;
		}
	public ArrayList<ArrayList<String>> numberOfWorkouts() { //oppg 4
		ArrayList<ArrayList<String>> numberOfWorkouts = UseDB.getTable("SELECT COUNT(*) FROM workout"); //denne funker:)
			return numberOfWorkouts;
		}	
	//--------------------------------------------------------------------------------------
	
	// Text based app interface
	public void textAppLoop() {
		Scanner scan = new Scanner(System.in);
		String in = "";
		System.out.println("This is a text based workout app.\n"
				+ "COMMANDS:\n"
				+ "   - Regeister equipment: REGEQ\n"
				+ "   - Regeister exercise: REGEX\n"
				+ "   - Regeister workout: REGWORK\n"
				+ "TODO"
				+ "TODO"
				+ "TODO"
				+ "   - Exit of of the app: QUIT or EXIT\n");
		while (true) {
			try {
				in = scan.nextLine().toUpperCase();
			}catch (Exception e) {
				System.out.println("Invalid input");
			}
			switch (in) {
			
				case "REGEQ":
					System.out.println("todo regeq");
					break;
				case "REGEX":
					System.out.println("todo regex");
					break;
				case "REGWORK":
					System.out.println("todo regwork");
					break;
					
				case "QUIT":
					System.out.println("Goodbye");
					System.exit(0);
				case "EXIT":
					System.out.println("Goodbye");
					System.exit(0);
					
				default:
					System.out.println("Not a command");
			
			}
		}
	}
	
	public static void main(String[] args) {
		Main test = new Main();
		
		/*ArrayList<ArrayList<String>> test = UseDB.getTable("SELECT * FROM exercise");
		System.out.println(test);
				
		System.out.println(test2.resultLogs("pullups", 2018, 1, 1, 2018, 2, 28));*/
		
		test.textAppLoop();
	}

}
