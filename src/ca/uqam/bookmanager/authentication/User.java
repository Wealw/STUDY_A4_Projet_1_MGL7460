package ca.uqam.bookmanager.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class User
{
    
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
    
    private String hashString(String string)
    {
        final byte[] bytes = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
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
    
    private String getUsername()
    {
        return username;
    }
    private void setUsername(String username)
    {
        this.username = username;
    }
    private String getPasswordHash()
    {
        return passwordHash;
    }
    private void setPasswordHash(String password)
    {
        this.passwordHash = hashString(password);
    }
    private UserRole getRole()
    {
        return role;
    }
    private void setRole(UserRole role)
    {
        this.role = role;
    }
    
}
