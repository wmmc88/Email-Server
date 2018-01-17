import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Melvin Wang on 2017-02-12.
 */
public class FileIO {
    public static int openMessagesFile(String fileName) {
        try {
            Globals.msg = new RandomAccessFile(fileName, "rw");
            Globals.totalRecordsInMessagesFile = (int) (Globals.msg.length() / Globals.RECORD_LEN);

            return Globals.PROCESS_OK;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return Globals.PROCESS_ERROR;
        }
    }

    public static int closeMessagesFile() {
        try {
            Globals.msg.close();
            return Globals.PROCESS_OK;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return Globals.PROCESS_ERROR;
        }
    }

    public static int saveAvailableList(String filename) {
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(filename, "rw");
            f.setLength(0);
            Available p = Globals.availableList.getHead();
            while (p != null) {
                f.writeInt(p.getRecordNumber());
                p = p.getNext();
            }
            f.close();
            return Globals.PROCESS_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return Globals.PROCESS_ERROR;
        }
    }

    public static int retrieveAvailableList(String filename) {
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(filename, "rw");
            f.seek(0);
            Globals.availableList = new AvailableList();
            int totalDeleteRecords = (int) (f.length() / Globals.AVAILABLE_NODE_RECORD_NUMBER_LEN);
            for (int node = 0; node < totalDeleteRecords; node++) {
                Globals.availableList.addRecord(f.readInt());
            }
            f.close();
            return Globals.PROCESS_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return Globals.PROCESS_ERROR;
        }
    }
}
