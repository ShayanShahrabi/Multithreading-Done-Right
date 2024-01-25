#include <iostream>
#include <vector>
#include <future>

unsigned long long calculateFactorial(int n) {
    unsigned long long result = 1;
    std::vector<std::future<unsigned long long>> futures;

    for (int i = 2; i <= n; i++) {
        futures.push_back(std::async(std::launch::async, [i] {
            unsigned long long partialResult = 1;
            for (int j = 2; j <= i; j++) {
                partialResult *= j;
            }
            return partialResult;
        }));
    }

    for (auto& future : futures) {
        result *= future.get();
    }

    return result;
}

int main() {
    int n = 100; // The number for which factorial needs to be calculated
    unsigned long long factorial = calculateFactorial(n);
    std::cout << "Factorial of " << n << " is: " << factorial << std::endl;
    return 0;
}