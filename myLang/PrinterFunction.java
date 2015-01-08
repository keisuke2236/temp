package newlang3;

public class PrinterFunction extends Function {

    int mode = 0;

    PrinterFunction() {//const
        this.mode = 1;
    }

    @Override
    public valueImpl getValue() {
        switch (mode) {
            case 1:
                System.out.println();
                System.out.println("****************");
                System.out.println("Hello Basic !");
                System.out.println("****************");
                break;
        }
        return null;
    }

    @Override
    public valueImpl getValue(LexicalUnit aug1) {
        switch (mode) {
            case 1:
                System.out.println();
                System.out.println("****************");
                System.out.println("Hello Basic !");
                System.out.println(aug1.toString());
                System.out.println("****************");
                break;
        }
        return null;
    }

    @Override
    public valueImpl getValue(LexicalUnit aug1,LexicalUnit aug2) {
        switch (mode) {
            case 1:
                System.out.println();
                System.out.println("****************");
                System.out.println("Hello Basic !");
                System.out.println(aug1.toString());
                System.out.println(aug2.toString());
                System.out.println("****************");
                break;
        }
        return null;
    }

    @Override
    public valueImpl getValue(LexicalUnit aug1,LexicalUnit aug2,LexicalUnit aug3) {
        switch (mode) {
            case 1:
                System.out.println();
                System.out.println("****************");
                System.out.println("Hello Basic !");
                System.out.println(aug1.toString());
                System.out.println(aug2.toString());
                System.out.println(aug3.toString());
                System.out.println("****************");
                break;
        }
        return null;
    }

}
