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
    int operator;
    Node varnode;
    LexicalUnit Name = null;//変数の名前を確保するだけ　getValueでif判定して使う王
    Node rightNode = null;
    Node leftNode = null;
    Stack box = new Stack();
    Stack<Node> Nbox;
    //LexicalUnit FLGUNIT = new LexicalUnit(LexicalType.EXTRA);

    ExprNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。
        boolean tp;

        tp = first.getType().equals(LexicalType.LP);
        if (tp == true) {
            Node exprnode = new ExprNode(env, first);
            return exprnode;
        }
        tp = first.getType().equals(LexicalType.NAME);
        if (tp == true) {
            //変数のときの処理あ

            Node exprnode = new ExprNode(env, first);
            return exprnode;
        }
        tp = first.getType().equals(LexicalType.SUB);
        if (tp == true) {
            Node exprnode = new ExprNode(env, first);
            return exprnode;
        }
        tp = first.getType().equals(LexicalType.INTVAL);
        if (tp == true) {
            Node exprnode = new ExprNode(env, first);
            return exprnode;
        }
        tp = first.getType().equals(LexicalType.DOUBLEVAL);
        if (tp == true) {
            Node exprnode = new ExprNode(env, first);
            return exprnode;
        }
        tp = first.getType().equals(LexicalType.LITERAL);
        if (tp == true) {
            Node exprnode = new ExprNode(env, first);
            return exprnode;
        }
        if (CallfNode.isMatch(env, first) != null) {
            if (CallfNode.isMatch(env, first).Parse()) {
                Node exprnode = new ExprNode(env, first);
                return exprnode;
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
            lex.unget(unity2);
            lex.unget(unity1);
            while (true) {//ここで足し算だけの計算式に変換する a+Node.getValue()+b+c+d+Node.getValue()
                LexicalUnit unity3 = lex.get();
                LexicalUnit unity4 = lex.get();
                LexicalType operatorType = unity4.getType();
                if (operatorType == LexicalType.ADD) {
                    operator = 1;
                }
                if (operatorType == LexicalType.SUB) {
                    operator = 2;
                }
                if (operatorType == LexicalType.MUL) {
                    operator = 3;
                }
                if (operatorType == LexicalType.DIV) {
                    operator = 4;
                }
                if (operatorType == LexicalType.NL) {
                    box.push(unity3);
                    break;
                }
                if (operator >= 3) {//*/のノードがほしーいー
                    Node mdnode = MDNode.isMatch(env, unity3);
                    lex.unget(unity4);
                    lex.unget(unity3);
                    mdnode.Parse();

                    //box.push(new LexicalUnit(LexicalType.INTVAL,new valueImpl(mdnode.getValue().getIValue())));
                    //box.push(FLGUNIT);//それがノードかユニットか判断するためのもの
                    box.add(mdnode);//FLGUNITで判定する
                    LexicalUnit unity6 = lex.get();
                    if (unity6.getType() == LexicalType.ADD || unity6.getType() == LexicalType.SUB) {
                        box.push(unity6);
                    }else{
                        lex.unget(unity6);
                    }
                } else {//+-
                    Node mdnode =MDNode.isMatch(env, unity3);
                    lex.unget(unity3);
                    if(!mdnode.Parse()){
                        return false;
                    }
                    box.push(mdnode);
                    box.push(unity4);

                    /*LexicalUnit unity5 = lex.get();
                     rightNode = ExprNode.isMatch(env, unity5);//右側は次々に読む
                     lex.unget(unity5);
                     rightNode.Parse();
                     leftNode = ExprNode.isMatch(env, unity3);//左側は固定
                     lex.unget(unity3);
                     leftNode.Parse();*/
                }
                unity3 = lex.get();
                if (unity3.getType() == LexicalType.NL) {
                    break;
                }
                lex.unget(unity3);
            }
            return true;

        } else {//改行だった場合はまあ、その文字列単体だろう
            lex.unget(unity2);
            operator = 0;
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
        switch (operator) {//なし　＋　ー　＊　/　
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
            default:
                left = 0;
                Stack hoz1 = new Stack();
                Stack hoz2 = new Stack();
                while(true){
                    Object copy = this.box.pop();
                    hoz1.push(copy);
                    hoz2.push(copy);
                    if(box.empty()){break;}    
                }
                while(true){
                    Object copy = hoz2.pop();
                    this.box.push(copy);
                    if(hoz2.empty()){break;}  
                }
                int operator = 0;
                while (true) {
                    int nowget = 0;
                    Object popObj = hoz1.pop();
                    if (popObj.toString() == "Node") {
                        Node popNode = (Node) popObj;
                        nowget = popNode.getValue().getIValue();
                        if(nowget==0){
                            String nowget2 = popNode.getValue().getSValue();
                            if(nowget2!=null){
                                nowget = (int) env.var_table.get(Name.getValue().getIValue());
                                
                            }
                        }
                    } else if (popObj.toString() == "ADD") {
                        operator = 1;
                        continue;
                    } else if (popObj.toString() == "SUB") {
                        operator = 2;
                        continue;
                    } else{
                        LexicalUnit tem = (LexicalUnit)popObj;
                        nowget = tem.getValue().getIValue();
                    }
                    switch (operator) {
                        case 0:left = nowget;
                            break;
                        case 1:left = left + nowget;
                            break;
                        case 2:left = left - nowget;
                            break;
                    }
                    
                    if (hoz1.empty()) {
                        break;
                    }
                }
                returnvalue = new valueImpl(left);

                break;
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
