var expr = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));

for (var i = 0; i < 11; i++) {
    println(expr(i));
}
