package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class CallsNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;

    CallsNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        boolean tp;
        if (first != null) {
            tp = first.getType().equals(LexicalType.NAME);
            if (tp == false) {
                return null;
            }
        }

        Node a = new CallsNode(env, first);
        return a;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.NAME) {
            LexicalUnit unity2 = lex.get();
            if (ExprListNode.isMatch(env, unity2) != null) {
                lex.unget(unity2);
                if (ExprListNode.isMatch(env, unity2).Parse()) {
                    //lex.unget(unity2);
                    return true;
                }
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        return false;
    }
}

/*
 <call_sub> ::=
 <NAME> <expr_list>
 */
