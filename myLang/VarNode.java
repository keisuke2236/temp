package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class VarNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    LexicalUnit hensu;
    Value returnvalue;

    VarNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        boolean tp;
        

        tp = first.getType().equals(LexicalType.NAME);
        if (tp == false) {
            return null;
        }
        Node a = new VarNode(env, first);
        return a;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.NAME) {
            hensu = unity1;
                   
            
            return true;
        }
        lex.unget(unity1);

        return false;
    }

    
    @Override
    public Value getValue(){
        System.out.print(hensu);
        Value a = (Value)env.var_table.get(new valueImpl(hensu.getValue().getSValue()));
        
        returnvalue = new valueImpl(1);
        
        
        return returnvalue;
    }
}

/*

 */
