import java.math.BigInteger;

public abstract class Day<T> implements Runnable {
    protected T solution_one;
    protected T solution_two;

    private String id;

    public Day(String id) {
        this.id = id;
    }

    protected abstract void runSolutionOneTest();

    protected abstract void runSolutionOne();

    protected abstract void runSolutionTwoTest();

    protected abstract void runSolutionTwo();

    protected void printSolutions() {
        System.out.println("Day " + id + ": " + solution_one + ", " + solution_two);
    }

    @Override
    public void run() {
        try {
            runSolutionOneTest();
            runSolutionOne();
            runSolutionTwoTest();
            runSolutionTwo();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
