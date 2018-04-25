package rizki.practicum.learning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.User;

import java.util.List;

/*
    Created by : Rizki Maulana Akbar, On 04 - 2018 ;
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGrade {

    private User user;

    private double grade;

}
