package rizki.practicum.learning.util.response;

import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseBuilder{

    HttpStatus statusCode;
    Object body;
    String location;
    int statusResponse;
    String message;

    public ResponseBuilder(){
    }

    public ResponseBuilder setMessage(String message){
        this.message = message;
        return this;
    }

    public ResponseBuilder setBody(Object body){
        this.body = body;
        return this;
    }

    public ResponseBuilder setFile(File file){
        String filename = file.getPath();

        return this;
    }

    public ResponseBuilder setStatusResponse(int statusResponse){
        this.statusResponse = statusResponse;
        return this;
    }

    public ResponseBuilder setLocation(String location){
        this.location = location;
        return this;
    }

    public ResponseBuilder setStatusCode(HttpStatus httpStatus){
        this.statusCode = httpStatus;
        return this;
    }

    public ResponseEntity<Map<String,Object>> build(){
        Map<java.lang.String, java.lang.Object> response = new HashMap<>();
        response.put("message", message);
        response.put("object", body);
        response.put("code",statusCode);
        response.put("response",statusResponse);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("X-Fsl-Location", location);
        httpHeaders.add("X-Fsl-Response-Code", java.lang.String.valueOf(statusCode));
        return new ResponseEntity<>(response, httpHeaders, this.statusCode);
    }



}
