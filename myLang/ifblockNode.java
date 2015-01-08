/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newlang3;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rorensu
 */
public class ifblockNode extends Node {

    Environment env;
    LexicalUnit first;
    LexicalType unit1, unit2, unit3, unit4, unit5, unit6;

    //保存するべきもの 条件式　 then elseの三種類のノード
    Node thenNode, elseNode, ifpnode;
    //この３つを入手して飛ぶ先を固定化しておく。

    ifblockNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {

        Node ifpnode;
        ifpnode = IfpNode.isMatch(env, first);
        if (ifpnode != null) {
            Node a = new ifblockNode(env, first);

            return a;
        }
        return null;

    }

    @Override
    public boolean Parse() {
        LexicalAnalyzer lex = env.getInput();
        int ifpflg = 0;
        int stmtflg = 0;
        int stmtlistflg = 0;
        int elseflg = 0;

        //パターン１
        LexicalUnit unity1 = lex.get();
        ifpnode = IfpNode.isMatch(env, unity1);
        Node stmtnode = null;
        Node stmtlistnode = null;
        Node elsenode = null;

        if (ifpnode != null) {
            lex.unget(unity1);
            if (ifpnode.Parse()) {

                ifpflg = 1;
                unit1 = unity1.type;
                LexicalUnit unity2 = lex.get();
                stmtnode = StmtNode.isMatch(env, unity2);
                if (stmtnode != null) {
                    lex.unget(unity2);
                    if (stmtnode.Parse()) {
                        thenNode = stmtnode;
                        stmtflg = 1;
                        unit2 = unity2.type;
                        LexicalUnit unity3 = lex.get();
                        if (unity3.type == LexicalType.NL) {
                            unit3 = unity3.type;
                            //lex.unget(unity3);
                            return true;
                        }
                        lex.unget(unity3);
                    }
                }
                lex.unget(unity2);
            }
            lex.unget(unity1);
        }

        //--------------------------------------------------------------------
        unity1 = lex.get();
        if (ifpflg == 0) {
            ifpnode = IfpNode.isMatch(env, unity1);
            if (ifpnode != null) {
                lex.unget(unity1);
                if (ifpnode.Parse()) {
                    ifpflg = 1;
                }
            }
        }
        if (ifpflg == 1) {
            unit1 = unity1.type;
            LexicalUnit unity2 = lex.get();
            if (unity2.getType() == LexicalType.NL) {
                if (stmtflg == 0) {
                    LexicalUnit unity3 = lex.get();
                    if (stmtflg == 0) {
                        stmtnode = StmtListNode.isMatch(env, unity3);
                        if (stmtnode == null) {
                            stmtnode = StmtNode.isMatch(env, unity3);
                        }

                        if (stmtnode != null) {
                            lex.unget(unity3);
                            if (stmtnode.Parse()) {
                                thenNode = stmtnode;
                                stmtflg = 1;
                                LexicalUnit unity4 = lex.get();
                                if(unity4.getType()==LexicalType.NL){
                                    LexicalUnit unity5 = lex.get();
                                    if(unity5.getType()== LexicalType.ENDIF){
                                        return true;
                                    }
                                    lex.unget(unity5);
                                }
                                lex.unget(unity4);
                                
                            }
                        } else {
                            lex.unget((unity3));
                        }
                    }

                }

                if (stmtflg == 1) {
                    LexicalUnit unity4 = lex.get();
                    if (unity4.getType() == LexicalType.NL) {//ステートメンとの次が開業
                        //ここにElse処理必要
                        LexicalUnit unity5 = lex.get();
                        if (unity5.getType() == LexicalType.ELSE) {
                            LexicalUnit unity61 = lex.get();
                            if (unity61.getType() == LexicalType.NL) {

                                LexicalUnit unity6 = lex.get();
                                elseNode = StmtListNode.isMatch(env, unity6);
                                if (elseNode == null) {
                                    elseNode = StmtNode.isMatch(env, unity6);
                                }
                                if (elseNode != null) {
                                    lex.unget(unity6);
                                    if (elseNode.Parse()) {
                                        LexicalUnit unity7 = lex.get();
                                        if (unity7.getType() == LexicalType.NL) {
                                            LexicalUnit unity8 = lex.get();
                                            if (unity8.getType() == LexicalType.ENDIF) {
                                                return true;
                                            }
                                            lex.unget(unity8);
                                        }
                                        lex.unget(unity7);
                                        return true;
                                    }
                                }
                                lex.unget(unity6);
                            } else {

                                if (unity61.getType() == LexicalType.IF) {//elseif現る
                                    elseNode = ifblockNode.isMatch(env, unity61);
                                    if (elseNode != null) {
                                        lex.unget(unity61);
                                        if(elseNode.Parse()){
                                            return true;
                                        }
                                        
                                    }
                                }
                                lex.unget(unity61);

                            }

                        }
                        lex.unget(unity5);
                        return true;
                    }
                    lex.unget(unity4);
                }
            }
            lex.unget(unity2);
        }
        lex.unget(unity1);

        //--------------------------------------------------------------------
        unity1 = lex.get();
        if (ifpflg == 0) {
            ifpnode = IfpNode.isMatch(env, unity1);
            if (ifpnode != null) {
                lex.unget(unity1);
                if (ifpnode.Parse()) {
                    ifpflg = 1;
                }
            }
        }
        if (ifpflg == 1) {
            unit1 = unity1.type;

            LexicalUnit unity2 = lex.get();
            if (stmtflg == 0) {
                stmtnode = StmtNode.isMatch(env, unity2);
                if (stmtnode != null) {
                    lex.unget(unity2);
                    if (stmtnode.Parse()) {
                        thenNode = stmtnode;
                        stmtflg = 1;
                    }
                }
            }
            if (stmtflg == 1) {
                unit2 = unity2.type;
                LexicalUnit unity3 = lex.get();
                if (unity3.type == LexicalType.ELSE) {
                    unit3 = unity3.type;
                    LexicalUnit unity4 = lex.get();
                    elseNode = StmtNode.isMatch(env, unity4);
                    if (elseNode != null) {
                        lex.unget(unity4);
                        if (elseNode.Parse()) {
                            unit4 = unity4.type;
                            LexicalUnit unity5 = lex.get();
                            if (unity5.type == LexicalType.NL) {
                                unit5 = unity5.type;
                                //lex.unget(unity5);
                                return true;
                            }
                            lex.unget(unity5);
                        }
                    }
                    lex.unget(unity4);
                }
                lex.unget(unity3);

            }
            lex.unget(unity2);

        }
        lex.unget(unity1);

        //-----------------------------------------------------
        unity1 = lex.get();
        if (ifpflg == 0) {
            ifpnode = IfpNode.isMatch(env, unity1);
            if (ifpnode != null) {
                lex.unget(unity1);
                if (ifpnode.Parse()) {
                    ifpflg = 1;
                }
            }
        }
        if (ifpflg == 1) {
            unit1 = unity1.type;
            LexicalUnit unity2 = lex.get();
            if (unity2.type == LexicalType.NL) {
                unit2 = unity2.type;
                LexicalUnit unity3 = lex.get();
                stmtlistnode = StmtNode.isMatch(env, unity3);
                if (stmtlistnode != null) {
                    lex.unget(unity3);
                    if (stmtlistnode.Parse()) {
                        thenNode = stmtlistnode;
                        stmtlistflg = 1;
                        unit3 = unity3.type;
                        LexicalUnit unity4 = lex.get();
                        elsenode = ElseBlockNode.isMatch(env, unity4);
                        if (elsenode != null) {
                            lex.unget(unity4);
                            if (elsenode.Parse()) {
                                elseflg = 1;
                                unit4 = unity4.type;
                                LexicalUnit unity5 = lex.get();
                                if (unity5.type == LexicalType.ENDIF) {
                                    unit5 = unity5.type;
                                    LexicalUnit unity6 = lex.get();
                                    if (unity6.type == LexicalType.NL) {
                                        unit6 = unity6.type;
                                        return true;
                                    }
                                    lex.unget(unity6);
                                }
                                lex.unget(unity5);
                            }
                        }
                        lex.unget(unity4);
                    }
                }
                lex.unget(unity3);
            }
            lex.unget(unity2);

        }
        lex.unget(unity1);
        //lex.unget(unity1);
        /*
         <if_prefix> <stmt> <NL>
         | <if_prefix> <stmt> <ELSE> <stmt> <NL>
         | <if_prefix> <NL> <stmt_list> <else_block> <ENDIF> <NL>
         */

        return false;

    }

    //条件ノード（ifp)　trueの時のノード elseの時のノード　3種類あればおk
    @Override
    public Value getValue() {
        System.out.println("if認識");
        if (ifpnode.getValue().getBValue()) {
            //then
            thenNode.getValue();

        } else {
            //else
            elseNode.getValue();//見つからなかったから登録しとけよな！ってところ
        }

        return null;
    }

    /*getValue
    
     if(条件式ノード.getValue().getBValue()!=false){
     Thenの時のノード.getValue();
     }else{
     Elseの時のノード.getValue();
     }
    
    
    
    
     */
}
