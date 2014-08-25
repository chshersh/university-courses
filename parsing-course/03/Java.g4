grammar Java;

/** Grammar for part of Java programming language;
 * no enum, inner classes, complex generic types,
 * no exceptions, no constructors, no comments, without complex arithm, no 'new' and so on
 */
javaLang
  : 'public' 'class' NAME '{' classBody EOF
  ;

classBody
  : globalName definition
  | '}'
  ;

globalName
  : modifier* varType NAME
  ;

// factorizing classBody rules
definition
  : varDefinition classBody
  | funDefinition classBody
  ;

varDefinition
locals [boolean longRule = true]
  : '=' value valueContinuation
  | ';' {$longRule = false;}
  ;

valueContinuation
 : ';'
 | binOperation value ';'
 ;

funDefinition
  : '(' funArgs '{' funBody // close brace in funArgs
  ;

funArgs
  : varType NAME funArgsContinuation[$NAME.text]
  | funArgsContinuation[""]
  ;

funArgsContinuation[String name]
locals [boolean endArgs = false]
  : ',' funArgs
  | ')' {$endArgs = true;}
  ;

funBody
  : statement funBody
  | expression funBody
  | returnCase? '}'
  ;

statement
  : varType? NAME varDefinition
  ;

expression
  : whileExpression
  | ifExpression
  ;

whileExpression
  : whileOperator '(' bool ')' '{' funBody
  ;

ifExpression
  : ifOperator '(' bool ')' '{' funBody elseExpression
  ;

elseExpression
locals [boolean sameLine = false]
  : elseOperator elseContinuation {$sameLine = true;}
  | EPS
  ;

elseContinuation
locals [boolean noIf = false]
  : '{' funBody  {$noIf = true;}
  | ifExpression
  ;

returnCase
  : retOperator value? ';'
  ;

value : bool | NUMBER | STRING | CHAR | '-'?NAME ;
bool : 'true' | 'false' ;
varType : primitiveType ('[' ']')* | 'void' ;
primitiveType : 'int' | 'long' | 'boolean' | 'double' | 'char' ;
modifier : 'public' | 'private' | 'protected' | 'static' | 'final' ;

whileOperator : 'while' ;
ifOperator : 'if' ;
elseOperator : 'else' ;
retOperator : 'return' ;

binOperation : '+' | '-' | '*' | '/' | '%' | '&' | '|' | '^' | ;
/** General keywords */
EPS : '' ;
NUMBER : '0' | ('-'? ('1'..'9') DIGIT*) | '-'? DIGIT '.'? DIGIT+ ;
CHAR   : '\'' .*? '\'' ;
STRING : '"' .*? '"' ;

NAME : LETTER (LETTER | DIGIT)* ;
fragment LETTER : [a-zA-Z_] ;
fragment DIGIT  : [0-9] ;

WS : [ \n\t\r]+ -> channel(HIDDEN);
IDENTIFIER : [a-zA-Z]+; // to ignore whitespaces