package newlang3;
/*これは結構強敵なのだが、ここですべての型変換を作成する.
 //ここでプログラミング言語が扱う値について考えるべき
 元々の方がInteger Boolean　なのかを覚えておけ


 以下は絶対に保持して置かなければならない。
 値
 形

 */

public class valueImpl implements Value {

    String stringNums;
    int intNums = 0;
    double doubleNums = 0;
    boolean booleanNums = false;
    int type = 0;

    public valueImpl(String s) {
        stringNums = s;
        type = 1;

    }

    public valueImpl(int s) {
        intNums = s;
        type = 2;
        //newでLexicalUnitのインスタンスを作成して、値を保存させる。
    }

    public valueImpl(double s) {
        doubleNums = s;
        type = 3;
    }

    public valueImpl(boolean s) {
        booleanNums = s;
        type = 4;
    }

    public void setNum(int a) {
        intNums = a;
    }

    public void setNumD(double a) {
        doubleNums = a;
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

    
    
    public int getTypeNum() {//タイプとして値を持ってくる
        return type;
    }

    @Override
    public ValueType getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
