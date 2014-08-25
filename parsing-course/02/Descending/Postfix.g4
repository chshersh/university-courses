grammar Postfix;

postfix
  : NUM aux
  | EPS
  ;

aux
  : NUM aux sign aux
  | EPS
  ;

sign
  : SIGN
  ;

NUM   : '-'?[0-9]+ ;
SIGN  : '-' | '+' | '*' ;
WS    : [ \t\r\n]+ -> skip ;
EPS   : '' ;