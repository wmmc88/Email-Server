import org.junit.jupiter.api.Test;

/**
 * Created by Melvin Wang on 2017-02-26.
 */

public class EmailServerTest {

    @Test
    void testing01_01() {
        System.out.println();
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            String message = "Hello how are you?";
            Record rec = new Record(message, Globals.END_OF_MESSAGE);
            rec.dumpData();
            code = FileIO.closeMessagesFile();
            if (code == Globals.PROCESS_OK) {
                System.out.println("File closed ok");
            }
        }
    }

    @Test
    void testing01_02() {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            String message = "Hello how are you?";
            Record rec = new Record(message, Globals.END_OF_MESSAGE);
            code = rec.writeToMessagesFile(0, Globals.APPEND);
            code = FileIO.closeMessagesFile();
            if (code == Globals.PROCESS_OK) {
                System.out.println("File closed ok");
            }
        }
    }

    @Test
    void testing01_03() {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            Record rec = new Record();
            code = rec.readFromMessagesFile(0);
            System.out.println(rec);
            code = FileIO.closeMessagesFile();
            if (code == Globals.PROCESS_OK) {
                System.out.println("File closed ok");
            }
        }
    }

    @Test
    void testing01_04() {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            Record rec = null;
            rec = new Record("Today is very cold", Globals.END_OF_MESSAGE);
            rec.writeToMessagesFile(0, Globals.MODIFY);

            rec = new Record("I think I will go to lunch", Globals.END_OF_MESSAGE);
            rec.writeToMessagesFile(1, Globals.MODIFY);

            rec = new Record("Hello Suzie: I think my mark in ICS is getting better\n", Globals.END_OF_MESSAGE);
            rec.writeToMessagesFile(2, Globals.MODIFY);

            code = FileIO.closeMessagesFile();
            if (code == Globals.PROCESS_OK) {
                System.out.println("File closed ok");
            }
        }
    }

    @Test
    void testing01_05() {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            Record rec = new Record();
            code = rec.readFromMessagesFile(1);
            System.out.println(rec);
            code = FileIO.closeMessagesFile();
            if (code == Globals.PROCESS_OK) {
                System.out.println("File closed ok");
            }
        }
    }

    @Test
    void testing01_06() {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            Record rec = new Record();
            for (int recordNumber = 0; recordNumber < Globals.totalRecordsInMessagesFile; recordNumber++) {
                rec.readFromMessagesFile(recordNumber);
                System.out.println(rec);
            }
            code = FileIO.closeMessagesFile();
            if (code == Globals.PROCESS_OK) {
                System.out.println("File closed ok");
            }
        }
    }


    void testing01_07() {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            Record rec = new Record();
            int recordNumber = -1;
            do {
                recordNumber = (int) (Math.random() * 3);
                rec.readFromMessagesFile(recordNumber);
                System.out.println(rec);
            } while (true);
            //FileIO.closeMessagesFile();
        }
    }

    @Test
    void utilsTesting() {
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
        long beforeTime;
        long afterTime;
        System.out.println("Thread will be paused for 100 ms. Time before sleep is: " + (beforeTime = System.currentTimeMillis()));
        Utils.delay(100);
        System.out.println("Time after sleep is: " + (afterTime = System.currentTimeMillis()));
        System.out.println("Time difference is: " + (afterTime - beforeTime));
    }

    @Test
    void MessageClassPrintingTest() {
        int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
        if (error == Globals.PROCESS_OK) {
            Message message = new Message();
            message.printFromMessagesFile(0);
            System.out.println();
            message.printFromMessagesFile(1);
            System.out.println();
            message.printFromMessagesFile(2);
            System.out.println();
            message.printFromMessagesFile(4);
            FileIO.closeMessagesFile();

        } else {
            System.out.println("Cannot open _messages.txt file");
        }
        FileIO.closeMessagesFile();

    }

    @Test
    void MessageWriting(){
        FileIO.openMessagesFile("_messages3.txt");

        Message message = new Message();
        FileIO.retrieveAvailableList("_available3.txt");
        System.out.println(Globals.availableList);
        String s = "CAAABBBCCC000AAA88822222222+CASE6~test";
        message.setMessage(s);
        message.writeToMessagesFile();
        System.out.println(Globals.availableList);
        FileIO.saveAvailableList("_available3.txt");
        FileIO.closeMessagesFile();
    }

    @Test
    void testAvailableListRetrieval(){
        int error = FileIO.retrieveAvailableList("_available3.txt");
        System.out.println(Globals.availableList);
    }

    @Test
    void saveLevelByLevel(){
        Tree t = new Tree();
        t.insertNode(new TNode("AB", 16,null, null, null));
        t.insertNode(new TNode("CD",7, null, null, null ));
        t.insertNode(new TNode("EF", 4, null, null, null));

        t.breadthFirstPrint();

        t.breadthFirstSave("testSave.dat");
    }
    @Test
    void retrieveTest(){
        Tree t = new Tree();
        t.breathFirstRetrieve("testSave.dat");
        t.breadthFirstPrint();
    }
}
