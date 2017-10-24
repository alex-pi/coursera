package edu.coursera.parallel;

import edu.rice.pcdp.PCDP;
import edu.rice.pcdp.config.SystemProperty;

import static edu.rice.pcdp.PCDP.forseq2d;

/**
 * Wrapper class for implementing matrix multiply efficiently in parallel.
 */
public final class MatrixMultiply {
    /**
     * Default constructor.
     */
    private MatrixMultiply() {
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) sequentially.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void seqMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {
        forseq2d(0, N - 1, 0, N - 1, (i, j) -> {
            C[i][j] = 0.0;
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) in parallel.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void parMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {
        /*
         * TODO Parallelize this outermost two-dimension sequential loop to
         * achieve performance improvement.
         */
        //System.out.println("numWorkers="+SystemProperty.numWorkers.getPropertyValue());
        PCDP.forall2dChunked(0, N - 1, 0, N - 1, 32768, (i, j) -> {
            C[i][j] = 0.0;
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });

        /*PCDP.forallChunked(0, N-1, 256, (i) -> {
            for (int j = 0; j < N; j++) {
                C[i][j] = 0.0;
                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        });*/

        /*PCDP.forallChunked(0, N-1, 256, (i) -> {
            PCDP.forallChunked(0, N-1, 256, (j) -> {
                C[i][j] = 0.0;
                for (int k = 0; k < N; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            });
        });*/

        /*forseq2d(0, N - 1, 0, N - 1, (i, j) -> {
            C[i][j] = 0.0;
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });*/
    }
}
