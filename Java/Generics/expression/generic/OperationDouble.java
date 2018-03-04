package expression.generic;

public class OperationDouble implements Operation<Double> {

    public Double add(Double first, Double second) {
        return first + second;
    }

    public Double subtract(Double first, Double second) {
        return first - second;
    }

    public Double multiply(Double first, Double second) {
        return first * second;
    }

    public Double divide(Double first, Double second) {
        return first / second;
    }

    public Double negate(Double first) {
        return -first;
    }

    public Double parse(String s) {
        return Double.parseDouble(s);
    }

    public Double converse(int first) { return (double) first; }

    public Double abs(Double first) {
        if (first >= 0) {
            return first;
        }
        return -first;
    }

    public Double square(Double first) {
        return multiply(first, first);
    }

    public Double mod(Double first, Double second) {
        return first % second;
    }
}
