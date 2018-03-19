package rizki.practicum.learning.dto;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentPlagiarism {
    private Document document;
    private PlagiarismContent plagiarism;
}

