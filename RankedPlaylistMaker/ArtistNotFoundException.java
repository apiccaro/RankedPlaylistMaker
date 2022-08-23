package ranker;
/*
 * this class creates an artist not found exception that is thrown when the 
 * artist isnt in the data base or if a captcha is detected
 */

public class ArtistNotFoundException extends Exception{
	    public ArtistNotFoundException(String errorMessage) {
	        super(errorMessage);
	    }
	}

