package newlang3;

//これはstmtNode BlockNodeの親です。
import java.util.ArrayList;
import java.util.List;

public class StmtNode extends Node {

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    int flg = 0;
    Node returnnode;
    Node second = null;
    Function func = null;
    LexicalUnit aug1, aug2, aug3;

    /* flg
     <stmt>  ::=   このstmtを満たす最も短い文はEND
     1	<subst>
     2	| <call_sub>
     3	| <FOR> <subst> <TO> <INTVAL> <NL> <stmt_list> <NEXT> <NAME>
     4	| <END>
     5 | PRINTは重要なので入れておく。
     */
    StmtNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。

        if (first != null) {//様々な関数を実行してくれるので登録数を増やそう！
            Function func = (Function) env.library.get(first.getType());
            if (func != null) {
                return new StmtNode(env, first);

            }
            if (first.getType() == LexicalType.FOR) {
                return new StmtNode(env, first);
                //forの処理
            }
            if (first.getType() == LexicalType.END) {
                return new StmtNode(env, first);
                //endの処理
            }
            if (first.getType() == LexicalType.PRINT) {
                return new StmtNode(env, first);
                //endの処理
            }

            SubstNode sbst = new SubstNode(env, first);
            if (sbst.isMatch(env, first) != null) {
                Node a = new StmtNode(env, first);
                return a;

            }
            if (CallsNode.isMatch(env, first) != null) {
                if (CallsNode.isMatch(env, first).Parse()) {
                    Node a = new StmtNode(env, first);
                    return a;
                }
            }
        }
        return null;

    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();//envとはlexicalanalyzerを運ぶためのオブジェクト
        //パターン１
        LexicalUnit unity1 = lex.get();
        //これが代入文化どうかを判断しきる

        if (unity1 != null) {//様々な関数を実行してくれるので登録数を増やそう！ 引数対応1,2,3
            func = (Function) env.library.get(unity1.getType());
            if (func != null) {
                LexicalUnit unity2 = lex.get();
                if (unity2.getType() != LexicalType.NL) {
                    aug1 = unity2;
                    LexicalUnit unity3 = lex.get();
                    if (unity3.getType() != LexicalType.NL) {
                        aug2 = unity3;
                        LexicalUnit unity4 = lex.get();
                        if (unity4.getType() != LexicalType.NL) {
                            aug3 = unity4;
                            flg = 3;
                            return true;
                        }
                        flg = 2;
                        return true;
                    }
                    flg = 1;
                    return true;
                }
                flg = 0;
                return true;
            }
        }

        if (unity1.getType() == LexicalType.PRINT) {
            LexicalUnit unity2 = lex.get();
            if (unity2.getType() == LexicalType.NAME || unity2.getType() == LexicalType.INTVAL || unity2.getType() == LexicalType.LITERAL) {
                flg = 1;
                returnnode = ExprNode.isMatch(env, unity2);
                if (returnnode != null) {
                    lex.unget(unity2);
                    returnnode.Parse();
                    LexicalUnit unity3 = lex.get();
                    if (unity3.getType() == LexicalType.COMMA) {
                        LexicalUnit unity4 = lex.get();
                        if (unity4.getType() == LexicalType.NAME || unity4.getType() == LexicalType.INTVAL || unity4.getType() == LexicalType.LITERAL) {
                            second = ExprNode.isMatch(env, unity4);
                            if (second != null) {
                                lex.unget(unity4);
                                second.Parse();
                                return true;
                            }
                            lex.unget(unity4);
                            return false;
                        }
                        lex.unget(unity3);
                        lex.unget(unity2);
                        return false;
                    }
                    return true;
                }
            }
            lex.unget(unity2);
        }

        returnnode = SubstNode.isMatch(env, unity1);
        if (returnnode != null) {
            lex.unget(unity1);
            if (returnnode.Parse()) {

                //lex.unget(unity1);
                return true;
            }
        }
        lex.unget(unity1);

