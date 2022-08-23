package ranker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class sorts the playlist based on user gui anwers
 */
public class Battles {
	public ArrayList<Song> songs = new ArrayList<Song>();
	public int battles;
	public Song songA;
	public Song songB;
	public int index1;
	public int index2;
	public boolean done;
	public int numLines;
	public ArrayList<SongPair> songPairs;
	public int unknown;

	public Battles() {

		battles = 1;
		songA = null;
		songB = null;
		done = false;
		songPairs = new ArrayList<SongPair>();
		unknown = 0;
	}
	//reads the file created by webscraped data that grabs songs throughout the file
	//rather than just alphabetically sorted top 10
	public void fileRead(String artist) {
		try {
			File inputFile1 = new File(artist + ".txt");

			Scanner in1 = new Scanner(inputFile1);
			numLines = 0;
			//count the number of lines in the file
			while (in1.hasNextLine()) {
				String data = in1.nextLine();
				numLines++;
			}
			//new file to read through 
			File inputFile2 = new File(artist + ".txt");
			Scanner in2 = new Scanner(inputFile2);

			int iterations = 0;
			String lines = numLines + "";
			String str = "";
			
			if (numLines < 100) {
				str = lines.substring(0, 1);//use a mod of 1's place
			} else {
				str = lines.substring(0, 2);//use a mod os 10's place
			}
			int multiple = Integer.parseInt(str);
			while (in2.hasNextLine()) {
				String data = in2.nextLine();
				if (numLines > 10) //if can actually use mod on number of lines
				{
					//add song every iteration mod
					if (iterations != 0 && iterations % multiple == 0) {
						Song song = new Song(data);
						songs.add(song);
					}
					iterations++;
				} //if mod cant be used just add the number of songs under 10
				else {
					Song song = new Song(data);
					songs.add(song);
				}
			}
			in1.close();
			in2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//swaps the two songs in the list based on which user likes more
	public void swap(int index1, int index2) {
		Song temp = songs.get(index1);
		songs.set(index1, songs.get(index2));
		songs.set(index2, temp);
		// sample[index1]=sample[index2];
		// sample[index2]=temp;
	}
	/**put the appropriate song names on the button in the GUI
	 * 
	 */
	public void getSongsToCompare() {
		boolean check = false;
		//compare are any two songs that have the same number of points
		outerloop: for (int i = 0; i < songs.size(); i++) {
			Song first = songs.get(i);
			int aPoints = first.getPoints();
			for (int j = 0; j < songs.size(); j++) {
				String name1 = songs.get(i).getName();
				String name2 = songs.get(j).getName();
				SongPair checking = new SongPair(name1, name2);
				boolean keep = true;

				if (songPairs.size() != 0) {
					for (int k = 0; k < songPairs.size(); k++) {
						SongPair thisPair = songPairs.get(k);
						boolean areEqual = checking.equals(thisPair);
						// ensures the user isn’t presented with two songs that
						//they already declared that they don’t have a preference between the two.
						if (areEqual == true) {
							keep = false;
						}
					}
				}

				if (i != j && keep == true) {
					Song second = songs.get(j);
					int bPoints = second.getPoints();

					if (aPoints == bPoints) {
						check = true;
						songA = first;
						songB = second;
						if (i < j) {
							index1 = i;
							index2 = j;
						} else {
							index1 = j;
							index1 = i;
						}
						break outerloop;
					}
				}
			}
		}
		//determines when the list is fully sorted,
		if (check == false) {
			done = true;
		}
	}
	//Returns true if the song in the first index 
	//should be swapped with the song in the second index t
	public boolean compareSongs(Song a, Song b) {
		// returns true if songs should swap and false if not
		int pointsA = a.getPoints();
		int pointsB = b.getPoints();
		if (pointsA < pointsB) {
			return true;
		}
		return false;
	}

	public boolean isOver() {
		return done;
	}
	//updates the songs in the ArrayList points according 
	//to whichever button the user pressed
	public void updateList(String keys) {
		if (keys.equals("A")) {
			songs.get(index1).addPoints();
		} else if (keys.equals("B")) {
			songs.get(index2).addPoints();
		} 
		// adds songs to the songPairs ArrayList for if the user 
		// selected that they don’t have a preference between the two songs, 
		else if (keys.equals("C")) {
			String name1 = songs.get(index1).getName();
			String name2 = songs.get(index2).getName();
			SongPair pair1 = new SongPair(name1, name2);
			SongPair pair2 = new SongPair(name2, name1);
			songPairs.add(pair1);
			songPairs.add(pair2);
			//keeps track of how many times the user selected that 
			//they don’t have a preference 
			unknown++;
		} else if (keys.equals("D")) {
			String name1 = songs.get(index1).getName();
			String name2 = songs.get(index2).getName();
			SongPair pair1 = new SongPair(name1, name2);
			SongPair pair2 = new SongPair(name2, name1);
			songPairs.add(pair1);
			songPairs.add(pair2);
			//keeps track of how many times the user selected that 
			//they don’t have a preference 
			unknown++;
			songs.get(index1).addPoints();
			songs.get(index2).addPoints();
		}
	}
	//scanner tester method
/**	public void scan() // this is where you should get button clicks instead of use the scanner (once
						// GUI is implemented)
	{
		System.out.println();

		// this.test();

		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to the Doja Cat song sorter!");
		System.out.println();
		System.out
				.println("Type 'A' if you like the first song better and type 'B' if you like the second song better.");
		while (done == false) {
			System.out.print("Battle #" + battles + ": ");

			this.getSongsToCompare();
			System.out.println(songA.getName() + " or " + songB.getName() + "?");

			String keys = scan.nextLine();
			updateList(keys);

			boolean swap = compareSongs(songs.get(index1), songs.get(index2));
			if (swap == true) {
				swap(index1, index2);
			}

			this.test();
			System.out.println(done);
			System.out.println();

			battles++;
		}

		System.out.println("Here is a ranked list of your favorite songs!");
		for (int i = 0; i < songs.size(); i++) {
			System.out.println(songs.get(i).getName());
		}
	}**/

	public void test() {
		for (int i = 0; i < songs.size(); i++) {
			System.out.println(songs.get(i).getName() + " Points: " + songs.get(i).getPoints());
		}
	}

	public int getIndex1() {
		// TODO Auto-generated method stub
		return index1;
	}

	public int getIndex2() {
		// TODO Auto-generated method stub
		return index2;
	}
	 public int getNumBattles(int n) //pass songs size into this parameter
	    {
	        int result = 1; 
	        return getNumBattlesRecursively(n, 2, result); 
	    }
	    
	    public int getNumBattlesRecursively(int n, int start, int result)
	    {
	        result += (start-1); 
	        if(start==n)
	        {
	            return result; 
	        }
	        
	        return getNumBattlesRecursively(n, start+1, result); 
	    }
	    
	    public double getPercentSorted(int battles)
	    {
	        int totalBattles = getNumBattles(songs.size()); 
	        double dTotal = 0.0; 
	        if(unknown==0)
	        {
	             dTotal = totalBattles*1.0; 
	        }
	        else
	        {
	        	 int newBattles = totalBattles - unknown;
	             dTotal = newBattles*1.0; 
	           
	            
	        }
	        double dThis = battles*1.0; 
	        
	        double divisor = dTotal/dThis; 
	        double answer = 100.0/divisor; 
	        System.out.println(Math. round(answer*100.0)/100.0);
	        return Math. round(answer*100.0)/100.0; 
	    }

}
