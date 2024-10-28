# LZ77 Compression Algorithm

This repository contains an implementation of the LZ77 compression algorithm in Java. The LZ77 algorithm is a lossless data compression algorithm that replaces repeated occurrences of data with references to a single copy. This implementation compresses and decompresses strings based on a sliding window approach.

## Contents

- **LZ77.java**: The main Java class implementing the LZ77 algorithm.

## Class Overview

### `LZ77`

The `LZ77` class contains methods for compressing and decompressing strings. It includes an inner class `Done` to represent tokens in the compressed output.

#### Inner Class: `Done`

The `Done` class represents a single token generated during the compression process. Each token contains:

- `dist`: The distance back in the sliding window to the start of the matching substring.
- `len`: The length of the matching substring.
- `next`: The character that appears immediately after the matching substring in the original input.

**Constructor**:
- `Done(int d, int l, char nxt)`: Initializes a `Done` object with the specified distance, length, and next character.

**Methods**:
- `String print()`: Formats the token as a string for output, e.g., `"<3, 4, 'a'>"`.

### Methods

#### `compress(String input, int windowSize)`

Compresses the input string using the LZ77 algorithm.

- **Parameters**:
  - `input`: The string to be compressed.
  - `windowSize`: The size of the sliding window used to find matches.
  
- **Returns**: A list of `Done` tokens representing the compressed form of the input string.

**Algorithm**:
1. Converts the input string into a character array for easier access.
2. Iterates through the input string while maintaining a sliding window.
3. Searches for the longest match within the sliding window.
4. Stores the match information as a `Done` token and continues until the end of the input.

#### `decompress(List<Done> compressed)`

Decompresses a list of tokens back into the original string.

- **Parameters**:
  - `compressed`: A list of `Done` tokens generated during compression.

- **Returns**: The decompressed string, reconstructed from the tokens.

**Algorithm**:
1. Iterates through each `Done` token in the compressed list.
2. For each token, retrieves the matching substring from the previously constructed string using the stored distance.
3. Appends the next character from the token to reconstruct the string.

### `main(String[] args)`

The main method to run the compression and decompression process.

- Prompts the user to input a string to compress.
- Calls the `compress` method with a specified window size.
- Prints the compressed output tokens.
- Calls the `decompress` method and prints the resulting decompressed string.

## Usage

1. Clone the repository.
2. Compile the `LZ77.java` file using a Java compiler.
3. Run the program and input a string when prompted to see the compressed and decompressed output.

```bash
javac LZ77.java
java LZ77
