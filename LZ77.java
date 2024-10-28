import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LZ77 {
    // Inner class to represent each token in the compressed output.
    // Each token stores:
    // - dist: The distance back in the sliding window to start of the matching substring.
    // - len: The length of the matching substring.
    // - next: The character that appears right after the matching substring in the original input.
    static class Done {
        int dist; // Distance back in the window to start of the match
        int len;  // Length of the match
        char next; // Next character following the match in the original input

        // Constructor to initialize a Done object with given distance, length, and next character.
        Done(int d, int l, char nxt) {
            dist = d;
            len = l;
            next = nxt;
        }

        // Method to format the token as a string for output
        // Example format: "<3, 4, 'a'>" indicating a match with distance 3, length 4, and next character 'a'
        public String print() {
            return "<" + dist + ", " + len + ", '" + next + "'>";
        }
    }

    // Method to compress a given input string using the LZ77 algorithm
    public static List<Done> compress(String input, int windowSize) {
        // List to store compressed tokens representing the input string
        List<Done> compressed = new ArrayList<>();
        
        // Convert input string into a character array for easier character access by index
        char[] s = input.toCharArray(); 
        
        int curr = 0;               // Current position in the input string
        int sz = input.length();    // Total length of the input string

        // Main loop to process each character in the input string until the end of the string
        while (curr < sz) {
            int dist = 0;          // Initialize distance of the best match found
            int mx_len = 0;        // Initialize maximum length of the match found
            char nxt = s[curr];    // Set the next character as the current character initially
            
            // Start of the sliding window is determined by max(0, curr - windowSize).
            // This restricts the window to within the last `windowSize` characters.
            int start = Math.max(0, curr - windowSize);

            // Inner loop to search for the longest match within the sliding window
            for (int i = start; i < curr; ++i) {
                int len = 0;  // Length of the match starting at position i within the window

                // Extend the match as long as characters at position i and curr are the same
                // and within bounds of the input string
                while (curr + len < sz && s[i + len] == s[curr + len]) {
                    len++;  // Increment length of the matching substring
                }

                // If the new match length is greater than the previous max length,
                // or if it's the same length but non-zero, update the best match
                if (len > mx_len || (len == mx_len && len != 0)) {
                    mx_len = len;               // Update max length of the match
                    dist = curr - i;            // Update distance back to the start of the match
                    nxt = curr + len < sz ? s[curr + len] : '\0'; // Set next character after the match
                }
            }

            // Add the current best match as a token in the compressed output list
            compressed.add(new Done(dist, mx_len, nxt));

            // Move the cursor in the input string forward by the length of the match plus 1
            // to continue processing the next unmatched character
            curr += mx_len + 1;
        }

        // Return the list of tokens representing the compressed form of the input string
        return compressed;
    }

    // Method to decompress a list of tokens back into the original string
    public static String decompress(List<Done> compressed) {
        // StringBuilder to construct the decompressed string efficiently
        StringBuilder res = new StringBuilder();

        // Iterate through each token in the compressed list
        for (Done curr : compressed) {
            // Calculate the start position in the result string to copy the matching substring
            int st = res.length() - curr.dist;

            // Append each character of the matching substring from the result so far
            for (int i = 0; i < curr.len; ++i) {
                // Append character at offset `st + i` in the existing result to extend the match
                res.append(res.charAt(i + st));
            }

            // Append the next character from the token to complete this part of the decompressed string,
            // unless it's the end-of-input marker '\0'
            if (curr.next != '\0') {
                res.append(curr.next);
            }
        }

        // Return the final decompressed string after all tokens have been processed
        return res.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Initialize scanner to read user input
        System.out.print("Enter a string to compress: ");
        String s = scanner.nextLine(); // Read input string from user
        scanner.close(); // Close the scanner to free up resources

        // Compress the input string using a window size of 10
        List<Done> compressed = compress(s, 10);

        // Display the compressed output
        System.out.print("Compressed: ");
        for (Done i : compressed) {
            System.out.print(i.print() + " "); // Print each token in the compressed list
        }
        System.out.println();

        // Decompress the list of tokens back into the original string
        String decompressed = decompress(compressed);

        // Display the decompressed output
        System.out.println("Decompressed: " + decompressed); // Should match the original input
    }
}
