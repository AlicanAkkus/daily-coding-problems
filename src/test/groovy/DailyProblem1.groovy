import spock.lang.Specification
import spock.lang.Unroll

/*
PROBLEM DEFINITION
==================
This problem was asked by Uber.
Given an array of integers, return a new array such that each element at index i of the new array is the product of all the numbers in the original array except the one at i.
For example, if our input was [1, 2, 3, 4, 5], the expected output would be [120, 60, 40, 30, 24]. If our input was [3, 2, 1], the expected output would be [2, 3, 6]
*/

class DailyProblem1 extends Specification {

    @Unroll("should convert #input to #output")
    void "daily problem 1 solution"() {
        expect:
        output == runAs()

        where:
        input           | output                | runAs
        [1, 2, 3, 4, 5] | [120, 60, 40, 30, 24] | { oldJava(input) }
        [3, 2, 1]       | [2, 3, 6]             | { groovyStyle(input) }
        [5, 3, 2]       | [6, 10, 15]           | { groovyStyle(input) }
    }

    private List<Integer> oldJava(List<Integer> input) {
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            int sum = 1;

            for (int j = 0; j < input.size(); j++) {
                if (i != j) {
                    sum *= input.get(j);
                }
            }
            output.add(sum);
        }
        return output;
    }

    private List<Integer> groovyStyle(List<Integer> input) {
        return input.withIndex().collect { item, index ->
            int result = 1
            input.withIndex().findAll { subitem, subitemIndex -> index != subitemIndex }
                    .collect { subitem, subitemIndex -> subitem }
                    .each { it -> result *= it }

            result
        }
    }
}
