/**
 * Created by Melvin Wang on 2017-02-23.
 */
public class RandomTests {
    public static void main(String[] args) {

        //test 1
        System.out.println("Test1: ");
        for (int i = 1; i <= 5; i++) {
            System.out.println(Utils.generateRandomCode(i));
        }


        //test 2
        System.out.println("\nTest2: ");
        System.out.println(Utils.isANumber("897"));
        System.out.println(Utils.isANumber("7G896"));

        //test 3
        System.out.println("\nTest 3: ");
        System.out.println(Utils.leftPad("456", 6, '0'));
        System.out.println(Utils.leftPad("456", 2, '0'));
        System.out.println(Utils.leftPad("", 4, '0'));

        //test 4
        System.out.println("\nTest 4: ");
        System.out.println(Utils.removeChars("Hello", 'l'));
        System.out.println(Utils.removeChars("00034255", '0'));
        System.out.println(Utils.removeChars("Holiday soon", 'f'));

        //test 5
        System.out.println("\nTest 5: ");
        System.out.println("Thread will be paused for 100 ms. Time before sleep is: " + Runtime.getRuntime());
        Utils.delay(100);
        System.out.println(Runtime.getRuntime());
    }
}
