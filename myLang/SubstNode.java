package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class SubstNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    LeftVarNode left;//変数名はいってる下のノードあとでvalueをgetするときに必要
    Node right;

    SubstNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
        int right;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        LeftVarNode left = new LeftVarNode(env, first);
        if (left.isMatch(env, first) != null) {
            Node a = new SubstNode(env, first);
            return a;
        }
        return null;
    }

    @Override
    public boolean Parse() {

        LexicalAnalyzer lex = env.getInput();
        //パターン１
        LexicalUnit unity1 = lex.get();
        left = new LeftVarNode(env, unity1);
        lex.unget(unity1);
        if (left.Parse()) {
            LexicalUnit unity2 = lex.get();
            if (unity2.type == LexicalType.EQ) {
                LexicalUnit unity3 = lex.get();
                Node right = ExprNode.isMatch(env, unity3);
                if (right != null) {
                    lex.unget(unity3);
                    if (right.Parse()) {
                        this.right = right;
                        return true;
                    }
                }
                lex.unget(unity3);
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        /*
         <subst> ::=      代入文　　　変数　＝　式　ということを示している。 expr　式：これは厄介下を参照しよう
         <leftvar> <EQ> <expr>
         */
        return false;
    }

    @Override
    public valueImpl getValue() {//左の変数に右の値を割り当てるそれだけ
        Value uke = left.getValue();
        Value seme = right.getValue();
        System.out.println(uke.getSValue() + "←" + seme.getIValue());
        env.var_table.put(uke.getSValue(), seme);
        
        //System.out.println("leoの変数の値は：" + env.var_table.get("leo"));

        return null;
    }
}

/*

 */
