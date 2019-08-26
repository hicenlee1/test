package com.meizu.fp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class StrtegyFp {

    public List<Hero> getHeros(List<Hero> heros, Predicate<Hero> predicate) {
        List<Hero> result = new ArrayList<Hero>();
        for (Hero h : heros) {
            if (predicate.test(h)) {
                result.add(h);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Hero sylvanas = new Hero("sylvanas", "female", "敏捷");
        Hero arthas = new Hero("arthas", "male", "力量");
        Hero niutou = new Hero("niutou", "male", "力量");
        Hero dafashi = new Hero("dafashi", "male", "智慧");
        
        Hero deadknight = new Hero("deadknight", "male", "力量");
        Hero mountainking = new Hero("mountainking", "male", "力量");
        Hero beastmaster = new Hero("beastmaster", "male", "力量");
        Hero saintknight = new Hero("saintknight", "male", "力量");

        List<Hero> hero = Arrays.asList(sylvanas, arthas, niutou, dafashi,
                deadknight, mountainking, beastmaster, saintknight);
        StrtegyFp fp = new StrtegyFp();
        List<Hero> filterHero = fp.getHeros(hero, new Predicate<Hero>() {

            @Override
            public boolean test(Hero t) {
                return t.getType().equals("力量") && t.getGender().equals("male");
            }
        });
       //filterHero.forEach(System.out :: println);
        filterHero.parallelStream().forEach(System.out :: println);
        
    }

}
