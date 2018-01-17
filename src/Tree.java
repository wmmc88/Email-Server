import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.bind.annotation.XmlElementDecl;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by melvi on 2017-03-28.
 */
public class Tree {
    private TNode root = null;

    public Tree() {
        root = null;
    }

    public Tree(TNode r) {
        root = r;
    }

    public TNode getRoot() {
        return root;
    }

    public void setRoot(TNode r) {
        root = r;
    }

    public void insertNode(TNode p) {
        insertNodeRecursive(p);
        balance(p);
    }

    private void insertNodeRecursive(TNode p) {
        if (root == null) {
            root = p;
        } else if (p.getId().compareTo(root.getId()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft(p);
                p.setParent(root);
            } else {
                Tree tree = new Tree(root.getLeft());
                tree.insertNodeRecursive(p);
            }
        } else if (p.getId().compareTo(root.getId()) > 0) {
            if (root.getRight() == null) {
                root.setRight(p);
                p.setParent(root);
            } else {
                Tree tree = new Tree(root.getRight());
                tree.insertNodeRecursive(p);
            }
        } else {
            System.out.println("ERROR INSERTING NODE. CONFLICTING IDS!");
        }
    }

    public TNode findNode(String id) {
        if (root == null) {
            return null;
        } else if (id.equals(root.getId())) {
            return root;
        } else if (id.compareTo(root.getId()) < 0) {
            Tree tree = new Tree(root.getLeft());
            return tree.findNode(id);
        } else if (id.compareTo(root.getId()) > 0) {
            Tree tree = new Tree(root.getRight());
            return tree.findNode(id);
        } else {
            System.out.println("FATAL ERROR FINDING NODE!");
            return null;
        }

    }

    public void deleteNode(TNode p) {
        if (p != null) {
            TNode q;
            if (p.getLeft() == null && p.getRight() == null) {
                if (p == root) {
                    root = null;
                } else {
                    setParentChildLink(p, null);
                }
            } else if (p.getLeft() != null && p.getRight() == null || p.getLeft() == null && p.getRight() != null) {
                if (p.getLeft() != null) {
                    q = p.getLeft();
                } else {
                    q = p.getRight();
                }
                if (p == root) {
                    root = q;
                } else {
                    setParentChildLink(p, q);
                }
                q.setParent(p.getParent());
            } else if (p.getLeft() != null & p.getRight() != null) {
                q = p.getLeft();
                if (q.getRight() == null) {
                    if (p == root) {
                        root = q;
                    } else {
                        setParentChildLink(p, q);
                    }
                    q.setParent(p.getParent());
                    q.setRight(p.getRight());
                    q.getRight().setParent(q);
                } else {
                    while (q.getRight() != null) {
                        q = q.getRight();
                    }
                    q.getParent().setRight(q.getLeft());
                    if (q.getLeft() != null) {
                        q.getLeft().setParent(q.getParent());
                    }
                    if (p == root) {
                        root = q;
                    } else {
                        setParentChildLink(p, q);
                    }
                    q.setParent(p.getParent());
                    q.setLeft(p.getLeft());
                    p.getLeft().setParent(q);
                    q.setRight(p.getRight());
                    p.getRight().setParent(q);
                }
            }
        }
        balance(p);
    }

    private void setParentChildLink(TNode p, TNode q) {
        if (p.getParent().getLeft() == p) {
            p.getParent().setLeft(q);
        } else {
            p.getParent().setRight(q);
        }
    }

    public void printTree() {
        if (root != null) {
            Tree tree = null;
            tree = new Tree(root.getLeft());
            tree.printTree();
            System.out.println(root);
            tree = new Tree(root.getRight());
            tree.printTree();
        }
    }

    public void printTree(int level) {
        if (root != null) {
            Tree tree = null;

            tree = new Tree(root.getLeft());
            tree.printTree(level + 1);

            System.out.print(root);
            System.out.println(" in level " + level + " Record #" + root.getRecNo());

            tree = new Tree(root.getRight());
            tree.printTree(level + 1);

        }
    }

    public int height() {
        return root == null ? 0 : 1 + Math.max(new Tree(root.getLeft()).height(), new Tree(root.getRight()).height());
    }

    // rotate the tree towards the right with respect to this
    private TNode rightRotate() {
        TNode p = root.getLeft();
        p.setParent(root.getParent());

        root.setLeft(p.getRight());

        if (root.getLeft() != null)
            root.getLeft().setParent(root);

        p.setRight(root);
        p.getRight().setParent(p);

        return p;
    }

