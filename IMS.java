import java.util.*;
import java.io.*;

public class IMS {
    public static void main(String[] args) throws Exception {
        CustomLinkedList inventory = new CustomLinkedList();
        Scanner input = new Scanner(System.in);
        File phoneFile = new File("dataOfPhone.txt");
        File laptopFile = new File("dataOfLaptop.txt");
        File pcFile = new File("dataOfPc.txt");

        loadInventoryFromFile(inventory, phoneFile, 1);
        loadInventoryFromFile(inventory, laptopFile, 2);
        loadInventoryFromFile(inventory, pcFile, 3);

        InventoryTree tree = new InventoryTree();
        Node current = inventory.getFirst();
        while (current != null) {
            tree.insert(current.getData());
            current = current.getNext();
        }//while

        while (true) {
            System.out.println("\n=============================="); 
            System.out.println("    Inventory Management System");
            System.out.println("==============================");
            System.out.println("Commands:");
            System.out.println("add          - Add a new item");
            System.out.println("remove       - Remove an existing item");
            System.out.println("update       - Update item details");
            System.out.println("sort         - Sort inventory");
            System.out.println("search       - Search item by name");
            System.out.println("searchprice  - Search items by price range");
            System.out.println("findhighestprice - Find most expensive item");
            System.out.println("display      - Display inventory items");
            System.out.println("monitorstock - Monitor stock levels");
            System.out.println("checklow     - Check low stock items");
            System.out.println("restock      - Restock items");
            System.out.println("sell         - Sell an item");
            System.out.println("undo         - Undo last transaction");
            System.out.println("totalvalue   - Calculate total inventory value");
            System.out.println("exit         - Exit the program");
            System.out.println("==============================");
            System.out.print("Enter a command: ");
            String command = input.nextLine().toLowerCase();
            if (command.equals("searchprice")) {
                System.out.println("Enter the low price: ");
                double low = input.nextDouble();
                input.nextLine();

                System.out.println("Enter the high price: ");
                double high = input.nextDouble();
                input.nextLine();
                tree.searchPriceInRange(low, high);

            } else if (command.equals("add")) {
                System.out.println("Enter ID of item (1 for Phone, 2 for Laptop, 3 for PC): ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter item name: ");
                String name = input.nextLine();
                System.out.print("Enter the model of the item: ");
                String model = input.nextLine();
                System.out.print("Enter item price: ");
                double price = input.nextDouble();
                System.out.print("Enter item quantity: ");
                int quantity = input.nextInt();
                input.nextLine();

                Item item = new Item(id, name, model, price, quantity);
                inventory.addItem(item);

                if (id == 1) {
                    saveItemToFile(phoneFile, name, model, price, quantity);
                    System.out.println("Item has been added!");
                } else if (id == 2) {
                    saveItemToFile(laptopFile, name, model, price, quantity);
                    System.out.println("Item has been added!");
                } else if (id == 3) {
                    saveItemToFile(pcFile, name, model, price, quantity);
                    System.out.println("Item has been added!");
                } else {
                    System.out.println("Invalid ID.");
                }

            } else if (command.equals("undo")) {
                inventory.undoLastTransaction();
                updateInventoryFile(inventory, phoneFile, laptopFile, pcFile);

            }else if (command.equals("totalvalue")) {
                System.out.println("Calculating total value: ");
                tree.calculateTotalInventoryValue();
            } else if (command.equals("update")) {
                System.out.println("Enter ID of item (1 for Phone, 2 for Laptop, 3 for PC): ");
                int iD = input.nextInt();
                input.nextLine();
                System.out.println("Enter the name of item do you want to update: ");
                String itemName = input.nextLine();
                System.out.print("Enter the model of the item: ");
                String newModel = input.nextLine();
                System.out.println("Enter the new price: ");
                double newPrice = input.nextDouble();
                System.out.println("Enter the new quantity: ");
                int newQuantity = input.nextInt();
                input.nextLine();
                Item updateItem = new Item(iD, itemName, newModel, newPrice, newQuantity);
                boolean updated = inventory.updateItem(itemName, updateItem);
                if (updated) {
                    System.out.println("Item updated successfully!!");
                    if (iD == 1) {
                        rewriteInventoryToFile(inventory, phoneFile, iD);
                    } else if (iD == 2) {
                        rewriteInventoryToFile(inventory, laptopFile, iD);
                    } else if (iD == 3) {
                        rewriteInventoryToFile(inventory, pcFile, iD);
                    } else {
                        System.out.println("Invalid ID.");
                    }
                } else {
                    System.out.println("Item not found.");
                }

            } else if (command.equals("remove")) {
                System.out.println("Enter ID of item (1 for Phone, 2 for Laptop, 3 for PC): ");
                int id = input.nextInt();
                input.nextLine();
                System.out.print("Enter the name of the item to remove: ");
                String itemname = input.nextLine();
                boolean removed = inventory.removeItem(itemname);
                if (removed) {
                    System.out.println("Item removed successfully.");
                    if (id == 1) {
                        rewriteInventoryToFile(inventory, phoneFile, 1);
                    } else if (id == 2) {
                        rewriteInventoryToFile(inventory, laptopFile, 2);
                    } else if (id == 3) {
                        rewriteInventoryToFile(inventory, pcFile, 3);
                    } else {
                        System.out.println("Item not found.");
                    }
                }

            } else if (command.equals("sort")) {
                System.out.println("Enter ID of item to sort (1 for Phone, 2 for Laptop, 3 for PC): ");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter the field you want to sort by (name, price, quantity): ");
                String criteria = input.nextLine().toLowerCase();

                inventory.sortInventory(criteria);

                File targetFile = null;
                if (id == 1) {
                    targetFile = phoneFile;
                } else if (id == 2) {
                    targetFile = laptopFile;
                } else if (id == 3) {
                    targetFile = pcFile;
                } else {
                    System.out.println("Invalid ID.");
                    continue;
                }

                rewriteInventoryToFile(inventory, targetFile, id);
                System.out.println("Inventory sorted and updated in the " + targetFile + " successfully!");

            } else if (command.equals("findhighestprice")) {
                System.out.println("Enter the category (1 for Phone, 2 for Laptop, 3 for PC):");
                int categoryId = input.nextInt();
                input.nextLine();

                Item highest = inventory.findMostExpensiveItem(categoryId);

                if (highest != null) {
                    System.out.println("\nMost expensive item in category " + categoryId + ":");
                    System.out.println("Name: " + highest.getName() + ", Model: " + highest.getModel() + ", Price: " + highest.getPrice() + ", Quantity: " + highest.getQuantity());
                } else {
                    System.out.println("No items found in this category.");
                }

            } else if (command.equals("checklow")) {
                System.out.println("Enter the category (1 for Phone, 2 for Laptop, 3 for PC):");
                int choose = input.nextInt();
                input.nextLine();

                File file = null;
                String fileType = "";

                if (choose == 1) {
                    file = phoneFile;
                    fileType = "Phone";
                } else if (choose == 2) {
                    file = laptopFile;
                    fileType = "Laptop";
                } else if (choose == 3) {
                    file = pcFile;
                    fileType = "PC";
                } else {
                    System.out.println("Invalid File choice.");
                    continue;
                }

                System.out.println("Enter the stock range:");
                if (!input.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine();
                    continue;
                }

                int range = input.nextInt();
                input.nextLine();

                checkLowFromThatFile(file, fileType, range);

            } else if (command.equals("search")) {
                System.out.println("Enter the name of the item you want to search: ");
                String itemName = input.nextLine();

                Item[] itemsArray = inventory.convertLinkedListToArray();
                Arrays.sort(itemsArray);
                for (Item temp : itemsArray) {
                    System.out.println(temp);
                }
                int low = 0, high = itemsArray.length - 1;
                int index = inventory.searchItemByNameRecursive(itemName, itemsArray, low, high);

                if (index != -1) {
                    System.out.println("Item has been found at " + index + " index");
                } else {
                    System.out.println("Item not found");
                }

            } else if (command.equals("display")) {
                inventory.displayItems();

            } else if (command.equals("exit")) {
                break;

            }
            else if (command.equals("sell")) {
                System.out.print("Enter item name: ");
                String itemName = input.next();

                
                if (!itemExists(itemName, inventory)){
                    System.out.println("Item not found in inventory.");
                    return; 
                }

                System.out.print("Enter quantity you want to buy: ");
                int quantity = input.nextInt();

                sellItem(inventory, itemName, quantity, phoneFile, laptopFile, pcFile);
            }

            else if (command.equals("monitorstock")) {
                predictRestockItems(inventory);
            } else if (command.equals("restock")) {
                restockItem(inventory, phoneFile, laptopFile, pcFile);
            } else {
                System.out.println("Invalid command. Try again.");
            }//else
        }//while
    }//main
 

