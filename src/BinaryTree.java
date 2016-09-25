import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Drapegnik on 20.11.15.
 */
public class BinaryTree<T extends Comparable> {
    public BinaryTree() {
        this.root = null;
    }

    public class BinaryNode<T extends Comparable> {
        public BinaryNode() {
            this.value = null;
            this.left = null;
            this.right = null;
        }

        public BinaryNode(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        T value;
        BinaryNode<T> left, right;

    }

    private BinaryNode<T> root;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    private static final String empty = "  ";

    public void add(T value) throws InterruptedException {
        root = add(root, value);
        System.out.println(value + " successfully added:");
        draw(root, height(root), false, value, false);
    }

    private BinaryNode add(BinaryNode<T> node, T value) {
        if (node == null)
            node = new BinaryNode<>(value);
        else if (value.compareTo(node.value) < 0)
            node.left = add(node.left, value);
        else if (value.compareTo(node.value) > 0)
            node.right = add(node.right, value);
        else
            System.out.println("Your tree already has had this value!");
        return node;
    }

    public void delete(T value) throws InterruptedException {
        BinaryNode<T> node = del(value);
        if (node != null) {
            System.out.println(value + " successfully deleted:");
            draw(root, height(root), false, node.value, true);
        }
    }

    private BinaryNode del(T value) throws InterruptedException {
        BinaryNode father = search(root, value, false);
        if (father != null) {
            BinaryNode node;
            int isLeftSon;
            if (value.compareTo(father.value) == 0) {
                node = father;
                isLeftSon = -1;
            } else if (father.left != null) {
                if (father.right != null) {
                    node = value.compareTo(father.left.value) == 0 ? father.left : father.right;
                    isLeftSon = value.compareTo(father.left.value) == 0 ? 1 : 0;
                } else {
                    node = father.left;
                    isLeftSon = 1;
                }
            } else {
                node = father.right;
                isLeftSon = 0;
            }

            BinaryNode temp = node;
            if (node.right != null) {
                temp = node.right;
                BinaryNode temp2 = node;
                if (temp.left != null) {
                    while (temp.left != null) {
                        temp2 = temp;
                        temp = temp.left;
                    }
                    temp2.left = null;
                    temp.right = node.right;
                    temp.left = node.left;
                }
            } else
                temp = node.left;

            if (isLeftSon == 1)
                father.left = temp;
            else if (isLeftSon == 0)
                father.right = temp;
            else
                root = temp;

            return temp;
        } else
            return null;
    }

    public boolean search(T value) throws InterruptedException {
        return search(root, value, true) != null;
    }

    private BinaryNode search(BinaryNode<T> node, T value, Boolean print) throws InterruptedException {
        if (value.compareTo(node.value) < 0)
            if (node.left != null)
                if (!print && value.compareTo(node.left.value) == 0)
                    return node;
                else
                    return search(node.left, value, print);
            else {
                System.out.println("There is no " + value + " in tree");
                return null;
            }
        else if (value.compareTo(node.value) > 0)
            if (node.right != null)
                if (!print && value.compareTo(node.right.value) == 0)
                    return node;
                else
                    return search(node.right, value, print);
            else {
                System.out.println("There is no " + value + " in tree");
                return null;
            }
        else {
            if (print) {
                System.out.println(value + " was found:");
                draw(root, height(root), false, value, false);
            }
            return node;
        }

    }

    public void goLeftRootRight() {
        System.out.print("LeftRootRight: ");
        goLeftRootRight(root);
        System.out.println();
    }

    private void goLeftRootRight(BinaryNode<T> node) {
        if (node != null) {
            goLeftRootRight(node.left);
            System.out.print(node.value + " ");
            goLeftRootRight(node.right);
        }
    }

    public void goRootLeftRight() {
        System.out.print("RootLeftRight: ");
        goRootLeftRight(root);
        System.out.println();
    }

    private void goRootLeftRight(BinaryNode<T> node) {
        if (node != null) {
            System.out.print(node.value + " ");
            goRootLeftRight(node.left);
            goRootLeftRight(node.right);
        }
    }

    public void gotLeftRightRoot() {
        System.out.print("LeftRightRoot: ");
        goLeftRightRoot(root);
        System.out.println();
    }

    private void goLeftRightRoot(BinaryNode<T> node) {
        if (node != null) {
            goLeftRightRoot(node.left);
            goLeftRightRoot(node.right);
            System.out.print(node.value + " ");
        }
    }

    private int height(BinaryNode<T> node) {
        int max = -1;
        if (node.left != null)
            max = Math.max(max, height(node.left));
        if (node.right != null)
            max = Math.max(max, height(node.right));
        return ++max;
    }

    private HashMap<Integer, ArrayList<String>> a = new HashMap<>();

    private void addToMap(int key, String value) {
        if (a.get(key) == null)
            a.put(key, new ArrayList<>());
        a.get(key).add(value);
    }

    private void go(BinaryNode<T> node, int level) {
        if (level > height(root))
            return;
        if (node == root)
            addToMap(level, node.value.toString());

        if (node.left != null) {
            addToMap(level + 1, node.left.value.toString());
            go(node.left, level + 1);
        } else {
            addToMap(level + 1, empty);
            go(new BinaryNode<>(), level + 1);
        }


        if (node.right != null) {
            addToMap(level + 1, node.right.value.toString());
            go(node.right, level + 1);
        } else {
            addToMap(level + 1, empty);
            go(new BinaryNode<>(), level + 1);
        }

    }

    public void draw() throws InterruptedException {
        System.out.println("Tree:");
        draw(root, height(root), true, null, false);
    }

    private void draw(BinaryNode<T> node, int count, Boolean pause, T coloredvalue, Boolean isDel) throws InterruptedException {
        a = new HashMap<>();
        go(node, 0);
        for (int i = 0; i < count + 1; i++) {
            for (int j = 0; j < (2 * (1 << (count - i - 1)) - 1); j++)
                System.out.print(" ");

            for (int j = 0; j < a.get(i).size(); j++) {
                String col = ANSI_CYAN;
                if (coloredvalue != null)
                    if (isDel)
                        col = (a.get(i).get(j).equals(coloredvalue.toString())) ? ANSI_RED : ANSI_CYAN;
                    else
                        col = (a.get(i).get(j).equals(coloredvalue.toString())) ? ANSI_YELLOW : ANSI_CYAN;
                System.out.print(col + a.get(i).get(j) + ANSI_RESET);

                if (!a.get(i).get(j).equals("  ") && pause)
                    Thread.sleep(500);

                for (int k = 0; k < (2 * (1 << (count - i - 1)) - 1); k++)
                    System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}