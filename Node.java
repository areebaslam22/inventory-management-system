public class Node {
    private Item data;
    private Node next;

    public Node(Item data) {
        this.data = data;
        this.next = null;
    }

    public Item getData() {
        return data;
    }

    public void setData(Item data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
