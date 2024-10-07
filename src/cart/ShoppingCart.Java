package cart;

import java.io.Console;
import java.util.ArrayList;
import java.io.IOException;

public class ShoppingCart {

    public static void main(String[] args) throws IOException {

        // Allow the user to input from console
        Console cons = System.console();

        // Welcome message
        System.out.println("Welcome to your shopping cart!");

        // Array list for the cart
        ArrayList<String> cart = new ArrayList<>();

        boolean programIsRunning = true;

        // Setting default shopping cart to db
        String cartDbDirectory = "db";
        ShoppingCartDb shoppingCartDb = new ShoppingCartDb(cartDbDirectory);

        while (programIsRunning) {

            String input = cons.readLine(">");
            String[] cmdItm = input.split(" ", 2); // this splits the input into command and item

            String command = cmdItm[0].trim().toLowerCase();
            String item = "";

            if (cmdItm.length > 1) {
                item = cmdItm[1].trim();
            }

            switch (command) {

                case "list":
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        System.out.println("Items in your cart:");
                        for (int i = 0; i < cart.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, cart.get(i));
                        }
                    }
                    break;

                case "add":
                    // Split the string by commas
                    String[] items = item.split(",");
                    for (String itm : items) {
                        String trimmedItem = itm.trim();
                        if (!trimmedItem.isEmpty()) {
                            // Check for duplicate items
                            if (cart.contains(trimmedItem)) {
                                System.out.printf("%s is already in the cart.\n", trimmedItem);
                            } else {
                                cart.add(trimmedItem);
                                System.out.printf("%s has been added to the cart.\n", trimmedItem);
                            }
                        }
                    }
                    break;

                case "delete":
                    if (!item.isEmpty() && item.matches("\\d+")) {
                        int index = Integer.parseInt(item) - 1;
                        if (index >= 0 && index < cart.size()) {
                            String removedItem = cart.remove(index);
                            System.out.printf("%s has been removed from the cart.\n", removedItem);
                        } else {
                            System.out.println("Incorrect item index.\n");
                        }
                    } else {
                        System.out.println("Please provide a valid item index to delete.");
                    }
                    break;

                case "exit":
                    programIsRunning = false;
                    System.out.println("Exiting the shopping cart now!");
                    break;

                case "login":
                    if (cmdItm.length > 1) {
                        String username = cmdItm[1].trim(); // Get the username from the input
                        if (!username.isEmpty()) {
                            shoppingCartDb.login(username);
                        } else {
                            System.out.println("Please provide a valid username to login.");
                        }
                    } else {
                        System.out.println("Please provide a username to login (usage: login <username>).");
                    }
                break;

                case "save":
                    shoppingCartDb.save(cart);
                    break;

                case "users":
                    shoppingCartDb.listUsers();
                    break;

                default:
                    System.out.println("Wrong command, please try again.\n");
            }
        }
    }
}
