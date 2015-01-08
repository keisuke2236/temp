package newlang3;

//ismatch は存在するかを調べているの？
import java.util.ArrayList;
import java.util.List;

public class BlockNode extends Node {

    List<Node> nodes;
    LexicalUnit first;
    Environment env;

    BlockNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first) {//ノードを継承していれば何でも良い
        //なのでここでそもそもマッチするのかを書くこの集合に属しているかの判断 FOR DOなど
        //そして属しているものが何かを判断させる

        Node node = ifblockNode.isMatch(env, first);
        if (node != null) {
            Node a = new ifblockNode(env, first);
            return a;
        }

        node = loopblockNode.isMatch(env, first);
        if (node != null) {
            Node a = new loopblockNode(env, first);
            return a;
        }

        return null;
    }
}
