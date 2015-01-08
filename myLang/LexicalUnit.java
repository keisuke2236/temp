package newlang3;


public class LexicalUnit {
	LexicalType type;
	valueImpl value;//implに書き直したら実行された
	LexicalUnit link;

	public LexicalUnit(LexicalType this_type) {
		type = this_type;
	}

	public LexicalUnit(LexicalType this_type, valueImpl this_value) {
            //タイプは今までどおり送信されて値が入っているのだが Value this_valueがめんどくさくて引数にnew new valueImpl(val)などを入れないといけない
            //最終的な呼び出し方は unit.getvalue.getSvalue()とかｗ svalueはimplで値をnewして作ったため、unit.getvalueを用いてこのLexicalunitへアクセスした後、その下に存在する.valueimplへ再アクセスし、なおかつその中に存在している関数のsgetvalueを用いるためにこうなる
		type = this_type;
		value = this_value;
	}

	public valueImpl getValue() {
		return value;
	}

	public LexicalType getType() {
		return type;
	}

	public String toString() {
		switch(type) {
			case LITERAL:
			return value.getSValue();
			case NAME:
			return  value.getSValue();
			case DOUBLEVAL:
			return "" + value.getDValue();
			case INTVAL:
			return "" + value.getIValue();
			case IF:
			return ("IF");
			case THEN:
			return ("THEN");
			case ELSE:
			return ("ELSE");
			case FOR:
			return ("FOR");
			case FORALL:
			return ("FORALL");
			case NEXT:
			return ("NEXT");
			case SUB:
			return ("SUB");
			case DIM:
			return ("DIM");
			case AS:
			return ("AS");
			case END:
			return ("END");
			case EOF:
			return ("EOF");
			case NL:
			return ("NL");
			case EQ:
			return ("EQ");
			case LT:
			return ("LT");
			case GT:
			return ("GT");
			case LE:
			return ("LE");
			case GE:
			return ("GE");
			case DOT:
			return ("DOT");
			case WHILE:
			return ("WHILE");
			case UNTIL:
			return ("UNTIL");
			case ADD:
			return ("ADD");
			case MUL:
			return ("MUL");
			case DIV:
			return ("DIV");
			case LP:
			return ("LP");
			case RP:
			return ("RP");
			case COMMA:
			return ("COMMA");
			case LOOP:
			return ("LOOP");
			case TO:
			return ("TO");
			case WEND:
			return ("WEND");
			case ELSEIF:
			return ("ELSEIF");
			case NE:
			return ("NE");
			case ENDIF:
			return ("ENDIF");
		}
		return "";
	}
}
