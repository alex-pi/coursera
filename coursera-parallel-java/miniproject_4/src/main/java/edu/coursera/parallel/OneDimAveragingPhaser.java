package edu.coursera.parallel;

import java.util.concurrent.Phaser;
//import edu.rice.pcdp.PCDP;

/**
 * Wrapper class for implementing one-dimensional iterative averaging using
 * phasers.
 */
public final class OneDimAveragingPhaser {
    /**
     * Default constructor.
     */
    private OneDimAveragingPhaser() {
    }

    /**
     * Sequential implementation of one-dimensional iterative averaging.
     *
     * @param iterations The number of iterations to run
     * @param myNew A double array that starts as the output array
     * @param myVal A double array that contains the initial input to the
     *        iterative averaging problem
     * @param n The size of this problem
     */
    public static void runSequential(final int iterations, final double[] myNew,
            final double[] myVal, final int n) {
        double[] next = myNew;
        double[] curr = myVal;

        for (int iter = 0; iter < iterations; iter++) {
            for (int j = 1; j <= n; j++) {
                next[j] = (curr[j - 1] + curr[j + 1]) / 2.0;
            }
            double[] tmp = curr;
            curr = next;
            next = tmp;
        }
    }

    /**
     * An example parallel implementation of one-dimensional iterative averaging
     * that uses phasers as a simple barrier (arriveAndAwaitAdvance).
     *
     * @param iterations The number of iterations to run
     * @param myNew A double array that starts as the output array
     * @param myVal A double array that contains the initial input to the
     *        iterative averaging problem
     * @param n The size of this problem
     * @param tasks The number of threads/tasks to use to compute the solution
     */
    public static void runParallelBarrier(final int iterations,
            final double[] myNew, final double[] myVal, final int n,
            final int tasks) {
        Phaser ph = new Phaser(0);
        ph.bulkRegister(tasks);

        Thread[] threads = new Thread[tasks];

        for (int ii = 0; ii < tasks; ii++) {
            final int i = ii;

            threads[ii] = new Thread(() -> {
                double[] threadPrivateMyVal = myVal;
                double[] threadPrivateMyNew = myNew;

                for (int iter = 0; iter < iterations; iter++) {
                    final int left = i * (n / tasks) + 1;
                    final int right = (i + 1) * (n / tasks);

                    for (int j = left; j <= right; j++) {
                        threadPrivateMyNew[j] = (threadPrivateMyVal[j - 1]
                            + threadPrivateMyVal[j + 1]) / 2.0;
                    }
                    ph.arriveAndAwaitAdvance();

                    double[] temp = threadPrivateMyNew;
                    threadPrivateMyNew = threadPrivateMyVal;
                    threadPrivateMyVal = temp;
                }
            });
            threads[ii].start();
        }

        for (int ii = 0; ii < tasks; ii++) {
            try {
                threads[ii].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A parallel implementation of one-dimensional iterative averaging that
     * uses the Phaser.arrive and Phaser.awaitAdvance APIs to overlap
     * computation with barrier completion.
     *
     * TODO Complete this method based on the provided runSequential and
     * runParallelBarrier methods.
     *
     * @param iterations The number of iterations to run
     * @param myNew A double array that starts as the output array
     * @param myVal A double array that contains the initial input to the
     *              iterative averaging problem
     * @param n The size of this problem
     * @param tasks The number of threads/tasks to use to compute the solution
     */
    public static void runParallelFuzzyBarrier2(final int iterations,
            final double[] myNew, final double[] myVal, final int n,
            final int tasks) {
        //runParallelBarrier(iterations, myNew, myVal, n, tasks);
        Phaser ph = new Phaser(0);
        ph.bulkRegister(tasks);

        Thread[] threads = new Thread[tasks];

        for(int t = 0 ; t < tasks ; t++) {
            final int group = t;

            threads[t] = new Thread( () -> {
                double[] threadPrivateMyVal = myVal;
                double[] threadPrivateMyNew = myNew;
                final int left = group * (n / tasks) + 1;
                final int right = (group + 1) * (n / tasks);

                for(int iter = 0 ; iter < iterations ; iter ++) {

                    threadPrivateMyNew[left] = (threadPrivateMyVal[left-1] + threadPrivateMyVal[left+1]) / 2.0;
                    threadPrivateMyNew[right] = (threadPrivateMyVal[right-1] + threadPrivateMyVal[right+1]) / 2.0;

                    int phase = ph.arrive();

                    for(int j = left+1 ; j <= right-1 ; j++) {
                        threadPrivateMyNew[j] = (threadPrivateMyVal[j-1] + threadPrivateMyVal[j+1]) / 2.0;
                    }

                    ph.awaitAdvance(phase);

                    double[] temp = threadPrivateMyVal;
                    threadPrivateMyVal = threadPrivateMyNew;
                    threadPrivateMyNew = temp;

                }
            });

            threads[t].start();
        }

        for (int ii = 0; ii < tasks; ii++) {
            try {
                threads[ii].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runParallelFuzzyBarrier(final int iterations,
                                               final double[] myNew, final double[] myVal, final int n,
                                               final int tasks) {
        //runParallelBarrier(iterations, myNew, myVal, n, tasks);
        //Phaser ph = new Phaser(0);
        //ph.bulkRegister(tasks);
        Phaser[] ph = new Phaser[tasks+2]; //array of phasers
        for (int i = 0; i < ph.length; i++) ph[i] = new Phaser(1);

        Thread[] threads = new Thread[tasks];

        for(int t = 0 ; t < tasks ; t++) {
            final int group = t;

            threads[t] = new Thread( () -> {
                double[] threadPrivateMyVal = myVal;
                double[] threadPrivateMyNew = myNew;
                final int left = group * (n / tasks) + 1;
                final int right = (group + 1) * (n / tasks);

                for(int iter = 0 ; iter < iterations ; iter ++) {

                    threadPrivateMyNew[left] = (threadPrivateMyVal[left-1] + threadPrivateMyVal[left+1]) / 2.0;
                    threadPrivateMyNew[right] = (threadPrivateMyVal[right-1] + threadPrivateMyVal[right+1]) / 2.0;

                    //int phase = ph.arrive();
                    int	index = group + 1;
                    ph[index].arrive();

                    for(int j = left+1 ; j <= right-1 ; j++) {
                        threadPrivateMyNew[j] = (threadPrivateMyVal[j-1] + threadPrivateMyVal[j+1]) / 2.0;
                    }

                    //ph.awaitAdvance(phase);
                    if (index > 1) ph[index - 1].awaitAdvance(iter);
                    if (index < tasks) ph[index + 1].awaitAdvance(iter);

                    double[] temp = threadPrivateMyVal;
                    threadPrivateMyVal = threadPrivateMyNew;
                    threadPrivateMyNew = temp;

                }
            });

            threads[t].start();
        }

        for (int ii = 0; ii < tasks; ii++) {
            try {
                threads[ii].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*public static void runParallelFuzzyBarrier1(final int iterations,
                                               final double[] myNew, final double[] myVal, final int n,
                                               final int tasks) {
        Phaser[] ph = new Phaser[tasks+2]; //array of phasers
        for (int i = 0; i < ph.length; i++) ph[i] = new Phaser(1);

        System.out.println("iterations = " + iterations);
        System.out.println("n = " + n);
        System.out.println("myNew.length = " + myNew.length);
        System.out.println("myVal.length = " + myVal.length);
        System.out.println("tasks = " + tasks);

        PCDP.forall(0, tasks-1, (t) -> {
            //System.out.println("t = " + t);
            double[] threadPrivateMyVal = myVal;
            double[] threadPrivateMyNew = myNew;
            final int left = t * (n / tasks) + 1;
            final int right = (t + 1) * (n / tasks);

            for(int iter = 0 ; iter < iterations ; iter ++) {

                //System.out.println("left = " + left);
                //System.out.println("right = " + right);
                threadPrivateMyNew[left] = (threadPrivateMyVal[left-1] + threadPrivateMyVal[left+1]) / 2.0;
                threadPrivateMyNew[right] = (threadPrivateMyVal[right-1] + threadPrivateMyVal[right+1]) / 2.0;

                int	index = t + 1;
                ph[index].arrive();

                for(int j = left+1 ; j <= right-1 ; j++) {
                    threadPrivateMyNew[j] = (threadPrivateMyVal[j-1] + threadPrivateMyVal[j+1]) / 2.0;
                }

                if (index > 1) ph[index - 1].awaitAdvance(iter);
                if (index < tasks) ph[index + 1].awaitAdvance(iter);

                double[] temp = threadPrivateMyVal;
                threadPrivateMyVal = threadPrivateMyNew;
                threadPrivateMyNew = temp;
            }
        });
    }*/
}
