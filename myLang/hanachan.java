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
public class hanachan extends Function {

    public valueImpl getValue() {
        System.out.println("はろーはなちゃん");
        return null;
    }
    public valueImpl getValue(LexicalUnit aug) {
        System.out.println("はろーはなちゃん"+aug.toString());
        return null;
    }
}


