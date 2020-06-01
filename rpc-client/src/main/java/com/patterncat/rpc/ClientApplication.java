package com.patterncat.rpc;

import com.patterncat.rpc.proxy.RpcProxyFactory;
import com.patterncat.rpc.service.demo.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * Created by patterncat on 2016-04-11.
 */
@SpringBootApplication
public class ClientApplication {


    @Bean
    public RpcProxyFactory rpcProxyFactory(){
        return new RpcProxyFactory();
    }

    /**
     * 也可以采用配置文件的方式
     * 如果不想自己proxy,可以像dubbo那样扩展schema
     * 或者自己scan指定包,在FactoryBean里头替换
     * @param rpcProxyFactory
     * @return
     */
    @Bean
    public HelloService buildHelloService(RpcProxyFactory rpcProxyFactory){
        return rpcProxyFactory.proxyBean(HelloService.class,100);
    }

    public static void main(String[] args){
        SpringApplication app = new SpringApplication(ClientApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
    }
}
