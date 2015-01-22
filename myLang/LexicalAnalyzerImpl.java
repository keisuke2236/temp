package newlang3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Stack;


public class LexicalAnalyzerImpl implements LexicalAnalyzer {

    File file = new File("./src/newlang3/test.txt");
    PushbackReader sorce = null;

    LexicalUnit returnUnit;//return してあげるためのもの
    String bf;
    HashMap<Character, LexicalUnit> map;//+*/-=などの記号を判別するためのもの
    HashMap<String, LexicalUnit> nameMap;//変数予約語を処理するためのもの
    Stack<LexicalUnit> hozon = new Stack<>();

    LexicalAnalyzerImpl(FileReader fin) throws FileNotFoundException, IOException { //コンストラクタ
        sorce = new PushbackReader(fin);
        init(); //初期化　とhash mapの登録処理
    }


    @Override
    public LexicalUnit get() {
        //****************************無限ループの開始*************************************
        if(!hozon.empty()){//ungetされたものがあるかどうか
            return hozon.pop();
        }
        while (true) {
            try {
                int ci = sorce.read();//元ファイルの読み込み

                //------------------End Of File--------------------
                if (ci < 0) {
                    //System.out.println("analyzer impl>>ファイルが終了しました");
                    return new LexicalUnit(LexicalType.EOF);//EOF　ファイルが読み終わってしまったということを返す
                }

                //------------------空白の読み飛ばし--------------------
                char c = (char) ci;//読み込んだソースプログラム1文字を文字列に変換し、もし空白文字であれば無視する
                if (c == '\t' || c == '\r' || c == ' ') {//タブ　開業　スペース
                    //System.out.println("    空白");
                    continue;//その一文字を読み飛ばす
                }

                //------------------数値データ--------------------------
                //もし123と3文字だった時のために、数値だった場合はもう一文字読み込んでそれが数字でなくなるまでループする。
                //もし4文字目を読んで数字でなかった場合はループから抜け、3桁の数字を生成するのだが、1文字読み込んでしまったのでなかったことにしないといけない。
                //そうじゃないと"123　"などの数値の後の空白が読み捨てられてしまう。
                //数字列1文字や3文字を読み取り最終的には INTVAL または DOUBLEVALを数字列3文字などについき1回のみ返すようにする。
                //input Streamではungetすることができないので高機能なものを利用する。
                if (c >= '0' && c <= '9') {
                    return lexicalNum(c, sorce);
                }

                //-------------文字列データ-----------------
                //a-Zまでの文字列への対応と予約後に対する処理
                //2文字で１文字となるものへの対応
                if (c == '"') {
                    return lexicalLiteral(c, sorce);
                }

                //予約語じゃなかった場合のみ変数とみなされる
                //--------------変数データ-------------
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '<' || c == '>') {
                    return lexicalName(c, sorce);
                    //ここで帰ってくる文字列ってもしかしたら変数じゃなくて予約文字な可能性もあるよね、そしたらどうするんだろう。。。
                }

                //---------------予約後対策[特殊文字編]------------------
                //クラスライブラリ　マップ　ハッシュマップ
                /*
                 keyとvalueがあって検索ができるようになっているもの
                 keyで検索するとvalueが取れる。 API参照

                 予約後をマップの中に入れておいて、予約後っぽいのが出てきたら表を引いてマップから値を持ってくる
                 マップとして適正なものとしてハッシュマップに予約語の一欄を入れる。
                 また、ない場合はnullが帰ってくるので２３行でコードが書ける。

                 表を初期化して実際に使ってみよう：変数定義の事ね
                 */
                //--------------特殊文字---------------
                return map.get(c);

                //          System.out.println("未判定文字列     :" + ci + "   に関してはLexicalUnitを返すことができていません。　ここよりも上で判定を行いreturnしてください");
                //error的なやつ
            } catch (IOException ex) {
                Logger.getLogger(LexicalAnalyzerImpl.class.getName()).log(Level.SEVERE, null, ex);
                return new LexicalUnit(LexicalType.EOF);//nullだけどnullって書くとエラーって言われるから仕方なくEOFにしたよ
            }
        }

    }

    //*********************************無限ループ停止***********************************
    //数字列
    private LexicalUnit lexicalNum(char c, PushbackReader sorce) throws IOException {
        String nums = "";
        nums += (char) c;
        while (true) {
            char ci2 = (char) sorce.read(); //次の文字列を読み込む
            if (ci2 < '0' || ci2 > '9') {//もし読み込んだけど数字ではなかった場合
                sorce.unread(ci2);
                //unread 読まなかったことにする。
                break;
            } else {
                nums += ci2;//数字であればnumsに値を追加する。
            }
        }
        int val = Integer.valueOf(nums);//数値に変換する
        return new LexicalUnit(LexicalType.INTVAL, new valueImpl(val));//形だけではなく値も作る
        //ここでつくった　valueimplが役に立つのである。
        //ここまでこれを作ってきたがこれはサブルーチンなのでどうしましょう

    }

    //ダブルクオーテーションで囲まれたただの文字列
    private LexicalUnit lexicalLiteral(char c, PushbackReader sorce) throws IOException {
        String lite = "";
        while (true) {
            char ci2 = (char) sorce.read(); //次の文字列を読み込む
            if (ci2 == '"') {//ダブルクオーテーションだった場合終了　　”ではじまって”で終わるから　　”は読み捨てる
                //読み捨てちゃうｗ
                break;

            } else {
                lite += ci2;//文字列が終了していない場合連結する。
            }
        }
        //ここでリターンするときに数字も一緒にINTVALと一緒に返したい
        return new LexicalUnit(LexicalType.LITERAL, new valueImpl(lite));//形だけではなく値も作る
        //ここでつくった　valueimplが役に立つのである。
        //ここまでこれを作ってきたがこれはサブルーチンなのでどうしましょう
    }

