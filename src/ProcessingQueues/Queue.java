package ProcessingQueues;
class Node<K> { // K is the custom datatype
    public K val ;
    public Node<K> next ;

    public Node() {
        this.val = null ;
        this.next= null ;
    }

    public Node(K val) {
        this.val = val;
        this.next= null ;
    }
}
/*
 * Custom Queue data-structure used for JobQueue & WaitingQueue .
 * Functions Queue is providing -
 * Queue()           - constructor
 * peek()            - returns the top element
 * length()          - returns the length of the queue
 * offer()           - inserts an element
 * poll()            - retrieves and removes the top end element
 * remove(element)   - removes the element from Queue without retrieving it
*/
public class Queue<K> {
    private int size ;
    private Node<K> head ;
    private Node<K> tail ;
    public Queue() {
        this.size = 0 ;
        this.head =  this.tail = null ;
    }
    public K peek(){
        return head.val ;
    }
    public int length(){
        return size ;
    }
    public void offer(K element) {
        Node<K> temp = new Node<>(element) ;
        if (tail == null)
            head = tail = temp ;
        else {
            tail.next = temp ;
            tail = tail.next ;
        }
        size = size + 1 ;
    }
    public K poll(){
        K temp = head.val ;
        head = head.next ;
        size = size - 1 ;
        if (size == 0)
            tail = null ;
        return temp ;
    }
    public void remove(K element){
        if (element == head.val)
            head = head.next ;
        else {
            Node<K> temp = head ;
            while (temp.next.val != element)
                temp = temp.next ;
            temp.next = temp.next.next ;
            if (tail.val == element)
                tail = temp ;
        }
        size -- ;
        tail = (size == 0) ? null : tail ;
    }
}
