package com.octo.blog.camel.test;

import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Exemple de test unitaire.
 *
 * @author <a href="mailto:clunven@octo.com">C&eacute;drick LUNVEN</a>
 */
public class CamelContextTest extends CamelSpringTestSupport {
    
    /** logger. */
    private Logger logger = Logger.getLogger(this.getClass());
    
    @EndpointInject(uri = "mock:destA")
    protected MockEndpoint endPointA;
    
    @EndpointInject(uri = "mock:destB")
    protected MockEndpoint endPointB;
    
    @EndpointInject(uri = "direct:testmulti")
    protected Endpoint testMulti;
    
    @Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:META-INF/spring/camel-context2.xml");
    }

    @Test
    public void testCamel() throws Exception {
        template.sendBody(testMulti, "Bonjour A et B");
        
        // Temps laissé pour initialisation du contexte
        Thread.sleep(1000);
        
        logger.info("A a recu '" + endPointA.getReceivedCounter() + "' élément(s)");
        logger.info("B a recu '" + endPointB.getReceivedCounter() + "' élément(s)");
        
        // 0 est ici l'index d'un tableu et commence à 0.
        endPointA.assertExchangeReceived(0);
        endPointB.assertExchangeReceived(0);
        
        List < Exchange > messagesA = endPointA.getExchanges();
        for (Exchange exchange : messagesA) {
            logger.info("Messages A :  " + exchange.getIn().getBody());
        }
        List < Exchange > messagesB = endPointB.getExchanges();
        for (Exchange exchange : messagesB) {
            logger.info("Messages B :  " + exchange.getIn().getBody());
        }
    }
}
