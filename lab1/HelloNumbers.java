public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0, y = 0;
        while (x < 10) {
            System.out.println(y + " ");
            x = x + 1;
            y = y + x;
        }
    }
}