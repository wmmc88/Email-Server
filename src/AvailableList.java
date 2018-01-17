public class AvailableList {
    private Available head = null;
    private Available tail = null;

    public AvailableList() {
        head = null;
        tail = null;
    }

    public Available getHead() {
        return head;
    }

    public Available getTail() {
        return tail;
    }

    public void setHead(Available p) {
        head = p;
    }

    public void setTail(Available p) {
        tail = p;
    }

    public void addRecord(int recNo) {
        Available p = new Available(recNo);

        if (head == null) {
            head = p;
        } else {
            tail.setNext(p);
        }

        tail = p;

    }

    public int getNextRecord() {
        int r = Globals.EMPTY_AVAILABLE_LIST;
        if (head != null) {
            r = head.getRecordNumber();
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
        }
        return r;
    }

    public String toString() {
        String s = "";
        for (Available p = head; p != null; p = p.getNext()) {
            s += "Node: " + p.getRecordNumber() + "\n";
        }
        return s.equals("") ? "empty" : s;
    }
}
