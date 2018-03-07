package rizki.practicum.learning.proxy;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.dto.CompilerIO;

@FeignClient(name = "online-compiler", url = "localhost:8080")
public interface CompilerProxy {
    @PostMapping("/api/v2/compile")
    CompilerIO compile(@RequestParam("sourcecode")MultipartFile ...code);
}
