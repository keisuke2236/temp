package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class LeftVarNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    valueImpl leftvar;

    LeftVarNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;

    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        if (first != null) {
            if (first.getType() == LexicalType.NAME) {
                Node a = new LeftVarNode(env, first);
                return a;
            }
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();
        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.NAME) {
            leftvar = new valueImpl(unity1.getValue().getSValue());//変数にとって必要な情報とは名前
            return true;
        }
        lex.unget(unity1);
        return false;
    }

    @Override
    public valueImpl getValue() {
        //leftvarの値をリターンしてあげる
        return leftvar;
    }
}

/*

 */
