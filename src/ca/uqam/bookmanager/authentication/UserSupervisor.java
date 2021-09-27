package ca.uqam.bookmanager.authentication;

import java.util.ArrayList;
import java.util.List;

public class UserSupervisor
{
    public void DisplayAuthenticationMenu(){
    
    }
    
    public List<String> AskCredential(){
        return new ArrayList<String>(2);
    }
    
    public void DisplayInvalidCredentialMessage(){
    
    }
    
    public void DisplayValidCredentialMessage(String username){
    
    }
    
    //TODO : Define the displaying function
    
}
