package ranker;


/**
 * Write a description of class Song here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Song
{
    private String name; 
    private int points;
    
    public Song(String name)
    {
        this.name = name; 
        points = 0; 
    }
    
    public String getName()
    {
        return name; 
    }
    
    public void addPoints()
    {
        points++; 
    }
    
    public int getPoints()
    {
        return points; 
    }
}
