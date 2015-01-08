package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class ElseBlockNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;

    ElseBlockNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        if (ElseIfBlockNode.isMatch(env, first) != null) {
            Node a = new ElseBlockNode(env, first);
            return a;
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (ElseIfBlockNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (ElseIfBlockNode.isMatch(env, unity1).Parse()) {
                LexicalUnit unity2 = lex.get();
                if (unity2.type == LexicalType.ELSE) {
                    LexicalUnit unity3 = lex.get();
                    if (unity3.type == LexicalType.NL) {
                        LexicalUnit unity4 = lex.get();
                        if (StmtListNode.isMatch(env, unity4) != null) {
                            lex.unget(unity4);
                            if (StmtListNode.isMatch(env, unity4).Parse()) {

                                return true;
                            }
                        }
                        lex.unget(unity4);
                    }
                    lex.unget(unity3);
                }
                lex.unget(unity2);
            }
        }
        lex.unget(unity1);

        return false;
    }
}

/*
 <call_sub> ::=
 <NAME> <expr_list>
 */
