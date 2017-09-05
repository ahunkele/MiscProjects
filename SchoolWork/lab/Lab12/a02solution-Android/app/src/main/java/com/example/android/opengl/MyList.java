// H343 Midterm (March 1st, Spring 2017) @ IUB

// question 8:



public class MyList {
    private int[] numList;
    private int maxSize;
    private int currSize;
    public MyList (int size) {
        maxSize = size;
        numList = new int[maxSize];
        currSize = 0;
    }
    public void add (int anum) {
        assert currSize < maxSize : "exceed maxsize";
        numList[currSize ++] = anum;
    }
    //implement your removeKTh() method below
    public void removeKTh(int k) {
        // ensure that the list is not empty
        if (currSize == 0) return;

        // if you need the first element: tmp = numList[0];
        // removed k-th element
        for (int i = k; i < currSize; i++) {
            // shift down all elements by -1
            numList[i-1] = numList[i];
        }
        currSize-- ; // update list size
    }

    // printout for debugging:
    public void printIt() {
        for (int i = 0; i < currSize ; i ++) {
            System.out.print(" " + numList[i]);
        }
        System.out.println(" ");
        System.out.println("maxSize=" + maxSize + " currSize=" + currSize);
    }


    // main program for testing:
    public static void main(String[] argv) {
        MyList theList = new MyList(100);

        // test the list with some integers:
        for (int i = 0; i < 20; i ++) {
            theList.add(i);
            System.out.println(i + " theList= " + theList);
        }
        theList.printIt();
        theList.removeKTh(5);
        theList.printIt();
        theList.removeKTh(10);
        theList.printIt();
        theList.removeKTh(10);
        theList.printIt();
    }
}

