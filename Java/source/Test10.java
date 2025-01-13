public class Test10 {
  public static void main(String[] args) {
    java.util.List<Integer> values = new java.util.ArrayList<>();
    values.add(4);
    values.add(5);
    values.add(6);
    for(java.util.Iterator<Integer> i = values.iterator(); i.hasNext(); ) {
      System.out.println(i.toString());
      int value = i.next();
      System.out.print(value + ", ");
    }
  } 
}