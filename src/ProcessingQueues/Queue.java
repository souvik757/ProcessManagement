package ProcessingQueues;

/*
 * Custom Queue data-structure used for JobQueue & WaitingQueue .
 * Functions Queue is providing -
 * Queue()           - constructor
 * size()            - returns the length of the queue
 * peek()            - returns the top element
 * offer()           - inserts an element
 * poll()            - retrieves and removes the top end element
 * remove(element)   - removes the element from Queue without retrieving it
 */
public class Queue<T>{
	private int size;
	private Node<T> head ;
	private Node<T> tail;
	public Queue() {
		size = 0;
		head = tail = null;
	}
	
	public T peek() {
		return head.data;
	}
	
	public int size(){
		return size;
	}
	
	public void remove(T element) {
		if (element == head.data) {
			head = head.next;
		}
		else {
			Node<T> temp = head;
			while (temp.next.data != element) {
				temp = temp.next;
			}
			temp.next = temp.next.next;
			if(tail.data == element)
				tail = temp;
		}
		size--;
		if(size == 0)
			tail = null;
	}

	public void offer(T element) {
		if(tail == null){
			head = tail = new Node<T>(element);
		}
		else {
			tail.next = new Node<T>(element);
			tail = tail.next;
		}
		size++;
	}

	public T poll() {
		T x = head.data;
		head = head.next;
		size--;
		if(size == 0)
			tail = null;
		return x;
	}

}


