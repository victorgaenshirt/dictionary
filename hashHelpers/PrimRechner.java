package dictionary.hashHelpers;

public class PrimRechner {

    public static int findNextLargerPrime(int isPrim) {

        while (! isNumberAPrim(isPrim)) {
            isPrim++;
        }
        printAsPrime(isPrim);
        return isPrim;
    }

    private static boolean isNumberAPrim(int nrToCheck) {

        if (nrToCheck == 1) {
            return true;
        }

        for (int divisor = 2; divisor <= nrToCheck; divisor++) {
            //falls teilbar ohne Rest
            if (nrToCheck % divisor == 0) {
                if (divisor != nrToCheck) {
                    break;
                }
                return true;
            }
        }
        return false;
    }

    private static void printAsPrime(int isPrime) {
        System.out.println("Primzahl: " + isPrime);
    }

    public static void findAllPrimesInRange(int upperLimit) {
        int isPrime = 1;

        while (isPrime <= upperLimit) {

            if (isPrime == 1) {
                printAsPrime(isPrime);
                isPrime++;
                continue;
            }

            for (int divisor = 2; divisor <= isPrime; divisor++) {
                //falls teilbar ohne Rest
                if (isPrime % divisor == 0) {
                    if (divisor != isPrime) {
                        break;
                    }
                    System.out.println("Primzahl: " + isPrime);
                }
            }
            isPrime++;
        }
    }
}
