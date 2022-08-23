package ranker;


public class SongPair
{
    private String nameA;
    private String nameB; 
    
    public SongPair(String nameA, String nameB)
    {
        this.nameA = nameA; 
        this.nameB = nameB; 
    }
    
    public String getFirstSongName()
    {
        return nameA; 
    }
    
    public String getSecondSongName()
    {
        return nameB; 
    }
    
    public boolean equals(SongPair pair)
    {
        if(pair instanceof SongPair)
        {
            String firstName = pair.getFirstSongName(); 
            String secondName = pair.getSecondSongName(); 
            
            String thisFirstName = this.getFirstSongName();
            String thisSecondName = this.getSecondSongName();
            
            if(firstName.equals(thisFirstName) && secondName.equals(thisSecondName))
            {
                return true; 
            }
        }
        return false; 
    }
}
