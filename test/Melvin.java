/**
 * Created by Melvin Wang on 2017-02-23.
 */
public class Melvin {
    public static void main(String[] args) {
        int code = FileIO.openMessagesFile("_messages.txt");
        if (code == Globals.PROCESS_OK) {
            Record rec = null;
            for (int recNo = 0; recNo < 1000; recNo++) {
                String text = Utils.leftPad("" + recNo, Globals.RECORD_DATA_LEN, '0');
                rec = new Record(text, Globals.END_OF_MESSAGE);
                rec.writeToMessagesFile(recNo, Globals.APPEND);
            }
            FileIO.closeMessagesFile();
        }
        else{
            System.out.println("Error Opening File");
        }
    }
}
