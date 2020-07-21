package java8.inaction.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class TraderClient {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        //找出2011年发生的所有交易，并按交易额排序（从低到高）
        List<Transaction> tr2011 = transactions.stream().filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println(tr2011);
        //交易员都在哪些不同的城市工作过
        List<String> cityList = transactions.stream().map(t -> t.getTrader().getCity()).distinct().collect(toList());
        //或者使用set
        Set<String> citySet =  transactions.stream().map(t -> t.getTrader().getCity()).collect(toSet());
        System.out.println(cityList.toString());
        System.out.println(citySet);

        //查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> nameOrderList = transactions.stream().map(t -> t.getTrader()).filter(t -> t.getCity().equals("Cambridge")).distinct().sorted(Comparator.comparing(Trader::getName))
                .collect(toList());
        System.out.println("name ordered list:" + nameOrderList);

        //返回所有交易员的姓名字符串，按字母顺序排序
        //String
        String names = transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().reduce("", (a,b)-> a+b);
        //StringBuilder
        String names1 = transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().collect(joining(","));
        System.out.println("all names: " + names);
        System.out.println("all names1: " + names1);

        //有没有交易员是在米兰工作的
        boolean hasMilan = transactions.stream().map(t -> t.getTrader().getCity()).anyMatch(city -> "Milan".equals(city));
        boolean hasMilan2 = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println("hasMilan:" + hasMilan);
        System.out.println("hasMilan2:" + hasMilan2);

        //打印生活在剑桥的交易员的所有交易额
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).forEach(System.out::println);

        //所有交易中，最高的交易额是多少
        transactions.stream().max(Comparator.comparing(Transaction::getValue)).ifPresent(System.out::println);
        transactions.stream().map(t -> t.getValue()).reduce(Integer::max).ifPresent(System.out::println);

        //找到交易额最小的交易
        transactions.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1: t2);


    }
}
