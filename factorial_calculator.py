import multiprocessing
from math import factorial

def calculate_factorial(n):
    pool = multiprocessing.Pool(processes=multiprocessing.cpu_count())
    result = pool.map(factorial, range(1, n+1))
    pool.close()
    pool.join()
    return result

if __name__ == '__main__':
    n = 100  # The number for which factorial needs to be calculated
    factorial_list = calculate_factorial(n)
    print("Factorial of", n, "is:", factorial_list[-1])