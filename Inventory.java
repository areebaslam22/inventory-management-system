import java.util.*;
class Inventory {
	
	private int inventoryId;
	private String location;
	private int totalStockQuantity;
	private ArrayList<Item> items;

	public Inventory(){
		this.inventoryId=0;
		this.location=null;
		this.totalStockQuantity=0;
		this.items = new ArrayList<>(); 
	}//Inventory()

	public Inventory(int inventoryId, String location,int totalStockQuantity){
		this.inventoryId=inventoryId;
		this.location=location;
		this.totalStockQuantity=totalStockQuantity;
		this.items = new ArrayList<>(); 
	}//Inventory()


	public void setInventoryId(int inventoryId){
		this.inventoryId=inventoryId;
	}//setInventoryId()

	public void setLocation(String location){
		this.location=location;
	}//setLocation()

	public void setTotalStockQuantity(int totalStockQuantity){
		this.totalStockQuantity=totalStockQuantity;
	}//setTotalStockQuantity()

	public int getInventoryId(){
		return inventoryId;
	}//getInventoryId()

	public String getLocation(){
		return location;
	}//getLocation()

	public int getTotalStockQuantity(){
		return totalStockQuantity;
	}//getTotalStockQuantity()

	@Override
    public String toString() {
      	return "Inventory id: "+getInventoryId()+" Location is: "+getLocation()+" Total Stock Quantity: "+getTotalStockQuantity();
    }//toString()

	


}//class