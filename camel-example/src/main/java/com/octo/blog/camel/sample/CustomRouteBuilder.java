package com.octo.blog.camel.sample;

import org.apache.camel.builder.RouteBuilder;

/**
 * Exemple de RouteBuilder.
 *
 * @author <a href="mailto:clunven@octo.com">C&eacute;drick LUNVEN</a>
 */
public class CustomRouteBuilder extends RouteBuilder {
    
    /** Constante a externaliser. */
    private static final String FOLDER_IN           = "file:in2";
    /** Constante a externaliser. */
    private static final String FOLDER_OUT_A        = "file:outA";
    /** Constante a externaliser. */
    private static final String FOLDER_OUT_B        = "file:outB";
    /** Constante a externaliser. */
    private static final String FOLDER_OUT_DEFAULT  = "file:outDefault";
        
    /** {@inheritDoc} */
    public void configure() throws Exception {
        from(FOLDER_IN).choice()
            .when(xpath("/demande/type = 'A'").booleanResult()).to(FOLDER_OUT_A)
            .when(xpath("/demande/type = 'B'").booleanResult()).to(FOLDER_OUT_B)
            .otherwise().to(FOLDER_OUT_DEFAULT);
    }

}
