package rizki.practicum.learning.proxy;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.dto.CompilerIO;

import java.io.File;

@FeignClient(name = "online-compiler", url = "localhost:8080",
        configuration = CompilerProxy.MultipartSupportConfig.class)
public interface CompilerProxy {
    @PostMapping("/api/v2/compile")
    @Headers("Content-Type: multipart/form-data")
    CompilerIO compile(@Param("sourcecode")File[] files);

    public class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }
}

