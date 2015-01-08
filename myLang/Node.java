/*箱（<stmt>など　をプログラムの中で表現するためのもの。
これがステートメント、これがstmtなど箱は箱でもすこしずづ違う。
その違いをこのクラスを継承することで利用できるようにする。

箱にプログラムのstmtなどを分ける作業は手作業もプログラムもほとんど同じ、
代入文は変数に代入する　変数や代入の数値はインスタンs変数として保存する。
ノードを継承したものの集まりが

少なくともnodeTypeに記述されているくらいのノードは必要なので継承しましょうね
のーどを　継承してはnodeTypeのノードを開発する。
式を解析はむずいけどそれ以外は単純
それぞれの箱は自分の下に何が来るかがわかる
つまり大きい箱から作る。
大きい箱から作っていき
<program>にはstmtとblockくらいしか来ない。
バラバラに作ります。

箱の中にファーストシンボル（集合)を集められる
代入文は全てNAME SBSTのファーすとシンボルはNAMEだけ
blockは同じくwhile do if のどれか
endはEOFだけ

nameが来たらステートメント、ちなみに終了はデフォルトで文法で規定されているのでおk。
ブロックだとわかった瞬間にどこで終わるかなんてことは文法であらかじめ決められている

Lexical Analyzer の拡張、DOを読みすぎた場合 Lexical UnitのUnit型を保存しておくバッファをキューとして保存しておけばおk

ismatch というものを作る
*/
package newlang3;

public class Node {
    NodeType type;
    Environment env;
    
    public Node next;

    /** Creates a new instance of Node */
    public Node() {//コンストラクタ
    }
    public Node(NodeType my_type) {
        type = my_type;
    }
    public Node(Environment my_env) {
        env = my_env;
    }
    
    public NodeType getType() {
        return type;
    }
    
    public boolean Parse() {
        
        return true;
    }
    
    public Value getValue() {//これを実装すると実行することができるようになる。
        //全てのクラスに対してこれを実行することで各クラスがそれに見合った動きをしてくれるのでおk
        return null;
    }
 
    public String toString() {
    	if (type == NodeType.END) return "END";
    	else return "Node";        
    }

}
