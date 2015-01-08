package newlang3;

//これはstmtNode BlockNodeの親です。
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExprNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    valueImpl returnvalue;
    int kigo;
    Node varnode;
    LexicalUnit Name = null;//変数の名前を確保するだけ　getValueでif判定して使う王
    Node rightNode = null;
    Node leftNode = null;

    ExprNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        boolean tp;
       
        tp = first.getType().equals(LexicalType.LP);
        if (tp == true) {
            Node a = new ExprNode(env, first);
            return  a;
        }
        tp = first.getType().equals(LexicalType.NAME);
        if (tp == true) {
            //変数のときの処理あ

            Node a = new ExprNode(env, first);
            return a;
        }
        tp = first.getType().equals(LexicalType.SUB);
        if (tp == true) {
            Node a = new ExprNode(env, first);
            return a;
        }
        tp = first.getType().equals(LexicalType.INTVAL);
        if (tp == true) {
            Node a = new ExprNode(env, first);
            return a;
        }
        tp = first.getType().equals(LexicalType.DOUBLEVAL);
        if (tp == true) {
            Node a = new ExprNode(env, first);
            return a;
        }
        tp = first.getType().equals(LexicalType.LITERAL);
        if (tp == true) {
            Node a = new ExprNode(env, first);
            return a;
        }
        if (CallfNode.isMatch(env, first) != null) {
            if (CallfNode.isMatch(env, first).Parse()) {
                Node a = new ExprNode(env, first);
                return a;
            }
        }
        /*        if (ExprNode.isMatch(env, first) != null) {
         if (CallfNode.isMatch(env, first).Parse()) {
         return new ExprNode(env,first);
         }
         }*/
        //ここはnullを返すsけど一時的に変更

        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        
        
        
        LexicalUnit unity1 = lex.get();
        LexicalUnit unity2 = lex.get();
        LexicalType tp = unity2.getType();
        if (tp == LexicalType.ADD || tp == LexicalType.SUB || tp == LexicalType.MUL || tp == LexicalType.DIV) {
            LexicalUnit unity3 = lex.get();
            if (tp == LexicalType.ADD) {
                kigo = 1;
            }
            if (tp == LexicalType.SUB) {
                kigo = 2;
            }
            if (tp == LexicalType.MUL) {
                kigo = 3;
            }
            if (tp == LexicalType.DIV) {
                kigo = 4;
            }

            leftNode = ExprNode.isMatch(env, unity1);
            rightNode = ExprNode.isMatch(env, unity3);
            if (leftNode != null && rightNode != null) {
                lex.unget(unity1);
                if (leftNode.Parse()) {
                    lex.unget(unity3);
                    if (rightNode.Parse()) {
                        return true;
                    }
                }
                lex.unget(unity3);
                lex.unget(unity2);

            } else {
                System.out.println("ExprNodeのleftかrightがぬるぽ");
            }

        } else {//改行だった場合はまあ、その文字列単体だろう
            lex.unget(unity2);
            kigo = 0;
            if (unity1.type == LexicalType.NAME) {//その変数の現在のあたい取得
                //var ノード使いたいな
                Name = unity1;
                return true;
            }
            if (unity1.type == LexicalType.INTVAL) {

                returnvalue = unity1.getValue();

                return true;
            }
            if (unity1.type == LexicalType.DOUBLEVAL) {

                return true;
            }
            if (unity1.type == LexicalType.LITERAL) {
                returnvalue = new valueImpl(unity1.toString());
                return true;
            }
        }
        lex.unget(unity1);
        return false;
    }

    @Override
    public valueImpl getValue() {//計算結果の数値とかだけを返してくれればいいよw
        int left = 0, right = 0;
        valueImpl result = null;
        switch (kigo) {//なし　＋　ー　＊　/　
            case 0:
                if (Name != null) {//計算してほしい内容は変数の参照計算
                    valueImpl varvalue = (valueImpl) env.var_table.get(Name.getValue().getSValue());
                    switch (varvalue.getTypeNum()) {
                        case 1:
                            returnvalue = new valueImpl(varvalue.getSValue());
                            break;//String
                        case 2:
                            returnvalue = new valueImpl(varvalue.getIValue());
                            break;//int
                        case 3:
                            returnvalue = new valueImpl(varvalue.getDValue());
                            break;//double
                        case 4:
                            returnvalue = new valueImpl(varvalue.getBValue());
                            break;//bool
                    }
                    //returnvalue = new valueImpl(varvalue.getIValue());
                    //System.out.println("保存されていた"+Name+"の変数の値は：" + returnvalue.getIValue());
                }
                break;
            case 1:
                left = leftNode.getValue().getIValue();
                right = rightNode.getValue().getIValue();
                result = new valueImpl(left + right);
                return result;
            case 2:
                left = leftNode.getValue().getIValue();
                right = rightNode.getValue().getIValue();
                result = new valueImpl(left - right);
                return result;
            case 3:
                left = leftNode.getValue().getIValue();
                right = rightNode.getValue().getIValue();
                result = new valueImpl((int)left * right);
                return result;
            case 4:
                left = leftNode.getValue().getIValue();
                right = rightNode.getValue().getIValue();
                result = new valueImpl((int)left / right);
                return result;
        }

        return returnvalue;
    }

}


/*
 式の方式
 式とは

 a = b + 1
 というものから成り立っている

 b+1 とはここも式なのである。
 
 
 定数　
 変数　
 オ ペレータ　　　＋ー＊/
 （式 ）        優先計算


 優先度計算は重要である。
 a = b + 1 * a これを計算できるようにしよう。

 プログラムとしてどのように処理を行うか。
 node 
 valiablenode
 constantNode 

 ()は中に式があるとして処理を行うのでexprNodeで対応することができる
 オペレータはenumのいつもの定義の奴

 オペレータ：２項演算子、つまり

 オペレータ１つと２つの項を１つのまとまりとして処理

 ３
 ＋
 b
 ＊
 １
 a




 処理方法
 a+b*c

 a 問題なし、通常読み込み
 + オペレータ出てきた
 b 問題なし
 * 優先ノード出てきた
 c 問題なし



 さらに補足
 expr = 

 BiNode　バイナリ演算
 2項演算



 これをparseで一つの木として生成してあとで
 getValueで計算する。

 葉となるのは定数と変数
 
  
 
 */
