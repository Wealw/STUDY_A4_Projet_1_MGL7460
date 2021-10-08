package ca.uqam.bookmanager.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class User
{
    
    private int           id;
    private String        username;
    private String        passwordHash;
    private UserRole      role;
    private MessageDigest messageDigest;
    
    public User(String username, String passwordHash, UserRole role)
    {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.print("Missing algorithm for password hashing, please refer to the developer.");
            System.exit(1);
        }
    }
    
    public User(int id, String username, String passwordHash, UserRole role){
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.print("Missing algorithm for password hashing, please refer to the developer.");
            System.exit(1);
        }
        this.setId(id);
    }
    
    private String hashString(String string)
    {
        final byte[]  bytes = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb    = new StringBuilder();
        for (byte aByte : bytes)
        {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                             .substring(1));
        }
        return sb.toString();
    }
    
    public boolean verifyPassword(String password)
    {
        return Objects.equals(passwordHash, hashString(password));
    }
    
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPasswordHash()
    {
        return passwordHash;
    }
    public void setPassword(String password)
    {
        this.passwordHash = hashString(password);
    }
    public UserRole getRole()
    {
        return role;
    }
    public void setRole(UserRole role)
    {
        this.role = role;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String ToString(){
        return String.format("\u001B[34mId:\u001B[0m %d \u001B[34mUsername:\u001B[0m %s \u001B[34mRole:\u001B[0m %s", id, username,role.toString());
    }
    
}
