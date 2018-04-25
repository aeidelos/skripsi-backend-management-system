package rizki.practicum.learning.dto;
/*
    Created by : Rizki Maulana Akbar, On 04 - 2018 ;
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rizki.practicum.learning.entity.Task;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExportClassroom {

    private Task task;

    private List<UserGrade> documents;
}
