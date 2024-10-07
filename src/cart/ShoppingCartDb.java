package cart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDb {

    private final File cartDbDirectory;
    private File currentUserFile;
    private List<String> cart; // Declare the cart variable

    public ShoppingCartDb(String cartDbDirectory) {
        this.cartDbDirectory = new File(cartDbDirectory);
        if (!this.cartDbDirectory.exists()) {
            this.cartDbDirectory.mkdirs(); // Create new directory if it doesn't exist
        }
        this.cart = new ArrayList<>(); // Initialize the cart
    }

    // Login and loading method
    public void login(String username) throws IOException {
        currentUserFile = new File(cartDbDirectory, username + ".db");
        // Create new file if it is a new user
        if (!currentUserFile.exists()) {
            currentUserFile.createNewFile();
        } else { // Load user file
            BufferedReader reader = new BufferedReader(new FileReader(currentUserFile));
            String line;
            int index = 0;
            boolean cartIsEmpty = true;
            while ((line = reader.readLine()) != null) {
                System.out.printf("%s , your cart contains these items:\n", username);
                System.out.printf("%d. %s\n", index +1, line);
                cart.add(line); // Add loaded items to the cart
                index++;
                cartIsEmpty = false;
            }

            if(cartIsEmpty) {
                System.out.println("Your cart is empty.");
            }
            reader.close(); // Close the reader
        }
    }

    // Save method
    public void save(ArrayList<String> cart) throws IOException {
        // Prompt to login before saving
        if (currentUserFile == null || !currentUserFile.exists()) {
            System.out.println("Please login first before saving your cart.");
            return; // Exit the method early if the user is not logged in
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(currentUserFile));
        for (String item : cart) {
            writer.write(item);
            writer.newLine(); // Write each item on a new line
        }
        writer.close(); // Close the writer
        System.out.println("Your cart has been saved.");
    }

    //listUser
    // List all users in the cart database
public void listUsers() {
    File[] userFiles = cartDbDirectory.listFiles((dir, name) -> name.endsWith(".db"));
    
    if (userFiles != null && userFiles.length > 0) {
        System.out.println("The following users are registered: ");
        for (int i = 0; i < userFiles.length; i++){
            String username = userFiles[i].getName().replace(".db", "");
            System.out.printf("%d. %s\n", i+1, username);
        }
    } else {
        System.out.println("No users found in the database.");
    }
}

}

