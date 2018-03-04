var _vars = ['x', 'y', 'z'];
function Variable(name) {
    this._index = _vars.indexOf(name);
}
Variable.prototype.evaluate = function () { return arguments[this._index]; };
Variable.prototype.toString = function () { return _vars[this._index]; };
Variable.prototype.prefix = function () { return _vars[this._index]; };

function Const(value) {
    this._value = value;
}
Const.prototype.evaluate = function () { return this._value; };
Const.prototype.toString = function () { return this._value.toString(); };
Const.prototype.prefix = function () { return this._value.toString(); };

var binOp = [], unOp = [];
function Operation(func, symbol) {
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
    Constructor.prototype.prefix = function () {
        return "(" + symbol + " " + this._args.map(function (a) { return a.prefix(); }).join(" ") + ")";
    };
    return Constructor;
}
var Add = Operation(function (a, b) { return a + b; }, "+");
var Subtract = Operation(function (a, b) { return a - b; }, "-");
var Multiply = Operation(function (a, b) { return a * b; }, "*");
var Divide = Operation(function (a, b) { return a / b; }, "/");
var Power = Operation(function (a, b) { return Math.pow(a, b); }, "pow");
var Log = Operation(function (a, b) { return Math.log(Math.abs(b)) / Math.log(Math.abs(a)); }, "log");
var Negate = Operation(function (a) { return -a; }, "negate");
var Sin = Operation(Math.sin, "sin");
var Cos = Operation(Math.cos, "cos");

function ParsePrefixError(message) {
    this.name = "ParsePrefixError";
    this.message = message;
}
ParsePrefixError.prototype = Error.prototype;

function parsePrefix(expression) {

    function isBracket(a) {
        return a === '(' || a === ')';
    }

    function isNumber(a) {
        var digits = ['0','1','2','3','4','5','6','7','8','9'];
        var i = 0;
        while (i < a.length && (digits.indexOf(a[i]) !== -1 || (i === 0 && a[i] === '-'))) {
            i++;
        }
        return i === a.length;
    }

    function checkEmpty() {
        var str = expression.trim();
        if (str.length === 0) {
            throw new ParsePrefixError("empty input");
        }
    }

    function getTokens() {
        var i = 0;
        while (i < expression.length) {
            if (expression[i] !== ' ') {
                if (isBracket(expression[i])) {
                    tokens.push({
                        name: expression[i],
                        index: i + 1
                    });
                } else {
                    var token_name = "";
                    var token_index = i + 1;
                    while (i < expression.length && !isBracket(expression[i]) && expression[i] !== ' ') {
                        token_name += expression[i];
                        i++;
                    }
                    tokens.push({
                        name: token_name,
                        index: token_index
                    });
                    i--;
                }
            }
            i++;
        }
    }

    function parse(prevRecurToken) {
        var prevToken = tokensIndex > 0 ? tokens[tokensIndex - 1] : { name: undefined, index: undefined };
        var curToken = tokens[tokensIndex++];
        if (curToken.name === '(') {
            var result = parse(curToken);
            if (tokensIndex === tokens.length || tokens[tokensIndex].name !== ')') {
                throw new ParsePrefixError("expected \")\" after \"" + tokens[tokensIndex - 1].name
                    + "\" at index " + tokens[tokensIndex - 1].index);
            }
            tokensIndex++;
            return result;
        }
        if (curToken.name === ')') {
            if (prevToken.name === '(') {
                throw new ParsePrefixError("empty brackets at index " + prevToken.index);
            }
            throw new ParsePrefixError("too few arguments for \"" + prevRecurToken.name
                + "\" at index " + prevRecurToken.index);
        }
        if (curToken.name in binOp) {
            if (prevToken.name !== '(') {
                throw new ParsePrefixError("missing \"(\" before " + curToken.name + " at index " + curToken.index);
            }
            return new binOp[curToken.name](parse(curToken), parse(curToken));
        } else if (curToken.name in unOp) {
            if (prevToken.name !== '(') {
                throw new ParsePrefixError("missing \"(\" before " + curToken.name + " at index " + curToken.index);
            }
            return new unOp[curToken.name](parse(curToken));
        } else if (_vars.indexOf(curToken.name) !== -1) {
            if (prevToken.name === '(') {
                throw new ParsePrefixError("\"" + curToken.name + "\" at index " + curToken.index + " can not be operation");
            }
            return new Variable(curToken.name);
        } else if (isNumber(curToken.name)) {
            if (prevToken.name === '(') {
                throw new ParsePrefixError("\"" + curToken.name + "\" at index " + curToken.index + " can not be operation");
            }
            return new Const(parseInt(curToken.name));
        } else {
            throw new ParsePrefixError("unknown token \"" + curToken.name + "\" at index " + curToken.index);
        }
    }

    checkEmpty();
    var tokens = [];
    getTokens();
    var tokensIndex = 0;
    var result = parse();
    if (tokensIndex !== tokens.length) {
        throw new ParsePrefixError("execessive info \"" + tokens[tokensIndex].name + "\" at index " +
            tokens[tokensIndex].index);
    }

    return result;
}