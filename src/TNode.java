/**
 * Created by melvi on 2017-03-28.
 */
public class TNode {
    private String id = "";
    private TNode left = null;
    private TNode right = null;
    private TNode parent = null;
    private int recNo = -1;

    public TNode() {
        id = "";
        left = null;
        right = null;
        parent = null;
        recNo = -1;
    }

    public TNode(String s, int i, TNode l, TNode r, TNode p) {
        id = s;
        left = l;
        right = r;
        parent = p;
        recNo = i;
    }

    public String getId() {
        return id;
    }

    public TNode getLeft() {
        return left;
    }

    public TNode getRight() {
        return right;
    }

    public TNode getParent() {
        return parent;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setId(String i) {
        id = i;
    }

    public void setLeft(TNode l) {
        left = l;
    }

    public void setRight(TNode r) {
        right = r;
    }

    public void setParent(TNode p) {
        parent = p;
    }

    public void setRecNo(int i) {
        recNo = i;
    }

    public String toString() {
        if (this == null) {
            return null;
        } else {
            return "Id: " + id;
        }
    }
}


