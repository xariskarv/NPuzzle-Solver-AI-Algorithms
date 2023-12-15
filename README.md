# NPuzzle-Solver-AI-Algorithms

## Purpose
This project was created in order to solve any N-Dimensional NPuzzle game.

## Implemented Algorithms
* [Breadth First Search](https://en.wikipedia.org/wiki/Breadth-first_search)
* [Depth-First-Search](https://en.wikipedia.org/wiki/Depth-first_search)
* [Best-First-Search](https://en.wikipedia.org/wiki/Best-first_search)
* [A* Search](https://en.wikipedia.org/wiki/A*_search_algorithm)

## Generator
The generator.c is a program written by @Ioannis Refanidis and its purpose is to generate random NPuzzle problems and save them to .txt files.

## How to use the generator
compile:

      gcc generator.c -o generator
Install GCC: https://dev.to/gamegods3/how-to-install-gcc-in-windows-10-the-easier-way-422j

If the executable file is called generator.exe run:

      generator.exe <filname> <id1> <id2> 
 where
 
      <filaneme>: the prefix of the file
      <id1> <id2>: 2 numbers for the suffix of the file
e.g.
       
       generator.exe puzzles 11 20
Will create 10 files with names: puzzles11.txt....puzzles20.txt

## Example of a puzzles file
       4  5  0 
       1  3  2
       6  8  7

## How to run Java Main
       javac Main.java
       
then run:

       javac Main <method> <input.txt> <solution.txt>

where:

        <method>: The algorithm used to solve the puzzle
        <input.txt>: The file with the puzzle
        <output.txt>: The file for the solution

e.g.

        java Main astar puzzles3.txt solution
        
## Example of an output file

        Search algorithm: A* Search 
        Number of steps to achieve solution: 200
        Execution time: 0.027 seconds 

        1  2  3
        4  5  6
        7  8  0

        Height: 17
        
## Results

The execution time shows the time that the algorithm needed to find the solution.\
The number of steps show how many nodes did the algorithm visit in order to find the solution. \
The height shows the solution node's height.

        
