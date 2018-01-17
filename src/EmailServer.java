import java.util.Scanner;

/**
 * Created by Melvin Wang on 2017-02-12.
 */
public class EmailServer {
    public static void main(String[] args) {

        Tree senderIndex = new Tree();
        Message message = new Message();

        if (FileIO.openMessagesFile(Globals.MESSAGES_FILE) == Globals.PROCESS_OK) {
            if (FileIO.retrieveAvailableList(Globals.AVAILABLE_LIST_FILE) == Globals.PROCESS_OK) {
                Scanner input = new Scanner(System.in);
                int command = -1;
                do {
                    System.out.print("Available List:\n" +
                            Globals.availableList + '\n' +
                            "Server options:\n" +
                            "1. Add Message\n" +
                            "2. Delete Message\n" +
                            "3. Print all messages\n" +
                            "4. Find message (sender ID + receiver ID + dateTime)\n" +
                            "5. Find message (receiver ID + sender ID + dateTime\n" +
                            "6. Find messages from partial identifications\n" +
                            "7. Rebuild available list\n" +
                            "8. Rebuild trees\n" +
                            "9. Breadth-first print of trees\n" +
                            "99. Quit\n\n" +
                            "Option -> ");
                    command = input.nextInt();

                    int recordNumber;
                    String identification;
                    TNode p;
                    switch (command) {

                        case 1:
                            System.out.print("Sender ID: ");
                            String senderID = input.next();

                            System.out.print("Receiver ID :");
                            String receiverID = input.next();

                            System.out.print("Date (8 digits): ");
                            String dateTime = input.next();
                            input.nextLine();
                            System.out.print("Subject: ");
                            String subject = input.nextLine();

                            System.out.print("Message: ");
                            String text = input.nextLine();

                            String entireMessage = "S" +
                                    senderID +
                                    receiverID +
                                    dateTime +
                                    Globals.FIRST_RECORD_OF_MESSAGE +
                                    subject +
                                    Globals.END_OF_SUBJECT_MARKER +
                                    text;

                            message.setMessage(entireMessage);
                            recordNumber = message.writeToMessagesFile();

                            identification = senderID + receiverID + dateTime;

                            p = new TNode(identification, recordNumber, null, null, null);
                            senderIndex.insertNode(p);
                            break;

                        case 2:
                            break;

                        case 3:
                            break;

                        case 4:
                            System.out.println("Sender message identification (senderId + receiverId + dateTime): ");
                            identification = input.next();

                            p = senderIndex.findNode(identification);

                            if (p != null) {
                                recordNumber = p.getRecNo();
                                System.out.println("At record number: " + recordNumber);
                                message.printFromMessagesFile(recordNumber);

                            } else {
                                System.out.println("Identification not found");
                            }
                            break;

                        case 5:
                            break;

                        case 6:
                            break;

                        case 7:
                            break;

                        case 8:
                            break;

                        case 9:
                            break;

                    }
                } while (command != 99);
                FileIO.saveAvailableList(Globals.AVAILABLE_LIST_FILE);
            } else {
                System.out.println("Error: Unable to open available list file.");
            }
            FileIO.closeMessagesFile();
        } else {
            System.out.println("Error Unable to open messages file.");
        }
    }
}
