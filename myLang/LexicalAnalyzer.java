package newlang3;

public interface LexicalAnalyzer {
  public LexicalUnit get();

  public boolean expect(LexicalType type);
  //字句の形を示す
  public void unget(LexicalUnit token);
}
