public class BinarySearchMissing {
    // pre: для ∀ i, j (0 <= i < j < a.length) ⇒ a[i] >= a[j] 
    // post: x ∈ a ⇒ ℝ == min(i) (i == 0..a.length - 1): a[i] == x, 
    //      x ∉ a ⇒ ℝ == -(insertion point + 1), где insertion point == min(i) 
    //      (i == 0..a.length - 1): a[i] < x или insertion point == a.length, 
    //      если для ∀ i (i == 0..a.length - 1) a[i] > x 
    public static int binarySearchIterative(int x, int[] a) {
        int l = -1;
        int r = a.length;
        // inv: l >= -1 ∧ r <= a.length ∧ l < r - 1 ∧ x < a[l] ∧ x >= a[r] (a[-1] == infinity, a[a.lentgth] == -infinity)
        // pre: l >= -1 ∧ r <= a.length ∧ l < r - 1 ∧ x < a[l] ∧ x >= a[r] (a[-1] == infinity, a[a.lentgth] == -infinity)               
        while (l < r - 1) {
            // inv
            int m = (l + r) / 2;
            // inv 
            // pre: m >= 0 ∧ m <= a.length - 1 ∧ l < m < r ∧ m == l + (r - l) / 2
            if (a[m] <= x) {
                // inv
                // pre: a[m] <= x 
                r = m;
                // post a[r] <= x ∧ r' == l + (r - l) / 2
            }
            else {
                // inv 
                // pre: a[m] > x
                l = m;
                // post: a[l] > x ∧ l' == l + (r - l) / 2
            }
        }
        // inv
        // post: l' >= -1 ∧ r' <= a.length ∧ l' < r' - 1 ∧ x < a[l'] 
        //      ∧ x >= a[r'] (a[-1] == infinity, a[a.lentgth] == -infinity)
        //      ∧ r' - l' == (r - l) / 2 
        // pre: r >= 0 ∧ r <= a.length ∧ r == min(i) (i == 0..a.length - 1): a[i] <= x
        if (r < a.length && x == a[r]) {
            // pre: r < a.length ∧ x == a[r] ∧ r == min(i) (i == 0..a.length - 1): a[i] == x
            return r;
            // post: ℝ == min(i) (i == 0..a.length - 1): a[i] == x
        }
        else {
            // pre: r == min(i) (i == 0..a.length - 1): a[i] < x
            //      ∧ ∀ i: 0 <= i < r ⇒ a[i] > x     
            return -(r + 1);
            // post ℝ == -(insertion point + 1), где insertion point == min(i) 
            //      (i == 0..a.length - 1): a[i] < x или insertion point == a.length, 
            //      если для ∀ i (i == 0..a.length - 1) a[i] > x
        }
    }

    // pre: для ∀ i, j (0 <= i < j < a.length) ⇒ a[i] >= a[j] 
    //      ∧ -1 <= l < r <= a.length 
    //      ∧ x < a[l] ∧ x >= a[r] (a[-1] == infinity, a[a.lentgth] == -infinity)
    // post: x ∈ a ⇒ ℝ == min(i) (i == 0..a.length - 1): a[i] == x, 
    //      x ∉ a ⇒ ℝ == -(insertion point + 1), где insertion point == min(i) 
    //      (i == 0..a.length - 1): a[i] < x или insertion point == a.length, 
    //      если для ∀ i (i == 0..a.length - 1) a[i] > x
    public static int binarySearchRecursive(int x, int[] a, int l, int r) {
        // pre: l >= -1 ∧ r <= a.length ∧ l < r - 1 ∧ x < a[l] ∧ x >= a[r] (a[-1] == infinity, a[a.lentgth] == -infinity)
        if (l < r - 1) {
            //pre: l < r - 1
            int m = (l + r) / 2;
            // pre: m >= 0 c m <= a.length - 1 ∧ m > l ∧ m < r 
            if (a[m] <= x) {
                // pre: a[m] <= x
                return binarySearchRecursive(x, a, l, m);
                // post: x ∈ a ⇒ ℝ == min(i) (i == 0..a.length - 1): a[i] == x, 
                //      x ∉ a ⇒ ℝ == -(insertion point + 1), где insertion point == min(i) 
                //      (i == 0..a.length - 1): a[i] < x или insertion point == a.length, 
                //      если для ∀ i (i == 0..a.length - 1) a[i] > x 
            } else {
                // pre: a[m] > x
                return binarySearchRecursive(x, a, m, r);   
                // post: x ∈ a ⇒ ℝ == min(i) (i == 0..a.length - 1): a[i] == x, 
                //      x ∉ a ⇒ ℝ == -(insertion point + 1), где insertion point == min(i) 
                //      (i == 0..a.length - 1): a[i] < x или insertion point == a.length, 
                //      если для ∀ i (i == 0..a.length - 1) a[i] > x
            }
            // post: ℝ == ℝ' ∨ ℝ == ℝ'', где ℝ' — результат BinarySearchRecursive(x, a, l, m)
            //      и ℝ'' — результат BinarySearchRecursive(x, a, m, r)
        }
        // pre: r >= 0 ∧ r <= a.length ∧ r == min(i) (i == 0..a.length - 1): a[i] <= x
        if (r < a.length && x == a[r]) {
            // pre: r < a.length ∧ x == a[r] ∧ r == min(i) (i == 0..a.length - 1): a[i] == x
            return r;
            // post: ℝ == min(i) (i == 0..a.length - 1): a[i] == x
        } else {
            // pre: r == min(i) (i == 0..a.length - 1): a[i] < x
            //      ∧ ∀ i: 0 <= i < r ⇒ a[i] > x     
            return -(r + 1);
            // post ℝ == -(insertion point + 1), где insertion point == min(i) 
            //      (i == 0..a.length - 1): a[i] < x или insertion point == a.length, 
            //      если для ∀ i (i == 0..a.length - 1) a[i] > x
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]); 
        int[] a = new int[args.length - 1]; 
        for (int i = 0; i < args.length - 1; i++) {
            a[i] = Integer.parseInt(args[i + 1]);  
        }
        System.out.println(binarySearchIterative(x, a));
        //System.out.println(binarySearchRecursive(x, a, -1, a.length)); 
    }
}