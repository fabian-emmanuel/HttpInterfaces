package com.codewithfibbee.httpinterfaces.config;

import com.codewithfibbee.httpinterfaces.client.FlutterWaveClient;
import com.codewithfibbee.httpinterfaces.client.PayStackClient;
import com.codewithfibbee.httpinterfaces.constants.Api;
import com.google.gson.Gson;
import jakarta.jms.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

import static com.codewithfibbee.httpinterfaces.utils.Util.getAuthHeader;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    @Value("${flw-secret-key}")
    private String FLW_AUTH;
    @Value("${pstk-secret-key}")
    private String PSTK_AUTH;
    private final List<ConnectionFactory> factories;

    @Bean
    public FlutterWaveClient flutterWaveClient() {

        WebClient webClient = WebClient.builder()
                .baseUrl(Api.FLW_BASE_URL)
                .defaultHeaders(header -> header.addAll(getAuthHeader(FLW_AUTH)))
                .build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build()
                .createClient(FlutterWaveClient.class);
    }

    @Bean
    public PayStackClient payStackClient(){

        WebClient webClient = WebClient.builder()
                .baseUrl(Api.PSTK_BASE_URI)
                .defaultHeaders(header -> header.addAll(getAuthHeader(PSTK_AUTH)))
                .build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build()
                .createClient(PayStackClient.class);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public JmsListenerContainerFactory<?>
    myFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, factories.get(0));
        return factory;
    }

//    private HttpHeaders getAuthHeader(String auth){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(auth);
//        return headers;
//    }
}