//変数として通常定義されるabcなど　intなど
//もし予約語でないのならばそれは変数である
    private LexicalUnit lexicalName(char c, PushbackReader sorce) throws IOException {
        String name = "";
        name += (char) c;
        while (true) {
            char ci2 = (char) sorce.read(); //次の文字列を読み込む
            
            if((ci2 == '=') && (c == '>')){
                return new LexicalUnit(LexicalType.LE);
            }else if((ci2 == '=') && (c == '<')){
               return new LexicalUnit(LexicalType.GE);  
            }else if((ci2 == '>') && (c == '<')){
                return new LexicalUnit(LexicalType.NE);  
            }else if(c == '<'){
                return new LexicalUnit(LexicalType.LT);
            }else if(c == '>'){
                return new LexicalUnit(LexicalType.GT);
            }
            if (!(ci2 >= 'a' && ci2 <= 'z') || (ci2 >= 'A' && ci2 <= 'Z')) {//もし読み込んだけど数字ではなかった場合
                sorce.unread(ci2);
                //unread 読まなかったことにする。
                break;
            } else {
                name += ci2;//数字であればnumsに値を追加する。
            }
        }
        if (nameMap.containsKey(name)) {
            return (nameMap.get(name));
        } else {
            //System.out.println("変数" + name + "を認識");
        }
        valueImpl hensuName = new valueImpl(name);
        //ここでリターンするときに数字も一緒にINTVALと一緒に返したい
        //System.out.println(hensuName.getSValue());
        return new LexicalUnit(LexicalType.NAME, hensuName);//形だけではなく値も作る
        //ここでつくった　valueimplが役に立つのである。
        //ここまでこれを作ってきたがこれはサブルーチンなのでどうしましょう
    }

    @Override
    public boolean expect(LexicalType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unget(LexicalUnit bk) {
        hozon.push(bk);
        
    }

    private void init() {
        map = new HashMap<>();
        map.put('=', new LexicalUnit(LexicalType.EQ));
        map.put('\n', new LexicalUnit(LexicalType.NL));
        map.put('.', new LexicalUnit(LexicalType.DOT));
        map.put('+', new LexicalUnit(LexicalType.ADD));
        map.put('-', new LexicalUnit(LexicalType.SUB));
        map.put('*', new LexicalUnit(LexicalType.MUL));
        map.put('/', new LexicalUnit(LexicalType.DIV));
        map.put(')', new LexicalUnit(LexicalType.LP));
        map.put('(', new LexicalUnit(LexicalType.RP));
        map.put(',', new LexicalUnit(LexicalType.COMMA));
        map.put('<', new LexicalUnit(LexicalType.LT));
        map.put('>', new LexicalUnit(LexicalType.GT));

        nameMap = new HashMap<>();
        nameMap.put("if", new LexicalUnit(LexicalType.IF));
        nameMap.put("then", new LexicalUnit(LexicalType.THEN));
        nameMap.put("else", new LexicalUnit(LexicalType.ELSE));
        nameMap.put("elseif", new LexicalUnit(LexicalType.ELSEIF));
        nameMap.put("endif", new LexicalUnit(LexicalType.ENDIF));
        nameMap.put("for", new LexicalUnit(LexicalType.FOR));
        nameMap.put("forall", new LexicalUnit(LexicalType.FORALL));
        nameMap.put("next", new LexicalUnit(LexicalType.NEXT));
        nameMap.put("func", new LexicalUnit(LexicalType.FUNC));
        nameMap.put("dim", new LexicalUnit(LexicalType.DIM));
        nameMap.put("as", new LexicalUnit(LexicalType.AS));
        nameMap.put("end", new LexicalUnit(LexicalType.END));
        nameMap.put("while", new LexicalUnit(LexicalType.WHILE));
        nameMap.put("hana", new LexicalUnit(LexicalType.HANA));
        nameMap.put("do", new LexicalUnit(LexicalType.DO));
        nameMap.put("print", new LexicalUnit(LexicalType.PRINT));
        nameMap.put("printer", new LexicalUnit(LexicalType.PRINTER));
        nameMap.put("until", new LexicalUnit(LexicalType.UNTIL));
        nameMap.put("loop", new LexicalUnit(LexicalType.LOOP));
        nameMap.put("to", new LexicalUnit(LexicalType.TO));
        nameMap.put("wend", new LexicalUnit(LexicalType.WEND));
        
        


    }

}