    // rotate the tree towards the left with respect to this
    private TNode leftRotate() {
        TNode p = root.getRight();
        p.setParent(root.getParent());

        root.setRight(p.getLeft());

        if (root.getRight() != null)
            root.getRight().setParent(root);

        p.setLeft(root);
        p.getLeft().setParent(p);

        return p;
    }

    // calculate the AVL balance factor: height of left child of this - height of right child of this
    private int balanceFactor() {
        Tree lTree = new Tree(this.getRoot().getLeft());
        Tree rTree = new Tree(this.getRoot().getRight());
        return lTree.height() - rTree.height();
    }

    // balance the tree starting at this according to AVL self-balancing algorithm
    // if called after insertion, p is the node that has just been inserted
    // if called after deletion, p will have to be one of these two:
    //
    //  1) a leaf that has been deleted: In the calling method, the passed
    //     parameter will still have the link information even if the node has been
    //     deleted in deleteNode(); thus it can be safely passed into here
    //  2) the node that has been shifted up and has taken q's place since the parent of this node may
    //     experience unbalancing
    //
    //  It might be best to do the call to balance() from within the insertNodeRecursive() and
    //  the deleteNode() so that all this node information is available

    private void balance(TNode p) {
        if (p != null) {
            TNode ancestor = p;

            while (ancestor != null) {
                Tree ancestorTree = new Tree(ancestor);

                if (ancestorTree.balanceFactor() == -2) {
                    Tree rTree = new Tree(ancestorTree.root.getRight());

                    int rTreeBalanceFactor = rTree.balanceFactor();
                    if (rTreeBalanceFactor == -1 || rTreeBalanceFactor == 0) { //0 happens in delete case 7a
                        if (ancestor == root)
                            root = ancestorTree.leftRotate();
                        else {

                            // determine if the ancestor is a left or a right child

                            if (ancestor.getParent().getLeft() == ancestor)
                                ancestor.getParent().setLeft(ancestorTree.leftRotate());
                            else
                                ancestor.getParent().setRight(ancestorTree.leftRotate());
                        }
                    } else if (rTreeBalanceFactor == 1 || rTreeBalanceFactor == 0) {
                        ancestor.setRight(rTree.rightRotate());
                        if (ancestor == root)
                            root = ancestorTree.leftRotate();
                        else {

                            // determine if the ancestor is a left or a right child

                            if (ancestor.getParent().getLeft() == ancestor)
                                ancestor.getParent().setLeft(ancestorTree.leftRotate());
                            else
                                ancestor.getParent().setRight(ancestorTree.leftRotate());
                        }
                    }
                } else if (ancestorTree.balanceFactor() == 2) {
                    Tree lTree = new Tree(ancestorTree.root.getLeft());

                    int lTreeBalanceFactor = lTree.balanceFactor();
                    if (lTreeBalanceFactor == 1 || lTreeBalanceFactor == 0) { // 0 this symmetrical case of 7a does not happen. it's here to make the method symmetric and possible future optimization
                        if (ancestor == root)
                            root = ancestorTree.rightRotate();
                        else {

                            // determine if the ancestor is a left or a right child

                            if (ancestor.getParent().getLeft() == ancestor)
                                ancestor.getParent().setLeft(ancestorTree.rightRotate());
                            else
                                ancestor.getParent().setRight(ancestorTree.rightRotate());
                        }
                    } else if (lTreeBalanceFactor == -1 || lTreeBalanceFactor == 0) {
                        ancestor.setLeft(lTree.leftRotate());
                        if (ancestor == root)
                            root = ancestorTree.rightRotate();
                        else {

                            // determine if the ancestor is a left or a right child

                            if (ancestor.getParent().getLeft() == ancestor)
                                ancestor.getParent().setLeft(ancestorTree.rightRotate());
                            else
                                ancestor.getParent().setRight(ancestorTree.rightRotate());
                        }
                    }
                }
                ancestor = ancestor.getParent(); // ancestor may have changed in rotation. ancestor keeps moving up anyway and will reach the null of the root's parent
            }
        }
    }


    // the following method prints the nodes of a tree level by level starting at level zero
    public void breadthFirstPrint() {
        int treeHeight = this.height();
        for (int level = 0; level <= treeHeight; level++) {
            printNodesOfOneLevel(this.root, level, 0);
        }
        System.out.println();
    }

    // print all nodes at level n
    private void printNodesOfOneLevel(TNode p, int level, int currentLevel) {
        if (p != null) {
            if (currentLevel == level) {
                System.out.print(p.getId());
                System.out.print(" in level " + level);
                if (p.getParent() == null) {
                    System.out.println(" Root");
                } else if (p.getParent().getLeft() == p) {
                    System.out.println(" Left child of " + p.getParent().getId());
                } else {
                    System.out.println(" Right child of " + p.getParent().getId());
                }
            } else {
                printNodesOfOneLevel(p.getLeft(), level, currentLevel + 1);
                printNodesOfOneLevel(p.getRight(), level, currentLevel + 1);
            }
        }
    }

