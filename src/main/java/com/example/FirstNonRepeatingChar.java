package com.example;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * First Non-Repeating Character Finder
 * Finds the first character that appears exactly once in a string.
 * 
 * Algorithm: Uses LinkedHashMap to maintain insertion order
 * Time Complexity: O(n) - two passes through the string
 * Space Complexity: O(k) - where k is the number of unique characters (max 26
 * for lowercase)
 */
public class FirstNonRepeatingChar {

    /**
     * Finds the first non-repeating character in a string (case-insensitive,
     * alphabetic only).
     * 
     * @param str the input string
     * @return the first non-repeating character, or '\0' if none exists
     * @throws IllegalArgumentException if string is null
     */
    public static char findFirstNonRepeating(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        // Normalize: lowercase and keep only alphabetic characters
        String normalized = str.toLowerCase().replaceAll("[^a-z]", "");

        // Empty string after normalization: no non-repeating character
        if (normalized.isEmpty()) {
            return '\0';
        }

        // LinkedHashMap maintains insertion order
        Map<Character, Integer> charCount = new LinkedHashMap<>();

        // First pass: count character frequencies
        for (char c : normalized.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        // Second pass: find first character with count == 1
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        // No non-repeating character found
        return '\0';
    }

    /**
     * Gets the character frequency map for analysis.
     * Used primarily for testing.
     * 
     * @param str the input string
     * @return LinkedHashMap with character frequencies in insertion order
     * @throws IllegalArgumentException if string is null
     */
    public static Map<Character, Integer> getCharacterFrequency(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        String normalized = str.toLowerCase().replaceAll("[^a-z]", "");
        Map<Character, Integer> charCount = new LinkedHashMap<>();

        for (char c : normalized.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        return charCount;
    }

    /**
     * Main method demonstrating the algorithm
     */
    public static void main(String[] args) {
        System.out.println("========== First Non-Repeating Character Finder ==========\n");

        // Test cases
        String[] testStrings = {
                "hello",
                "aabbcc",
                "the quick brown fox",
                "aabbccdd",
                "programming"
        };

        for (String test : testStrings) {
            char result = findFirstNonRepeating(test);
            String resultStr = (result == '\0') ? "NO NON-REPEATING CHARACTER" : String.valueOf(result).toUpperCase();
            System.out.println("Input: \"" + test + "\"");
            System.out.println("First Non-Repeating: " + resultStr);
            System.out.println();
        }
    }
}
