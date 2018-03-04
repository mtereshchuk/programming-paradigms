public abstract class AbstractQueue implements Queue {

    protected int size;
    
    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        Object element = element();
        size--;
        remove();
        return element;
    }

    protected abstract void remove();

     public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();

    public Object[] toArray() {
        Object[] elements = new Object[size];
        for (int i = 0; i < size; i++) {
            elements[i] = dequeue();
            enqueue(elements[i]);
        }
        return elements;
    }
}
