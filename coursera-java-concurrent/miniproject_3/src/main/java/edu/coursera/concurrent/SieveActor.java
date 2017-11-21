package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;

import java.util.Arrays;
import java.util.stream.IntStream;

import static edu.rice.pcdp.PCDP.finish;

/**
 * An actor-based implementation of the Sieve of Eratosthenes.
 *
 * TODO Fill in the empty SieveActorActor actor class below and use it from
 * countPrimes to determin the number of primes <= limit.
 */
public final class SieveActor extends Sieve {
    /**
     * {@inheritDoc}
     *
     * TODO Use the SieveActorActor class to calculate the number of primes <=
     * limit in parallel. You might consider how you can model the Sieve of
     * Eratosthenes as a pipeline of actors, each corresponding to a single
     * prime number.
     */
    @Override
    public int countPrimes(final int limit) {

        //int max = (int)(limit*0.02);
        //System.out.println("max="+max);
        SieveActorActor saa = new SieveActorActor(2, 250);
        finish(() -> {
            for(int i = 3; i <= limit ; i+=2) {
                saa.send(i);
            }
        });

        int result = 0;
        for(SieveActorActor curr = saa; curr != null; curr = curr.nextActor) {
            result += curr.getNumLocalPrimes();
        }

        return result;
    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {

        private final int maxPrimesPerActor;
        private final int localPrimes[];
        private int numLocalPrimes;
        private SieveActorActor nextActor;

        SieveActorActor(final int localPrime, final int maxPrimesPerActor) {
            this.maxPrimesPerActor = maxPrimesPerActor;
            this.localPrimes = new int[maxPrimesPerActor];
            this.localPrimes[0] = localPrime;
            this.numLocalPrimes = 1;
        }

        public int getNumLocalPrimes() {
            return numLocalPrimes;
        }

        /**
         * Process a single message sent to this actor.
         *
         * TODO complete this method.
         *
         * @param msg Received message
         */
        @Override
        public void process(final Object msg) {
            final int candidate = (Integer) msg;
            if(candidate <= 0) {
                if (this.nextActor != null) {
                    nextActor.send(0);
                }
            } else {
                boolean isLocalPrime = isLocallyPrime(candidate);
                if(isLocalPrime) {
                    if(numLocalPrimes < maxPrimesPerActor) {
                        localPrimes[numLocalPrimes] = candidate;
                        numLocalPrimes ++;
                    } else if (this.nextActor == null) {
                        this.nextActor = new SieveActorActor(candidate, maxPrimesPerActor);
                    } else {
                        this.nextActor.send(msg);
                    }
                }
            }
        }

        private boolean isLocallyPrime(final int candidate) {
            for(int i = 0 ; i < numLocalPrimes ; i++) {
                if (candidate % localPrimes[i] == 0) {
                    return false;
                }
            }
            return true;

            /*return !IntStream.range(0, numLocalPrimes)
                    .parallel()
                    .anyMatch( i -> candidate % localPrimes[i] == 0);*/
        }
    }
}
