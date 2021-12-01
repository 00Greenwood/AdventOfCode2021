public abstract class Day implements Runnable {
    protected String id;
    protected String solution_one;
    protected String solution_two;

    protected abstract void runSolutionOneTest();
    protected abstract void runSolutionOne();
    protected abstract void runSolutionTwoTest();
    protected abstract void runSolutionTwo();

    protected void printSolutions() {
        System.out.println("Day " + id + ": " + solution_one + ", " + solution_two);
    }

    @Override
    public void run() {
        runSolutionOneTest();
        runSolutionOne();
        runSolutionTwoTest();
        runSolutionTwo();
    }


}
