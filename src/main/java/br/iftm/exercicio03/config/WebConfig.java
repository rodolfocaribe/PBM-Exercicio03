package br.iftm.exercicio03.config;

import br.iftm.exercicio03.utils.converters.YamlJackson2HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final MediaType MEDIA_TYPE_APPLICATION_YML
            = MediaType.valueOf("application/x-yaml");
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
// Via HEADER PARAM
        configurer.favorParameter(false)// Não aceita parametros pela url
                .ignoreAcceptHeader(false)// aceita os parametros no header (não aceita pela url)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }
}