public abstract class Day<T> implements Runnable {
    protected T solution_one;
    protected T solution_two;
    protected double run_time;

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
        // System.out.println("Day " + id + ": " + run_time + "s");
    }

    @Override
    public void run() {
        try {
            double start_time = System.nanoTime();
            runSolutionOneTest();
            runSolutionOne();
            runSolutionTwoTest();
            runSolutionTwo();
            run_time = (System.nanoTime() - start_time) / Math.pow(10, 9);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
