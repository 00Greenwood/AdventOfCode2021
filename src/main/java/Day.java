public abstract class Day implements Runnable {
    protected Integer solution_one = 0;
    protected Integer solution_two = 0;

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
        runSolutionOneTest();
        runSolutionOne();
        runSolutionTwoTest();
        runSolutionTwo();
    }


}
