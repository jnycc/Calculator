# Calculator

In this project a simple CLI calculator has been created. For practice purposes, it contains several versions, each increasing in complexity.

- **Version 1 - Simple**: The Command Line Interface asks for two numbers and sums them up.
  - Experiment with different ways to get input (System.in/out via Scanner, System.Console)
  - Validate input first
  - Write unit tests

- **Version 2 - Intermediate**: The CLI asks for one calculation input, parsing e.g. 4 + 6 - 3, and prints the result. 
  - Support +, -, *, / (Addition, subtraction, multiplication, division)
  - Support multiple numbers in one string
  - Support negative numbers
  - Validate input - i.e. only numbers and operators and multiple operators in sequence (e.g. +*) are not allowed except for minuses (e.g. --- is allowed)

- **Version 3 - Advanced**: Via the CLI, the application understands the syntax of the input and performs priority calculations in accordance with PEMDAS
  - Support prioritization for multiplication and divisions (PEMDAS)
  - Support rational numbers (i.e. decimals)
  - Experiment with different structures (tokenization, parsing into lists or trees)
