package java8.inaction.stream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Client {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        List<String> threeHighCaloryDish = menu.stream().filter(dish -> dish.getCalories() > 300).map(Dish::getName)
                //.limit(3)
                .skip(2).collect(toList());
        System.out.println(threeHighCaloryDish);

        menu.stream().filter(dish -> dish.getType() == Dish.Type.MEAT).limit(2).collect(toList());

        //int[] intarray = {1, 2, 3, 4, 5};
        //Arrays.stream(intarray).map(x -> x*x).collect(, , );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream().map(x -> x * x).collect(toList());


        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        //List<int[]> pairs = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[] {i, j})).collect(toList());
        List<int[]> pairs = numbers1.stream().flatMap(i -> numbers2.stream().filter(j -> (i+j) %3 == 0).map(j -> new int[]{i, j})).collect(toList());
        System.out.println(Arrays.deepToString(pairs.toArray()));


        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("the menu is (somewhat)  vegetarian friendly!!");
        }

        boolean ishealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        boolean ishealthy2 = menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);

        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();
        menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(System.out::println);

        //何时使用findFirst和findAny
        //你可能会想，为什么会同时有findFirst和findAny呢？答案是并行。找到第一个元素
        //在并行上限制更多。如果你不关心返回的元素是哪个，请使用findAny，因为它在使用并行流
        //时限制较少。
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream().map(x -> x*x).filter(x -> x%3 == 0).findFirst();


        int sum = someNumbers.stream().reduce(0, Integer::sum);
        Optional<Integer> sumWithoutInitial = someNumbers.stream().reduce(Integer::sum);
        System.out.println("sum:" + sum);
        System.out.println("sumWithoutInitial:" + sumWithoutInitial.orElse(0));

        someNumbers.stream().reduce(Integer::max).ifPresent(i -> System.out.println("max:" + i));

        //使用IntStrem ,避免BOX.UNBOX
        menu.stream().mapToInt(Dish::getCalories).sum();

        //PIRMTIVY TO  OBJECT
        List<Integer> integerList = menu.stream().mapToInt(Dish::getCalories).boxed().collect(toList());

        //OptionalInt
        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        maxCalories.orElse(1);

        IntStream.range(1, 5).forEach(System.out::print);
        System.out.println("");
        IntStream.rangeClosed(1, 5).forEach(System.out::print);


        //IntStream 的map方法，返回值只能是int. 如果想要返回对象，比如数组，有两种方法
        //1.   boxed() 转换为 Stream<Integer>
        //2.   mapToObj
        List<int[]> intArray1 = IntStream.rangeClosed(1, 10).boxed().map(i -> new int[]{1, i}).collect(toList());
        System.out.println(Arrays.deepToString(intArray1.toArray()));

        List<int[]> intArray2 = IntStream.rangeClosed(1, 10).mapToObj(i -> new int[]{1, i}).collect(toList());
        System.out.println(Arrays.deepToString(intArray2.toArray()));

        //构建勾股数
        Stream<int[]> gougushu = IntStream.rangeClosed(1, 100).boxed().flatMap(
                a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a*a + b*b)%1 == 0).mapToObj(b -> new int[] {a, b, (int)Math.sqrt(a*a + b*b)})
        );
        //优化版,只需要计算一次开方
        Stream<double[]> gougushu2 = IntStream.rangeClosed(1, 100).boxed().flatMap(
                a -> IntStream.rangeClosed(a, 100).mapToObj(b -> new double[]{a, b, Math.sqrt(a*a+b*b)})
                .filter(i -> i[2]%1 == 0)
        );
        gougushu2.limit(10).forEach(t -> System.out.println(t[0]+ " "+ t[1]+ " "+t[2]));

        //构造stream 的方法
        //从值构建
        Stream.of("Java 8", "Lamda", "in action").map(String::toUpperCase).forEach(System.out::println);
        //从数组构建
        int[] numberArray = new int[]{1,2,3,4,5};
        Arrays.stream(numberArray).sum();
        //从文件构建,计算一个多少个字符
        long count = 0;
        try (Stream<String> lines = Files.lines(Paths.get("E:\\code\\spring\\src\\main\\java\\java8\\inaction\\stream\\Client.java"),Charset.defaultCharset())) {
                count = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println("character size:" + count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //无限流 斐波那契数列生成
        //Stream.iterate
        //Stream.generate()
        Stream.iterate(new int[]{0, 1}, i -> new int[]{i[1], i[0]+i[1]}).limit(10).map(t -> t[0]).forEach(n -> System.out.print(" " + n));
        System.out.println("");
        Stream.generate(Math::random).limit(5).forEach(n -> System.out.print(" " + n));


        //收集器  对象的某个属性进行收集
        //热量最大的菜
        Comparator<Dish> maxCaloryDishComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> maxCaloryDish = menu.stream().collect(maxBy(maxCaloryDishComparator));
        System.out.println("");
        maxCaloryDish.ifPresent(System.out::println);
        //计数
        menu.stream().count();
        menu.stream().collect(counting());
        //求和，平均
        int sumcalory = menu.stream().collect(summingInt(Dish::getCalories));
        double averagecolory = menu.stream().collect(averagingInt(Dish::getCalories));
        //统计接口
        IntSummaryStatistics statistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("statistics:" + statistics);

        //对象某个属性的字符串拼接
        menu.stream().map(Dish::getName).collect(joining(","));


        //collect 的一般形式： Collectors.reducing
        //例如,summingInt的一般形式
        //reducing 参数说明：
        //参数1： 起始值   参数2：map函数，把对象转换为数值   参数3： BinaryOperator,规约函数
        int summing1 = menu.stream().collect(reducing(0, Dish::getCalories, (a,b)->a+b));
        //等价于
        int summing2 = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("summing1:" + summing1 + "; summing2:" + summing2);

        //热量最高的菜的一般写法
        menu.stream().collect(reducing((a,b)-> a.getCalories() > b.getCalories()? a : b));
        //等价于
        //menu.stream().collect(maxBy(maxCaloryDishComparator));

        Map<CaloricLevel,List<Dish>> dishesByCaloricLevel = menu.stream().collect(groupingBy(d -> {
            if (d.getCalories() <= 400) {
                return CaloricLevel.DIET;
            } else if (d.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        }));

        Function<Dish, CaloricLevel> f = d -> {if (d.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (d.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }};

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel2 = menu.stream().collect(groupingBy(f));

        //groupingby 多级分组1
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>>  dishesByTypeCaloricLevel = menu.stream().collect(groupingBy(Dish::getType, groupingBy(f)));
        System.out.println("dishesByTypeCaloricLevel=>" + dishesByTypeCaloricLevel);

        //groupingby 多级分组2   参考前面  count()  counting() 使用，这里就是用到了counting
        //实际上  menu.stream.collect(groupingBy(Dish::getType)) 是
        //menu.stream().collect(groupingBy(Dish::getType, toList()));
        //的简写
        Map<Dish.Type, Long> dishCount = menu.stream().collect(groupingBy(Dish::getType, counting()));

        //groupingby 多级分组3
        //查找每个分组中，热量最高的菜
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("mostCaloricByType=>" + mostCaloricByType);

        //把收集器的结果转换为另一种类型
        Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),Optional::get)));
        System.out.println("mostCaloricByType2=>" + mostCaloricByType2);

        //groupingby 每个分类菜的热量合计
        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(
                groupingBy(Dish::getType, summingInt(Dish::getCalories))
        );
        System.out.println("totalCaloriesByType=>" + totalCaloriesByType);

        //groupingBy 和 mapping 配合
        //mapping 接收两个参数：1.对元素进行转换   2. 对处理结果进行收集
        //注意和 collectingAndThen 的区别
        //collectingAndThen 是先收集，然后转换
        //mapping 是先转换，然后收集
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream().collect(
                groupingBy(Dish::getType, mapping(f, toSet())));
        System.out.println("caloricLevelsByType=>" + caloricLevelsByType);

        //使用HashSet
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType2 = menu.stream().collect(
                groupingBy(Dish::getType, mapping(f, toCollection(HashSet::new))));
        System.out.println("caloricLevelsByType2=>" + caloricLevelsByType2);

        //分区 是 分组的特殊情况，KEY 是boolean 类型
        Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitionedMenu=>" + partitionedMenu);

        //List<Dish> vegetarianDishes = partitionedMenu.get(true);
        //等价于
        //List<Dish> vegetarianDishes = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());

        //分区  多级分组示例：
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println("vegetarianDishesByType=>" + vegetarianDishesByType);

        //分区  多级分组示例：
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("mostCaloricPartitionedByVegetarian=" + mostCaloricPartitionedByVegetarian);

//        Function<Integer, Boolean> isprime = i -> {
//            int candidateRoot = (int) Math.sqrt(i);
//            return IntStream.rangeClosed(2, candidateRoot).noneMatch(x -> i % x == 0);
//        };

        Map<Boolean, List<Integer>> partitionPrimes = IntStream.rangeClosed(2, 100).boxed().collect(partitioningBy(x -> Client.isprime(x)));
        System.out.println("partitionPrimes=>" + partitionPrimes);
    }

    public enum CaloricLevel{DIET, NORMAL, FAT}

    public static boolean isprime(int i) {
        int candicateRoot = (int) Math.sqrt(i);
        return IntStream.rangeClosed(2, candicateRoot).noneMatch(x -> i % x == 0);
    }
}
