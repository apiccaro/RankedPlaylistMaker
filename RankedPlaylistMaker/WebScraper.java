package ranker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * This class scrapes a song list website based on an artist
 * @author annikapiccaro
 *
 */
public class WebScraper {
	private int indexStart=-1;
	private int indexEnd=-1;
	
	/**
	 * Reads the website with the
	 * @param artist
	 * 
	 * @throws ArtistNotFoundException
	 * if artist doesnt have a website or captcha is come across
	 */
	public void readWebsite(String artist) throws ArtistNotFoundException {
		

		Document page = null;
		try {
			page = Jsoup.connect("https://www.songfacts.com/songs/" + artist).get();
			String title = page.title();
			//if captcha is encoutnered throw an artistnotfoundexception
			if(title.equals("ShieldSquare Captcha")){
				throw new ArtistNotFoundException(artist);
			}
			//print title for practice purposes
			//System.out.println("Title: " + title);
			//select all "a" class elements- aka the data that holds the songs
			Elements songs = page.select("a");
			//prints for confirmation
			/*for(Element song:songs) {
				System.out.println(song.text());
			}*/
			//if page is not found throw not found exception
			if (title.equalsIgnoreCase("error 404 (Not Found)")) {
				throw new ArtistNotFoundException(artist);
			}else {
				//else parse through data to find start and end of songs
				getIndices(songs);
				//write the file
				writeSongFile(songs,artist);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getIndices(Elements songs) {
		// most songs start after string "artistfacts" so find that and assign as 
		//the start of song data
		for (Element song : songs) {
			if(song.text().equals("artistfacts")) {
				indexStart=songs.indexOf(song)+1;
			}
		}
		//some songs start after string categories as there is not artistfacts
		//so if there is not artistfacts-index of start is still -1
		if(indexStart==-1) {
			//find index of categories and assign the start one after that
			//because there is an empty line after categories
			for(Element song:songs) {
			if(song.text().equals("categories")) {
				indexStart=songs.indexOf(song)+1;
			}
			}
		}

		
			
			
		}
		
	/**
	 * Writes a file of 
	 * @param songs
	 * given an
	 * @param artist
	 */
	public void writeSongFile(Elements songs, String artist) {
		try {
			FileWriter myWriter = new FileWriter(artist + ".txt");
			//from the start of songs to when there is an empty line-aka song data is over
			//write to file
			for(int i=indexStart;i<songs.size();i++) {
				System.out.println(songs.get(i).text());
				if(songs.get(i).text().isEmpty()) {
					break;
				}else {
				myWriter.write(songs.get(i).text()+"\n");
				}
			}
			myWriter.close();
			//confirmation it read correctly
			//System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
}
