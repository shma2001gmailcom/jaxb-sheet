package org.misha;

import junit.framework.Assert;
import org.apache.log4j.spi.AppenderAttachable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class SpringAppTests {
    
    @Inject
    private AppenderAttachable helloService;
    
    @Test
    public void testSayHello() {
        Assert.assertEquals("Logger", helloService.getClass().getSimpleName());
    }
}
