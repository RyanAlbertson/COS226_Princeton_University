### OVERVIEW

Implements an autocompletion feature. A user can specifiy a string and the Autocomplete
program will search a provided text file for strings that begin with the specified sub-string.
The frequency of each string is factored into the results.


### EXECUTION

#### Autocomplete.java
Reads a given text file which stores strings and their respective frequencies. Takes
a given number of matches and searches the text file for that amount of strings that
begin with a given sub-string which was typed into StdIn. For each inputted sub-string,
the specified number of strings containing it, along with those strings, are printed.
> java-algs4 Autocomplete _\<filename\> \<# matches\>_


### DEPENDENCIES

- java.util.Arrays
- java.util.Comparator
- [Princeton's StdIn](https://introcs.cs.princeton.edu/java/stdlib/StdIn.java.html)
- [Princeton's StdOut](https://introcs.cs.princeton.edu/java/stdlib/StdOut.java.html)
- [Princeton's algs4.jar (_clicking will download the .jar_ )](https://algs4.cs.princeton.edu/code/algs4.jar)
