package newlang3;

public class ProgramNode extends Node {

    Environment env;
    LexicalUnit first;
    Node stmtlistnode;

    ProgramNode(Environment env, LexicalUnit first) {
        this.env = env;
        this.first = first;

    }

    public static Node isMatch(Environment env, LexicalUnit first) {
        Node node = StmtListNode.isMatch(env, first);

        if (node != null) {
            Node a = new ProgramNode(env, first);
            return a;
        }
        return null;

    }

    @Override
    public boolean Parse() {//Program Node のパース　＝　program nodeを文法解析するもの。

        LexicalAnalyzer lex = env.getInput();

        LexicalUnit first = lex.get();//isMathcのときの読んだものを再利用しましょうは禁止
        stmtlistnode = StmtListNode.isMatch(env, first);//子供に聴いてみる　もし子供が持っていたら<stmt>などのオブジェクトを返してくる。
        //つまり子供に向かってparseしろといえばいい
        if (stmtlistnode == null) {
            return false;
        }//もし子がnull何もないと返してきた場合はそれはもう文法エラー
        lex.unget(first);//一文字読んでしまったので戻る
        return stmtlistnode.Parse();//帰ってきたnodeの中身を全部よこせと
    }

    @Override
    public Value getValue() {
        //System.out.println("-----プログラムノード-----");
        //System.out.println("ステートメントリストノードにgetValue");
        Value value = stmtlistnode.getValue();//test
        

        valueImpl returnValue;
        returnValue = new valueImpl("プログラムが正常に実行されました");

        return returnValue;
    }

}
