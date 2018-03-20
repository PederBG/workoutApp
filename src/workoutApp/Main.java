package workoutApp;


import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;





public class Main {
	
	public void registerEquipment(String name,String description) { //oppg 1
		UseDB.addRow("equipment",name,description);
	}
	public void registerExercise(String name,String description) { //oppg 1
		UseDB.addRow("exercise",name,description);
	}
	public void registerWorkout(int year, int month, int day,int hour, int minute, int second,int sessionhour,int sessionminute, int sessionsecond,String info,int shape,int performance,String note) {
		DateTime date = new DateTime(year,month,day,hour,minute,second);   //oppg 1
		LocalTime time = new LocalTime(sessionhour,sessionminute,sessionsecond);
		
		UseDB.addRow("workout",date,time,info,shape,performance,note);
	}
	public void registerGroup(String name,String description) { //oppg 4
		UseDB.addRow("ex_group",name,description);
	}
	
	public ArrayList<ArrayList<String>> latestWorkouts(int n) { //oppg 2
		ArrayList<ArrayList<String>> latestWorkouts = UseDB.getTable("SELECT * FROM workout ORDER by (date_time) DESC limit 0,"+n+";"); //Funker, men burde legge til flere workouts for å være sikker
		return latestWorkouts;
	}
	public ArrayList<ArrayList<String>> resultLogs() { //oppg 3
	ArrayList<ArrayList<String>> resultLogs = UseDB.getTable("SQL spørring"); //feil sql syntax
		return resultLogs;
	}
	public ArrayList<ArrayList<String>> similarExercises() { //oppg 4
		ArrayList<ArrayList<String>> similarExercises = UseDB.getTable("SQL spørring"); //feil sql syntax
			return similarExercises;
		}
	public ArrayList<ArrayList<String>> numberOfWorkouts() { //oppg 4
		ArrayList<ArrayList<String>> numberOfWorkouts = UseDB.getTable("SELECT COUNT(*) FROM workout"); //denne funker:)
			return numberOfWorkouts;
		}
	
	public static void main(String[] args) {
		System.out.println("halla gutta");
		
		ArrayList<ArrayList<String>> test = UseDB.getTable("SELECT * FROM exercise");
		System.out.println(test);
		
		Main test2 = new Main();
		
		System.out.println(test2.latestWorkouts(1));
	}

}
