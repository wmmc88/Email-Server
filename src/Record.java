import java.io.IOException;

/**
 * Created by Melvin Wang on 2017-02-12.
 * <p>
 * This class will manage the most fundamental reading and writing of information to the _messages.txt file
 * <p>
 * One record is a fixed number of bytes. This class will contain the necessary variables and methods to be able to:
 * 1 . R e a d o n e r e c o r d f r o m t h e _ m e s s a g e s . t x t f i l e a n d s t o r e i t i n a n a r r a y o f b y t e s
 * 2 . W r i t e a n a r r a y o f b y t e s a s o n e r e c o r d t o t h e _ m e s s a g e s . t x t f i l e
 * <p>
 * The size of the record is determined by the contant RECORD_LEN as specified in the Globals class
 */
public class Record {
    private byte[] data = new byte[Globals.RECORD_DATA_LEN];
    private int next = Globals.END_OF_MESSAGE;

    public Record() {
        for (int i = 0, j = data.length; i < j; i++) {
            data[i] = (byte) Globals.BLANK;
        }
        next = Globals.END_OF_MESSAGE;
    }

    public Record(String s, int nextRecord) {
        setData(s, nextRecord);
    }

    public String getData() {
        String s = "";
        for (int i = 0, j = data.length; i < j; i++) {
            s += (char) data[i];
        }
        return s;
    }

    public void setData(String text, int nextRecord) {
        for (int i = 0, j = text.length(); i < j; i++) {
            data[i] = (byte) text.charAt(i);
        }

        for (int i = text.length(), j = data.length; i < j; i++) {
            data[i] = (byte) Globals.BLANK;
        }

        next = nextRecord;
    }

    public int getNext() {
        return next;
    }

    /*Method that reads a record from _messages.txt, from a given record number position, and puts the record in the data byte array.
    The method returns a integer value that denotes whether the process was successful. The method also reads the integer for next
    */
    public int readFromMessagesFile(int recordNumber) {
        try {
            Globals.msg.seek(recordNumber * Globals.RECORD_LEN);
            int bytes = Globals.msg.read(data);
            next = Globals.msg.readInt();
            return Globals.PROCESS_OK;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return Globals.PROCESS_ERROR;
        }
    }

    // Method that writes a record to _messages.txt. The data is in the data array and is written in the record position recordNumber.
    public int writeToMessagesFile(int recordNumber, int mode) {
        try {
            Globals.msg.seek(recordNumber * Globals.RECORD_LEN);
            Globals.msg.write(data);
            Globals.msg.writeInt(next);
            if (mode == Globals.APPEND) {
                Globals.totalRecordsInMessagesFile++;
            }
            return Globals.PROCESS_OK;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return Globals.PROCESS_ERROR;
        }
    }

    public void deleteFromMessagesFile(int recNo) {
        int error = readFromMessagesFile(recNo);
        if (error == Globals.PROCESS_OK) {
            data[0] = (byte) Globals.DELETED;
            error = writeToMessagesFile(recNo, Globals.MODIFY);
            if (error == Globals.PROCESS_OK) {
                Globals.availableList.addRecord((recNo));
            } else {
                System.out.println("Error writing record in deleteFrom Messages()");
            }
        } else {
            System.out.println("Error reading record in deleteFromMessagesFile");
        }
    }

    public void dumpData() {
        for (int i = 0; i < data.length; i++) {
            System.out.println("byte " + i + " $" + (char) data[i] + "$");
        }
    }

    public String toString() {
        return getData() + next;
    }

}
