package com.jason.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by bob on 2017/8/23.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */
public class StringUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isEmpty() throws Exception {
        boolean isEmpty1 = StringUtils.isEmpty("");
        boolean isEmpty2 = StringUtils.isEmpty(null);
        boolean isEmpty3 = StringUtils.isEmpty("null");
        boolean isEmpty4 = StringUtils.isEmpty(" ");
        assertTrue(isEmpty1);
        assertTrue(isEmpty2);
        assertFalse(isEmpty3);
        assertFalse(isEmpty4);

    }

    @Test
    public void isEmptyOptionTrim() throws Exception {
        boolean isEmptyOptionTrim1 = StringUtils.isEmptyOptionTrim("");
        boolean isEmptyOptionTrim2 = StringUtils.isEmptyOptionTrim(null);
        boolean isEmptyOptionTrim3 = StringUtils.isEmptyOptionTrim("null");
        boolean isEmptyOptionTrim4 = StringUtils.isEmptyOptionTrim(" ");
        assertTrue(isEmptyOptionTrim1);
        assertTrue(isEmptyOptionTrim2);
        assertFalse(isEmptyOptionTrim3);
        assertTrue(isEmptyOptionTrim4);
    }

    @Test
    public void isEmptyWithNULL() throws Exception {
        boolean isEmptyWithNULL1 = StringUtils.isEmptyWithNULL("");
        boolean isEmptyWithNULL2 = StringUtils.isEmptyWithNULL(null);
        boolean isEmptyWithNULL3 = StringUtils.isEmptyWithNULL("null");
        boolean isEmptyWithNULL4 = StringUtils.isEmptyWithNULL(" ");
        assertTrue(isEmptyWithNULL1);
        assertTrue(isEmptyWithNULL2);
        assertTrue(isEmptyWithNULL3);
        assertFalse(isEmptyWithNULL4);
    }

    @Test
    public void isEmptyWithNULLOptionTrim() throws Exception {
        boolean isEmptyWithNULLOptionTrim1 = StringUtils.isEmptyWithNULLOptionTrim("");
        boolean isEmptyWithNULLOptionTrim2 = StringUtils.isEmptyWithNULLOptionTrim(null);
        boolean isEmptyWithNULLOptionTrim3 = StringUtils.isEmptyWithNULLOptionTrim("null");
        boolean isEmptyWithNULLOptionTrim4 = StringUtils.isEmptyWithNULLOptionTrim(" ");
        assertTrue(isEmptyWithNULLOptionTrim1);
        assertTrue(isEmptyWithNULLOptionTrim2);
        assertTrue(isEmptyWithNULLOptionTrim3);
        assertTrue(isEmptyWithNULLOptionTrim4);

    }

    @Test
    public void upperFirstLetter() throws Exception {
        String upper1 = StringUtils.upperFirstLetter("abc");
        String upper2 = StringUtils.upperFirstLetter("ABC");
        String upper3 = StringUtils.upperFirstLetter("中国人");
        String upper4 = StringUtils.upperFirstLetter(null);
        String upper5 = StringUtils.upperFirstLetter("");
        assertEquals("Abc", upper1);
        assertEquals("ABC", upper2);
        assertEquals("中国人", upper3);
        assertNull(upper4);
        assertEquals("", upper5);

    }

    @Test
    public void lowerFirstLetter() throws Exception {
        String lower1 = StringUtils.lowerFirstLetter("abc");
        String lower2 = StringUtils.lowerFirstLetter("ABC");
        String lower3 = StringUtils.lowerFirstLetter("中国人");
        String lower4 = StringUtils.lowerFirstLetter(null);
        String lower5 = StringUtils.lowerFirstLetter("");
        assertEquals("abc", lower1);
        assertEquals("aBC", lower2);
        assertEquals("中国人", lower3);
        assertNull(lower4);
        assertEquals("", lower5);
    }

    @Test
    public void reverseLetter() throws Exception {
        String reverseLetter1 = StringUtils.reverseLetter("abc");
        String reverseLetter2 = StringUtils.reverseLetter("中国人");
        String reverseLetter3 = StringUtils.reverseLetter("中 国人");
        String reverseLetter4 = StringUtils.reverseLetter(null);
        String reverseLetter5 = StringUtils.reverseLetter("");
        assertEquals("cba", reverseLetter1);
        assertEquals("人国中", reverseLetter2);
        assertEquals("人国 中", reverseLetter3);
        assertNull(reverseLetter4);
        assertEquals("", reverseLetter5);

    }

    @Test
    public void reverseWordWithRegex() throws Exception {
        String reverseWordWithRegex1 = StringUtils.reverseWordWithRegex("abc", null, " ");
        String reverseWordWithRegex2 = StringUtils.reverseWordWithRegex("a bc", " ", "");
        String reverseWordWithRegex3 = StringUtils.reverseWordWithRegex("a|b|c", "\\|", "|");
        String reverseWordWithRegex4 = StringUtils.reverseWordWithRegex("a*bc", "\\*", "*");
        String reverseWordWithRegex5 = StringUtils.reverseWordWithRegex("中 国人", null, "1");
        String reverseWordWithRegex6 = StringUtils.reverseWordWithRegex(null, null, " ");
        String reverseWordWithRegex7 = StringUtils.reverseWordWithRegex("", null, " ");
        assertEquals("abc", reverseWordWithRegex1);
        assertEquals("bc a", reverseWordWithRegex2);
        assertEquals("c|b|a", reverseWordWithRegex3);
        assertEquals("bc*a", reverseWordWithRegex4);
        assertEquals("国人1中", reverseWordWithRegex5);
        assertNull(reverseWordWithRegex6);
        assertEquals("", reverseWordWithRegex7);
    }

}