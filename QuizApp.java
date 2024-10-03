import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

// Class representing a quiz question
class QuizQuestion {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    // Constructor to initialize the question, options, and correct answer index
    public QuizQuestion(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters for question and options
    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    // Method to check if the selected answer is correct
    public boolean isCorrect(int selectedAnswerIndex) {
        return selectedAnswerIndex == correctAnswerIndex;
    }
}

// Main Quiz class
public class QuizApp {
    private static final int TIME_LIMIT_PER_QUESTION = 10; // time limit in seconds
    private List<QuizQuestion> questions;
    private int score = 0;
    private List<String> resultSummary = new ArrayList<>();

    // Constructor to initialize quiz questions
    public QuizApp() {
        questions = new ArrayList<>();
        loadQuestions();
    }

    // Load quiz questions into the list
    private void loadQuestions() {
        questions.add(new QuizQuestion("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 2));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Venus", "4. Jupiter"}, 1));
        questions.add(new QuizQuestion("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. J.K. Rowling", "3. William Shakespeare", "4. Mark Twain"}, 2));
        questions.add(new QuizQuestion("What is the largest ocean on Earth?", new String[]{"1. Atlantic Ocean", "2. Indian Ocean", "3. Arctic Ocean", "4. Pacific Ocean"}, 3));
        questions.add(new QuizQuestion("What is the chemical symbol for water?", new String[]{"1. O2", "2. CO2", "3. H2O", "4. N2"}, 2));
    }

    // Start the quiz
    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion currentQuestion = questions.get(i);
            boolean answered = askQuestion(currentQuestion, scanner);
            if (!answered) {
                System.out.println("Time's up! Moving to the next question.");
                resultSummary.add("Question " + (i + 1) + ": Not answered (Time's up)");
            }
        }

        // Show final results
        showResult();
        scanner.close();
    }

    // Method to display a question and get user's answer within the time limit
    private boolean askQuestion(QuizQuestion question, Scanner scanner) {
        System.out.println("\n" + question.getQuestion());
        String[] options = question.getOptions();
        for (String option : options) {
            System.out.println(option);
        }

        AtomicBoolean answered = new AtomicBoolean(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (!answered.get()) {
                    System.out.println("\nTime's up!");
                    answered.set(true);
                }
            }
        }, TIME_LIMIT_PER_QUESTION * 1000);

        System.out.print("Your answer (1-4): ");
        int userAnswer = -1;
        while (!answered.get()) {
            if (scanner.hasNextInt()) {
                userAnswer = scanner.nextInt() - 1;
                answered.set(true);
            }
        }
        timer.cancel();

        if (answered.get()) {
            if (question.isCorrect(userAnswer)) {
                System.out.println("Correct!");
                score++;
                resultSummary.add("Question: " + question.getQuestion() + " | Your answer: Correct");
            } else {
                System.out.println("Incorrect.");
                resultSummary.add("Question: " + question.getQuestion() + " | Your answer: Incorrect");
            }
            return true;
        }
        return false;
    }

    // Display the result summary at the end of the quiz
    private void showResult() {
        System.out.println("\n===== Quiz Results =====");
        System.out.println("Final Score: " + score + "/" + questions.size());

        System.out.println("\nSummary of your answers:");
        for (String summary : resultSummary) {
            System.out.println(summary);
        }
    }

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.start();
    }
}

