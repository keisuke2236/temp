/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newlang3;

/**
 *
 * @author rorensu
 */
public class loopblockNode extends Node {

    Environment env;
    LexicalUnit first;
    Node loopNode = null;
    Node condNode = null;

    loopblockNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {

        boolean tp;
        if (first != null) {
            tp = first.getType().equals(LexicalType.DO);
            if (tp == true) {
                Node a = new loopblockNode(env, first);
                return a;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();

        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.WHILE) {
            LexicalUnit unity2 = lex.get();
            condNode = CondNode.isMatch(env, unity2);
            if (condNode != null) {
                lex.unget(unity2);
                if (condNode.Parse()) {
                    LexicalUnit unity3 = lex.get();
                    if (unity3.type == LexicalType.NL) {
                        LexicalUnit unity4 = lex.get();
                        loopNode = StmtListNode.isMatch(env, unity4);
                        if (loopNode == null) {
                            loopNode = StmtNode.isMatch(env, unity4);
                        }
                        if (loopNode != null) {
                            lex.unget(unity4);
                            if (loopNode.Parse()) {
                                LexicalUnit unity5 = lex.get();
                                if (unity5.type == LexicalType.WEND) {
                                    LexicalUnit unity6 = lex.get();
                                    if (unity6.type == LexicalType.NL) {
                                        //lex.unget(unity6);
                                        return true;
                                    }
                                    lex.unget(unity6);
                                }
                                lex.unget(unity5);
                            }
                        }
                        lex.unget(unity4);
                    }
                    lex.unget(unity3);
                }
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        //パターン2,3
        unity1 = lex.get();
        if (unity1.getType() == LexicalType.DO) {
            LexicalUnit unity2 = lex.get();

            if (unity2.type == LexicalType.WHILE || unity2.type == LexicalType.UNTIL) {
                LexicalUnit unity3 = lex.get();
                condNode = CondNode.isMatch(env, unity3);
                if (condNode != null) {
                    lex.unget(unity3);
                    if (condNode.Parse()) {
                        LexicalUnit unity4 = lex.get();
                        if (unity4.type == LexicalType.NL) {
                            LexicalUnit unity5 = lex.get();
                            loopNode = StmtListNode.isMatch(env, unity5);
                            if (loopNode == null) {
                                loopNode = StmtNode.isMatch(env, unity5);
                            }
                            if (loopNode != null) {
                                lex.unget(unity5);
                                if (loopNode.Parse()) {
                                    LexicalUnit unity61 = lex.get();
                                    if (unity61.getType() == LexicalType.NL) {
                                        LexicalUnit unity6 = lex.get();
                                        if (unity6.type == LexicalType.LOOP) {
                                            LexicalUnit unity7 = lex.get();
                                            if (unity7.type == LexicalType.NL) {
                                                //lex.unget(unity7);
                                                return true;
                                            }
                                            lex.unget(unity7);
                                        }
                                        lex.unget(unity6);
                                    }
                                    lex.unget(unity61);
                                }
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
        lex.unget(unity1);

        //パターン4,5
        unity1 = lex.get();
        if (unity1.getType() == LexicalType.DO) {
            LexicalUnit unity2 = lex.get();
            if (unity1.type == LexicalType.NL) {
                LexicalUnit unity3 = lex.get();
                if (StmtListNode.isMatch(env, unity3) != null) {
                    lex.unget(unity3);
                    if (StmtListNode.isMatch(env, unity3).Parse()) {
                        LexicalUnit unity4 = lex.get();
                        if (unity4.type == LexicalType.LOOP) {
                            LexicalUnit unity5 = lex.get();
                            if (unity4.type == LexicalType.WHILE || unity4.type == LexicalType.UNTIL) {
                                LexicalUnit unity6 = lex.get();
                                if (CondNode.isMatch(env, unity6) != null) {
                                    lex.unget(unity6);
                                    if (CondNode.isMatch(env, unity6).Parse()) {
                                        LexicalUnit unity7 = lex.get();
                                        if (unity7.type == LexicalType.NL) {
                                            //lex.unget(unity7);
                                            return true;
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
                }
                lex.unget(unity3);
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        /*
         | <WHILE> <cond> <NL> <stmt_list> <WEND> <NL>
         | <DO> <WHILE> <cond> <NL> <stmt_list> <LOOP> <NL>
         | <DO> <UNTIL> <cond> <NL> <stmt_list> <LOOP> <NL>
         | <DO> <NL> <stmt_list> <LOOP> <WHILE> <cond> <NL>
         | <DO> <NL> <stmt_list> <LOOP> <UNTIL> <cond> <NL>
         */
        return false;
    }

    @Override
    public Value getValue() {//condを満たさなくなるまでくるくるまわる
        System.out.println("↓↓------loop------↓↓");
        while(true){  
            System.out.print("ループ条件：");
            if(condNode.getValue().getBValue()){
                loopNode.getValue();
            }else{
                break;
            }
        }
        System.out.println("↑↑------loop------↑↑");
        return null;
    }

}
