package src;

// Import statements
import java.util.*;
import src.users.*;
import src.challenges.*;
import src.equipment.*;
import utils.*;

public class Game {
    private List<User> users = new ArrayList<>();
    private User loggedUser = null;
    private List<Challenge> challenges = new ArrayList<>();
    private List<Armor> armorsAvailable = new ArrayList<>();
    private List<Weapon> weaponsAvailable = new ArrayList<>();

    // Constructor ========================================================================================================
    public Game() {}

    // Public Methods =====================================================================================================

    // Method to play the game
    public void play() {
        // Load the game
        this.load();
        System.out.println("Playing...");

        // Main Loop
        while (true) {
            // Print the menu
            this.menu();
        
            // Save the game
            this.save();
        }
    }

    // Private Methods ====================================================================================================

    // Method to load the game
    private void load() {
        // Load the game from the file
        Game game = FileManager.readFile(Const.DATA_PATH);

        // Replace the game settings
        if (game != null) this.replaceSettings(game);
    }

    // Method to replace the game settings
    private void replaceSettings(Game game) {
        // Set the game attributes
        this.setUsers(game.getUsers());
        this.setLoggedUser(game.getLoggedUser());
        this.setChallenges(game.getChallenges());
        this.setArmorsAvailable(game.getArmorsAvailable());
        this.setWeaponsAvailable(game.getWeaponsAvailable());
    }

    // Method to save the game
    private void save() {
        // Save the game to the file
        FileManager.saveFile(this);
    }

    // Method to print the main menu
    private void menu() {
        if (this.loggedUser == null)
            this.notLoggedMenu();
        else if (this.loggedUser instanceof Player)
            this.loggedPlayerMenu();
        else if (this.loggedUser instanceof Admin)
            this.loggedAdminMenu();
    }
    
    // Method to print the not logged menu options
    private void notLoggedMenu() {
        String [] options = {"Login", "Register", "Exit"};
        int answer = MenuBuilder.menu("Welcome to RPG Game", options);

        if (answer == 1) this.login();
        else if (answer == 2) this.register();
        else System.exit(0);
    }

    // Method to print the logged admin menu options
    private void loggedPlayerMenu() {
        System.out.println("Logged Player Menu");
    }

    // Method to print the logged player menu options
    private void loggedAdminMenu() {
        System.out.println("Logged Admin Menu");
    }

    // Login Methods ======================================================================================================

    // Method to login the user by credentials
    private void login() {
        while (this.loggedUser == null) {
            // Get the user credentials
            String [] credentials = this.getUserCredentials();

            // Retrieve the user by credentials
            User user = this.retrUser(credentials[0], credentials[1]);

            // Alert the user if the credentials are invalid || Set the logged user if the credentials are valid
            if (user == null) {
                MenuBuilder.alert("Invalid Credentials", "The username or password are invalid. Please try again.");
                boolean answer = MenuBuilder.askYesNo("Do you want to try again?");
                if (!answer) break;

            } else this.loggedUser = user;
        }
    }

    // Method to get the user credentials
    private String[] getUserCredentials() {
        String [] labels = {"Username", "Password"};
        return MenuBuilder.form("Login", labels);
    }

    // Method to retrieve the user by credentials
    private User retrUser(String username, String password) {
        for (User user : this.users) {
            if (validateUser(user, username, password)) return user;
        }

        return null;
    }

    // Method to validate the user credentials
    private boolean validateUser(User user, String username, String password) {
        return user.getName().equals(username) && user.getPassword().equals(password);
    }

    // Register Methods ===================================================================================================

    // Method to register a new user
    private void register() {
        // Get the user data
        String [] userData = this.getUserData();

        // Ask for the user type Player (1) || Admin (2)
        int userType = this.getUserType();

        // Create the user
        User user = this.createUser(userData, userType);

        // Add the user to the users list
        this.users.add(user);
    }

    // Method to get the user data
    private String[] getUserData() {
        // Get basic user data
        String [] labels = {"Username", "Nick", "Password", "Confirm Password"};
        String [] data = MenuBuilder.form("Register", labels);

        // Validate the password
        while (!data[2].equals(data[3])) {
            MenuBuilder.alert("Invalid Password", "The passwords do not match. Please try again.");
            data = MenuBuilder.form("Register", labels);
        }

        return data;
    }

    // Method to get the user type Player (1) || Admin (2)
    private int getUserType() {
        String [] options = {"Player", "Admin"};
        return MenuBuilder.menu("Select User Type", options);
    }

    // Method to create the user
    private User createUser(String[] userData, int userType) {
        if (userType == 1) return new Player(userData[0], userData[1], userData[2], this.generatePlayerId());
        else return new Admin(userData[0], userData[1], userData[2]);
    }

    // Method to generate the plaer id
    private String generatePlayerId() {
        int usersSize = this.users.size();

        // ID Format LNLLN (L: Letter, N: Number)
        return "P" + (usersSize) + "A" + (char) (65 + usersSize) + (char) (65 + usersSize) + (usersSize + 1);
    }

    // Getters & Setters ==================================================================================================
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public User getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
    public List<Challenge> getChallenges() {
        return challenges;
    }
    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }
    public List<Armor> getArmorsAvailable() {
        return armorsAvailable;
    }
    public void setArmorsAvailable(List<Armor> armorsAvailable) {
        this.armorsAvailable = armorsAvailable;
    }
    public List<Weapon> getWeaponsAvailable() {
        return weaponsAvailable;
    }
    public void setWeaponsAvailable(List<Weapon> weaponsAvailable) {
        this.weaponsAvailable = weaponsAvailable;
    }
}