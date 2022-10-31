import java.util.ArrayList;

public class Node implements Comparable<Node> {
    int level;
    Integer fval;
    int[][] data;

    public Node(int[][] arr, int level, Integer fval) {
        this.data = arr;
        this.level = level;
        this.fval = fval;
    } 

    //Go through all of its neighbors
    public ArrayList<Node> generate() {
        ArrayList<Node> neighbors = new ArrayList<Node>();

        int[] blank_loc = find(0, data);
        int xBlank = blank_loc[0];
        int yBlank = blank_loc[1];

        int[][] pos_moves = {{xBlank, yBlank-1}, {xBlank, yBlank+1}, {xBlank-1, yBlank}, {xBlank+1, yBlank}};

        for(int i = 0; i < 4; i++) {
            int[][] temp = swap(data, xBlank, yBlank, pos_moves[i][0], pos_moves[i][1]);

            if(temp != null) {
                Node neighbor = new Node(temp, level+1, 0);
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

     //To find the position of the number or the blank space.
    public int[] find (int toFind, int[][] arr) {
        int[] result = new int[2];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] == toFind) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }
        
    //Swaps the values in the given 2 locations
    public int[][] swap(int[][] arr, int x1, int y1, int x2, int y2) {

        if(x2 >= 0 && x2 < arr.length && y2 >= 0 && y2 < arr.length) {
            int[][] copyArr = new int[arr.length][arr.length];
            copyArray(arr, copyArr);

            int temp = copyArr[x2][y2];
            copyArr[x2][y2] = copyArr[x1][y1];
            // System.err.println("HERE");
            copyArr[x1][y1] = temp;

            return copyArr;
        }

        return null;
    }

    //Copy the values of the array to another one
    public void copyArray(int[][] arr, int[][] temp) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                temp[i][j] = arr[i][j];
            }
        }
    }

    @Override
    public int compareTo(Node n) {
        return fval.compareTo(n.fval);
    }
}