package main

import (
	"fmt"
	"math/big"
	"runtime"
	"sync"
)

func calculateFactorial(n int) *big.Int {
	result := big.NewInt(1)
	var wg sync.WaitGroup
	wg.Add(n)

	numWorkers := runtime.NumCPU()

	jobs := make(chan int, numWorkers)
	results := make(chan *big.Int, n)

	// Worker function to calculate factorial
	worker := func() {
		for num := range jobs {
			partialResult := big.NewInt(int64(num))
			for i := 2; i <= num; i++ {
				partialResult.Mul(partialResult, big.NewInt(int64(i)))
			}
			results <- partialResult
			wg.Done()
		}
	}

	// Start worker goroutines
	for i := 0; i < numWorkers; i++ {
		go worker()
	}

	// Generate factorial jobs
	go func() {
		for i := 1; i <= n; i++ {
			jobs <- i
		}
		close(jobs)
	}()

	// Collect and multiply partial results
	for i := 1; i <= n; i++ {
		result.Mul(result, <-results)
	}

	wg.Wait()
	close(results)

	return result
}

func main() {
	n := 100 // The number for which factorial needs to be calculated
	factorial := calculateFactorial(n)
	fmt.Printf("Factorial of %d is: %s\n", n, factorial.String())
}