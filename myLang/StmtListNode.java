package newlang3;

//これはstmtNode BlockNodeの親です。
import java.util.*;

public class StmtListNode extends Node {

    List<Node> nodelist = new ArrayList<Node>();
    Environment StmtListEnv;
    LexicalUnit first;
    //どこのノードにどの順番でgetValueするか保存

    Node returnnode;

    StmtListNode(Environment env, LexicalUnit first) {
        this.StmtListEnv = env;
        this.first = first;

    }

    public static Node isMatch(Environment env, LexicalUnit first) {//空っぽの箱を作る。      

        Node node = BlockNode.isMatch(env, first);
        if (node != null) {
            return new StmtListNode(env, first);
        }

        node = StmtNode.isMatch(env, first);
        if (node != null) {
            return new StmtListNode(env, first);
        }

        return null;

    }

    @Override
    public boolean Parse() {//ブロックだったらブロックごと読み飛ばす
        //nodes = new ArrayList<Node>();
        LexicalAnalyzer lex = StmtListEnv.getInput();//envとはlexicalanalyzerを運ぶためのオブジェクト
        int count = 0;
        while (true) {
            
            first = lex.get();

            if (first.getType() == LexicalType.EOF || first == null||first.getType() == LexicalType.ELSE||first.getType() == LexicalType.ENDIF||first.getType() == LexicalType.LOOP) {
                if(first.getType() == LexicalType.ELSE||first.getType() == LexicalType.ENDIF||first.getType() == LexicalType.LOOP){
                    //wendとelseとendifはかいぎょうした後にしかかけないため
                    lex.unget(first);
                    lex.unget(new LexicalUnit(LexicalType.NL));//と書こう！
                }
                break;
            }
            if (first.getType() == LexicalType.NL) {
                continue;
            }

            returnnode = StmtNode.isMatch(StmtListEnv, first);
            if (returnnode == null) {
                returnnode = BlockNode.isMatch(StmtListEnv, first);
            }

            if (returnnode == null) {
                lex.unget(first);
                return false;
            }//nodeがもらえなかったらerror
            //今回のunitのnodeが帰ってきた場合　parseを実行して何事もなければ次に行ける
            lex.unget(first);
            if (returnnode.Parse() == false) {//文法の最終チェック
                return false;
            }
            count += 1;
            //System.out.println("ステートメント" + count + ":" + first + "から始まります");
            nodelist.add(returnnode);
            

        }

        return true;
    }

    @Override
    public Value getValue() {
        Value returnvalue = null;
        int count = 0;
        //System.out.println("-----ステートメントリストノード-----");
        while (true) {
            if (nodelist.size() == count) {
                break;
            }
            Node popNode = nodelist.get(count);
            count++;
            System.out.print(count + "|");
            //System.out.println("leoの変数の値：" + StmtListEnv.var_table.get("leo"));
            returnvalue = popNode.getValue();
        }
        System.out.println();
        return returnvalue;
    }

}

/*

 */
