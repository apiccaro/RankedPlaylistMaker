package ranker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * This class createsand listens the GUI that user interacts with to determine
 * a ranked song
 */
public class UserGui extends JFrame implements ActionListener {

	private JTextField artistName;
	private JButton okayB;
	private JButton songAB=new JButton();
	
	
	private JButton songBB=new JButton();
	private boolean swap;
	private String playlist="";
	JPanel panel = new JPanel();

	private JTextArea intro;
	private JTextArea outro=new JTextArea();
	private Battles battle = new Battles();
	private JButton end = new JButton();
	private Font outroFont = new Font("SansSerif", Font.PLAIN, 18);
	private JButton idk= new JButton();
	private JButton same= new JButton();
	private JTextArea percentT = new JTextArea();
	private int percent=0;
	private double percentSorted=0;

	public UserGui() {
		super("Battle Of the Songs");
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel.setBackground(new Color(	51-153-255));
		this.add(panel);
		//adds blurb how to interact with gui
		Font introFont = new Font("SansSerif", Font.PLAIN, 17);
		intro = new JTextArea(
				"Welcome to the song sorter!\n\nFor each song battle, pick your favorite song between the given two songs.\nKeep doing this to get a ranked song playlist! \n\nFirst enter a music artist.",
				19, 19);
		intro.setFont(introFont);
		panel.add(intro);
		//textfield that takes music artist
		artistName = new JTextField(15);
		artistName.setFont(new Font("SansSerif", Font.PLAIN, 16));
		artistName.setPreferredSize(new Dimension(200, 40));
		panel.add(artistName);
		//buttons that begins the battle of songs
		okayB = new JButton("Okay");
		okayB.setFont(new Font("SansSerif", Font.PLAIN, 15));
		okayB.setPreferredSize(new Dimension(70, 40));
		okayB.addActionListener(this);
		panel.add(okayB);

	}

