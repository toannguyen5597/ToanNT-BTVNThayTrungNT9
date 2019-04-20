import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args) {

        List<String> listString = Arrays.asList("helo","helo","helo","helo","helo","helo","loge","loge","loge","loge","loge","loge");
        List<Integer> listNumber = Arrays.asList(1,2,3,4,5,6,7,8,9,19,2,5,1,2);

        //use Sum
        Function<List<Integer>, Integer> mySum = (in) -> {
            return in.stream().reduce(0,(c,b)->(c+b));
        };
        System.out.println("Sum of Collection: "+ mySum.apply(listNumber));

        //use Group
        Function<List<String>,Map<String, Long>> myGroup = (in) -> {
            return in.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        };
        System.out.println("Group Collection: "+myGroup.apply(listString));

        //use Count
        Function<List<String>, Long> myCount = (in) ->{
            return in.stream().count();
        };
        System.out.println("Count of Collection: "+myCount.apply(listString));

        //use Filter
        Function<List<String>, List<String>> myFilter = (in) ->{
            return listString.stream().filter(e -> "helo".equals(e)).collect(Collectors.toList());
        };
        System.out.println("Get Collection filter by helo: "+myFilter.apply(listString));

        //use min
        Function<List<Integer>, Integer> myMin = (in) ->{
            return in.stream().min(Comparator.comparing(Integer::intValue)).orElseThrow(NoSuchElementException::new);
        };
        System.out.println("Get Min of Collection: "+myMin.apply(listNumber));

        //use max
        Function<List<Integer>, Integer> myMax = (in) ->{
            return in.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
        };
        System.out.println("Get Max of Collection: "+myMin.apply(listNumber));

        //use parallelStream and reduce
        Function<List<Integer>, Optional> myParaReduce = (in) ->{
            return  in.parallelStream().reduce((a,b) -> (a+b));
        };
        System.out.println("Sum use reduce by paralleStream :"+myParaReduce.apply(listNumber));

        //use parallelStream and map
        Function<List<String>, List<String>> myParaMap = (in) ->{
            return in.parallelStream().map(String::toUpperCase).collect(Collectors.toList());
        };
        System.out.println("uppercase collection use map by paralleStream: "+myParaMap.apply(listString));

    }
}
