package com.task.citrix;

import org.junit.Assert;
import org.junit.Test;

public class PigLatinTransformerTest {
    @Test
    public void testWordStartWithVowel() {
        final String transformResult = PigLatinTransformer.transformToPigLatin("apple");
        String expected = "appleway";
        Assert.assertEquals(expected, transformResult);
    }

    @Test
    public void testWordWithPunctuation() {
        final String transformResult = PigLatinTransformer.transformToPigLatin("can't");
        String expected = "antca'y";
        Assert.assertEquals(expected, transformResult);
    }

    @Test
    public void testWordWithHyphens() {
        final String transformResult = PigLatinTransformer.transformToPigLatin("this-thing");
        String expected = "histay-hingtay";
        Assert.assertEquals(expected, transformResult);
    }

    @Test
    public void testWordWithCapitalization() {
        final String transformResult = PigLatinTransformer.transformToPigLatin("this-thing");
        String expected = "histay-hingtay";
        Assert.assertEquals(expected, transformResult);
    }

    @Test
    public void testSentence() {
        final String transformResult = PigLatinTransformer.transformToPigLatin("I can't hear you!");
        String expected = "Iway antca'y earhay ouyay!";
        Assert.assertEquals(expected, transformResult);
    }

    @Test
    public void testNull() {
        final String transformResult = PigLatinTransformer.transformToPigLatin(null);
        String expected = "";
        Assert.assertEquals(expected, transformResult);
    }

    @Test
    public void testBlank() {
        final String transformResult = PigLatinTransformer.transformToPigLatin("    ");
        String expected = "";
        Assert.assertEquals(expected, transformResult);
    }
}
