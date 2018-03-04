function cnst(value) {
    return function() {
        return value;
    }
}
var pi = cnst(Math.PI);
var e = cnst(Math.E);

var vars = ['x', 'y', 'z'];
function variable(name) {
    return function() {
        return arguments[vars.indexOf(name)];
    }
}
var x = variable("x");
var y = variable("y");
var z = variable("z");

function operation(func) {
    return function () {
        var args = Array.prototype.slice.call(arguments);
        return function() {
            var rememberedArgs = arguments;
            return func.apply(null, args.map(function (a) { return a.apply(null, rememberedArgs); }));
        }
    }
}
var add = operation(function (a, b) { return a + b; });
var subtract = operation(function (a, b) { return a - b; });
var multiply = operation(function (a, b) { return a * b; });
var divide = operation(function (a, b) { return a / b; });
var negate = operation(function (a) { return -a; });
var min3 = operation(function () { return Math.min.apply(null, arguments); });
var max5 = operation(function () { return Math.max.apply(null, arguments); });

function parse(expression) {

    expression = expression.trim();
    var tokens = expression.split(/\s+/);
    var stack = [];

    var constants = {
        "pi": pi,
        'e': e
    };

    var binaryOperations = {
        '+': add,
        '-': subtract,
        '*': multiply,
        '/': divide
    };

    for (var i = 0; i < tokens.length; i++) {
        var token = tokens[i], element;
        if (vars.indexOf(token) !== -1) {
            stack.push(variable(token));
        } else if (token in constants) {
            stack.push(constants[token]);
        } else if (!isNaN(parseInt(token))) {
            stack.push(cnst(parseInt(token)));
        } else {
            var func, number;
            if (token in binaryOperations) {
                func = binaryOperations[token];
                number = 2;
            } else if (token === "negate") {
                func = negate;
                number = 1;
            } else if (token === "min3") {
                func = min3;
                number = 3;
            } else {
                func = max5;
                number = 5;
            }
            stack.push(func.apply(null, stack.splice(stack.length - number, number)));
        }
    }

    return stack.pop();
}
