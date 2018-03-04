var _vars = ['x', 'y', 'z'];

function Variable(name) {
    this._index = _vars.indexOf(name);
}

Variable.prototype.evaluate = function () { return arguments[this._index]; };
Variable.prototype.toString = function () { return _vars[this._index]; };
Variable.prototype.diff = function(name) { return name === _vars[this._index] ? new Const(1) : new Const(0); };

function Const(value) {
    this._value = value;
}

Const.prototype.evaluate = function () { return this._value; };
Const.prototype.toString = function () { return this._value.toString(); };
Const.prototype.diff = function() { return new Const(0); };

var binOp = [], unOp = [];
function Operation(func, symbol, diffFunc) {
    function Constructor() {
        this._args = Array.prototype.slice.call(arguments);
        if (this._args.length === 1) {
            unOp[symbol] = Constructor;
        } else {
            binOp[symbol] = Constructor;
        }
    }
    Constructor.prototype.evaluate = function () {
        var _rememberedArgs = arguments;
        return func.apply(null, this._args.map(function (a) { return a.evaluate.apply(a, _rememberedArgs); }));
    };
    Constructor.prototype.toString = function () {
        return this._args.join(" ") + " " + symbol;
    };
    Constructor.prototype.diff = function (name) {
        return diffFunc(this._args, name);
    };
    return Constructor;
}

var Add = Operation(function (a, b) { return a + b; }, "+", diffAdd);
var Subtract = Operation(function (a, b) { return a - b; }, "-", diffSubtract);
var Multiply = Operation(function (a, b) { return a * b; }, "*", diffMultiply);
var Divide = Operation(function (a, b) { return a / b; }, "/", diffDivide);
var Negate = Operation(function (a) { return -a; }, "negate", diffNegate);
var Square = Operation(function (a) { return a * a; }, "square", diffSquare);
var Sqrt = Operation(function (a) { return Math.sqrt(Math.abs(a)); }, "sqrt", diffSqrt);
var Sign = Operation(function (a) { return a > 0 ? 1 : -1; });

function diffAdd(args, name)  { return new Add(args[0].diff(name), args[1].diff(name)); }
function diffSubtract(args, name)  { return new Subtract(args[0].diff(name), args[1].diff(name)); }
function diffMultiply(args, name)  { return new Add(new Multiply(args[0].diff(name), args[1]),
    new Multiply(args[0], args[1].diff(name))); }
function diffDivide(args, name)  { return new Divide(new Subtract(new Multiply(args[0].diff(name), args[1]),
    new Multiply(args[0], args[1].diff(name))),
    new Multiply(args[1],args[1])); }
function diffNegate(args, name)  { return new Negate(args[0].diff(name)); }
function diffSquare(args, name) { return new Multiply(new Const(2), new Multiply(args[0], args[0].diff(name))); }
function diffSqrt(args, name) { return new Divide(new Multiply(new Sign(args[0]), args[0].diff(name)), new Multiply(new Const(2), new Sqrt(args[0]))); }

function parse(expression) {

    expression = expression.trim();
    var tokens = expression.split(/\s+/);
    var stack = [];

    for (var i = 0; i < tokens.length; i++) {
        var token = tokens[i], element;
        if (token in binOp) {
            var secondArg = stack.pop();
            stack.push(new binOp[token](stack.pop(), secondArg));
        } else if (token in unOp) {
            stack.push(new unOp[token](stack.pop()));
        } else if (_vars.indexOf(token) !== -1) {
            stack.push(new Variable(token));
        } else {
            stack.push(new Const(parseInt(token)));
        }
    }

    return stack.pop();
}