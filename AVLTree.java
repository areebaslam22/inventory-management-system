class AVLTree {
    class Node {

        int key;
        int height;
        Node left , right;

        Node(int key){
            this.key = key;
            this.height = 1;
           
        }

}

    Node root;


    int height(Node N) {
        if (N == null) {
            return 0;
        }else{
            return N.height;
        }
     
    }


    int getBalance(Node N) {
        if (N == null) {
            return 0;
           
        }
        return height(N.left) - height(N.right);
           
    }


    Node rightRotate(Node y) {
       
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;


        x.height = Math.max(height(x.left),height(x.right))+1;
        y.height = Math.max(height(y.left),height(y.right))+1;

        return x;

    }


    Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left),height(x.right))+1;
        y.height = Math.max(height(y.left),height(y.right))+1;

        return y;
     
    }

 
    Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = insert(node.left,key);
        }
        if (key > node.key) {
            node.right = insert(node.right,key);
        }

        node.height = Math.max(height(node.left),height(node.right)) +1;
        int balance = getBalance(node);

        // LL  == 2 , key > root.left.key ----> too insure that thie add at the left side
        if(balance > 1 && key < root.left.key){
            return rightRotate(node);// ---->
        }
        // RR == -2
        if (balance < -1 && key > root.right.key) {
            return leftRotate(node);
        }

        // LR
        if (balance > 1 && key > root.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
           
        }
        // RL
        if (balance < -1 && key < root.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
           
        }
    return node;


       
    }
     

    void insert(int key) {
        root = insert(root, key);
    }

     public Item findMostExpensiveItem(int categoryId) {
        Node current = root;
        Item mostExpensive = null;

        while (current != null) {
            if (current.data.getID() == categoryId) {
                mostExpensive = current.data; // Update most expensive item
            }
            current = current.right; // Move to the right (higher prices)
        }

        return mostExpensive;
    }



    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

      }
}
