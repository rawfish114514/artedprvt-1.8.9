package org.mozilla.javascript.resources;

public class MessagesString {
    public static String getMessagesString() {
        return "#\n" +
                "# Default JavaScript messages file.\n" +
                "#\n" +
                "# This Source Code Form is subject to the terms of the Mozilla Public\n" +
                "# License, v. 2.0. If a copy of the MPL was not distributed with this\n" +
                "# file, You can obtain one at http://mozilla.org/MPL/2.0/.\n" +
                "\n" +
                "# This is replaced during jar assembly from property string\n" +
                "# and should not be translated\n" +
                "implementation.version = @IMPLEMENTATION.VERSION@\n" +
                "\n" +
                "#\n" +
                "# To add JavaScript error messages for a particular locale, create a\n" +
                "# new Messages_[locale].properties file, where [locale] is the Java\n" +
                "# string abbreviation for that locale.  For example, JavaScript\n" +
                "# messages for the Polish locale should be located in\n" +
                "# Messages_pl.properties, and messages for the Italian Swiss locale\n" +
                "# should be located in Messages_it_CH.properties.  Message properties\n" +
                "# files should be accessible through the classpath under\n" +
                "# org.mozilla.javascript.resources\n" +
                "#\n" +
                "# See:\n" +
                "# java.util.ResourceBundle\n" +
                "# java.text.MessageFormat\n" +
                "#\n" +
                "\n" +
                "# SomeJavaClassWhereUsed\n" +
                "\n" +
                "params.omit.non.js.object.warning = true\n" +
                "\n" +
                "msg.non.js.object.warning =\\\n" +
                "    RHINO USAGE WARNING: Missed Context.javaToJS() conversion: Rhino runtime detected object \"{0}\" of class \"{1}\" where it expected String, Number, Boolean or Scriptable instance. Please check your code for missing Context.javaToJS() call.\n" +
                "\n" +
                "# Codegen\n" +
                "msg.dup.parms =\\\n" +
                "    Duplicate parameter name \"{0}\".\n" +
                "\n" +
                "msg.too.big.jump =\\\n" +
                "    Program too complex: too big jump offset.\n" +
                "\n" +
                "msg.too.big.index =\\\n" +
                "    Program too complex: internal index exceeds 64K limit.\n" +
                "\n" +
                "msg.while.compiling.fn =\\\n" +
                "    Encountered code generation error while compiling function \"{0}\": {1}\n" +
                "\n" +
                "msg.while.compiling.script =\\\n" +
                "    Encountered code generation error while compiling script: {0}\n" +
                "\n" +
                "# Context\n" +
                "msg.ctor.not.found =\\\n" +
                "    Constructor for \"{0}\" not found.\n" +
                "\n" +
                "msg.not.ctor =\\\n" +
                "    \"{0}\" is not a constructor.\n" +
                "\n" +
                "# FunctionObject\n" +
                "msg.varargs.ctor =\\\n" +
                "    Method or constructor \"{0}\" must be static with the signature \\\n" +
                "    \"(Context cx, Object[] args, Function ctorObj, boolean inNewExpr)\" \\\n" +
                "    to define a variable arguments constructor.\n" +
                "\n" +
                "msg.varargs.fun =\\\n" +
                "    Method \"{0}\" must be static with the signature \\\n" +
                "    \"(Context cx, Scriptable thisObj, Object[] args, Function funObj)\" \\\n" +
                "    to define a variable arguments function.\n" +
                "\n" +
                "msg.incompat.call =\\\n" +
                "    Method \"{0}\" called on incompatible object.\n" +
                "\n" +
                "msg.incompat.call.details =\\\n" +
                "    Method \"{0}\" called on incompatible object ({1} is not an instance of {2}).\n" +
                "\n" +
                "msg.bad.parms =\\\n" +
                "    Unsupported parameter type \"{0}\" in method \"{1}\".\n" +
                "\n" +
                "msg.bad.method.return =\\\n" +
                "    Unsupported return type \"{0}\" in method \"{1}\".\n" +
                "\n" +
                "msg.bad.ctor.return =\\\n" +
                "    Construction of objects of type \"{0}\" is not supported.\n" +
                "\n" +
                "msg.no.overload =\\\n" +
                "    Method \"{0}\" occurs multiple times in class \"{1}\".\n" +
                "\n" +
                "msg.method.not.found =\\\n" +
                "    Method \"{0}\" not found in \"{1}\".\n" +
                "\n" +
                "msg.method.missing.parameter =\\\n" +
                "    {0}: At least {1} arguments required, but only {2} passed\n" +
                "\n" +
                "# IRFactory\n" +
                "\n" +
                "msg.bad.for.in.lhs =\\\n" +
                "    Invalid left-hand side of for..in loop.\n" +
                "\n" +
                "msg.mult.index =\\\n" +
                "    Only one variable allowed in for..in loop.\n" +
                "\n" +
                "msg.bad.for.in.destruct =\\\n" +
                "    Left hand side of for..in loop must be an array of length 2 to accept \\\n" +
                "    key/value pair.\n" +
                "    \n" +
                "msg.cant.convert =\\\n" +
                "    Can''t convert to type \"{0}\".\n" +
                "\n" +
                "msg.bad.assign.left =\\\n" +
                "    Invalid assignment left-hand side.\n" +
                "\n" +
                "msg.bad.decr =\\\n" +
                "    Invalid decrement operand.\n" +
                "\n" +
                "msg.bad.incr =\\\n" +
                "    Invalid increment operand.\n" +
                "\n" +
                "msg.bad.yield =\\\n" +
                "    yield must be in a function.\n" +
                "\n" +
                "msg.yield.parenthesized =\\\n" +
                "    yield expression must be parenthesized.\n" +
                "\n" +
                "# NativeGlobal\n" +
                "msg.cant.call.indirect =\\\n" +
                "    Function \"{0}\" must be called directly, and not by way of a \\\n" +
                "    function of another name.\n" +
                "\n" +
                "msg.eval.nonstring =\\\n" +
                "    Calling eval() with anything other than a primitive string value will \\\n" +
                "    simply return the value. Is this what you intended?\n" +
                "\n" +
                "msg.eval.nonstring.strict =\\\n" +
                "    Calling eval() with anything other than a primitive string value is not \\\n" +
                "    allowed in strict mode.\n" +
                "\n" +
                "msg.bad.destruct.op =\\\n" +
                "    Invalid destructuring assignment operator\n" +
                "\n" +
                "# NativeCall\n" +
                "msg.only.from.new =\\\n" +
                "    \"Constructor {0}\" may only be invoked from a \"new\" expression.\n" +
                "\n" +
                "msg.deprec.ctor =\\\n" +
                "    The \"{0}\" constructor is deprecated.\n" +
                "\n" +
                "# NativeFunction\n" +
                "msg.no.function.ref.found =\\\n" +
                "    no source found to decompile function reference {0}\n" +
                "\n" +
                "msg.arg.isnt.array =\\\n" +
                "    second argument to Function.prototype.apply must be an array\n" +
                "\n" +
                "# NativeGlobal\n" +
                "msg.bad.esc.mask =\\\n" +
                "    invalid string escape mask\n" +
                "\n" +
                "# NativeJavaClass\n" +
                "msg.cant.instantiate =\\\n" +
                "    error instantiating ({0}): class {1} is interface or abstract\n" +
                "\n" +
                "msg.bad.ctor.sig =\\\n" +
                "    Found constructor with wrong signature: \\\n" +
                "    {0} calling {1} with signature {2}\n" +
                "\n" +
                "msg.not.java.obj =\\\n" +
                "    Expected argument to getClass() to be a Java object.\n" +
                "\n" +
                "msg.no.java.ctor =\\\n" +
                "    Java constructor for \"{0}\" with arguments \"{1}\" not found.\n" +
                "\n" +
                "# NativeJavaMethod\n" +
                "msg.method.ambiguous =\\\n" +
                "    The choice of Java method {0}.{1} matching JavaScript argument types ({2}) is ambiguous; \\\n" +
                "    candidate methods are: {3}\n" +
                "\n" +
                "msg.constructor.ambiguous =\\\n" +
                "    The choice of Java constructor {0} matching JavaScript argument types ({1}) is ambiguous; \\\n" +
                "    candidate constructors are: {2}\n" +
                "\n" +
                "# NativeJavaObject\n" +
                "msg.conversion.not.allowed =\\\n" +
                "    Cannot convert {0} to {1}\n" +
                "\n" +
                "msg.no.empty.interface.conversion =\\\n" +
                "    Cannot convert function to interface {0} with no methods\n" +
                "\n" +
                "msg.no.function.interface.conversion =\\\n" +
                "    Cannot convert function to interface {0} since it contains methods with \\\n" +
                "    different names\n" +
                "\n" +
                "msg.undefined.function.interface =\\\n" +
                "    Property \"{0}\" is not defined in interface adapter\n" +
                "\n" +
                "msg.not.function.interface =\\\n" +
                "    Property \"{0}\" is not a function in interface adapter\n" +
                "\n" +
                "# NativeJavaPackage\n" +
                "msg.not.classloader =\\\n" +
                "    Constructor for \"Packages\" expects argument of type java.lang.Classloader\n" +
                "\n" +
                "# NativeRegExp\n" +
                "msg.bad.quant =\\\n" +
                "    Invalid quantifier {0}\n" +
                "\n" +
                "msg.overlarge.backref =\\\n" +
                "    Overly large back reference {0}\n" +
                "\n" +
                "msg.overlarge.min =\\\n" +
                "    Overly large minimum {0}\n" +
                "\n" +
                "msg.overlarge.max =\\\n" +
                "    Overly large maximum {0}\n" +
                "\n" +
                "msg.zero.quant =\\\n" +
                "    Zero quantifier {0}\n" +
                "\n" +
                "msg.max.lt.min =\\\n" +
                "    Invalid regular expression: The quantifier maximum ''{0}'' is less than the minimum ''{1}''.\n" +
                "\n" +
                "msg.unterm.quant =\\\n" +
                "    Unterminated quantifier {0}\n" +
                "\n" +
                "msg.unterm.paren =\\\n" +
                "    Unterminated parenthetical {0}\n" +
                "\n" +
                "msg.unterm.class =\\\n" +
                "    Unterminated character class {0}\n" +
                "\n" +
                "msg.bad.range =\\\n" +
                "    Invalid range in character class.\n" +
                "\n" +
                "msg.trail.backslash =\\\n" +
                "    Trailing \\\\ in regular expression.\n" +
                "\n" +
                "msg.re.unmatched.right.paren =\\\n" +
                "    unmatched ) in regular expression.\n" +
                "\n" +
                "msg.no.regexp =\\\n" +
                "    Regular expressions are not available.\n" +
                "\n" +
                "msg.bad.backref =\\\n" +
                "    back-reference exceeds number of capturing parentheses.\n" +
                "\n" +
                "msg.bad.regexp.compile =\\\n" +
                "    Only one argument may be specified if the first argument to \\\n" +
                "    RegExp.prototype.compile is a RegExp object.\n" +
                "\n" +
                "msg.arg.not.object =\\\n" +
                "    Expected argument of type object, but instead had type {0}\n" +
                "\n" +
                "# NativeDate\n" +
                "msg.invalid.date =\\\n" +
                "    Date is invalid.\n" +
                "\n" +
                "msg.toisostring.must.return.primitive =\\\n" +
                "    toISOString must return a primitive value, but instead returned \"{0}\"\n" +
                "\n" +
                "# NativeJSON\n" +
                "msg.json.cant.serialize =\\\n" +
                "    Do not know how to serialize a {0}\n" +
                "\n" +
                "# Parser\n" +
                "msg.got.syntax.errors = \\\n" +
                "    Compilation produced {0} syntax errors.\n" +
                "\n" +
                "msg.var.redecl =\\\n" +
                "    TypeError: redeclaration of var {0}.\n" +
                "\n" +
                "msg.const.redecl =\\\n" +
                "    TypeError: redeclaration of const {0}.\n" +
                "    \n" +
                "msg.let.redecl =\\\n" +
                "    TypeError: redeclaration of variable {0}.\n" +
                "\n" +
                "msg.parm.redecl =\\\n" +
                "    TypeError: redeclaration of formal parameter {0}.\n" +
                "\n" +
                "msg.fn.redecl =\\\n" +
                "    TypeError: redeclaration of function {0}.\n" +
                "\n" +
                "msg.let.decl.not.in.block =\\\n" +
                "    SyntaxError: let declaration not directly within block\n" +
                "\n" +
                "msg.bad.object.init =\\\n" +
                "    SyntaxError: invalid object initializer\n" +
                "\n" +
                "# NodeTransformer\n" +
                "msg.dup.label =\\\n" +
                "    duplicated label\n" +
                "\n" +
                "msg.undef.label =\\\n" +
                "    undefined label\n" +
                "\n" +
                "msg.bad.break =\\\n" +
                "    unlabelled break must be inside loop or switch\n" +
                "\n" +
                "msg.continue.outside =\\\n" +
                "    continue must be inside loop\n" +
                "\n" +
                "msg.continue.nonloop =\\\n" +
                "    continue can only use labeles of iteration statements\n" +
                "\n" +
                "msg.bad.throw.eol =\\\n" +
                "    Line terminator is not allowed between the throw keyword and throw \\\n" +
                "    expression.\n" +
                "\n" +
                "msg.no.paren.parms =\\\n" +
                "    missing ( before function parameters.\n" +
                "\n" +
                "msg.no.parm =\\\n" +
                "    missing formal parameter\n" +
                "\n" +
                "msg.no.paren.after.parms =\\\n" +
                "    missing ) after formal parameters\n" +
                "\n" +
                "msg.no.brace.body =\\\n" +
                "    missing '{' before function body\n" +
                "\n" +
                "msg.no.brace.after.body =\\\n" +
                "    missing } after function body\n" +
                "\n" +
                "msg.no.paren.cond =\\\n" +
                "    missing ( before condition\n" +
                "\n" +
                "msg.no.paren.after.cond =\\\n" +
                "    missing ) after condition\n" +
                "\n" +
                "msg.no.semi.stmt =\\\n" +
                "    missing ; before statement\n" +
                "\n" +
                "msg.missing.semi =\\\n" +
                "    missing ; after statement\n" +
                "\n" +
                "msg.no.name.after.dot =\\\n" +
                "    missing name after . operator\n" +
                "\n" +
                "msg.no.name.after.coloncolon =\\\n" +
                "    missing name after :: operator\n" +
                "\n" +
                "msg.no.name.after.dotdot =\\\n" +
                "    missing name after .. operator\n" +
                "\n" +
                "msg.no.name.after.xmlAttr =\\\n" +
                "    missing name after .@\n" +
                "\n" +
                "msg.no.bracket.index =\\\n" +
                "    missing ] in index expression\n" +
                "\n" +
                "msg.no.paren.switch =\\\n" +
                "    missing ( before switch expression\n" +
                "\n" +
                "msg.no.paren.after.switch =\\\n" +
                "    missing ) after switch expression\n" +
                "\n" +
                "msg.no.brace.switch =\\\n" +
                "    missing '{' before switch body\n" +
                "\n" +
                "msg.bad.switch =\\\n" +
                "    invalid switch statement\n" +
                "\n" +
                "msg.no.colon.case =\\\n" +
                "    missing : after case expression\n" +
                "\n" +
                "msg.double.switch.default =\\\n" +
                "    double default label in the switch statement\n" +
                "\n" +
                "msg.no.while.do =\\\n" +
                "    missing while after do-loop body\n" +
                "\n" +
                "msg.no.paren.for =\\\n" +
                "    missing ( after for\n" +
                "\n" +
                "msg.no.semi.for =\\\n" +
                "    missing ; after for-loop initializer\n" +
                "\n" +
                "msg.no.semi.for.cond =\\\n" +
                "    missing ; after for-loop condition\n" +
                "    \n" +
                "msg.in.after.for.name =\\\n" +
                "    missing in after for\n" +
                "\n" +
                "msg.no.paren.for.ctrl =\\\n" +
                "    missing ) after for-loop control\n" +
                "\n" +
                "msg.no.paren.with =\\\n" +
                "    missing ( before with-statement object\n" +
                "\n" +
                "msg.no.paren.after.with =\\\n" +
                "    missing ) after with-statement object\n" +
                "    \n" +
                "msg.no.with.strict =\\\n" +
                "    with statements not allowed in strict mode\n" +
                "\n" +
                "msg.no.paren.after.let =\\\n" +
                "    missing ( after let\n" +
                "\n" +
                "msg.no.paren.let =\\\n" +
                "    missing ) after variable list\n" +
                "\n" +
                "msg.no.curly.let =\\\n" +
                "    missing } after let statement\n" +
                "\n" +
                "msg.bad.return =\\\n" +
                "    invalid return\n" +
                "\n" +
                "msg.no.brace.block =\\\n" +
                "    missing } in compound statement\n" +
                "\n" +
                "msg.bad.label =\\\n" +
                "    invalid label\n" +
                "\n" +
                "msg.bad.var =\\\n" +
                "    missing variable name\n" +
                "\n" +
                "msg.bad.var.init =\\\n" +
                "    invalid variable initialization\n" +
                "\n" +
                "msg.no.colon.cond =\\\n" +
                "    missing : in conditional expression\n" +
                "\n" +
                "msg.no.paren.arg =\\\n" +
                "    missing ) after argument list\n" +
                "\n" +
                "msg.no.bracket.arg =\\\n" +
                "    missing ] after element list\n" +
                "\n" +
                "msg.bad.prop =\\\n" +
                "    invalid property id\n" +
                "\n" +
                "msg.no.colon.prop =\\\n" +
                "    missing : after property id\n" +
                "\n" +
                "msg.no.brace.prop =\\\n" +
                "    missing } after property list\n" +
                "\n" +
                "msg.no.paren =\\\n" +
                "    missing ) in parenthetical\n" +
                "\n" +
                "msg.reserved.id =\\\n" +
                "    identifier is a reserved word: {0}\n" +
                "\n" +
                "msg.no.paren.catch =\\\n" +
                "    missing ( before catch-block condition\n" +
                "\n" +
                "msg.bad.catchcond =\\\n" +
                "    invalid catch block condition\n" +
                "\n" +
                "msg.catch.unreachable =\\\n" +
                "    any catch clauses following an unqualified catch are unreachable\n" +
                "\n" +
                "msg.no.brace.try =\\\n" +
                "    missing '{' before try block\n" +
                "\n" +
                "msg.no.brace.catchblock =\\\n" +
                "    missing '{' before catch-block body\n" +
                "\n" +
                "msg.try.no.catchfinally =\\\n" +
                "    ''try'' without ''catch'' or ''finally''\n" +
                "\n" +
                "msg.no.return.value =\\\n" +
                "  function {0} does not always return a value\n" +
                "\n" +
                "msg.anon.no.return.value =\\\n" +
                "  anonymous function does not always return a value\n" +
                "\n" +
                "msg.return.inconsistent =\\\n" +
                "  return statement is inconsistent with previous usage\n" +
                "\n" +
                "msg.generator.returns =\\\n" +
                "  TypeError: generator function {0} returns a value\n" +
                "\n" +
                "msg.anon.generator.returns =\\\n" +
                "  TypeError: anonymous generator function returns a value\n" +
                "\n" +
                "msg.syntax =\\\n" +
                "    syntax error\n" +
                "\n" +
                "msg.unexpected.eof =\\\n" +
                "    Unexpected end of file\n" +
                "\n" +
                "msg.XML.bad.form =\\\n" +
                "    illegally formed XML syntax\n" +
                "\n" +
                "msg.XML.not.available =\\\n" +
                "    XML runtime not available\n" +
                "\n" +
                "msg.too.deep.parser.recursion =\\\n" +
                "    Too deep recursion while parsing\n" +
                "\n" +
                "msg.too.many.constructor.args =\\\n" +
                "    Too many constructor arguments\n" +
                "\n" +
                "msg.too.many.function.args =\\\n" +
                "    Too many function arguments\n" +
                "\n" +
                "msg.no.side.effects =\\\n" +
                "    Code has no side effects\n" +
                "\n" +
                "msg.extra.trailing.semi =\\\n" +
                "    Extraneous trailing semicolon\n" +
                "\n" +
                "msg.extra.trailing.comma =\\\n" +
                "    Trailing comma is not legal in an ECMA-262 object initializer\n" +
                "\n" +
                "msg.trailing.array.comma =\\\n" +
                "    Trailing comma in array literal has different cross-browser behavior\n" +
                "\n" +
                "msg.equal.as.assign =\\\n" +
                "    Test for equality (==) mistyped as assignment (=)?\n" +
                "\n" +
                "msg.var.hides.arg =\\\n" +
                "    Variable {0} hides argument\n" +
                "\n" +
                "msg.destruct.assign.no.init =\\\n" +
                "    Missing = in destructuring declaration\n" +
                "\n" +
                "msg.destruct.default.vals =\\\n" +
                "    Default values in destructuring declarations are not supported\n" +
                "\n" +
                "msg.no.old.octal.strict =\\\n" +
                "    Old octal numbers prohibited in strict mode.\n" +
                "\n" +
                "msg.no.old.octal.bigint =\\\n" +
                "    Old octal numbers prohibited in BigInt.\n" +
                "\n" +
                "msg.dup.obj.lit.prop.strict =\\\n" +
                "    Property \"{0}\" already defined in this object literal.\n" +
                "\n" +
                "msg.dup.param.strict =\\\n" +
                "    Parameter \"{0}\" already declared in this function.\n" +
                "\n" +
                "msg.bad.id.strict =\\\n" +
                "    \"{0}\" is not a valid identifier for this use in strict mode.\n" +
                "\n" +
                "msg.no.unary.expr.on.left.exp =\\\n" +
                "    \"{0}\" is not allowed on the left-hand side of \"**\".\n" +
                "\n" +
                "# ScriptRuntime\n" +
                "\n" +
                "# is there a better message for this? \n" +
                "# it's currently only used as a poison pill for caller, caller and arguments properties\n" +
                "msg.op.not.allowed =\\\n" +
                "    This operation is not allowed.\n" +
                "\n" +
                "msg.no.properties =\\\n" +
                "    {0} has no properties.\n" +
                "\n" +
                "msg.invalid.iterator =\\\n" +
                "    Invalid iterator value\n" +
                "\n" +
                "msg.iterator.primitive =\\\n" +
                "    __iterator__ returned a primitive value\n" +
                "\n" +
                "msg.not.iterable = \\\n" +
                "    {0} is not iterable\n" +
                "\n" +
                "msg.invalid.for.each = \\\n" +
                "    invalid for each loop\n" +
                "\n" +
                "msg.assn.create.strict =\\\n" +
                "    Assignment to undeclared variable {0}\n" +
                "\n" +
                "msg.ref.undefined.prop =\\\n" +
                "    Reference to undefined property \"{0}\"\n" +
                "\n" +
                "msg.prop.not.found =\\\n" +
                "    Property {0} not found.\n" +
                "\n" +
                "msg.set.prop.no.setter =\\\n" +
                "    Cannot set property {0} that has only a getter to value ''{1}''.\n" +
                "\n" +
                "msg.invalid.type =\\\n" +
                "    Invalid JavaScript value of type {0}\n" +
                "\n" +
                "msg.primitive.expected =\\\n" +
                "    Primitive type expected (had {0} instead)\n" +
                "\n" +
                "msg.namespace.expected =\\\n" +
                "    Namespace object expected to left of :: (found {0} instead)\n" +
                "\n" +
                "msg.null.to.object =\\\n" +
                "    Cannot convert null to an object.\n" +
                "\n" +
                "msg.undef.to.object =\\\n" +
                "    Cannot convert undefined to an object.\n" +
                "\n" +
                "msg.cant.convert.to.bigint =\\\n" +
                "    Cannot convert {0} to an BigInt.\n" +
                "\n" +
                "msg.cant.convert.to.bigint.isnt.integer =\\\n" +
                "    Cannot convert {0} to an BigInt. It isn\\'t an integer.\n" +
                "\n" +
                "msg.bigint.bad.form =\\\n" +
                "    illegally formed BigInt syntax\n" +
                "\n" +
                "msg.cyclic.value =\\\n" +
                "    Cyclic {0} value not allowed.\n" +
                "\n" +
                "msg.is.not.defined =\"{0}\" is not defined.\n" +
                "\n" +
                "msg.undef.prop.read =\\\n" +
                "    Cannot read property \"{1}\" from {0}\n" +
                "\n" +
                "msg.undef.prop.write =\\\n" +
                "    Cannot set property \"{1}\" of {0} to \"{2}\"\n" +
                "\n" +
                "msg.undef.prop.delete =\\\n" +
                "    Cannot delete property \"{1}\" of {0}\n" +
                "\n" +
                "msg.undef.method.call =\\\n" +
                "    Cannot call method \"{1}\" of {0}\n" +
                "\n" +
                "msg.undef.with =\\\n" +
                "    Cannot apply \"with\" to {0}\n" +
                "\n" +
                "msg.isnt.function =\\\n" +
                "    {0} is not a function, it is {1}.\n" +
                "\n" +
                "msg.isnt.function.in =\\\n" +
                "    Cannot call property {0} in object {1}. It is not a function, it is \"{2}\".\n" +
                "\n" +
                "msg.function.not.found =\\\n" +
                "    Cannot find function {0}.\n" +
                "\n" +
                "msg.function.not.found.in =\\\n" +
                "    Cannot find function {0} in object {1}.\n" +
                "\n" +
                "msg.isnt.xml.object =\\\n" +
                "    {0} is not an xml object.\n" +
                "\n" +
                "msg.no.ref.to.get =\\\n" +
                "    {0} is not a reference to read reference value.\n" +
                "\n" +
                "msg.no.ref.to.set =\\\n" +
                "    {0} is not a reference to set reference value to {1}.\n" +
                "\n" +
                "msg.no.ref.from.function =\\\n" +
                "    Function {0} can not be used as the left-hand side of assignment \\\n" +
                "    or as an operand of ++ or -- operator.\n" +
                "\n" +
                "msg.bad.default.value =\\\n" +
                "    Object''s getDefaultValue() method returned an object.\n" +
                "\n" +
                "msg.instanceof.not.object = \\\n" +
                "    Can''t use ''instanceof'' on a non-object.\n" +
                "\n" +
                "msg.instanceof.bad.prototype = \\\n" +
                "    ''prototype'' property of {0} is not an object.\n" +
                "\n" +
                "msg.in.not.object = \\\n" +
                "    Can''t use ''in'' on a non-object.\n" +
                "\n" +
                "msg.bad.radix = \\\n" +
                "    illegal radix {0}.\n" +
                "\n" +
                "msg.division.zero = \\\n" +
                "    Division by zero.\n" +
                "\n" +
                "msg.bigint.negative.exponent = \\\n" +
                "    BigInt negative exponent.\n" +
                "\n" +
                "msg.bigint.out.of.range.arithmetic = \\\n" +
                "    BigInt is too large.\n" +
                "\n" +
                "# ScriptableObject\n" +
                "msg.default.value =\\\n" +
                "    Cannot find default value for object.\n" +
                "\n" +
                "msg.zero.arg.ctor =\\\n" +
                "    Cannot load class \"{0}\" which has no zero-parameter constructor.\n" +
                "\n" +
                "duplicate.defineClass.name =\\\n" +
                "    Invalid method \"{0}\": name \"{1}\" is already in use.\n" +
                "\n" +
                "msg.ctor.multiple.parms =\\\n" +
                "    Can''t define constructor or class {0} since more than one \\\n" +
                "    constructor has multiple parameters.\n" +
                "\n" +
                "msg.extend.scriptable =\\\n" +
                "    {0} must extend ScriptableObject in order to define property {1}.\n" +
                "\n" +
                "msg.bad.getter.parms =\\\n" +
                "    In order to define a property, getter {0} must have zero parameters \\\n" +
                "    or a single ScriptableObject parameter.\n" +
                "\n" +
                "msg.obj.getter.parms =\\\n" +
                "    Expected static or delegated getter {0} to take a ScriptableObject parameter.\n" +
                "\n" +
                "msg.getter.static =\\\n" +
                "    Getter and setter must both be static or neither be static.\n" +
                "\n" +
                "msg.setter.return =\\\n" +
                "    Setter must have void return type: {0}\n" +
                "\n" +
                "msg.setter2.parms =\\\n" +
                "    Two-parameter setter must take a ScriptableObject as its first parameter.\n" +
                "\n" +
                "msg.setter1.parms =\\\n" +
                "    Expected single parameter setter for {0}\n" +
                "\n" +
                "msg.setter2.expected =\\\n" +
                "    Expected static or delegated setter {0} to take two parameters.\n" +
                "\n" +
                "msg.setter.parms =\\\n" +
                "    Expected either one or two parameters for setter.\n" +
                "\n" +
                "msg.setter.bad.type =\\\n" +
                "    Unsupported parameter type \"{0}\" in setter \"{1}\".\n" +
                "\n" +
                "msg.add.sealed =\\\n" +
                "    Cannot add a property to a sealed object: {0}.\n" +
                "\n" +
                "msg.remove.sealed =\\\n" +
                "    Cannot remove a property from a sealed object: {0}.\n" +
                "\n" +
                "msg.modify.sealed =\\\n" +
                "    Cannot modify a property of a sealed object: {0}.\n" +
                "\n" +
                "msg.modify.readonly =\\\n" +
                "    Cannot modify readonly property: {0}.\n" +
                "\n" +
                "msg.both.data.and.accessor.desc =\\\n" +
                "    Cannot be both a data and an accessor descriptor.\n" +
                "\n" +
                "msg.change.configurable.false.to.true =\\\n" +
                "    Cannot change the configurable attribute of \"{0}\" from false to true.\n" +
                "\n" +
                "msg.change.enumerable.with.configurable.false =\\\n" +
                "    Cannot change the enumerable attribute of \"{0}\" because configurable is false.\n" +
                "\n" +
                "msg.change.writable.false.to.true.with.configurable.false =\\\n" +
                "    Cannot change the writable attribute of \"{0}\" from false to true because configurable is false.\n" +
                "\n" +
                "msg.change.value.with.writable.false =\\\n" +
                "    Cannot change the value of attribute \"{0}\" because writable is false.\n" +
                "\n" +
                "msg.change.getter.with.configurable.false =\\\n" +
                "    Cannot change the get attribute of \"{0}\" because configurable is false.\n" +
                "\n" +
                "msg.change.setter.with.configurable.false =\\\n" +
                "    Cannot change the set attribute of \"{0}\" because configurable is false.\n" +
                "\n" +
                "msg.change.property.data.to.accessor.with.configurable.false =\\\n" +
                "    Cannot change \"{0}\" from a data property to an accessor property because configurable is false.\n" +
                "\n" +
                "msg.change.property.accessor.to.data.with.configurable.false =\\\n" +
                "    Cannot change \"{0}\" from an accessor property to a data property because configurable is false.\n" +
                "\n" +
                "msg.not.extensible =\\\n" +
                "    Cannot add properties to this object because extensible is false.\n" +
                "\n" +
                "msg.delete.property.with.configurable.false =\\\n" +
                "    Cannot delete \"{0}\" property because configurable is false.\n" +
                "\n" +
                "# TokenStream\n" +
                "msg.missing.exponent =\\\n" +
                "    missing exponent\n" +
                "\n" +
                "msg.caught.nfe =\\\n" +
                "    number format error\n" +
                "\n" +
                "msg.unterminated.string.lit =\\\n" +
                "    unterminated string literal\n" +
                "\n" +
                "msg.unterminated.comment =\\\n" +
                "    unterminated comment\n" +
                "\n" +
                "msg.unterminated.re.lit =\\\n" +
                "    unterminated regular expression literal\n" +
                "\n" +
                "msg.invalid.re.flag =\\\n" +
                "    invalid flag ''{0}'' after regular expression\n" +
                "\n" +
                "msg.no.re.input.for =\\\n" +
                "    no input for {0}\n" +
                "\n" +
                "msg.illegal.character =\\\n" +
                "    illegal character: {0}\n" +
                "\n" +
                "msg.invalid.escape =\\\n" +
                "    invalid Unicode escape sequence\n" +
                "\n" +
                "msg.bad.namespace =\\\n" +
                "    not a valid default namespace statement. \\\n" +
                "    Syntax is: default xml namespace = EXPRESSION;\n" +
                "\n" +
                "# TokensStream warnings\n" +
                "msg.bad.octal.literal =\\\n" +
                "    illegal octal literal digit {0}; interpreting it as a decimal digit\n" +
                "\n" +
                "msg.reserved.keyword =\\\n" +
                "    illegal usage of future reserved keyword {0}; interpreting it as ordinary identifier\n" +
                "\n" +
                "# LiveConnect errors\n" +
                "msg.java.internal.field.type =\\\n" +
                "    Internal error: type conversion of {0} to assign to {1} on {2} failed.\n" +
                "\n" +
                "msg.java.conversion.implicit_method =\\\n" +
                "    Can''t find converter method \"{0}\" on class {1}.\n" +
                "\n" +
                "msg.java.method.assign =\\\n" +
                "    Java method \"{0}\" cannot be assigned to.\n" +
                "\n" +
                "msg.java.internal.private =\\\n" +
                "    Internal error: attempt to access private/protected field \"{0}\".\n" +
                "\n" +
                "msg.java.no_such_method =\\\n" +
                "    Can''t find method {0}.\n" +
                "\n" +
                "msg.script.is.not.constructor =\\\n" +
                "    Script objects are not constructors.\n" +
                "\n" +
                "msg.nonjava.method =\\\n" +
                "    Java method \"{0}\" was invoked with {1} as \"this\" value that can not be converted to Java type {2}.\n" +
                "\n" +
                "msg.java.member.not.found =\\\n" +
                "    Java class \"{0}\" has no public instance field or method named \"{1}\".\n" +
                "\n" +
                "msg.java.array.index.out.of.bounds =\\\n" +
                "    Array index {0} is out of bounds [0..{1}].\n" +
                "\n" +
                "msg.java.array.member.not.found =\\\n" +
                "    Java arrays have no public instance fields or methods named \"{0}\".\n" +
                "\n" +
                "msg.pkg.int =\\\n" +
                "    Java package names may not be numbers.\n" +
                "\n" +
                "msg.access.prohibited =\\\n" +
                "    Access to Java class \"{0}\" is prohibited.\n" +
                "\n" +
                "# ImporterTopLevel\n" +
                "msg.ambig.import =\\\n" +
                "    Ambiguous import: \"{0}\" and and \"{1}\".\n" +
                "\n" +
                "msg.not.pkg =\\\n" +
                "    Function importPackage must be called with a package; had \"{0}\" instead.\n" +
                "\n" +
                "msg.not.class =\\\n" +
                "    Function importClass must be called with a class; had \"{0}\" instead.\n" +
                "\n" +
                "msg.not.class.not.pkg =\\\n" +
                "    \"{0}\" is neither a class nor a package.\n" +
                "\n" +
                "msg.prop.defined =\\\n" +
                "    Cannot import \"{0}\" since a property by that name is already defined.\n" +
                "\n" +
                "#JavaAdapter\n" +
                "msg.adapter.zero.args =\\\n" +
                "    JavaAdapter requires at least one argument.\n" +
                "\n" +
                "msg.not.java.class.arg = \\\n" +
                "Argument {0} is not Java class: {1}.\n" +
                "\n" +
                "#JavaAdapter\n" +
                "msg.only.one.super = \\\n" +
                "Only one class may be extended by a JavaAdapter. Had {0} and {1}.\n" +
                "\n" +
                "\n" +
                "# Arrays\n" +
                "msg.arraylength.bad =\\\n" +
                "    Inappropriate array length.\n" +
                "\n" +
                "# Arrays\n" +
                "msg.arraylength.too.big =\\\n" +
                "    Array length {0} exceeds supported capacity limit.\n" +
                "\n" +
                "msg.empty.array.reduce =\\\n" +
                "    Reduce of empty array with no initial value\n" +
                "\n" +
                "# URI\n" +
                "msg.bad.uri =\\\n" +
                "    Malformed URI sequence.\n" +
                "\n" +
                "# Number\n" +
                "msg.bad.precision =\\\n" +
                "    Precision {0} out of range.\n" +
                "\n" +
                "# NativeGenerator\n" +
                "msg.send.newborn =\\\n" +
                "  Attempt to send value to newborn generator\n" +
                "\n" +
                "msg.already.exec.gen =\\\n" +
                "    Already executing generator\n" +
                "    \n" +
                "msg.StopIteration.invalid =\\\n" +
                "    StopIteration may not be changed to an arbitrary object.\n" +
                "\n" +
                "msg.generator.executing =\\\n" +
                "    The generator is still executing from a previous invocation.\n" +
                "\n" +
                "# Interpreter\n" +
                "msg.yield.closing =\\\n" +
                "  Yield from closing generator\n" +
                "\n" +
                "msg.called.null.or.undefined=\\\n" +
                "  {0}.prototype.{1} method called on null or undefined\n" +
                "\n" +
                "msg.first.arg.not.regexp=\\\n" +
                "  First argument to {0}.prototype.{1} must not be a regular expression\n" +
                "\n" +
                "msg.arrowfunction.generator =\\\n" +
                "  arrow function can not become generator\n" +
                "\n" +
                "# Arguments\n" +
                "msg.arguments.not.access.strict =\\\n" +
                "  Cannot access \"{0}\" property of the arguments object in strict mode.\n" +
                "\n" +
                "msg.object.cyclic.prototype =\\\n" +
                "  Cyclic prototype \"{0}\" value not allowed.\n" +
                "\n" +
                "# Symbol support\n" +
                "msg.object.not.symbolscriptable =\\\n" +
                "  Object {0} does not support Symbol keys\n" +
                "\n" +
                "msg.no.assign.symbol.strict =\\\n" +
                "  Symbol objects may not be assigned properties in strict mode\n" +
                "\n" +
                "msg.not.a.string =\\\n" +
                "  The object is not a string\n" +
                "\n" +
                "msg.not.a.number =\\\n" +
                "  The object is not a number\n" +
                "\n" +
                "msg.cant.convert.to.number =\\\n" +
                "  Cannot convert {0} to a number\n" +
                "\n" +
                "msg.no.symbol.new =\\\n" +
                "  Symbol objects may not be constructed using \\\"new\\\"\n" +
                "\n" +
                "msg.compare.symbol =\\\n" +
                "  Symbol objects may not be compared\n" +
                "\n" +
                "msg.no.new =\\\n" +
                "  {0} objects may not be constructed using \\\"new\\\"\n" +
                "\n" +
                "msg.map.function.not =\\\n" +
                "  Map function is not actually a function\n" +
                "\n" +
                "msg.constructor.no.function \\=\n" +
                "  The constructor for {0} may not be invoked as a function\n" +
                "\n" +
                "msg.this.not.instance \\= \\\"this\\\" is not an instance of the class\n" +
                "\n" +
                "# Promises\n" +
                "msg.function.expected =\\\n" +
                "  Expecting the first argument to be a function\n" +
                "\n" +
                "msg.constructor.expected =\\\n" +
                "  Expecting the first argument to be a constructor\n" +
                "\n" +
                "msg.promise.self.resolution =\\\n" +
                "  Promise is being self-resolved\n" +
                "\n" +
                "msg.promise.capability.state \\=\n" +
                "Invalid promise capability state\n" +
                "\n" +
                "msg.promise.all.toobig \\=\n" +
                "  Too many inputs to Promise.all";
    }
}
