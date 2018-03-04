public class LinkedQueue extends AbstractQueue {

    private Node head, tail;
    
    protected void enqueueImpl(Object element) {
        Node oldTail = tail;
        tail = new Node(element);
        if (size == 0) {
            head = tail;
        } else {
            oldTail.next = tail;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }   

    protected void remove() {
        head = head.next;
        if (size == 0) {
            tail = null;
        }
    }

    protected void clearImpl() {
        head = null;
        tail = null;
    }

    private class Node {
        private Object value;
        private Node next;

        public Node(Object value) {
            assert value != null;
            this.value = value;
            this.next = null;
        }
    }
}
