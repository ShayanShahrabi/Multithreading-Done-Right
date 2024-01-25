import java.math.BigInteger;
import java.util.concurrent.*;

public class FactorialCalculator {
    public static BigInteger calculateFactorial(int n) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BigInteger result = BigInteger.ONE;
        
        try {
            // Create a list to hold the Future objects
            List<Future<BigInteger>> futures = new ArrayList<>();
            
            for (int i = 1; i <= n; i++) {
                int num = i;
                Callable<BigInteger> factorialTask = () -> {
                    BigInteger partialResult = BigInteger.valueOf(num);
                    for (int j = 2; j <= num; j++) {
                        partialResult = partialResult.multiply(BigInteger.valueOf(j));
                    }
                    return partialResult;
                };
                futures.add(executor.submit(factorialTask));
            }
            
            // Wait for all tasks to complete and multiply the partial results
            for (Future<BigInteger> future : futures) {
                result = result.multiply(future.get());
            }
        } finally {
            executor.shutdown();
        }
        
        return result;
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int n = 100; // The number for which factorial needs to be calculated
        BigInteger factorial = calculateFactorial(n);
        System.out.println("Factorial of " + n + " is: " + factorial);
    }
}