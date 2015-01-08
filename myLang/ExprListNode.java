package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class ExprListNode extends Node {

    List<Node> nodes;
    Environment env;

    ExprListNode(Environment env, LexicalUnit first) {
        this.env = env;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        Node exprnode = ExprNode.isMatch(env, first);
        if (exprnode != null) {
            Node a = new ExprListNode(env, first);
            return a;
        }
        if (exprnode != null) {
            Node a = new ExprListNode(env, first);
            return a;
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (ExprNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (ExprNode.isMatch(env, unity1).Parse()) {
                return true;
            }
        }
        lex.unget(unity1);

        //パターン2
        unity1 = lex.get();
        if (ExprNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (ExprListNode.isMatch(env, unity1).Parse()) {
                LexicalUnit unity2 = lex.get();
                if (unity2.type == LexicalType.COMMA) {
                    LexicalUnit unity3 = lex.get();
                    if (ExprNode.isMatch(env, unity3) != null) {
                        lex.unget(unity3);
                        if (ExprNode.isMatch(env, unity3).Parse()) {
                            //lex.unget(unity3);
                            return true;
                        }
                    }
                    lex.unget(unity3);
                }
                lex.unget(unity2);
            }
        }
        lex.unget(unity1);

        /*
         <expr_list>  ::=
         <expr>
         | <expr_list> <COMMA> <expr>
         */
        return false;
    }

}

/*

 */
