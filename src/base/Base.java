package base;

import java.util.Random;
import java.util.Scanner;

public class Base {
    Random rand = new Random();
    Scanner input = new Scanner(System.in);
    int dim;
    int[][] map;
    boolean game, first;
    String console;
    String[] list = {"w", "a", "s", "d"};
    boolean[] test;
    boolean[] over;
    int[] records;
    int score, highScore, answer, count;


    public Base(int dim) {
        this.dim = dim;
        this.map = new int[dim][dim];
        this.score = 0;
        this.highScore = 0;
        this.count = 0;
        this.game = true;
        this.first = true;
        this.test = new boolean[dim];
        this.over = new boolean[4];
        this.records = new int[5];
    }

    public void run() {
        while (game) {
            boolean tester = false;
            prepare();
            while (true) {
                // I think, we don't need to false test elements.
//                for (int b = 0; b < test.length; b++) {
//                    test[b] = false;
//                }
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.print("Score: " + score);
                System.out.println("\tHigh Score : " + highScore(score));
                print();
                for (int i = 0; i < list.length; i++) {
                    test(list[i]);
                    for (int j = 0; j < test.length; j++) {
                        if (test[j] == false) {
                            over[i] = false;
                            break;
                        } else { // test[j] == true;
                            over[i] = true;
                        }
                    }
                }
                String cons = "";
                for (int i = 0; i < over.length; i++) {
                    if (over[i] == false) {
                        cons += list[i] + " ";
                    }
                }
                if (cons.length() == 0) {
                    System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
                    System.out.println("||                   GAME OVER                    ||");
                    System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
                    question();
                    break;
                }
                System.out.println("You have only " + (cons.length() / 2) + " choices: " + cons);
                System.out.print("Choice: ");
                console = input.next();
                if (console.equalsIgnoreCase(list[0]) || console.equalsIgnoreCase(list[1])
                        || console.equalsIgnoreCase(list[2]) || console.equalsIgnoreCase(list[3])) {
                } else {
                    System.out.println("Consoles are : w a s d");
                    continue;
                }
                test(console);
                for (int i = 0; i < test.length; i++) {
                    if (test[i] == false) {
                        tester = true;
                        break;
                    }
                }
                if (tester) {
                    break;
                }
            }
            result(console);
        }
    }

    public void prepare() {
        int x, y, randNum;
        boolean random = true;
        while (random) {
            x = rand.nextInt(dim);
            y = rand.nextInt(dim);
            randNum = rand.nextInt(4); // 1/4 chance to 4
            if (map[x][y] == 0) {
                if (randNum == 0) {
                    map[x][y] = 4;
                } else {
                    map[x][y] = 2;
                }
            } else {
                continue;
            }
            if (count != 0) {
                random = false;
            }
            count = 1;
        }

    }

    public void print() {
        int maxNum = num(myMax(map)); // how many digits is in max number of map
        String x;
        System.out.println("----------------------------------------------------");
        for (int a = 0; a < map.length; a++) {
            for (int b = 0; b < map[a].length; b++) {
                int num = num(map[a][b]); // how many digits is in map[a][b]
                if (num < maxNum) {
                    x = diff(num, maxNum);
                } else {
                    x = "";
                }
                System.out.print(map[a][b] + x + "       ");
            }
            System.out.println("");
        }
        System.out.println("----------------------------------------------------");
    }

