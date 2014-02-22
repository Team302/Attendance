import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class AttendanceMainV1
{
	static String[][] people = new String[200][4]; // all people on the team
	static Scanner fileget = new Scanner(System.in);

	public static void main(String[] args) throws IOException
	{
		// System.out.println("Input location of file:");
		String file = "C:/Users/Will Kirkpatrick/Desktop/people.txt";// fileget.nextLine();
		FileReader fr = new FileReader(file);
		Scanner s = new Scanner(fr);
		Scanner s2 = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
		int x = 0;
		try
		{
			while (s.hasNext())
			{
				people[x][0] = s.next(); // ID number
				people[x][1] = s.nextLine(); // Name
				people[x][1] = people[x][1].trim(); // Removes white space
				people[x][2] = "00:00:00"; // Time in
				people[x][3] = "00:00:00"; // Time at meeting before signing out
				x++;
			}
			for (; x < 199; x++)
			{
				people[x][0] = "empty";
			}
		} finally
		{
			fr.close();
			s.close();
		}
		// System.out.println(people[0][0]);
		// System.out.println(people[0][1]);
		// System.out.println(people[0][2]);
		while (true)
		{
			System.out
					.println("Enter your choice:\n1 - Sign In\n2 - Sign Out\n3 - Print Attendance List\n4 - Write to CSV File");
			String r = s2.nextLine();
			if (!(r == ""))
			{

				int choice = Integer.parseInt(r);
				if (choice == 1) // Sign In
				{
					System.out.println("\nInput ID Number:");
					int y = -1;
					boolean found = false;
					String search = s2.nextLine();
					while (y < 199 && !found)
					{
						y++;
						if (search.equals(people[y][0]))
						{
							found = true;
						}
					}
					if (found == false)
						System.out.println("Error: ID not found.\n");
					else if (people[y][2].equals("00:00:00") && found == true)
					{
						people[y][2] = sdf.format(new Date()) + "";
						System.out.println("Welcome, " + people[y][1] + "\n");
					} else
					{
						System.out.println("You already signed in at " + people[y][2] + "\n");
					}
				} else if (choice == 2) // Sign Out
				{
					System.out.println("Input ID Number:");
					int y = -1;
					boolean found = false;
					String search = s2.nextLine();
					while (y < 199 && !found)
					{
						y++;
						if (search.equals(people[y][0]))
						{
							found = true;
						}
					}
					if (found == false)
						System.out.println("Error: ID not found.\n");
					else if (people[y][3].equals("00:00:00"))
					{
						people[y][3] = sdf.format(new Date());
						System.out.println("Thank you for signing out, " + people[y][1]
								+ "\nTime in: " + totaltime(people[y][2], people[y][3])[0]);
					} else
					{
						System.out.println("You are not signed in.\n");
					}
				} else if (choice == 3) // Print List
				{
					System.out.println("Name - Time In - Time Out - Total Time");
					for (int p = 0; p < 199; p++)
					{
						System.out.print(people[p][1] + " - ");
						if (people[p][2].equals("00:00:00"))
							System.out.print("N/A");
						else
							System.out.print(people[p][2]);
						System.out.print(" - ");
						if (people[p][3].equals("00:00:00"))
							System.out.println("N/A - N/A");
						else
						{
							System.out.print(people[p][3]);
							System.out.print(" - ");
							System.out.println(totaltime(people[p][2], people[p][3])[0]);
						}
						if (people[p + 1][0].equals("empty"))
							p = 999;
					}
				} else if (choice == 4) // Write to .csv
				{
					file = "C:/Users/Will Kirkpatrick/Desktop/Attendance.csv";
					FileWriter fw = new FileWriter(file, true);
					for (int w = 0; w < 199; w++)
					{
						if (!(people[w][0] == "empty"))
						{
							String[] data = new String[2];
							data = totaltime(people[w][2], people[w][3]);
							fw.write("\n" + sdf2.format(new Date()) + "," + people[w][0] + ","
									+ people[w][1] + "," + people[w][2] + "," + people[w][3] + ","
									+ data[0] + "," + data[1]);

						}
					}
					fw.close();
					System.out.println("File successfully written to " + file);
				} else
					System.out.println("Error: Invalid Input\n");
			} else
				System.out.println("Error: No Input.\n");
		}
	}

	public static String[] totaltime(String start, String end)
	{
		String[] x = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (start.equals("00:00:00") || end.equals("00:00:00"))
		{
			x[0] = "00:00:00";
			x[1] = "0";
			return x;
		}
		String starttime = start.charAt(6) + "" + start.charAt(7);
		String endtime = end.charAt(6) + "" + end.charAt(7);
		int temp = (Integer.parseInt(endtime) - Integer.parseInt(starttime));
		boolean temp1 = false;
		if (temp < 0)
		{
			temp = 60 + temp;
			temp1 = true;
		}
		if (temp <= 9)
			x[0] = ":0" + temp;
		else
			x[0] = ":" + temp;
		//
		starttime = start.charAt(3) + "" + start.charAt(4);
		endtime = end.charAt(3) + "" + end.charAt(4);
		temp = (Integer.parseInt(endtime) - Integer.parseInt(starttime));
		if (temp1 == true)
			temp -= 1;
		if (temp < 0)
		{
			temp = 60 + temp;
			temp1 = true;
		} else
			temp1 = false;
		if (temp <= 9)
			x[0] = ":0" + temp + x[0];
		else
			x[0] = ":" + temp + x[0];
		//
		starttime = start.charAt(0) + "" + start.charAt(1);
		endtime = end.charAt(0) + "" + end.charAt(1);
		temp = (Integer.parseInt(endtime) - Integer.parseInt(starttime));
		if (temp1 == true)
			temp -= 1;
		if (temp <= 9)
			x[0] = "0" + temp + x[0];
		else
			x[0] = temp + x[0];
		int temp2 = Integer.parseInt(x[0].charAt(0) + "" + x[0].charAt(1));
		if ((sdf.format(new Date()) == "Sat" || sdf.format(new Date()) == "Sun") && temp2 > 50)
			x[1] = "1";
		else if (temp2 > 23)
			x[1] = "1";
		else
			x[1] = "0";
		return x;
	}
}// C:\Users\Will Kirkpatrick\Desktop\People.txt