    /////////////////////////////////////////////
//////CURRENTLY USELESS CODE///////////
//////////////////////////////////////////////////
    private void findPartialID(String id) {
        String startRange = id + "00000000000000000";
        String endRange = id + "99999999999999999";

        //finds highest node that is in range
        TNode p = this.root;
        while (!(p.getId().startsWith(id))) {
            if (p.getId().compareTo(startRange) < 0) {
                p = p.getRight();
            } else if (p.getId().compareTo(endRange) > 0) {
                p = p.getLeft();
            } else {
                System.out.println("ERROR FINDING ID RANGE");
            }
        }
        Tree tree = new Tree(p);
//        tree.printMatchingNodes(id);
    }
/////////////////////////////////////////////////////////////

    // prints in ascending order the node information in the range [start.getId(), end.getId()]
    // using a modified depth-first-search
    //
    // Restriction (checked in code): both start and end must be non-null
    // Restriction (checked in code): start.getId() must be less than or equal to end.getId()
    //
    // The start and end nodes are given as parameters and have been previously located with
    // a search method with order of complexity O(log n)
    public void printTree(TNode start, TNode end) {
        // check restriction: start and end cannot be null
        if (start != null && end != null) {
            // check restriction: id of start must be less or equal to id of end
            if (start.getId().compareTo(end.getId()) <= 0) {
                // if start and end are not on the same side of the tree then proceed with printing
                if (start.getId().compareTo(root.getId()) <= 0 && root.getId().compareTo(end.getId()) <= 0) {
                    TNode p = start;
                    while (p != null && p != root) { // work on nodes left of the root
                        System.out.println(p);
                        Tree rTree = new Tree(p.getRight());
                        rTree.printTree();
                        // move up to an ancestor that is within the range [start.getId(), end.getId]
                        // but exit if null is found while percolating upwards; short circuit evaluation in effect
                        do {
                            p = p.getParent();
                        } while (p != null && p.getId().compareTo(start.getId()) < 0);
                    }
                    System.out.println(root); // print the root
                    p = root.getRight(); // work on nodes right of the root
                    while (p != null) {
                        if (p.getId().compareTo(end.getId()) <= 0) {
                            Tree lTree = new Tree(p.getLeft());
                            lTree.printTree();
                            System.out.println(p);
                            p = p.getRight();
                        } else {
                            p = p.getLeft();
                        }
                    }
                } else if (end.getId().compareTo(root.getId()) < 0) { // if start and end are in left subtree
                    Tree lTree = new Tree(root.getLeft());
                    lTree.printTree(start, end);
                } else if (start.getId().compareTo(root.getId()) > 0) { // if start and end are in right subtree
                    Tree rTree = new Tree(root.getRight());
                    rTree.printTree(start, end);
                }
            }
        }
    }

    public void breadthFirstSave(String filename) {
        try {
            RandomAccessFile f = new RandomAccessFile(filename, "rw");
            f.setLength(0);
            int h = height();
            for (int i = 0; i < h; i++) {
                writeLevel(i, f);
            }
            f.close();
        } catch (IOException e) {
            System.out.println("Error opening file" + filename);
            e.printStackTrace();
        }
    }

    private void writeLevel(int level, RandomAccessFile f) {
        if (level == 0) {
            try {
                if (root != null) {
                    f.writeBytes(root.getId());
                    f.writeInt(root.getRecNo());
                }
            } catch (IOException e) {
                System.out.println("Error writing in the Tree File");
                e.printStackTrace();
            }
        } else {
            Tree tree = new Tree(root.getLeft());
            tree.writeLevel(level - 1, f);
            tree = new Tree(root.getRight());
            tree.writeLevel(level - 1, f);
        }
    }

    public void breathFirstRetrieve(String filename) {
        try {
            byte[] identificationArray = new byte[Globals.IDENTIFICATION_LEN];
            RandomAccessFile f = new RandomAccessFile(filename, "rw");
            for (int node = 0, totalNodes = (int) (f.length() / (Globals.IDENTIFICATION_LEN + 4)); node < totalNodes; node++) {
                f.read(identificationArray);
                this.insertNode(new TNode(new String(identificationArray), f.readInt(), null, null, null));
            }
            f.close();
        } catch (IOException e) {
            System.out.printf("Error retrieving file: %s\n", filename);
        }
    }
}

