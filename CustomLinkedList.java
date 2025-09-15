import java.util.Iterator;
import java.util.*;
public class CustomLinkedList {
    private Node first;
    private int numberOfEntries;
    private Stack<String> transactionHistory; 
    private Stack<Item> removedItemsStack;

    public CustomLinkedList() {
        first = null;
        numberOfEntries = 0;
        transactionHistory = new Stack<>();
        removedItemsStack = new Stack<>();
    }//CustomLinkedList
     public Node getFirst() {
        return first;
    }//getFirst

    public void addItem(Item item) {     
        Node newNode = new Node(item);
        if (first == null) {
            first = newNode;
        } else {
            Node current = first;
            while (current.getNext() != null) {
                current = current.getNext();
            }//while
            current.setNext(newNode);
        }//else
        numberOfEntries++;

    }//addItem()

    public boolean updateItem(String oldItem,Item newItem){
        Node temp = first;
        boolean result=false;
        if(first==null){
            throw new NoSuchElementException("The list is empty, nothing to update.");

        }else{

            while (temp != null) {
                if (temp.getData().getName().equals(oldItem)) {
                    temp.getData().setModel(newItem.getModel());
                    temp.getData().setPrice(newItem.getPrice());
                    temp.getData().setQuantity(newItem.getQuantity());
                    result = true;
                    break; 
                }//if
            temp = temp.getNext();
            }//while
        }//else
        return result;

    }//updateItem()

    public void displayItems() {
        if (first == null) {
            System.out.println("The inventory is empty.");
            return;
        }//if

        Node current = first;
        System.out.println("");
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }//while
    }//displayItems

    public int searchItemByNameRecursive(String itemName, Item items[], int low, int high){

        int mid= low + (high - low)/2;
        int result=0;
        if(low > high){
            return -1;
        }//if

        if(items[mid].getName().equalsIgnoreCase(itemName)){
            return mid;
        }//if
        else if(itemName.compareToIgnoreCase(items[mid].getName())<0){
            result= searchItemByNameRecursive(itemName, items,low, mid-1);
        }//else if
        else{
            result=searchItemByNameRecursive(itemName,items,mid+1, high);
        }//else
        return result;
    }//searchItemByNameRecursive()

    public Item[] convertLinkedListToArray(){

        int size=0;
        Node curr=first;

        while(curr !=null){
            size++;
            curr=curr.getNext();
        }//while

        Item[] itemsArray= new Item[size];
        curr=first;
        int index=0;

        while(curr!=null){
            itemsArray[index]=curr.getData();
            index++;
            curr=curr.getNext();
        }//while
        return itemsArray;
    }//convertLinkedListToArray()

    public void sortInventory(String criteria) {
        Node temp = first;
        
        while (temp != null) {
            Node temp2 = temp.getNext();
    
            while (temp2 != null) {
                boolean swap = false;

                if (criteria.equalsIgnoreCase("name")) {
                    if (temp.getData().getName().compareTo(temp2.getData().getName()) > 0) {
                        Item tempItem = temp.getData();
                        temp.setData(temp2.getData());  
                        temp2.setData(tempItem); 
                    }//if
                } else if (criteria.equalsIgnoreCase("price")) {
                    if (temp.getData().getPrice() > temp2.getData().getPrice()) {
                        Item tempItem = temp.getData();
                        temp.setData(temp2.getData());  
                        temp2.setData(tempItem); 
                    }//if
                } else if (criteria.equalsIgnoreCase("quantity")) {
                    if (temp.getData().getQuantity() > temp2.getData().getQuantity()) {
                        Item tempItem = temp.getData();
                        temp.setData(temp2.getData());  
                        temp2.setData(tempItem); 
                    }//if
                }//else if
                temp2 = temp2.getNext();  
            }//inner while

            temp = temp.getNext(); 
        }//outer while
    }//sortInventory()

    public boolean removeItem(String name){
        Node temp = first;
        Node previous = null;
        boolean found = false;
        while(temp != null){
            if(temp.getData().getName().equals(name)){
                transactionHistory.push("Removed: " + name); // Log transaction
                removedItemsStack.push(temp.getData()); // Store removed item for undo
                if (previous != null) {
                    previous.setNext(temp.getNext());
                }else{
                    first = temp.getNext();
                }//else
                found = true;
            }else {
                previous = temp;
            }//else
            temp = temp.getNext();
        }//while
        return found;
    }//removeItem()

    public Item findMostExpensiveItem(int categoryId) {
    if (first == null) return null;  

    Node current = first;  
    Item mostExpensive = null;

    while (current != null) {
        Item item = current.getData();
        
        
        if (item.getID() == categoryId) {
            
            if (mostExpensive == null || item.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = item;
            }//inner for
        }//outer for
        
        current = current.getNext();  
    }//while

    return mostExpensive; 

    }//findMostExpensiveItem()


    public void undoLastTransaction() {
        if (!transactionHistory.isEmpty()) {
            String lastAction = transactionHistory.pop();
            System.out.println("Undoing: " + lastAction);
            
            if (lastAction.startsWith("Removed: ")) {
                if (!removedItemsStack.isEmpty()) {
                    Item restoredItem = removedItemsStack.pop();
                    addItem(restoredItem); // Restore removed item
                    System.out.println("Undo successful: Item " + restoredItem.getName() + " restored.");
                }//inner most for
            }//outer for
        } else {
            System.out.println("No transactions to undo.");
        }//else
    }//undoLastTransaction()

}//class
