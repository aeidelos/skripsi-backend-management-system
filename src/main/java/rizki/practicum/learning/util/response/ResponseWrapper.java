package rizki.practicum.learning.util.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public final class ResponseWrapper {

    public ResponseEntity<Map<String,Object>> restResponseCollectionWrapper(HttpStatus statusCode, List<Object> body,
                                                                        String location, int statusResponse,String... message){

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("object", body);
        response.put("code",statusCode);
        response.put("response",statusResponse);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("X-Fsl-Location", location);
        httpHeaders.add("X-Fsl-Response-Code", String.valueOf(statusCode));

        return new ResponseEntity<Map<String, Object>>(response, httpHeaders, statusCode);
    }

}