    private static void loadInventoryFromFile(CustomLinkedList inventory, File file, int id) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] data = line.split("\t");
                    if (data.length >= 4) {
                        String name = data[0];
                        String model = data[1];
                        double price = Double.parseDouble(data[2]);
                        int quantity = Integer.parseInt(data[3]);
                        inventory.addItem(new Item(id, name, model, price, quantity));
                    }//if
                }//if
            }//while
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found.");
        } catch (Exception e) {
            System.out.println("Error loading data from file " + file.getName());
        }//catch
    }//loadInventoryFromFile()

    private static void saveItemToFile(File file, String name, String model, double price, int quantity) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(name + "\t" + model + "\t" + price + "\t" + quantity + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + file.getName());
        }//catch
    }//saveItemToFile

    private static void rewriteInventoryToFile(CustomLinkedList inventory, File file, int id) {
        try (FileWriter writer = new FileWriter(file, false)) {
            Node current = inventory.getFirst();
            while (current != null) {
                Item item = current.getData();
                if (item.getID() == id) {
                    writer.write(item.getName() + "\t" + item.getModel() + "\t" + item.getPrice() + "\t" + item.getQuantity() + "\n");
                }//if
                current = current.getNext();
            }//while
        } catch (IOException e) {
            System.out.println("Error rewriting to file: " + file.getName());
        }//catch
    }//rewriteInventoryToFile()

    private static void checkLowFromThatFile(File file, String fileType, int range) {
        boolean found = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] data = line.split("\t");
                if (data.length >= 4) {
                    String name = data[0];
                    String model = data[1];
                    double price = Double.parseDouble(data[2]);
                    int quantity = Integer.parseInt(data[3]);

                    if (quantity < range) {
                        System.out.println("Item: " + name + " Model: " + model + " Price: " + price + " Quantity: " + quantity + " has low stock");
                        found = true;
                    }//if
                }//if
            }//while
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("There is something wrong");
        }//catch

        if (!found) {
            System.out.println("No item below the stock");
        }//if
    }//checkLowFromThatFile()

    private static void restockItem(CustomLinkedList inventory, File phoneFile, File laptopFile, File pcFile) {
        Node current = inventory.getFirst();
        boolean restocked = false;

        while (current != null) {
            Item item = current.getData();
            if (item.getQuantity() < 10) {  
                item.setQuantity(item.getQuantity() + 30);
                System.out.println("Restocked: " + item.getName() + " | New Quantity: " + item.getQuantity());
                restocked = true;
            }//if
            current = current.getNext();
        }//while

        if (restocked) {
        
            rewriteInventoryToFile(inventory, phoneFile, 1);
            rewriteInventoryToFile(inventory, laptopFile, 2);
            rewriteInventoryToFile(inventory, pcFile, 3);
        } else {
            System.out.println("No items need restocking.");
        }//else
    }//restockItem()
    private static void predictRestockItems(CustomLinkedList inventory) {
        Node current = inventory.getFirst(); 

       System.out.println("Items that need restocking:");

        boolean needsRestock = false;

        while (current != null) {
            Item item = current.getData(); 
            if (item.getQuantity() < 10) {  
                System.out.println(item.getName() + " | Current Quantity: " + item.getQuantity());
                needsRestock = true;
            }//if
            current = current.getNext(); 
        }//while

        if (!needsRestock) {
            System.out.println("All items are sufficiently stocked.");
        }//if
    }//predictRestockItems()

    private static void updateInventoryFile(CustomLinkedList inventory, File phoneFile, File laptopFile, File pcFile) {
        try {
            FileWriter phoneWriter = new FileWriter(phoneFile, false);
            FileWriter laptopWriter = new FileWriter(laptopFile, false);
            FileWriter pcWriter = new FileWriter(pcFile, false);
            Node current = inventory.getFirst();

            while (current != null) {
                Item item = current.getData();
                String itemData = item.getName() + "\t" + item.getModel() + "\t" + item.getPrice() + "\t" + item.getQuantity() + "\n";

                if (item.getID() == 1) {
                    phoneWriter.write(itemData);
                } else if (item.getID() == 2) {
                    laptopWriter.write(itemData);
                } else if (item.getID() == 3) {
                    pcWriter.write(itemData);
                }
                current = current.getNext();
            }//while

            phoneWriter.close();
            laptopWriter.close();
            pcWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating inventory files.");
        }//catch
    }//updateInventoryFile()

    public static boolean itemExists(String name,CustomLinkedList inventory) {
        Node current = inventory.getFirst();
        while (current != null) {
            if (current.getData().getName().equalsIgnoreCase(name)) {
                return true;
            }//if
        current = current.getNext();
        }//while
        return false;
    }//itemExists()

    public static void sellItem(CustomLinkedList inventory, String itemName, int quantity, File phoneFile, File laptopFile, File pcFile) {
        Node current = inventory.getFirst();
    
        while (current != null) {
            Item item = current.getData();
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (item.getQuantity() >= quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    System.out.println(quantity + " units of " + itemName + " sold. Remaining stock: " + item.getQuantity());
                    updateInventoryFile(inventory, phoneFile, laptopFile, pcFile);
                } else {
                    System.out.println("Not enough stock available!");
                }//else
                return;
            }//if
            current = current.getNext();
        }//while
        System.out.println("Item not found!");
    }//sellItem() 
}//class