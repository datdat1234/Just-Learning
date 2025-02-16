public class Test12 {
  public static void main(String[] args) {
      String name = "Webby";
      hello(name);
      System.out.println("Hash code (reference): " + name.hashCode());
      System.out.println("Reference: " + name);
  } 

  public static void hello(String name) {
      System.out.println("Hash code (hello method): " + name.hashCode());
      System.out.println("Reference: " + name);
  }
}