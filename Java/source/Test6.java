public class Test6 {
  protected void finalize() {
  System.out.println("Calling finalize");
  }
  public static void main(String[] args) {
    Test6 f = new Test6();
  } }