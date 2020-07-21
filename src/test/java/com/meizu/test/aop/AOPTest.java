package com.meizu.test.aop;

import com.meizu.spring.aop.api.IHelloWorld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/*.xml"})
public class AOPTest {
    @Autowired
    List<IHelloWorld> hello;

    @Test
    public void test() {
        hello.forEach(h -> h.test("hicen"));
        //hello.test("hicen");

        System.out.println("------------");

        hello.forEach(h -> h.sayHello("hicen2"));
    }
}
