package com.meizu.fp;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPIDemo {

    public static void main(String[] args) {
        //collect  toList
        List<String> list = Stream.of("a", "b", "c", "d").collect(Collectors.toList());
        System.out.println(Arrays.deepToString(list.toArray()));
        
        Set<Integer> set = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toSet());
        System.out.println(Arrays.deepToString(set.toArray()));
        
        //map
        List<String> upperList = Stream.of("a", "b", "c", "hello").map(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println(Arrays.deepToString(upperList.toArray()));
        
        //filter
        List<String> initialDigitList = Stream.of("a", "b", "c", "1a", "2b", "3c", "hello").filter(s -> Character.isDigit(s.charAt(0))).collect(Collectors.toList());
        System.out.println(Arrays.deepToString(initialDigitList.toArray()));
        
        //flatMap
        List<String> flatMapList = Stream.of(asList("1", "2"), asList("3", "4")).flatMap(a -> a.stream()).collect(toList());
        System.out.println(Arrays.deepToString(flatMapList.toArray()));
        
        //min
        List<Track> tracks = Arrays.asList(new Track("my heart will go on", 360),
                                    new Track("take me to your heart", 480),
                                    new Track("halo", 340),
                                    new Track("rock you", 340)
                                    );
        //Track track = tracks.stream().min(Comparator.comparing(t -> t.getDuration())).get();
        Track track = tracks.stream().min(Comparator.comparing(t -> t.getDuration())).get();
        System.out.println(track.getName());
        
        //reduce
        int count = Stream.of(1, 2, 3, 4, 5, 6).reduce(0, (acc, element) -> acc + element);
        System.out.println(count);
        
        int countWithIntitial = Stream.of(1, 2, 3, 4, 5, 6).reduce(100, (acc, element) -> acc + element);
        System.out.println(countWithIntitial);
        
        
      //mapToInt
        IntSummaryStatistics trackDurationStatis = tracks.stream().mapToInt(t -> t.getDuration()).summaryStatistics();
        System.out.printf("track.duration.max:%d\n track.duration.min:%d\n track.duration.average:%f\n track.duration.sum:%d \r\n", 
                            trackDurationStatis.getMax(), trackDurationStatis.getMin(), trackDurationStatis.getAverage(), trackDurationStatis.getSum());
        
        //方法引用
        Set<Integer> s = new HashSet<>(Arrays.asList(3,1,4,6,2,7, 9, 10));
        Set<Integer> orderSet = s.stream().collect(toCollection(TreeSet::new));
        orderSet.forEach(System.out::println);
        
        //收集器使用
//        List<Track> tracks = Arrays.asList(new Track("my heart will go on", 360),
//                new Track("take me to your heart", 480),
//                new Track("halo", 340),
//                new Track("rock you", 340)
//                );
        Artist chow = new Artist("周杰伦");
        chow.setSolo(true);
        chow.setMembers(Arrays.asList("周杰伦"));
        
        Album qilixiang = new Album("七里香");
        qilixiang.setTrackList(Arrays.asList(
                new Track("我的地盘", 300),
                new Track("外婆", 240),
                new Track("将军", 250)));
        
        Album fantasy = new Album("范特西");
        fantasy.setTrackList(Arrays.asList(
                new Track("爱在西元前", 249),
                new Track("简单爱", 320),
                new Track("开不了口", 290),
                new Track("威廉古堡", 300),
                new Track("双截棍", 310)
                ));
        
        Album jay = new Album("JAY");
        jay.setTrackList(Arrays.asList(
                new Track("可爱女人", 150),
                new Track("完美主义", 180),
                new Track("星晴", 240),
                new Track("娘子", 320),
                new Track("斗牛", 160),
                new Track("黑色幽默", 420)
                ));
        
        chow.setAlbum(Arrays.asList(qilixiang, fantasy, jay));
        
        Artist beyond = new Artist("BEYOND");
        beyond.setSolo(false);
        beyond.setMembers(Arrays.asList("黄家驹", "黄贯中", "黄家强", "叶世荣"));
        
        Album secretPolice = new Album("秘密警察");
        secretPolice.setMainMusician("黄家驹");
        secretPolice.setTrackList(Arrays.asList(
                new Track("冲开一切", 220),
                new Track("秘密警察", 240),
                new Track("再见理想", 230),
                new Track("昨日的牵绊", 210),
                new Track("大地", 200)
                ));
        
        Album Beyondiv = new Album("Beyond IV");
        Beyondiv.setMainMusician("黄家强");
        Beyondiv.setTrackList(Arrays.asList(
                new Track("真的爱你", 290),
                new Track("我有我风格", 280),
                new Track("曾是拥有", 235),
                new Track("摩登时代", 220),
                new Track("原谅我今天", 290),
                new Track("与你共行", 300)
                
                ));
        
        Album fateParty = new Album("命运派对");
        fateParty.setMainMusician("黄家驹");
        fateParty.setTrackList(Arrays.asList(
                new Track("光辉岁月", 290),
                new Track("两颗心", 280),
                new Track("俾面派对", 235)
                
                ));
        
        beyond.setAlbum(Arrays.asList(secretPolice,Beyondiv, fateParty));
        
        List<Artist> artists = Arrays.asList(chow, beyond);
        //找出成员最多的乐队
        Function<Artist, Integer> f =     artist -> artist.getMembers().size();
        Optional<Artist> op = artists.stream().collect(maxBy(Comparator.comparing(f)));
        System.out.println(op.get().getName());
        
        //找出周杰伦专辑的平均曲数
        double avaergeTracks = chow.getAlbum().stream().collect(Collectors.averagingInt(album -> album.getTrackList().size()));
        System.out.println("averageTracks=>" + avaergeTracks);
        
        //partitioningBy 把数据分成 true/false 
        Map<Boolean, List<Artist>> bandAndSolo = artists.stream().collect(partitioningBy(artist -> artist.isSolo()));
        System.out.println(bandAndSolo);
        System.out.println(bandAndSolo.get(Boolean.FALSE).get(0).getName());
        
        //groupBy 按照属性分组
        Map<String, List<Album>> albumByArtist = beyond.getAlbum().stream().collect(groupingBy(album -> album.getMainMusician()));
        System.out.println(albumByArtist);
        
        //收集成字符串
        String allArtistNames = artists.stream().map(Artist::getName).collect(joining(",", "[", "]"));
        System.out.println(allArtistNames);
        
        
        //组合收集器
        //计算每个艺术家的专辑个数
        Map<String, Long> m = beyond.getAlbum().stream().collect(groupingBy(album -> album.getMainMusician(), counting()));
        System.out.println(m);

        
        //使用收集器求每个艺术家的专辑名
        Map<String, List<String>> artistAlbum = beyond.getAlbum().stream().collect(groupingBy(Album::getMainMusician, 
                                                       mapping(Album::getName, toList())
                                                ));
        System.out.println(artistAlbum);
        
        Stream<String> stream = Stream.of("hello", "world");
        System.out.println(stream.collect(Collectors.mapping(x -> x.length(), Collectors.toList())));
        
        Stream.of(1, 2, 3).collect(Collectors.collectingAndThen(Collectors.averagingInt(x -> x), x -> x.intValue()));
        
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        
        List<Integer> nums = Arrays.asList(1, 1, 2, 3, 5);
        nums.stream().skip(2).forEach(System.out::println);
        System.out.println(nums.stream().anyMatch(x -> x > 3));
        nums.stream().findFirst().ifPresent(System.out::println);
        
        nums.stream().distinct().forEach(System.out::println);
        
        List<String> words = Arrays.asList("foo", "bar");
        words.stream().flatMap(x -> Arrays.asList(x, x).stream()).forEach(System.out::println);
        
        System.out.println(nums.stream().reduce("Foo", (x, y) -> x + y, (x, y) -> x + y));
        
        //System.out.println(nums.stream().collect(ArrayList<Integer>::new, ArrayList<Integer>::add, ArrayList::addAll));

//      //计算每个艺术家的曲目数 
//      Function<Album, Integer> trackSizeFunction = album -> album.getTrackList().size();
//      groupingBy(trackSizeFunction, counting());
//      Map<String, Long> artistMusicMap = beyond.getAlbum().stream().collect(groupingBy(album -> album.getMainMusician(), groupingBy(trackSizeFunction, counting())));
        Map<String, Optional<Integer>> artistTrackCount = beyond.getAlbum().stream().collect(groupingBy(Album::getMainMusician, 
                                                        mapping(album -> album.getTrackList().size(), 
                                                                reducing((x,y)-> x+y))));
        System.out.println(artistTrackCount);
        
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        
        Stream.iterate(0, n -> n + 3).limit(10). forEach(x -> System.out.print(x + " "));
        System.out.println();
        
        
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        System.out.println(Arrays.deepToString(evens));
        
        System.out.println(Stream.of(1, 2, 3, 4, 5).collect(Collectors.toMap(x -> x, x -> x * x)));
        
        //Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果。这也说明了了第三个函数参数的数据类型必须为返回数据类型了。
        //https://www.zhihu.com/question/35451347?q=Stream%20%E7%9A%84%20reduce
        System.out.println(nums.stream().reduce("Foo", (x, y) -> x + y, (x, y) -> x + y));
        System.out.println(nums.parallelStream().reduce("Foo", (x, y) -> x + y, (x, y) -> x + y));
    }
    
}

