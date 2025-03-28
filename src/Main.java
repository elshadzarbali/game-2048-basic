import base.Base;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // The codes of this game are belong to me.
        // All codes are 100% mine.
        int dim = 1;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please, enter dimension of game!");
        while (dim < 2) {
            System.out.print("Dimension: ");
            dim = scan.nextInt();
            if (dim < 2) {
                System.out.println("Dimension must be minimum 2!");
            }
        }
        Base start = new Base(dim);
        start.run();
    }
}