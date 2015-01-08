package newlang3;
/*
ファイルを一行ずつ読み込むだけのクラスです
メソッド一覧
read()

*/


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Integer.*;
        
//ASCII文字コード
class codeStreams1 {
File file = new File("./src/newlang3/test.txt");
int index[] = new int[30000];
    public void mCode(){
        try {
            FileInputStream readed = new FileInputStream(file);
            int code;//
            while ((code = readed.read()) != -1) {
                System.out.println(code);
            }
            readed.close();
        } catch (IOException e) {
            System.out.println(e);
        }        
    }
    
    
    public int nextC(int now) throws FileNotFoundException, IOException{
        FileInputStream readed = new FileInputStream(file);
        int inNo = 0;
        while (readed.read() != -1) {
            inNo += 1;
            if(inNo == now){
                int code = readed.read();
                return code;
            }
        }
        now += 1;
        return 0;
    } 
    
}