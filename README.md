# RankedPlaylistMaker
A program that once the user enters an artist, the artist's songs are web-scraped from www.songsfacts.com with artist songs. 
These songs are scraped into a an artist song file. They are read into an algorithm to be ranked by the user. 
The user is prompted to choose between 2 different songs by the chosen artist. 
The user also has the option to select "I don't know" or "I like them the same".
The user interface includes the percent sorted as the user selects between songs.
This process continues until the user has a ranked playlist of 10 songs by the artist.

In order to run this program, you must have a Java supporting IDE (i.e. Eclipse or IntelliJ Idea). Once the code is downloaded, a JSoup pathway must be formed for the webscraping class of the program to function. For eclipse this entails right clicking on the project and selecting “Java Build Path” from the sidebar and then “Libraries” from the top bar. There should be an option to choose “Add External JARs…”, select that and then import the Jsoup JAR file. 

Contributors:

Annika Piccaro: 
Webscraped the songs into readable files from www.songsfacts.com and 
desgined a simple user interface for the user to interact with.

Lucy Flannagan: 
Read the file of songs by the given artist in
and created the sorting algorithm for the songs.
