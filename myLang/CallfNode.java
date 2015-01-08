package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class CallfNode extends Node {

    List<Node> nodes;
    Environment env;

    CallfNode(Environment env,LexicalUnit first) {
        this.env = env;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        boolean tp;

        tp = first.getType().equals(LexicalType.NAME);
        if (tp == true) {
            Node a = new CallfNode(env, first);
            return a;
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.NAME) {
            LexicalUnit unity2 = lex.get();
            if (unity2.type == LexicalType.LP) {
                LexicalUnit unity3 = lex.get();
                if (ExprListNode.isMatch(env, unity3) != null) {
                    lex.unget(unity3);
                    if (ExprListNode.isMatch(env, unity3).Parse()) {
                        LexicalUnit unity4 = lex.get();
                        if (unity4.type == LexicalType.RP) {
                            //lex.unget(unity4);
                            return true;
                        }
                        lex.unget(unity4);
                    }
                }
                lex.unget(unity3);
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        return false;
    }

}

/*

 */
