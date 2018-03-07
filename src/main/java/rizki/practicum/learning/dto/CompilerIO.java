package rizki.practicum.learning.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CompilerIO {
    private String result;
    private String errorLog;
    private String idDocument;
}
