public class Item implements Comparable<Item>{
    private int id;
    private String name;
    private String model;
    private double price;
    private int quantity;

    public Item(int id ,String name,String model, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }
    public int getID(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getModel(){
        return model;
    }
    public void setModel(String model){
        this.model = model;

    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name){

        this.name=name;
    }
    public void setPrice(double price){

        this.price=price;
    }
    public void setQuantity(int quantity){

        this.quantity=quantity;
    }
    @Override
    public int compareTo(Item item) {
        
        return this.name.compareToIgnoreCase(item.name);
    }
    @Override
    public String toString() {
        return "ID = "+id+ 
        "  name='" + name + 
        "      Model ="+model+
        "',       price=" + price + 
        ",      quantity=" + quantity + "";
    }
}
