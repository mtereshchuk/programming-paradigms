public class ArrayQueue extends AbstractQueue {

    private int head = 0, tail = 0;
    private Object[] elements = new Object[5];

    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }
  
    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        elements = correctOrderQueue(capacity * 2);
        head = 0; 
        tail = size;
    }

    private Object[] correctOrderQueue(int newSize) {
        Object[] newElements = new Object[newSize];
        if (newSize == 0) {
            return newElements;
        }
        if (head < tail) {
            System.arraycopy(elements, head, newElements, 0, size);
        } else {
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            System.arraycopy(elements, 0, newElements, elements.length - head, tail);
        }
        return newElements;
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected void remove() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    protected void clearImpl() {
        elements = new Object[5];
        head = 0;
        tail = 0;
    }
}
