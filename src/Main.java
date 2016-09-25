public class Main {

    public static void main(String[] args) throws InterruptedException{
        BinaryTree <Integer> t = new BinaryTree<>();
        t.add(7);
        t.add(4);
        t.add(3);
        t.add(1);
        t.add(5);
        t.add(6);
        t.add(12);
        t.add(11);
        t.add(14);
        t.add(13);
        t.add(15);
        t.add(16);
        t.goLeftRootRight();
        t.goRootLeftRight();
        t.gotLeftRightRoot();
        t.draw();
        t.search(5);
        t.search(17);
        t.add(17);
        t.add(0);
        t.delete(3);
        t.delete(17);
        t.delete(7);
        t.delete(5);
    }
}
