import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    static Timer timer = new Timer();
    static double seconds = 0;
    static boolean running = false;
    static ArrayList<String> incorrectWords = new ArrayList<>();
    static ArrayList<String> correctWords = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ArrayList<String> words = new ArrayList<String>();

        System.out.println("Typing Test - Jonathan Nissim");
        System.out.println("\n##############################");
        System.out.println("Pick a word count:");
        System.out.println("\n10 | 15 | 25 | 50 | 250");
        System.out.println("##############################");
        int count = 0;

        try {
            File f = new File("/Users/z/typing_test/words.txt");
            Scanner read = new Scanner(f);
            while (read.hasNextLine()) {
                words.add(read.nextLine());
            }

            Scanner s = new Scanner(System.in);
            int next = s.nextInt();

            if (next == 10 || next == 15 || next == 25 || next == 50 || next == 250) {
                count = next;

                System.out.println("\n##############################");
                System.out.println("The test begins after your first word.");
                System.out.println("YOUR WORDS ARE...\n");
                ArrayList<String> randomWords = randoms(words, count);
                System.out.println(randomWords.toString().replaceAll(",", "").replace("[", "").replace("]", ""));
                System.out.println("##############################\n");

                Scanner sc = new Scanner(System.in);
                startTimer();

                for (int i = 0; i < count; i++) {
                    String typedWord = sc.next();
                    if (!typedWord.equals(randomWords.get(i))) {
                        incorrectWords.add(typedWord);
                        correctWords.add(randomWords.get(i));
                    }
                }
                stopTimer();

                double mins = seconds / 60.0;
                double wpm = Math.round(count / mins);

                System.out.println("##############################");
                if (!incorrectWords.isEmpty()) {
                    System.out.println("Words you got wrong:\n");
                    for (int i = 0; i < incorrectWords.size(); i++) {
                        System.out.println("WRONG: " + incorrectWords.get(i) + " | RIGHT: " + correctWords.get(i));
                    }
                    System.out.println("##############################");
                }
                System.out.println("Time: " + seconds + " seconds");
                System.out.println("Words per minute: " + wpm);
                System.out.println("##############################");
            }
        } catch (Exception e) {
        }
    }

    public static void startTimer() {
        if (!running) {
            seconds = 0;
            TimerTask task = new TimerTask() {
                public void run() {
                    seconds++;
                }
            };
            timer = new Timer();
            timer.scheduleAtFixedRate(task, 1000, 1000);
        }
        running = true;
    }

    public static void stopTimer() {
        if (running) {
            timer.cancel();
            running = false;
        }
    }

    public static ArrayList<String> randoms(ArrayList<String> list, int n) {
        if (list == null || list.size() == 0 || n <= 0 || n > list.size()) {
            throw new IllegalArgumentException("Invalid list or number of items requested");
        }
        ArrayList<String> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        return new ArrayList<>(copy.subList(0, n));
    }
}
