package newlang3;
//字句のタイプ一覧表
public enum LexicalType {
	LITERAL,	// ""で囲まれるj
	INTVAL,		// 整数
	DOUBLEVAL,	// 実数
	NAME,		// 変数
	IF,			// IF
	THEN,	
        PRINT,
	ELSE,		// ELSE
	ELSEIF,		// ELSEIF
	ENDIF,		// ENDIF
	FOR,		// FOR
	FORALL,		// FORALL
	NEXT	,	// NEXT
	EQ,			// =
	LT,			// <
	GT,			// >
	LE,			// <=
	GE,			// >=
	NE,			// <>
	FUNC,		// SUB
	DIM,		// DIM
	AS,			// AS
        HANA,
        PRINTER,
	END,		// END
	NL,			// ���s
	DOT,		// .
	WHILE,		// WHILE
	DO,			// DO
	UNTIL,		// UNTIL
	ADD,		// +
	SUB,		// -
	MUL,		// *
	DIV,		// /
	LP,			// )
	RP,			// (
	COMMA,		// ,
	LOOP,		// LOOP
	TO,			// TO
	WEND,		// WEND
	EOF,		// end of file
}
