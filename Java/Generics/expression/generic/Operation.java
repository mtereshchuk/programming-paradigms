package expression.generic;

import expression.exception.*;

public interface Operation<T> {

    T add(T first, T second) throws Exception;

    T subtract(T first, T second) throws Exception;

    T multiply(T first, T second) throws Exception;

    T divide(T first, T second) throws Exception;

    T negate(T first) throws Exception;

    T parse(String s) throws Exception;

    T converse(int first);

    T abs(T first) throws Exception;

    T square(T first) throws Exception;

    T mod(T first, T second);
}
