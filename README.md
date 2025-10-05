# Assignment 2 — Insertion Sort

**Name:** Sagynbayeva Zhanat  
**Group:** SE_2402  
**Course:** Design and Analysis of Algorithms  

---

## Introduction
In this assignment, I implemented the **Insertion Sort** algorithm in Java.  
The goal was to analyze the algorithm’s efficiency, test its correctness, and collect performance data.  
The program is structured as a Maven project and includes source code, performance tracking, and unit tests.

---

## Implementation
The project contains:
- The main algorithm: `InsertionSort`
- A performance tracker: `PerformanceTracker`
- A command-line tool: `BenchmarkRunner` for running experiments  

Insertion Sort works by gradually building a sorted portion of the array and inserting new elements in the correct position.  
An additional option for **binary insertion** was added to reduce the number of comparisons for nearly sorted data.

---

## Testing
Several test cases were written to verify the algorithm:
- Empty array  
- Single element  
- Duplicate elements  
- Sorted and reversed arrays  
- Random data  

All tests passed successfully using **JUnit 5**.

---

## Conclusion
Insertion Sort is simple and efficient for small or nearly sorted datasets,  
but its time complexity grows quadratically for large random arrays.  
The implementation works correctly and produces expected results on all test cases.
