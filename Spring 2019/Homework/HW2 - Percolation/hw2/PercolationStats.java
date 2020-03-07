package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * @author Ronald Yonggi
 */

public class PercolationStats {

    /** We repeat the percolation threshold computation T times. Hence we need an array of
     * size T to store the results. Remember that the percolation threshold is
     * "the ith site that makes the system percolates when opened" divided by "the
     * total number of sites".
     */
    private double[] results;

    /** numOpen keeps track of the number of open sites.
     *
     */


    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        /** Recall that the results is an array of size T.
         *
         */
        results = new double[T];

        for (int i = 0; i < T; i++){
            /** Keeps track of the number of open sites for one calculation
             * of percolation threshold. For every calculation, we reset
             * numOpen to 0.
             */
            int numOpen = 0;

            /** The PercolationFactor class only have one method: make(N), which
             * is basically a Percolation constructor with NxN grid.
             */
            Percolation newPercol = pf.make(N);

            /** This while loop executes as long as the system
             * doesn't percolate.
             */
            while (!newPercol.percolates()){
                /** Draw a random row and a random column.
                 *
                 */
                int randomRow = StdRandom.uniform(N);
                int randomCol = StdRandom.uniform(N);

                /** If a site is not already open, open the site
                 * and increment numOpen.
                 */
                if (!newPercol.isOpen(randomRow, randomCol)){
                    newPercol.open(randomRow, randomCol);
                    numOpen++;
                }
            }

            /** The execution comes out of while loop once the system percolates. Here
             * we compute ONE CALCULATION of percolation threshold by dividing
             * numOpen with the total number of grids.
             */
            results[i] = (double) numOpen / (N * N);
        }


    }

    public double mean(){

    }

    public double stddev(){

    }

    public double confidenceLow(){

    }

    public double confidenceHigh(){

    }
}
