import java.util.Arrays;
import tree.bst.BinarySearchTree;

class Main {
    public static void main(String args[]) {
        int[] randInts = new int[10];
        for (int i = 0; i <= randInts.length - 1; i++) {
            randInts[i] = (int) (Math.random() * 100);
            
        }
        System.out.println(Arrays.toString(randInts));

        BinarySearchTree bst = new BinarySearchTree();
        for (int randInt: randInts) {
            bst.insertNode(randInt);
        }

        bst.deleteNode(randInts[3]);

        int wantNum = 10;
        if (bst.valueExists(wantNum)) {
            System.out.println(wantNum + "はあります. ");
        } else {
            System.out.println(wantNum + "はありません. ");
        }
    }
}
