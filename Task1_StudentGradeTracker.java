import java.util.ArrayList;
import java.util.Scanner;
public class Grade 
{
    public static void main(String args[]) 
    {
        ArrayList<Integer> grades = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("STUDENT GRADE TRACKER");
        System.out.println("Enter the number of grades:");
        int n = sc.nextInt();
        
        System.out.println("Enter student grades:");
        for (int i = 0; i < n; i++) 
        {
            int grade = sc.nextInt();
            grades.add(grade);
        }
        sc.close();
        int sum = 0;
        int high = grades.get(0);
        int low = grades.get(0);
        for (int i = 0; i < grades.size(); i++) 
        {
            int grade = grades.get(i);
            sum += grade;
            if (grade > high) 
            {
                high = grade;
            }
            if (grade < low) 
            {
                low = grade;
            }
        }
        sc.close();
        double avg = (double) sum / grades.size();
        System.out.println("Average Grade: " + avg);
        System.out.println("Highest Grade: " + high);
        System.out.println("Lowest Grade: " + low);
    }
}