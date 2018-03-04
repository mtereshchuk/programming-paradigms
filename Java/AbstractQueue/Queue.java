// inv: n ≥ 0 ∧ ∀i = 1..n: a[i] ≠ null
public interface Queue {
    // pre: element ≠ null 
    // post: n' = n + 1 ∧ ∀i = 1..n: a[i]' = a[i] ∧ a[n'] = element
    void enqueue(Object element);

    // pre: n > 0
    // post: ℝ = a[1] ∧ n' = n ∧ ∀i = 1..n': a[i]' = a[i]
    Object element();

    // pre: n > 0
    // post: ℝ = a[1] ∧ n' = n - 1 ∧ ∀i = 1..n': a[i]' = a[i + 1]
    Object dequeue();

    // post: ℝ = n ∧ n' = n ∧ ∀i = 1..n': a[i]' = a[i]
    int size();

    // post: ℝ = n > 0 ∧ n' = n ∧ ∀i = 1..n': a[i]' = a[i]
    boolean isEmpty();

    // post: n' = 0 ∧ a = ∅ 
    void clear();

    // post: ℝ = a ∧ n' = n ∧ ∀i = 1..n': a[i]' = a[i]
    Object[] toArray();
}