    public int myMax(int[][] array) {
        int max = 0;
        int[] maxRow = new int[array.length];
        int temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < array[i].length; j++) {
                temp = Math.max(array[i][j - 1], array[i][j]);
                if (j == 1) {
                    maxRow[i] = temp;
                } else {
                    if (temp > maxRow[i]) {
                        maxRow[i] = temp;
                    }
                }
            }
        }
        for (int i = 1; i < maxRow.length; i++) {
            temp = Math.max(maxRow[i - 1], maxRow[i]);
            if (i == 1) {
                max = temp;
            } else {
                if (temp > max) {
                    max = temp;
                }
            }
        }
        return max;
    }

    public int num(int num) {
        int u = 1;
        double ten = 1;
        while (true) {
            if (num >= ten * 10) {
                ten *= 10;
                u++;
            } else {
                break;
            }
        }
        return u;
    }

    public String diff(int a, int b) {
        String diff = " ";
        for (int i = a; i < b - 1; i++) {
            diff += " ";
        }
        return diff;
    }

    public void result(String a) {
        if (a.equalsIgnoreCase("w")) { //up
            up();
        } else if (a.equalsIgnoreCase("s")) { // down
            down();
        } else if (a.equalsIgnoreCase("a")) { // left
            left();
        } else { // right
            right();
        }
    }

    public void up() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int zero = 1;
                while ((zero < map.length - i)) { // zero < 4 - i
                    if (map[i + zero][j] == 0) {
                        zero++;
                    } else if (map[i + zero][j] == map[i][j]) {
                        map[i][j] += map[i + zero][j];
                        map[i + zero][j] = 0;
                        score += map[i][j];
                        break;
                    } else if ((map[i][j] == 0) && (map[i + zero][j] != 0)) {
                        map[i][j] = map[i + zero][j];
                        map[i + zero][j] = 0;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void down() {
        for (int i = map.length - 1; i >= 0; i--) {
            for (int j = map[i].length - 1; j >= 0; j--) {
                int zero = 1;
                while ((zero <= i)) {
                    if (map[i - zero][j] == 0) {
                        zero++;
                    } else if (map[i - zero][j] == map[i][j]) {
                        map[i][j] += map[i - zero][j];
                        map[i - zero][j] = 0;
                        score += map[i][j];
                        break;
                    } else if ((map[i][j] == 0) && (map[i - zero][j] != 0)) {
                        map[i][j] = map[i - zero][j];
                        map[i - zero][j] = 0;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void left() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int zero = 1;
                while ((zero < map.length - j)) { // zero < 4 - j
                    if (map[i][j + zero] == 0) {
                        zero++;
                    } else if (map[i][j + zero] == map[i][j]) {
                        map[i][j] += map[i][j + zero];
                        map[i][j + zero] = 0;
                        score += map[i][j];
                        break;
                    } else if ((map[i][j] == 0) && (map[i][j + zero] != 0)) {
                        map[i][j] = map[i][j + zero];
                        map[i][j + zero] = 0;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void right() {
        for (int i = map.length - 1; i >= 0; i--) {
            for (int j = map[i].length - 1; j >= 0; j--) {
                int zero = 1;
                while ((zero <= j)) {
                    if (map[i][j - zero] == 0) {
                        zero++;
                    } else if (map[i][j - zero] == map[i][j]) {
                        map[i][j] += map[i][j - zero];
                        map[i][j - zero] = 0;
                        score += map[i][j];
                        break;
                    } else if ((map[i][j] == 0) && (map[i][j - zero] != 0)) {
                        map[i][j] = map[i][j - zero];
                        map[i][j - zero] = 0;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void test(String t) {
        if (t.equalsIgnoreCase("w")) { //up
            testUp();
        } else if (t.equalsIgnoreCase("s")) { // down
            testDown();
        } else if (t.equalsIgnoreCase("a")) { // left
            testLeft();
        } else { // right
            testRight();
        }
    }

    public void testUp() {
        // Ordinary: 1  2  3  4
        //           5  6  7  8
        //           9  10 11 12
        for (int i = 0; i < map.length - 1; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if ((map[i][j] == 0) && (map[i + 1][j] != 0)) {
                    test[j] = false;
                } else if ((map[i][j] == map[i + 1][j]) && (map[i][j] != 0)) {
                    test[j] = false;
                } else {
                    if ((i > 0) && (test[j] == false)) {
                        test[j] = false;
                    } else {
                        test[j] = true;
                    }
                }
            }
        }
    }

    public void testDown() {
        // Ordinary: 9  10 11 12
        //           5  6  7  8
        //           1  2  3  4
        for (int i = map.length - 1; i > 0; i--) {
            for (int j = 0; j < map[i].length; j++) {
                if ((map[i][j] == 0) && (map[i - 1][j] != 0)) {
                    test[j] = false;
                } else if ((map[i][j] == map[i - 1][j]) && (map[i][j] != 0)) {
                    test[j] = false;
                } else {
                    if ((i < map.length - 1) && (test[j] == false)) {
                        test[j] = false;
                    } else {
                        test[j] = true;
                    }
                }
            }
        }
    }

    public void testLeft() {
        // Ordinary: 1  2  3
        //           4  5  6
        //           7  8  9
        //           10 11 12
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length - 1; j++) {
                if ((map[i][j] == 0) && (map[i][j + 1] != 0)) {
                    test[i] = false;
                } else if ((map[i][j] == map[i][j + 1]) && (map[i][j] != 0)) {
                    test[i] = false;
                } else {
                    if ((j > 0) && (test[i] == false)) {
                        test[i] = false;
                    } else {
                        test[i] = true;
                    }
                }
            }
        }
    }

    public void testRight() {
        // Ordinary: 3  2  1
        //           6  5  4
        //           9  8  7
        //           12 11 10
        for (int i = 0; i < map.length; i++) {
            for (int j = map.length - 1; j > 0; j--) {
                if ((map[i][j] == 0) && (map[i][j - 1] != 0)) {
                    test[i] = false;
                } else if ((map[i][j] == map[i][j - 1]) && (map[i][j] != 0)) {
                    test[i] = false;
                } else {
                    if ((j < map.length - 1) && (test[i] == false)) {
                        test[i] = false;
                    } else {
                        test[i] = true;
                    }
                }
            }
        }
    }

    public int highScore(int score) {
        if (score >= highScore) {
            highScore = score;
        }
        return highScore;
    }

    public void question() {
        System.out.println("Dou you want to play again?\n1 - Yes\t\t0 - No");
        while (true) {
            System.out.print("Answer: ");
            answer = input.nextInt();
            if ((answer == 1) || answer == 0) {
                break;
            }
        }
        if (answer == 0) {
            game = false;
            recordList(records);
        } else {
            game = true;
            recordList(records);
            count = 0;
            score = 0;
            for (int[] map1 : map) {
                for (int j = 0; j < map1.length; j++) {
                    map1[j] = 0;
                }
            }
        }
    }

    public void recordList(int[] arr) {
        if (score > arr[arr.length - 1]) {
            arr[arr.length - 1] = score;
        }
        int temp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        if (game == false) {
            System.out.println("____________________________________________________");
            System.out.println("Top 5 high scores:");
            for (int i = 0; i < arr.length; i++) {
                System.out.println((i + 1) + ". " + arr[i]);
            }
        }

    }
}
