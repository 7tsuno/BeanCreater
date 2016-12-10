package jp.n7ats.utilities;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.Data;

public class BeanCreaterTest {

    @Test
    public void test_001_Bean作成_通常生成時() {

        // exercise
        TestBean actual = BeanCreater.of(new TestBean()).construct(bean -> bean.setName("name"))
                .construct(bean -> bean.setId(new BigDecimal(330))).build();

        // verify
        assertThat(actual, is(hasProperty("name", equalTo("name"))));
        assertThat(actual, is(hasProperty("id", equalTo(new BigDecimal(330)))));

    }

    @Test
    public void test_002_Bean作成_ストリーム中での生成() {

        // exercise
        List<TestBean> actual = Arrays.asList("hoge", "fuga", "piyo").stream()
                .map(name -> BeanCreater.of(new TestBean()).construct(bean -> bean.setName(name)).build())
                .collect(Collectors.toList());

        // verify
        assertThat(actual, hasSize(3));
        assertThat(actual, hasItem(hasProperty("name", equalTo("hoge"))));
        assertThat(actual, hasItem(hasProperty("name", equalTo("fuga"))));
        assertThat(actual, hasItem(hasProperty("name", equalTo("piyo"))));

    }

    @Test
    public void test_003_Bean非作成_通常生成時() {

        // exercise
        TestBean actual = BeanCreater.of(TestBean::new).construct(bean -> bean.setName("name"))
                .construct(bean -> bean.setId(new BigDecimal(330))).build();

        // verify
        assertThat(actual, is(hasProperty("name", equalTo("name"))));
        assertThat(actual, is(hasProperty("id", equalTo(new BigDecimal(330)))));

    }

    @Test
    public void test_004_Bean非作成_ストリーム中での生成() {

        // exercise
        List<TestBean> actual = Arrays.asList("hoge", "fuga", "piyo").stream()
                .map(name -> BeanCreater.of(TestBean::new).construct(bean -> bean.setName(name)).build())
                .collect(Collectors.toList());

        // verify
        assertThat(actual, hasSize(3));
        assertThat(actual, hasItem(hasProperty("name", equalTo("hoge"))));
        assertThat(actual, hasItem(hasProperty("name", equalTo("fuga"))));
        assertThat(actual, hasItem(hasProperty("name", equalTo("piyo"))));

    }

    @Data
    public class TestBean {

        private String name;

        private BigDecimal id;

        private boolean flag;

    }

}
