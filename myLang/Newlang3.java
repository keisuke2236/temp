package newlang3;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Newlang3 {
    /*
     予約語は小文字で描くこと
     プログラムの終了は必ず改行すること
     */

    private static Object Expr;

    public static void main(String[] args) throws IOException {
        FileReader fin = null;
        LexicalAnalyzer lex;
        LexicalUnit first = null;
        Environment env;
        Node expression = null;
        int a = 15;
        try {
            fin = new FileReader("/Users/rorensu/NetBeansProjects/newlang3_syntax/src/newlang3/test.txt");

        } catch (Exception e) {
            System.out.println("file not found");
            System.exit(-1);
        }
        lex = new LexicalAnalyzerImpl(fin);
        env = new Environment(lex);

        first = lex.get();
        expression = ProgramNode.isMatch(env, first);//firstがどのnodeか
        //unget?
        lex.unget(first);
        if (expression != null) {
            if (expression.Parse() == true) {
                //ここまできた、getvalueは個別に書いていないし動かない。 やはり一番下のノードの要素を持ってきてexpressionに入れないとだめかもしれない。
                System.out.println("------------文法解析OK-------------");
                //System.out.println(expression+"が帰ってきたよ");
                System.out.println(expression.getValue().getSValue());
            } else {
                System.out.println("パースエラー");
            }
        } else {
            System.out.println(expression);
            System.out.println("マッチエラー");
        }

    }
}

/*
 ismatch とは一番外側の<program>というノードを作ることに値する。
 */
