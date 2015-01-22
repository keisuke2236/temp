package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class CondNode extends Node {
    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    Node left, right;
    LexicalType fugo;

    CondNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。

        Node exprnode = ExprNode.isMatch(env, first);
        if (exprnode != null) {
            Node a = new CondNode(env, first);
            return a;
        }
        return null;
    }

    @Override
    public boolean Parse() {

        LexicalAnalyzer lex = env.getInput();
        LexicalUnit unity1 = lex.get();
        left = ExprNode.isMatch(env, unity1);
        if (left != null) {
            lex.unget(unity1);
            if (left.Parse()) {
                LexicalUnit unity2 = lex.get();
                if (unity2.type == LexicalType.GT || unity2.type == LexicalType.LT || unity2.type == LexicalType.GE || unity2.type == LexicalType.LE || unity2.type == LexicalType.EQ || unity2.type == LexicalType.NE) {
                    fugo = unity2.type;
                    LexicalUnit unity3 = lex.get();
                    right = ExprNode.isMatch(env, unity3);
                    if (right != null) {
                        lex.unget(unity3);
                        if (right.Parse()) {
                            
                            //lex.unget(unity3);
                            return true;
                        }
                    }
                    lex.unget(unity3);
                }
                lex.unget(unity2);
            }
            lex.unget(unity1);
        }
        return false;
    }

    public Value getValue() {//thenかelseを返すだけのbooleanValue
        Value returnvalue = new valueImpl(true);//デフォルトは通るように設定しておけばまあなんとかなるでしょw
        switch (fugo) {
            case LE:
                if (left.getValue().getIValue() <= right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new valueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new valueImpl(false);
                }
            case GE:
                if (left.getValue().getIValue() >= right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new valueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new valueImpl(false);
                }
            case LT:
                if (left.getValue().getIValue() < right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new valueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new valueImpl(false);
                }
            case GT:
                if (left.getValue().getIValue() > right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new valueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new valueImpl(false);
                }
        }

        return returnvalue;
    }

}

/*
 getValue
 必要な要素：A 何か B  
 A<Bなど

 ここのもしAが式だった場合
 3>4 == 5<2
 3>4という値を持ったcondのノードを保存しておく必要がある


 return new ValueImpl(true);




 */