        //パターン２
        unity1 = lex.get();
        if (CallsNode.isMatch(env, unity1) != null) {
            lex.unget(unity1);
            if (CallsNode.isMatch(env, unity1).Parse()) {
                //lex.unget(unity1);
                return true;
            }
        }
        lex.unget(unity1);

        //パターン３
        unity1 = lex.get();
        if (unity1 != null) {
            if (unity1.type == LexicalType.END) {
                //lex.unget(unity1);
                return true;
            }
            lex.unget(unity1);
        }

        //パターン４
        unity1 = lex.get();
        if (unity1.type == LexicalType.FOR) {
            LexicalUnit unity2 = lex.get();
            if (SubstNode.isMatch(env, unity2) != null) {
                lex.unget(unity2);
                if (SubstNode.isMatch(env, unity2).Parse()) {
                    LexicalUnit unity3 = lex.get();
                    if (unity3.type == LexicalType.TO) {
                        LexicalUnit unity4 = lex.get();
                        if (unity3.type == LexicalType.INTVAL) {
                            LexicalUnit unity5 = lex.get();
                            if (unity5.type == LexicalType.NL) {
                                LexicalUnit unity6 = lex.get();
                                if (StmtNode.isMatch(env, unity6) != null) {
                                    lex.unget(unity6);
                                    if (StmtListNode.isMatch(env, unity6).Parse()) {
                                        LexicalUnit unity7 = lex.get();
                                        if (unity7.type == LexicalType.NEXT) {
                                            LexicalUnit unity8 = lex.get();
                                            if (unity8.type == LexicalType.NAME) {
                                                //lex.unget(unity8);
                                                return true;
                                            }
                                            lex.unget(unity8);
                                        }
                                        lex.unget(unity7);
                                    }
                                }
                                lex.unget(unity6);
                            }
                            lex.unget(unity5);
                        }
                        lex.unget(unity4);
                    }
                    lex.unget(unity3);
                }
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        /*
         <stmt>  ::=   このstmtを満たす最も短い文はEND
         <subst>
         | <call_sub>
         | <FOR> <subst> <TO> <INTVAL> <NL> <stmt_list> <NEXT> <NAME>
         | <END>

        
         */
        return false;
    }

    public Value getValue() {
        //System.out.println("ステートメントノード");
        if (func != null) {

            switch (flg) {
                case 0:
                    func.getValue();
                    break;
                case 1:
                    func.getValue(aug1);
                    break;
                case 2:
                    func.getValue(aug1,aug2);
                    break;
                case 3:
                    func.getValue(aug1,aug2,aug3);
                    break;
            }
            return new valueImpl(true);
        }

        if (second != null) {
            valueImpl a = (valueImpl) returnnode.getValue();
            valueImpl b = (valueImpl) second.getValue();
            switch (a.getTypeNum()) {
                case 1:
                    System.out.print(a.getSValue());
                    break;
                case 2:
                    System.out.print(a.getIValue());
                    break;
                case 3:
                    System.out.print(a.getDValue());
                    break;
                case 4:
                    System.out.print(a.getBValue());
                    break;
            }
            switch (b.getTypeNum()) {
                case 1:
                    System.out.println(b.getSValue());
                    break;
                case 2:
                    System.out.println(b.getIValue());
                    break;
                case 3:
                    System.out.println(b.getDValue());
                    break;
                case 4:
                    System.out.println(b.getBValue());
                    break;
            }

        } else if (flg == 1) {
            valueImpl a = (valueImpl) returnnode.getValue();
            switch (a.getTypeNum()) {
                case 1:
                    System.out.println(a.getSValue());
                    break;
                case 2:
                    System.out.println(a.getIValue());
                    break;
                case 3:
                    System.out.println(a.getDValue());
                    break;
                case 4:
                    System.out.println(a.getBValue());
                    break;
            }
        }
        Value returnvalue = returnnode.getValue();
        return returnvalue;
    }

}

/*

 */
