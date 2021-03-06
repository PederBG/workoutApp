package workoutApp;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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

	//Gives all exercises that are in the same exercise group
	public ArrayList<String> similarExercises(String exGroupName) { //oppg 4
		String sql = "SELECT ex_name FROM ex_in_exgroup WHERE group_name = '"+exGroupName+"'";
		ArrayList<ArrayList<String>> similarExercises = UseDB.getTable(sql);
		if (similarExercises.isEmpty()) {
			return null;
		}
		else {
		ArrayList<String> Result = new ArrayList<>();
		for (int i = 0; i<similarExercises.size(); i++) {
			Result.add(similarExercises.get(i).get(0));
		}
		return Result;
		}
	}

	public String numberOfWorkouts() { //oppg 5
		ArrayList<ArrayList<String>> numberOfWorkouts = UseDB.getTable("SELECT COUNT(*) FROM workout"); //denne funker:)
			return numberOfWorkouts.get(0).get(0);
		}
	//--------------------------------------------------------------------------------------

	// Text based app interface
	public void textAppLoop() {
		Scanner scan = new Scanner(System.in);
		String in = "";
		String arg1, arg2, arg3, arg4, arg5, arg6;
		
		//NOTE: This is hardcoded because making the java code was not the main objective of this project

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
					while (!isValidDateTime(arg1)) {
						System.out.println("Not a valid datetime!");
						System.out.print("Workout datetime (yyyy-mm-dd tt:mm:ss): ");
						arg1 = scan.nextLine();
					}
					System.out.print("Workout length (tt:mm:ss): ");
					arg2 = scan.nextLine();
					System.out.print("Info: ");
					arg3 = scan.nextLine();
					System.out.print("Shape (1-10): ");
					arg4 = scan.nextLine();
					while (true) {
					try {
							int toInt = Integer.parseInt(arg4);
							if (toInt < 0 || toInt > 10) {
								throw new Exception();
							}
							break;
						}catch (Exception e) {
							System.out.println("Not a valid number!");
							System.out.print("Shape (1-10): ");
							arg4 = scan.nextLine();
						}
					}
					System.out.print("Performance (1-10): ");
					arg5 = scan.nextLine();
					while (true) {
					try {
							int toInt = Integer.parseInt(arg5);
							if (toInt < 0 || toInt > 10) {
								throw new Exception();
							}
							break;
						}catch (Exception e) {
							System.out.println("Not a valid number!");
							System.out.print("Performance (1-10): ");
							arg5 = scan.nextLine();
						}
					}
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
					while (!isValidDateTime(arg2)) {
						System.out.println("Not a valid datetime!");
						System.out.print("Start datetime (yyyy-mm-dd tt:mm:ss): ");
						arg2 = scan.nextLine();
					}
					
					System.out.print("End datetime (yyyy-mm-dd tt:mm:ss): ");
					arg3 = scan.nextLine();
					while (!isValidDateTime(arg3)) {
						System.out.println("Not a valid datetime!");
						System.out.print("End datetime (yyyy-mm-dd tt:mm:ss): ");
						arg3 = scan.nextLine();
					}
					
					ArrayList<ArrayList<String>> res = resultLogs(arg1, arg2, arg3);
					if (res.isEmpty()) { System.out.println("No results from the given time period and exercise"); }
					else { System.out.println(res); }
					break;

				case "EXINGROUP":
					System.out.print("Exercise group name: ");
					arg1 = scan.nextLine();
					if ( similarExercises(arg1) == null) {
						System.out.println("Invalid exercise name.. Try again!");
					}
					else { System.out.println("Exercises in "+arg1+": "+similarExercises(arg1));}
					break;
				
				case "NUMWORKS":
					System.out.println("Number of total workouts: " + numberOfWorkouts());
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
				+ "   - Get exercise in group: EXINGROUP\n"
				+ "   - Get total number of workouts: NUMWORKS\n"
				+ "   - Get this info again: INFO or HELP\n"
				+ "   - Exit of of the app: QUIT or EXIT\n");
	}
	
    public static boolean isValidDateTime(String datetime) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setLenient(false);
	    try {sdf.parse(datetime); return true;}
	    catch (ParseException e) {return false;}
}


	public static void main(String[] args) throws ParseException {
		Main m = new Main();

		m.textAppLoop();

	}

}
