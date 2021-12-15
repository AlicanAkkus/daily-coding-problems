import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.Collectors

/*
PROBLEM DEFINITION
==================
This problem was asked by Microsoft.
Given a dictionary of words and a string made up of those words (no spaces), return the original sentence in a list. If there is more than one possible reconstruction, return any of them. If there is no possible reconstruction, then return null.
For example, given the set of words 'quick', 'brown', 'the', 'fox', and the string "thequickbrownfox", you should return ['the', 'quick', 'brown', 'fox'].
Given the set of words 'bed', 'bath', 'bedbath', 'and', 'beyond', and the string "bedbathandbeyond", return either ['bed', 'bath', 'and', 'beyond] or ['bedbath', 'and', 'beyond'].
*/

class DailyProblem2 extends Specification {

    //TODO exclude repeated words from output

    @Unroll("should convert #givenWords to #output")
    void "daily problem 2 solution"() {
        expect:
        output == runAs()

        where:
        givenWords                                  | word               | output                                      | runAs
        ['quick', 'brown', 'the', 'fox']            | 'thequickbrownfox' | ['the', 'quick', 'brown', 'fox']            | { groovyStyle(givenWords, word) }
        ['bed', 'bath', 'bedbath', 'and', 'beyond'] | 'bedbathandbeyond' | ['bed', 'bedbath', 'bath', 'and', 'beyond'] | { groovyStyle(givenWords, word) }
        ['quick', 'brown', 'the', 'fox']            | 'thequickbrownfox' | ['the', 'quick', 'brown', 'fox']            | { javaStyle(givenWords, word) }
        ['bed', 'bath', 'bedbath', 'and', 'beyond'] | 'bedbathandbeyond' | ['bed', 'bedbath', 'bath', 'and', 'beyond'] | { javaStyle(givenWords, word) }
    }

    private List<String> javaStyle(List<String> givenWords, String word) {
        return givenWords
                .stream()
                .map(givenWord -> word.contains(givenWord) ? Map.of("word", givenWord, "index", word.indexOf(givenWord)) : null)
                .filter(mappedWord -> Objects.nonNull(mappedWord))
                .sorted((m1, m2) -> Integer.valueOf(m1.get("index").toString()) <=> Integer.valueOf(m2.get("index").toString()))
                .map(sortedWords -> sortedWords.get("word").toString())
                .collect(Collectors.toList())
    }

    private List<String> groovyStyle(List<String> givenWords, String word) {
        return givenWords.collect { it -> word.contains(it) ? ["word": it, index: word.indexOf(it)] : null }
                .findAll { it != null }
                .sort { it.index }
                .collect { it.word as String }
    }
}
