package com.task.citrix;


import java.util.*;
import java.util.regex.Pattern;

public class PigLatinTransformer {
    private static final Set<Character> VOWELS = new HashSet<>();
    private static final Set<Character> PUNCTUATION_MARKS = new HashSet<>();
    private static final String END_WITH_WAY_PATTERN;
    private static final String HYPHENS = "-";
    private static final String APPEND_FOR_VOWELS = "way";
    private static final String APPEND_FOR_CONSONANT = "ay";

    static {
        VOWELS.add('a');
        VOWELS.add('e');
        VOWELS.add('i');
        VOWELS.add('o');
        VOWELS.add('u');
        PUNCTUATION_MARKS.add('!');
        PUNCTUATION_MARKS.add('\'');
        PUNCTUATION_MARKS.add(',');
        PUNCTUATION_MARKS.add('.');
        PUNCTUATION_MARKS.add(':');
        PUNCTUATION_MARKS.add(';');
        PUNCTUATION_MARKS.add('<');
        PUNCTUATION_MARKS.add('=');
        PUNCTUATION_MARKS.add('>');
        PUNCTUATION_MARKS.add('?');
        PUNCTUATION_MARKS.add('@');
        PUNCTUATION_MARKS.add('\\');
        PUNCTUATION_MARKS.add('_');
        PUNCTUATION_MARKS.add('`');
        StringBuilder builder = new StringBuilder();
        builder.append("(.*?)(").append(APPEND_FOR_VOWELS).append(")").append("([");
        PUNCTUATION_MARKS.forEach(character -> {
            builder.append("\\\\").append(character);
        });
        builder.append("]*)$");
        END_WITH_WAY_PATTERN = builder.toString();
    }

    /**
     * transform given string to pig-latin
     *
     * @param text - string to transform
     * @return transformed string or empty if input string was null or blank
     */
    public static String transformToPigLatin(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        final String[] words = text.trim().split("\\s");
        final StringBuilder transformedText = new StringBuilder();
        for (String word : words) {
            transformedText.append(transformWord(word)).append(" ");
        }
        transformedText.setLength(transformedText.length() - 1);
        return transformedText.toString();
    }

    /**
     * transform word to pig-latin
     * @param word - word to transform
     * @return transformed word
     */
    private static String transformWord(String word) {
        String transformedWord;
        if (word.contains(HYPHENS)) {
            StringBuilder transformedWordBuilder = new StringBuilder();
            final String[] wordParts = word.split(HYPHENS);
            String delimiter = "";
            for (String wordPart : wordParts) {
                transformedWordBuilder.append(delimiter);
                delimiter = HYPHENS;
                final String transformed = transformWord(wordPart);
                transformedWordBuilder.append(transformed);
            }
            transformedWord = transformedWordBuilder.toString();
        } else if(word.matches(END_WITH_WAY_PATTERN)) {
            return word;
        } else {
            char[] chars = word.toCharArray();
            boolean isStartingWithVowel = VOWELS.contains(Character.toLowerCase(chars[0]));
            final char[] appendPart = isStartingWithVowel ? APPEND_FOR_VOWELS.toCharArray() : APPEND_FOR_CONSONANT.toCharArray();
            char[] resultChars = new char[chars.length + appendPart.length];
            int i = 0;
            int j = isStartingWithVowel ? 0 : 1;
            int resultIndex = 0;
            while (i < chars.length) {
                if (resultChars[resultIndex] != 0) {// skip filled result
                    resultIndex++;
                } else if (PUNCTUATION_MARKS.contains(chars[i])) { // skip punctuation chars
                    i++;
                } else if (PUNCTUATION_MARKS.contains(chars[j])) { // add punctuation chars
                    resultChars[resultChars.length - (chars.length - j)] = chars[j];
                    j = (j + 1) % chars.length;
                } else {
                    boolean isCapitalLetter = Character.isUpperCase(chars[i]);
                    char c = chars[j];
                    resultChars[resultIndex] = isCapitalLetter ? Character.toUpperCase(c) : Character.toLowerCase(c);
                    j = (j + 1) % chars.length;
                    i++;
                    resultIndex++;
                }
            }
            i = 0;
            while (i < appendPart.length) { // add appendPart
                if (resultChars[resultIndex] == 0) {
                    resultChars[resultIndex] = appendPart[i];
                    i++;
                }
                resultIndex++;
            }
            transformedWord = new String(resultChars);
        }
        return transformedWord;
    }
}
