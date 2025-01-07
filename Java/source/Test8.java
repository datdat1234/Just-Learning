public class Test8 {
  protected void finalize() {
  System.out.println("Roar!");
  }
  public static void main(String[] args) {
    Test8 bear = new Test8();
    bear = null;
    System.gc();
    } 
  }