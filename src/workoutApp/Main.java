package workoutApp;


import java.util.ArrayList;
import java.util.Scanner; 



public class Main {
	
	//--------------------------------------------------------------------------------------
	public boolean registerEquipment(String name, String description) { //oppg 1
		return UseDB.addRow("equipment", name, description);
	}
	
	public boolean registerExercise(String name, String description) { //oppg 1
		return UseDB.addRow("exercise", name, description);
	}
	
	//datetime format: yyyy-mm-dd tt:mm:ss, length format: tt:mm:ss
	public void registerWorkout(String datetime, String length, String info, int shape, int performance, String note) {
		UseDB.addRow("workout", datetime, length, info, shape, performance, note);
	}
	
	public void registerGroup(String name, String description) { //oppg 4
		UseDB.addRow("ex_group",name,description);
	}
	
	public ArrayList<ArrayList<String>> latestWorkouts(int n) { //oppg 2
		ArrayList<ArrayList<String>> res = UseDB.getTable("SELECT * FROM workout WHERE date_time < CURDATE() ORDER by (date_time) DESC limit 0," + n); 
		return res;
	}
	
	//datetime format: yyyy-mm-dd tt:mm:ss, length format: tt:mm:ss
	public ArrayList<ArrayList<String>> resultLogs(String exercise, String startDatetime, String endDatetime) { //oppg 3
		ArrayList<ArrayList<String>> res = UseDB.getTable("SELECT DISTINCT name, date_time,"
				+ " performance, note FROM ((workout join ex_in_workout on workout.date_time ="
				+ " ex_in_workout.workout_date_time) join exercise on ex_in_workout.ex_name = exercise.name)"
				+ " WHERE name = '" + exercise + "' AND date_time > '" + startDatetime + "' AND date_time < '"
				+ endDatetime + "'");
		return res;
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
		String arg1, arg2, arg3, arg4, arg5, arg6;
		
		printInfo();
		while (true) {
			try {
				System.out.print("Command: ");
				in = scan.nextLine().toUpperCase();
			}catch (Exception e) {
				System.out.println("Invalid input");
			}
			switch (in) {
			
				case "REGEQ":
					System.out.print("Name: ");
					arg1 = scan.nextLine();
					System.out.print("Description: ");
					arg2 = scan.nextLine();
					registerEquipment(arg1, arg2);
					break;
					
				case "REGEX":
					System.out.print("Name: ");
					arg1 = scan.nextLine();
					System.out.print("Description: ");
					arg2 = scan.nextLine();
					registerExercise(arg1, arg2);
					break;
					
				case "REGWORK":
					System.out.print("Workout datetime (yyyy-mm-dd tt:mm:ss): ");
					arg1 = scan.nextLine();
					System.out.print("Workout length (tt:mm:ss): ");
					arg2 = scan.nextLine();
					System.out.print("Info: ");
					arg3 = scan.nextLine();
					System.out.print("Shape (1-10): ");
					arg4 = scan.nextLine();
					System.out.print("Performance (1-10): ");
					arg5 = scan.nextLine();
					System.out.print("Note: ");
					arg6 = scan.nextLine();
					registerWorkout(arg1, arg2, arg3, Integer.parseInt(arg4), Integer.parseInt(arg5), arg6);
					break;
					
				case "REGGROUP":
					System.out.print("Name: ");
					arg1 = scan.nextLine();
					System.out.print("Description: ");
					arg2 = scan.nextLine();
					registerGroup(arg1, arg2);
					break;
					
				case "RESLOG":
					System.out.print("Exercise: ");
					arg1 = scan.nextLine();
					System.out.print("Start datetime (yyyy-mm-dd tt:mm:ss): ");
					arg2 = scan.nextLine();
					System.out.print("End datetime (yyyy-mm-dd tt:mm:ss): ");
					arg3 = scan.nextLine();
					System.out.println(resultLogs(arg1, arg2, arg3));
					break;
					
				case "INFO":
					printInfo();
					break;
				case "HELP":
					printInfo();
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
	
	private static void printInfo() {
		System.out.println("This is a text based workout app.\n"
				+ "COMMANDS:\n"
				+ "   - Regeister equipment: REGEQ\n"
				+ "   - Regeister exercise: REGEX\n"
				+ "   - Regeister workout: REGWORK\n"
				+ "   - Regeister exercise group: REGGROUP\n"
				+ "   - View results log from time period: RESLOG\n"
				+ "TODO"
				+ "   - Get this info again: INFO or HELP\n"
				+ "   - Exit of of the app: QUIT or EXIT\n");
	}
	
	public static void main(String[] args) {
		Main m = new Main();

		System.out.println(m.resultLogs("Benkpress", "2018-02-01 00:00:00", "2018-05-01 00:00:00"));
		
		m.textAppLoop();
	}

}
