grammar Zools;

@parser::members {
  private String fileName;

  public ZoolsParser(String fileName) throws java.io.IOException {
    super(new CommonTokenStream(new ZoolsLexer(new ANTLRFileStream(fileName))));
    fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }
}


TOKEN_NEWLINE: [\n\r]+ -> skip;
TOKEN_SPACE: [\t ]+  -> skip;
TOKEN_PRIMITIVE: 'primitive';
TOKEN_STRUCT: 'struct';
TOKEN_IDENTIFIER: [a-zA-Z]+[a-zA-Z0-9_]*;
TOKEN_SEMICOLUMN: ';';
TOKEN_COLUMN: ':';
TOKEN_ASSIGN: '=';
TOKEN_COMMA: ',';
TOKEN_LCURRY: '{';
TOKEN_RCURRY: '}';
TOKEN_LPARAN: '(';
TOKEN_RPARAN: ')';
TOKEN_LBRACKET: '[';
TOKEN_RBRACKET: ']';
TOKEN_BACK_SLASH: '/' -> more;
TOKEN_AT: '@';
TOKEN_DOT: '.';
ESCAPED_BACKSLASH: '\\/';
TOKEN_REGEX: '/' ( ESCAPED_BACKSLASH | ~('\n'|'\r') )*? '/';
TOKEN_COMMENT: '//' (~('\n'|'\r')+) TOKEN_NEWLINE -> skip;
TOKEN_QUESTIONMARK: '?';


file: primitive+ struct_def+ mapping_rule+ EOF;
primitive: TOKEN_PRIMITIVE TOKEN_IDENTIFIER TOKEN_ASSIGN TOKEN_REGEX TOKEN_SEMICOLUMN;
struct_def: TOKEN_STRUCT TOKEN_IDENTIFIER TOKEN_LCURRY properties TOKEN_RCURRY;
properties: property (TOKEN_COMMA property)*;
property: TOKEN_IDENTIFIER TOKEN_QUESTIONMARK? TOKEN_COLUMN TOKEN_IDENTIFIER;
mapping_rule: TOKEN_IDENTIFIER TOKEN_COLUMN target_type;
target_type: list_type
           | name_and_mappings;
name_and_mappings: TOKEN_IDENTIFIER (TOKEN_LPARAN mappings TOKEN_RPARAN)?;
list_type: TOKEN_LBRACKET target_type TOKEN_RBRACKET;
mappings: mapping (TOKEN_COMMA mapping)*;
mapping: TOKEN_IDENTIFIER TOKEN_ASSIGN property_selector;
property_selector: TOKEN_IDENTIFIER cast_selector?;
cast_selector: TOKEN_AT TOKEN_IDENTIFIER (TOKEN_DOT TOKEN_IDENTIFIER cast_selector?)? ;