class InventoryTree{
private class TreeNode{
	private Item item;
	private TreeNode left;
	private TreeNode right;
	public TreeNode(Item item){
		this.item = item;
		this.left = null;
		this.right = null;
	}//TreeNode()
	
}//TreeNode class

	private TreeNode node;
	public InventoryTree(){
		node = null;
	}
	public void insert(Item item){
		node = insertRec(node ,item);

	}//insert()
	private TreeNode insertRec(TreeNode node,Item item){
		if(node == null){
			node = new TreeNode(item);
			return node;
		}//if
		if(item.getPrice()<node.item.getPrice()){
			node.left = insertRec(node.left,item);
		}else{
			node.right = insertRec(node.right,item);
		}//else
		return node;
	}//insertRec()
	public void searchPriceInRange(double low,double high){
		searchPriceInRangeRec(node,low,high);
	}//searchPriceInRange()
	private void searchPriceInRangeRec(TreeNode node,double low,double high){
		if(node!=null){
			if(node.item.getPrice()>=low && node.item.getPrice()<=high){
				System.out.println(node.item);
			}//if
			if(node.item.getPrice()>low){
				searchPriceInRangeRec(node.left,low,high);
			}//if
			if(node.item.getPrice()<high){
				searchPriceInRangeRec(node.right,low,high);
			}//if
			
		}//outer if
	}//searchPriceInRangeRec()

		
	public void calculateTotalInventoryValue() {
	    double totalValue = calculateTotalInventoryValueRec(node);
	    System.out.println("Total inventory value: " + totalValue);
	}//calculateTotalInventoryValue()

	private double calculateTotalInventoryValueRec(TreeNode node) {
	    if (node == null) {
	        return 0;
	    }//if
	    double leftValue = calculateTotalInventoryValueRec(node.left);
	    double rightValue = calculateTotalInventoryValueRec(node.right);
	    return node.item.getPrice() * node.item.getQuantity() + leftValue + rightValue;
	}//calculateTotalInventoryValueRec()
	
}//class