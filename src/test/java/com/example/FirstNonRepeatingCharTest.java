package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

@DisplayName("First Non-Repeating Character Tests")
public class FirstNonRepeatingCharTest {

    @Test
    @DisplayName("Basic: Find first non-repeating in simple words")
    public void testBasicNonRepeating() {
        assertEquals('h', FirstNonRepeatingChar.findFirstNonRepeating("hello"));
        assertEquals('p', FirstNonRepeatingChar.findFirstNonRepeating("python"));
        assertEquals('b', FirstNonRepeatingChar.findFirstNonRepeating("aab")); // a appears twice, b once
    }

    @Test
    @DisplayName("No non-repeating: All characters repeat")
    public void testNoNonRepeating() {
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("aabbcc"));
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("aabbccdd"));
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("xxyyzz"));
    }

    @Test
    @DisplayName("Case insensitive: Handles uppercase and lowercase")
    public void testCaseInsensitive() {
        assertEquals('h', FirstNonRepeatingChar.findFirstNonRepeating("HeLLo"));
        assertEquals('p', FirstNonRepeatingChar.findFirstNonRepeating("PYTHON"));
        assertEquals('b', FirstNonRepeatingChar.findFirstNonRepeating("AAb")); // normalized to "aab", b is first
                                                                               // non-repeating
    }

    @Test
    @DisplayName("With spaces: Removes spaces, finds first non-repeating alphabetic")
    public void testWithSpaces() {
        assertEquals('t', FirstNonRepeatingChar.findFirstNonRepeating("the quick")); // "thequick" - t is first unique
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("a a b b")); // "aabb" - a=2, b=2, all repeat
        assertEquals('w', FirstNonRepeatingChar.findFirstNonRepeating("  w  oo dd")); // "wodd" - w=1, o=2, d=2, w is
                                                                                      // first
    }

    @Test
    @DisplayName("With special characters: Ignores special chars and numbers")
    public void testWithSpecialCharacters() {
        assertEquals('h', FirstNonRepeatingChar.findFirstNonRepeating("h@e#l$l%o"));
        assertEquals('a', FirstNonRepeatingChar.findFirstNonRepeating("a1b2b3"));
        assertEquals('x', FirstNonRepeatingChar.findFirstNonRepeating("x-y-y-z-z"));
    }

    @Test
    @DisplayName("Empty string: Returns null character")
    public void testEmptyString() {
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating(""));
    }

    @Test
    @DisplayName("Only spaces and special chars: Returns null character")
    public void testOnlyNonAlphabetic() {
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("   "));
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("@#$%^&*"));
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("123456"));
    }

    @Test
    @DisplayName("Single character: Returns that character")
    public void testSingleCharacter() {
        assertEquals('a', FirstNonRepeatingChar.findFirstNonRepeating("a"));
        assertEquals('z', FirstNonRepeatingChar.findFirstNonRepeating("z"));
        assertEquals('m', FirstNonRepeatingChar.findFirstNonRepeating("m"));
    }

    @Test
    @DisplayName("Single character repeated: Returns null character")
    public void testSingleCharacterRepeated() {
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("aaa"));
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("zzzzzz"));
        assertEquals('\0', FirstNonRepeatingChar.findFirstNonRepeating("bbbbbbbbbb"));
    }

    @Test
    @DisplayName("Long string: Finds correctly in complex input")
    public void testLongString() {
        // "the quick brown fox jumps" -> "thequickbrownfoxjumps"
        // t=1 (first), h=1, e=1, q=1... so 't' is first non-repeating
        char result = FirstNonRepeatingChar.findFirstNonRepeating("the quick brown fox jumps");
        assertEquals('t', result);

        // "programming" -> p=1, r=1, o=2, g=2, a=1, m=2, i=1, n=1
        // first non-repeating is 'p'
        char result2 = FirstNonRepeatingChar.findFirstNonRepeating("programming");
        assertEquals('p', result2);
    }

    @Test
    @DisplayName("Null input: Throws IllegalArgumentException")
    public void testNullStringThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> FirstNonRepeatingChar.findFirstNonRepeating(null));
    }

    @Test
    @DisplayName("Character frequency: Validates frequency map correctness")
    public void testCharacterFrequency() {
        Map<Character, Integer> freq = FirstNonRepeatingChar.getCharacterFrequency("aabbcc");
        assertEquals(2, freq.get('a'));
        assertEquals(2, freq.get('b'));
        assertEquals(2, freq.get('c'));
        assertEquals(3, freq.size());
    }

    @Test
    @DisplayName("Character frequency with mixed: Handles special characters in frequency")
    public void testCharacterFrequencyWithMixed() {
        Map<Character, Integer> freq = FirstNonRepeatingChar.getCharacterFrequency("a1b2c3");
        assertEquals(1, freq.get('a'));
        assertEquals(1, freq.get('b'));
        assertEquals(1, freq.get('c'));
        assertFalse(freq.containsKey('1'));
        assertFalse(freq.containsKey('2'));
        assertEquals(3, freq.size());
    }

    @Test
    @DisplayName("Character frequency null: Throws IllegalArgumentException")
    public void testCharacterFrequencyNull() {
        assertThrows(IllegalArgumentException.class,
                () -> FirstNonRepeatingChar.getCharacterFrequency(null));
    }

    @Test
    @DisplayName("Real world examples: Complex strings from literature/common phrases")
    public void testRealWorldExamples() {
        // "a gentleman" -> "agentleman" -> a=2, g=1, e=2, n=2, t=1, l=1, m=1
        // first pass: g is first with count 1? Let me trace: a-g-e-n-t-l-e-m-a-n
        // a=2(positions 0,8), g=1(position 1)... so g is first non-repeating
        char result1 = FirstNonRepeatingChar.findFirstNonRepeating("a gentleman");
        assertEquals('g', result1);

        // "education" -> e=1, d=1, u=1, c=1, a=1, t=1, i=1, o=1, n=1 (all unique)
        // first character in string 'e' appears once, so 'e' is first non-repeating
        char result2 = FirstNonRepeatingChar.findFirstNonRepeating("education");
        assertEquals('e', result2);

        // "racecar" -> r=2, a=2, c=2, e=1
        // first pass through: r-a-c-e-c-a-r
        // e(position 3) has count 1, so 'e' is first non-repeating
        char result3 = FirstNonRepeatingChar.findFirstNonRepeating("racecar");
        assertEquals('e', result3);
    }

    @Test
    @DisplayName("Insertion order: LinkedHashMap preserves first occurrence order")
    public void testInsertionOrder() {
        // "leetcode" -> 'l' is first that appears once
        char result = FirstNonRepeatingChar.findFirstNonRepeating("leetcode");
        assertEquals('l', result);

        // "loveleetcode" -> 'v' is first non-repeating
        char result2 = FirstNonRepeatingChar.findFirstNonRepeating("loveleetcode");
        assertEquals('v', result2);
    }
}
