package ca.uqam.bookmanager.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * User class
 */
public class User {
    
    /**
     * User id.
     */
    private int           id;
    /**
     * User username.
     */
    private String        username;
    /**
     * User password hash.
     */
    private String        passwordHash;
    /**
     * User role.
     */
    private UserRole      role;
    /**
     * Object instance used for password hashing
     */
    private MessageDigest messageDigest;
    
    /**
     * Create a user object
     *
     * @param username User username
     * @param passwordHash User password hash
     * @param role user role
     */
    public User(String username, String passwordHash, UserRole role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.print("Missing algorithm for password hashing, please refer to the developer.");
            System.exit(1);
        }
    }
    
    /**
     * Create a user object
     *
     * @param id User id
     * @param username User username
     * @param passwordHash User password hash
     * @param role user role
     */
    public User(int id, String username, String passwordHash, UserRole role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.print("Missing algorithm for password hashing, please refer to the developer.");
            System.exit(1);
        }
        this.setId(id);
    }
    
    /**
     * Verify if user password furnished correpond to stored hashed password.
     *
     * @param password Clear password
     * @return Password is true or false
     */
    public boolean verifyPassword(String password) {
        return Objects.equals(passwordHash, hashString(password));
    }
    
    private String hashString(String string) {
        final byte[]  bytes = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb    = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                             .substring(1));
        }
        return sb.toString();
    }
    
    /**
     * @return User username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username User username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return User password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }
    
    /**
     * @param password User password hash
     */
    public void setPassword(String password) {
        this.passwordHash = hashString(password);
    }
    
    /**
     * @return User role
     */
    public UserRole getRole() {
        return role;
    }
    
    /**
     * @param role User role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    /**
     * @return User ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id User ID
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Convert user object to string.
     *
     * @return String that represent user properties
     */
    @Override
    public String toString() {
        return String.format("\u001B[34mId:\u001B[0m %d \u001B[34mUsername:\u001B[0m %s \u001B[34mRole:\u001B[0m %s", id, username, role.toString());
    }
}
