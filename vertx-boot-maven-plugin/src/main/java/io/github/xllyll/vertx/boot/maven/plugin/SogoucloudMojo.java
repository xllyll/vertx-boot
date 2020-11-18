package io.github.xllyll.vertx.boot.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

@Mojo( name= "sogoucloud" , defaultPhase= LifecyclePhase.PACKAGE)
public class SogoucloudMojo  extends AbstractMojo {

    @Parameter
    private String  msg ;

    @Parameter
    private List<String> options ;

    @Parameter( property =  "args")
    private String  args ;


    public void  execute()  throws MojoExecutionException, MojoFailureException {
        System. out.println( "hello sogoucloud:"+ msg) ;
        System. out.println( "hello sogoucloud:"+ options) ;
        System. out.println( "hello sogoucloud:"+ args) ;
    }

}
