public class LargerDemo {

  // need to include the type returned by a function (e.g. in this case, int)
  public static int larger(int x, int y) {
    if (x > y) {
      return x;
    }
    return y;
  }
  public static void main(String[] args) {
    System.err.println(larger(-5, 10));
  }
}


/**
 * 1. Function must be declated as part of a class
 * 
 * 2. A function that part of a class is called a method
 * Thus in Java, all functions are methods
 * 
 * 3. To define a function in Java, need to use "public static"
 * 
 * 4. All params of a function must have a declared type, and the
 * return value also must have a declared type
 * 
 * 5. Functions in Java return only one value
 */