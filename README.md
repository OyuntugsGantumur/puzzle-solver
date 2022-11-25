## Sliding Puzzle Solver

The goal of the current project is to solve 3-, 8-, and 15-tiled slider puzzles using four different heuristics and to compare the efficiency among them - the number of misplaced tiles, the sum of the Manhattan distance for each tile, the number of swaps used in the Maxsort algorithm, and the sum of the Euclidean distance for each tile. When analyzing the performance of each heuristic, the number of nodes/neighbors visited when solving the puzzle was used as a performance measure.

The goal of the slider puzzle is to rearrange the tiles so that the numbers are in order from left to right and top to bottom. The tiles can be slid either horizontally or vertically into the blank square.
 
![image](https://user-images.githubusercontent.com/88627733/204032303-50f4413e-7c83-40c1-ac93-cf7f19bdd25d.png)


## Input and Output files
The input file is named "config.txt" and contains the values of each puzzle in one row with each value separated by a space. In place of the blank space, the value 0 is entered. It's important that there are only one puzzle values for each row.

The output file is called "report.txt". After the program is run, it prints the puzzle and then how many nodes were created in the process of solving that puzzle with each heuristic.


## Compilation and Execution

A makefile is created for this project, so the user can run make or make clean (if the user run the program before) in the terminal to compile and run java Main to execute the program. 

Further analysis of the heuristics can be found [here](https://docs.google.com/document/d/1zqi0Htz-hE5J7ghHY4mfBFiuOof9rjkAL824vxjnW74/edit?usp=sharing).
