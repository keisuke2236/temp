package newlang3;

//これはstmtNode BlockNodeの親です。
import java.util.ArrayList;
import java.util.List;

public class IfpNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalType unit1, unit2, unit3, unit4, unit5, unit6;
    LexicalUnit first;
    CondNode condnode;

    IfpNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;

    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        LexicalType tp;
        if (first != null) {
            tp = first.type;
            if (tp == LexicalType.IF) {
                if (tp != null) {
                    Node a = new IfpNode(env, first);
                    return a;
                }
            }
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();
        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.IF) {
            unit1 = unity1.type;
            LexicalUnit unity2 = lex.get();
            condnode = new CondNode(env, unity2);
            lex.unget(unity2);
            if (condnode.Parse()) {
                unit2 = unity2.type;
                LexicalUnit unity3 = lex.get();
                if (unity3.type == LexicalType.THEN) {
                    unit3 = unity3.type;
                    return true;
                }
                lex.unget(unity3);
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        return false;
    }

    @Override
    public Value getValue() {
        Value returnValue;
        returnValue = condnode.getValue();//条件式を実行boolean型でthen or elseをもらう。
        return returnValue;
    }

}


/*

 */
