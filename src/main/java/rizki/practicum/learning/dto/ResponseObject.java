package rizki.practicum.learning.dto;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject <T> {
    private int code;
    private String status;
    private T object;
    private String message;
}
