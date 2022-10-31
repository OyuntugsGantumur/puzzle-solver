import java.util.Collections;
import java.lang.Math;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Puzzle {
    int size;
    ArrayList<Node> open;
    ArrayList<Node> closed;

    public Puzzle(int size) {
        this.size = size;
        open = new ArrayList<Node>();
        closed = new ArrayList<Node>();
    }

    //Counts the number of misplaced tiles for each tile and
    //Returns the sum
    public int h_score(Node curr, Node goal) {
        int cnt = 0;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(curr.data[i][j] != goal.data[i][j] && goal.data[i][j] != 0) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    //Calculates the Manhattan distance for each tile and returns the sum
    public int find_manhattanD(Node curr, Node goal) {
        int[] curr_loc;
        int[] goal_loc;
        int manhattanD = 0;

        for(int i = 1; i < size*size; i++) {  
            curr_loc = curr.find(i, curr.data);
            goal_loc = goal.find(i, goal.data);

            int temp = Math.abs(curr_loc[0] - goal_loc[0]) + Math.abs(curr_loc[1] - goal_loc[1]);

            manhattanD += temp;
        }
        return manhattanD;
    }

    //Converts the 2D array into 1D array and sorts it
    //Returns the number of swaps done
    public int maxSort(Node curr) {
        int[] dataArr = new int[size*size];
        int k = 0;
        int numSwaps = 0;

        for(int i = 0; i < curr.data.length; i++) {
            for(int j = 0; j < curr.data.length; j++) {
                dataArr[k] = curr.data[i][j];
                k++;
            }
        }

        for(int i = dataArr.length-1; i > 0; i--) {
            int max = 0;
            for(int j = 1; j <= i; j++) {
                if(dataArr[max] < dataArr[j]) {
                    max = j;
                }
            }

            if(dataArr[max] != dataArr[i]) {
                int temp = dataArr[max];
                dataArr[max] = dataArr[i];
                dataArr[i] = temp;
                numSwaps++;
            }
        }

        return numSwaps;
    }

    //Calculates the Euclidean distance for each tile and returns the sum
    public int find_EuclideanD(Node curr, Node goal) {
        int dist = 0;
        int[] curr_loc;
        int[] goal_loc;

        for(int i = 0; i < size*size; i++) {
            curr_loc = curr.find(i, curr.data);
            goal_loc = goal.find(i, goal.data);

            double temp = Math.sqrt(Math.pow((curr_loc[0] - goal_loc[0]), 2) + 
                          Math.pow((curr_loc[1] - goal_loc[1]), 2));

            dist += temp;
        }
        return dist;
    }

    //Prints a puzzle
    public String print(Node curr) {
        String str = "";
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                str += curr.data[i][j] + " ";
            }

            str += "\n";
        }
        return str;
    }

    //Solves the puzzle with the number of misplaced tiles as the basis
    public String solveByMisplaced(Node start, Node goal, FileWriter writer) {
        int childrenExpanded = 0;
        String str = "";
        open.add(start);
        start.fval = h_score(start, goal);

        while(true) {
            Node curr = open.get(0);

            if(h_score(curr, goal) == 0){
                str += "Solved by Misplaced Tiles in " + childrenExpanded + " nodes\n";
                break;
            } 

            for(Node child: curr.generate()) {
                child.fval = h_score(child, goal) + child.level;
                open.add(child);
                childrenExpanded++;
            }

            closed.add(curr);
            open.remove(0);
            Collections.sort(open);
        }

        open.clear();
        closed.clear();
        return str;
    }

    //Solves the puzzle using the sum of Manhattan distances as the basis
    public String solveByManhattanD(Node start, Node goal, FileWriter writer) {
        int childrenExpanded = 0;
        String str = "";

        open.add(start);
        start.fval = find_manhattanD(start, goal);

        while(true) {
            Node curr = open.get(0);

            if(find_manhattanD(curr, goal) == 0){
                str += "Solved by Manhattan Distance in " + childrenExpanded + " nodes.\n";
                break;
            }

            for(Node child: curr.generate()) {
                child.fval = find_manhattanD(child, goal) + child.level;
                open.add(child);
                childrenExpanded++;
            }

            closed.add(curr);
            open.remove(0);
            Collections.sort(open);
        }

        open.clear();
        closed.clear();
        return str;
    }

    //Solves the puzzle using the number of swaps in MaxSort as the basis
    public String solveByMaxSort(Node start, Node goal, FileWriter writer) {
        int cnt = 0;
        String str = "";

        open.add(start);
        start.fval = maxSort(start);
        print(start);

        while(true) {
            Node curr = open.get(0);

            if(maxSort(curr) == 0){
                str += "Solved by Maxsort in " + cnt + " nodes.\n";
                break;
            }

            for(Node child: curr.generate()) {
                child.fval = maxSort(child) + child.level;
                open.add(child);
                cnt++;
            }

            closed.add(curr);
            open.remove(0);
            Collections.sort(open);
        }

        open.clear();
        closed.clear();
        return str;
    }

    //Solves the puzzle using the sum of Euclidean distances as the basis
    public String solveByEuclideanD(Node start, Node goal, FileWriter writer) {
        int cnt = 0;
        String str = "";

        open.add(start);
        start.fval = find_EuclideanD(start, goal);

        while(true) {
            Node curr = open.get(0);

            if(find_EuclideanD(curr, goal) == 0){
                str += "Solved by Euclidean Distance in " + cnt + " nodes.\n";
                break;
            }

            for(Node child: curr.generate()) {
                child.fval = find_EuclideanD(child, goal) + child.level;
                open.add(child);
                cnt++;
            }

            closed.add(curr);
            open.remove(0);
            Collections.sort(open);
        }
        open.clear();
        closed.clear();
        return str;
    }

    //Calls the corresponding solving method depending on the methodType
    public void solve(Node start, Node goal, int methodType, FileWriter writer) {
        String str = "";

        try{
            if(methodType == 1) {
                str = solveByMisplaced(start, goal, writer);
            } else if (methodType == 2) {
                str = solveByManhattanD(start, goal, writer);
            } else if (methodType == 3) {
                str = solveByMaxSort(start, goal, writer);
            } else if (methodType == 4) {
                str = solveByEuclideanD(start, goal, writer);
            }

            System.out.println(str);
            writer.write(str);
        } catch (IOException e) {
            System.out.println("Error occured in writing to the file!");
        }
    }
}
