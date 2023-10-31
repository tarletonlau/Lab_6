import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Main {
    
    // ================ task 1 ===================================

    public static IntStream twinPrimes(int n) {
        return IntStream.rangeClosed(2,n)
            .filter(x -> isPrime(x) && (isPrime(x + 2) || isPrime(x - 2)));
    }

    private static boolean isPrime(int n) {
        return n > 1 && IntStream.range(2,n)
            .noneMatch(x -> n % x == 0);
    }

    // ==================== task 2 ================================
    
    public static String reverse(String str) {
        return Stream.<String>of(str.split(""))
            .reduce("", (x,y) -> y + x);
    }


    // ==================== task 3 ================================

    public static long countRepeats(List<Integer> list) {
        return IntStream.range(0, list.size() - 1)
                .filter(i -> shouldCount(i, list))
                .count();
    }

    private static boolean shouldCount(int index, List<Integer> list) {
        // ========== base cases ===========
        if (index >= list.size() - 1) {
            return false;
        }

        if (!list.get(index).equals(list.get(index + 1))) {
            return false;
        }

        // =====================

        // recursive check: if next number is the same, skip this index
        if (index < list.size() - 2 && list.get(index + 1).equals(list.get(index + 2))) {
            return false;
        }
        return true;
    }


    // ==================== task 4 ================================

    public static Stream<String> gameOfLife(List<Integer> list,
                                            UnaryOperator<List<Integer>> rule, int n) {

        return Stream.iterate(list, rule)
                .limit(n)
                .map(gen -> gen.stream()
                        .map(cell -> cell == 0 ? "." : "x")
                        .collect(Collectors.joining()));
    }

    public static UnaryOperator<List<Integer>> generateRule() {
        return currentGen -> IntStream.range(0, currentGen.size())
                .mapToObj(i -> computeNextState(i, currentGen))
                .collect(Collectors.toList());
    }

    private static int nextState(int current, int liveNeighbours) {
        if (current == 1) {
            return 0;
        } else {
            if (liveNeighbours == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static int computeNextState(int index, List<Integer> currentGen) {
        int left = getNeighbourState(index - 1,currentGen);
        int right = getNeighbourState(index + 1,currentGen);
        int current = currentGen.get(index);

        return nextState(current,left + right);
    }

    private static int getNeighbourState(int index, List<Integer> currentGen) {
        if (index <= 0 || index >= currentGen.size() - 1) {
            return 0;
        } else {
            return currentGen.get(index);
        }
    }

}
