import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String dataInput;
        int[][] data;
        int[][] goalArray;
        int size;
        Puzzle p;
        int cnt = 1;

        try {
            File file = new File("config.txt");
            Scanner scnr = new Scanner(file);
            FileWriter writer = new FileWriter("report.txt");
            
            while(scnr.hasNextLine()) {

                writer.write("PUZZLE #" + cnt + "\n");
                dataInput = scnr.nextLine();
                String[] dataArr = dataInput.split(" ");
                size = (int)Math.sqrt(dataArr.length);
                data = new int[size][size];
                goalArray = new int[size][size];

                String str = "";
                String str1 = "";

                //Converting the data into a 2D array
                for(int i = 0; i < dataArr.length; i++) {
                    data[i / size][i % size] = Integer.valueOf(dataArr[i]);
                    goalArray[i / size][i % size] = i;
                    str += data[i / size][i % size] + " ";
                    str1 += goalArray[i / size][i % size] + " ";
                }
                System.out.println(str);
                System.out.println(str1);
                
                Node start = new Node(data, 0, 0);
                Node goal = new Node(goalArray, 0, 0);
                p = new Puzzle(size);
                
                try{
                    writer.write(p.print(start));
                } catch (IOException e) {
                    System.err.println("Error in writing to file.\n");
                }

                for(int i = 1; i <= 4; i++) {
                    p.solve(start, goal, i, writer);
                }
                cnt++;
            }

            scnr.close();
            writer.close();

        } catch (IOException i) {
            System.out.println("File not found!\n");
        }
    }
}
