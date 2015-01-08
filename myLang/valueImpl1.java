package newlang3;
/*これは結構強敵なのだが、ここですべての型変換を作成する.
//ここでプログラミング言語が扱う値について考えるべき
元々の方がInteger Boolean　なのかを覚えておけ


以下は絶対に保持して置かなければならない。
値
形

*/

public class valueImpl1 implements Value {
    String stringNums;
    int intNums;
    double doubleNums;
    boolean booleanNums;

    
    public valueImpl1(String s){
        stringNums = s;

    }
    public valueImpl1(int s){
        intNums = s;   
   
        //newでLexicalUnitのインスタンスを作成して、値を保存させる。
    }
    public valueImpl1(double s){
        doubleNums = s;

    }
    public valueImpl1(boolean s){
        booleanNums = s;
       
    }    

    @Override
    public String getSValue() {//文字列として値を持ってくる

        return stringNums;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIValue() {//整数値として値を持ってくる
        /*switch(kata){
            //case 1:stringNumsを数値にして返す
            //case 2:int numsをそのまま帰す
        }
                */
        return intNums;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getDValue() {//ダブル型として持ってくる
        return doubleNums;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getBValue() {//true false
        return booleanNums;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValueType getType() {//タイプとして値を持ってくる
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
