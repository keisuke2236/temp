package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class VarListNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;

    VarListNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        if (VarNode.isMatch(env, first) != null) {
            Node a = new VarListNode(env, first);
            return a;
        }
        if (VarListNode.isMatch(env, first) != null) {
            Node a = new VarListNode(env, first);
            return a;
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (VarNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (VarNode.isMatch(env, unity1).Parse()) {
                //lex.unget(unity1);
                return true;
            }
        }
        if (VarListNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (VarListNode.isMatch(env, unity1).Parse()) {
                LexicalUnit unity2 = lex.get();
                if (unity2.type == LexicalType.COMMA) {
                    LexicalUnit unity3 = lex.get();
                    if (VarNode.isMatch(env, unity3) != null) {
                        lex.unget(unity3);
                        if (VarNode.isMatch(env, unity3).Parse()) {
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
        return true;
    }

}

/*

 */
