package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class ElseIfBlockNode extends Node {

    List<Node> nodes;
    Environment env;

    ElseIfBlockNode(Environment env, LexicalUnit first) {
        this.env = env;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        if (ElseIfBlockNode.isMatch(env, first) != null) {
            Node a = new ElseIfBlockNode(env, first);
        }
        return null;

    }

    @Override
    public boolean Parse() {
        nodes = new ArrayList<Node>();
        LexicalAnalyzer lex = env.getInput();

        LexicalUnit unity1 = lex.get();
        if (ElseIfBlockNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (ElseIfBlockNode.isMatch(env, unity1).Parse()) {
                LexicalUnit unity2 = lex.get();
                if (unity2.type == LexicalType.ELSEIF) {
                    LexicalUnit unity3 = lex.get();
                    if (CondNode.isMatch(env, unity3) != null) {
                        lex.unget(unity3);
                        if (CondNode.isMatch(env, unity3).Parse()) {
                            LexicalUnit unity4 = lex.get();
                            if (unity4.type == LexicalType.THEN) {
                                LexicalUnit unity5 = lex.get();
                                if (unity5.type == LexicalType.NL) {
                                    LexicalUnit unity6 = lex.get();
                                    if (StmtListNode.isMatch(env, unity6) != null) {
                                        lex.unget(unity6);
                                        if (StmtListNode.isMatch(env, unity6).Parse()) {
                                            //lex.unget(unity6);
                                            return true;
                                        }
                                    }
                                    lex.unget(unity6);
                                }
                                lex.unget(unity5);
                            }
                            lex.unget(unity4);
                        }
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

 */
