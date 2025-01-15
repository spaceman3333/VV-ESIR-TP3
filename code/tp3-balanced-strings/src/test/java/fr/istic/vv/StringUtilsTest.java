package fr.istic.vv;

import org.junit.jupiter.api.Test;

import static fr.istic.vv.StringUtils.isBalanced;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    @Test
    void testIsBalancedEmptyString() {
        assertTrue(isBalanced(""));
    }

    @Test
    void testIsBalancedSingleCharacter1() {
        assertFalse(isBalanced("]"));
    }

    @Test
    void testIsBalancedSingleCharacter2() {
        assertFalse(isBalanced("("));
    }

    @Test
    void testIsBalancedMultipleCharacters() {
        assertTrue(isBalanced("{[()]}"));
    }

    @Test
    void testIsBalancedUnbalancedString() {
        assertFalse(isBalanced("([)]"));
    }

    @Test
    void testIsBalancedExtraOpeningBrackets() {
        assertFalse(isBalanced("({{"));
    }

    @Test
    void testIsBalancedReversedOrder() {
        assertFalse(isBalanced(")("));
    }

    @Test
    void testIsBalancedRRepeatedPairs() {
        assertTrue(isBalanced("()[]{}"));
    }

    @Test
    void testIsBalancedLongBalancedNesting() {
        assertTrue(isBalanced("({[]})"));
    }

    @Test
    void testIsBalancedUnbalancedWithExtraClosing() {
        assertFalse(isBalanced("(()))"));
    }

    @Test
    void testIsBalancedUnbalancedEnhancedCoverage() {
        assertTrue(isBalanced("((()))"));
    }

}