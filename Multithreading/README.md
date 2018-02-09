# Multithreading
###### 1. Create a file and store in it for every N number of lines from given file
Entry class name is:

```
SplitFileByNLines.java
```
To test it, either you can use default constructor or overloaded constructor on which you call split() method like below

```
new SplitFileByNLines().split();
```
OR

```
new SplitFileByNLines(filePath, splitNumber, outputFilePath).split();
```
###### 2. Read a given file, extract strings from it as per the delimiter, make a Wiki call for each word, extract description from the output and store it in a file.
To test it use below line of code, the boolean which has passed to the constructor is whether or not to use fork join framework (true value is to used for fork join):

```
new WikiCall(boolean).fetchStringsMakeWikiCallAndWrite();
```
It has multiple overloaded constructors to specify source directory to read a file and target directory to store files and other parameters like delimiter, wikiURL, position of the string after splitting line by delimiter etc. One of the constructors is shown below

```
WikiCall(String filePath, String delimiter, Integer position, String outputFilePath, boolean useForkJoin, String wikiURLString) 
```


###### 3. Read each file available in a directory, split text by one or more spaces and find number of occurrences of each word and store it in a file.
To test it:

```
WordCount wordCount = new WordCount();
wordCount.processWordCount();
```
It has overloaded constructor to specify source directory to read all files available and target directory to store word and corresponding occurrence in a file. The constructor is shown below

```
WordCount(String filesPath, String outputFilePath);
```