	public void actionPerformed(ActionEvent e) {
		String artist1="";
		if (e.getSource() == okayB) {
			//gets artist's name inputed and changes into website format
			artist1=artistName.getText();
			String artist = artistName.getText().trim().toLowerCase().replaceAll(" ", "-");
			WebScraper scrapArtist = new WebScraper();
			try {
				//scrapes the website given the artist and reads the file into the list
				scrapArtist.readWebsite(artist);
				battle.fileRead(artist);
				//if the scraping was succesful the panel is changed to show the songs
				panel.remove(okayB);
				panel.remove(artistName);
				panel.remove(intro);
				panel.revalidate();
				panel.repaint();
				battle.getSongsToCompare();
				if (!battle.done) {
					//gets songs name
					percentSorted = battle.getPercentSorted(battle.battles-1); 
			        percent = (int)percentSorted; 
			        percentT.setText("Percent Sorted: "+percent+"%");
			        battle.battles++;
					songAB.setText(battle.songA.getName());
					songBB.setText(battle.songB.getName());
					idk.setText("I don't know.");
					same.setText("I like them the same.");
					songAB.setPreferredSize(new Dimension(250, 60));
					songBB.setPreferredSize(new Dimension(250, 60));
					idk.setPreferredSize(new Dimension(200, 60));
					same.setPreferredSize(new Dimension(200, 60));
					
					panel.add(songAB);
					songAB.addActionListener(this);
					panel.add(songBB);
					songBB.addActionListener(this);
					panel.add(idk);
					idk.addActionListener(this);
					panel.add(same);
					same.addActionListener(this);
					panel.add(percentT);
					panel.revalidate();
					panel.repaint();
				} else {
					//if battle is over
					panel.remove(songAB);
					panel.remove(songBB);
					panel.remove(percentT);
					outro.setText("OVER");
					this.revalidate();
					this.repaint();
				}

			} catch (ArtistNotFoundException ex) {//if given artist isnt found
				JOptionPane.showMessageDialog(null,
						"Sorry this artist is not in our database, please try someone else");
				artistName.setText("");
			}

		}
		if (e.getSource() == songAB) {
			//updates preferences
			battle.updateList("A");
			swap = battle.compareSongs(battle.songA, battle.songB);
			if (swap) {
				battle.swap(battle.getIndex1(), battle.getIndex2());
			}
			battle.getSongsToCompare();
			//if battle isnt done reupdate
			if (!battle.done) {
				percentSorted = battle.getPercentSorted(battle.battles-1); 
		        percent = (int)percentSorted; 
		        percentT.setText("Percent Sorted: "+percent+"%");
		        battle.battles++;
				songAB.setText(battle.songA.getName());
				songBB.setText(battle.songB.getName());
				panel.repaint();
				panel.revalidate();
				
			} else {
				//make playlist of Song objects into a string and present on screen
				for (int i = 0; i < battle.songs.size(); i++) {
					playlist += battle.songs.get(i).getName()+"\n";
				}
				//remove all buttons and set outro screen
				panel.remove(songAB);
				panel.remove(songBB);
				panel.remove(idk);
				panel.remove(same);
				panel.remove(percentT);
				this.repaint();
				outro.setText("Here is your sorted "+artist1+" playlist: \n"+playlist);
				
				panel.add(outro);
				outro.setFont(outroFont);
				end.setText("Exit");
				end.addActionListener(this);
				panel.add(end);
				panel.revalidate();
				panel.repaint();
			}

		}
		if (e.getSource() == songBB) {
			//update preferences
			
			battle.updateList("B");
			swap = battle.compareSongs(battle.songA, battle.songB);
			if (swap) {
				battle.swap(battle.getIndex1(), battle.getIndex2());
			}
			battle.getSongsToCompare();
			//if battle isnt done reupdate
			if (!battle.done) {
				percentSorted = battle.getPercentSorted(battle.battles-1); 
		        percent = (int)percentSorted; 
		        percentT.setText("Percent Sorted: "+percent+"%");
		        battle.battles++;
				songAB.setText(battle.songA.getName());
				songBB.setText(battle.songB.getName());
				
			} else {
				//make string playlist out of Song objects and present on screen
				for (int i = 0; i < battle.songs.size(); i++) {
					playlist += battle.songs.get(i).getName()+"\n";
				}
				//remove all buttons and set outro screen
				panel.remove(songAB);
				panel.remove(songBB);
				panel.remove(idk);
				panel.remove(same);
				panel.remove(percentT);
				this.repaint();
				outro.setText("Here is your sorted "+artist1+" playlist: \n"+playlist);
				panel.add(outro);
				outro.setFont(outroFont);
				end.setText("Exit");
				end.addActionListener(this);
				panel.add(end);
				panel.revalidate();
				panel.repaint();
			}
		}if(e.getSource()==idk) {
			//updates preferences
			battle.updateList("C");
			//if user clicks same or idk 6 times tell the user the list will be inaccurate
			if(battle.unknown==6) {
				JOptionPane.showMessageDialog(null, "If you continue clicking this option your playlist will not be very accurate.");
			}
			//if user clicks same or idk 8 times remind the user the list will be inaccurate
			if(battle.unknown==8) {
				JOptionPane.showMessageDialog(null, "Remember! If you continue clicking this option your playlist will not be very accurate.");
			}
			battle.getSongsToCompare();
			//if battles isnt done reupdate
			if (!battle.done) {
				percentSorted = battle.getPercentSorted(battle.battles-1); 
		        percent = (int)percentSorted; 
		        percentT.setText("Percent Sorted: "+percent+"%");
		        battle.battles++;
				songAB.setText(battle.songA.getName());
				songBB.setText(battle.songB.getName());
				panel.repaint();
				panel.revalidate();
				
			} else {
				//make string playlisy out of Song objects and present on screen
				for (int i = 0; i < battle.songs.size(); i++) {
					playlist += battle.songs.get(i).getName()+"\n";
				}
				//remove all buttons and set outro screen
				panel.remove(songAB);
				panel.remove(songBB);
				panel.remove(idk);
				panel.remove(same);
				panel.remove(percentT);
				this.repaint();
				outro.setText("Here is your sorted "+artist1+" playlist: \n"+playlist);
				panel.add(outro);
				outro.setFont(outroFont);
				end.setText("Exit");
				end.addActionListener(this);
				panel.add(end);
				panel.revalidate();
				panel.repaint();
			}

		}if(e.getSource()==same) {
			//update preferences
			battle.updateList("D");
			//if user clicks same or idk 6 times tell the user the list will be inaccurate
			if(battle.unknown==6) {
				JOptionPane.showMessageDialog(null, "If you continue clicking this option your playlist will not be very accurate.");
			}
			//if user clicks same or idk 8 times remind the user the list will be inaccurate
			if(battle.unknown==8) {
				JOptionPane.showMessageDialog(null, "Remember! If you continue clicking this option your playlist will not be very accurate.");
			}
			battle.getSongsToCompare();
			//if battles isnt done reupdate
			if (!battle.done) {
				percentSorted = battle.getPercentSorted(battle.battles-1); 
		        percent = (int)percentSorted; 
		        percentT.setText("Percent Sorted: "+percent+"%");
		        battle.battles++;
				songAB.setText(battle.songA.getName());
				songBB.setText(battle.songB.getName());
				panel.repaint();
				panel.revalidate();
				
			} else {
				//create a string playlist out of Song objects to display on screen.
				for (int i = 0; i < battle.songs.size(); i++) {
					playlist += battle.songs.get(i).getName()+"\n";
				}
				//remove all buttons and set outro screen
				panel.remove(songAB);
				panel.remove(songBB);
				panel.remove(idk);
				panel.remove(same);
				panel.remove(percentT);
				this.repaint();
				outro.setText("Here is your sorted "+artist1+" playlist: \n"+playlist);
				panel.add(outro);
				outro.setFont(outroFont);
				end.setText("Exit");
				end.addActionListener(this);
				panel.add(end);
				panel.revalidate();
				panel.repaint();
			}

		}
		if(e.getSource()==end) {
			System.exit(0);
		}
	}
	public static void main(String[] args) throws Exception {
		
		UserGui sg = new UserGui();
		sg.setVisible(true);

	}

}
