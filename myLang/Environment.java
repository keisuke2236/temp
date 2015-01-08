package newlang3;

import java.util.Hashtable;
import java.util.function.Function;

public class Environment implements Cloneable {

    LexicalAnalyzer input;
    Hashtable var_table;//hashテーブルを動的に作成して保存しておく
    Hashtable library;

    public Environment(LexicalAnalyzer my_input) {
        input = my_input;
        library = new Hashtable();
        library.put(LexicalType.PRINTER, new PrinterFunction());
        library.put(LexicalType.HANA, new hanachan());
        var_table = new Hashtable();
    }

    public LexicalAnalyzer getInput() {
        return input;
    }

    public Function getFunction(String fname) {
        return (Function) library.get(fname);
    }

    //同じ変数は１つまで法則
    public Variable getVariable(String vname) {
        Variable v;
        v = (Variable) var_table.get(vname);
        if (v == null) {
            v = new Variable(vname);
            var_table.put(vname, v);
        }
        return v;
    }
}
