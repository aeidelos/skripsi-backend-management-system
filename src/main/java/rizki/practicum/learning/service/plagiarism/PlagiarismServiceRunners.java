package rizki.practicum.learning.service.plagiarism;
/*
    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
*/

import com.github.javaparser.ParseProblemException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rizki.practicum.learning.configuration.FilesLocationConfig;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.repository.PlagiarismContentRepository;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.storage.StorageService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@Component
public class PlagiarismServiceRunners implements Runnable {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private PlagiarismContentRepository plagiarismContentRepository;

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService documentStorageService;

    @Autowired
    @Qualifier("SourceCodeStorageService")
    private StorageService sourceCodeStorageService;

    @Autowired
    private Levensthein levensthein;

    private List<Document> document;

    public PlagiarismServiceRunners() {
    }

    public PlagiarismServiceRunners(List<Document> document) {
        this.document = document;
    }

    @Override
    public void run() {
        List<Document> documentsList = documentRepository.findAllByAssignmentAndPracticanIsNot
                (document.get(0).getAssignment(),document.get(0).getPractican()) ;
        for (Document doc : document) {
            if (documentsList == null || documentsList.size() > 0) {
                for (Document temp : documentsList) {
                    try {
                        this.documentCheckPlagiarism(doc, temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void documentCheckPlagiarism(Document document, Document temp) throws IOException, InvalidFormatException {
        String file_ext_1 = FilenameUtils.getExtension(document.getFilename());
        String file_ext_2 = FilenameUtils.getExtension(temp.getFilename());
        double result = 0;
        String content_file_1 = null;
        String content_file_2 = null;

        if (Arrays.asList(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED).contains(file_ext_1) &&
                Arrays.asList(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED).contains(file_ext_2)) {
            content_file_1 = documentStorageService.load(document.getFilename());
            content_file_2 = documentStorageService.load(temp.getFilename());
            content_file_1.replaceAll("[^A-Za-z0-9]", "");
            content_file_2.replaceAll("[^A-Za-z0-9]", "");
            result = 100 - ((levensthein.rates(content_file_1, content_file_2)) * 100);
        } else if (Arrays.asList(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED).contains(file_ext_1) &&
                Arrays.asList(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED).contains(file_ext_2)) {
            ASTPlagiarism astPlagiarism = null;
            try {
                astPlagiarism = new ASTPlagiarism(document.getFilename(), temp.getFilename());
                result = astPlagiarism.rates;
            } catch (ParseProblemException e) {
                result = -1;
            }
        } else {
            throw new FileFormatException("File tidak didukung atau format file yang dibandingkan tidak sama");
        }
        PlagiarismContent plagiarismContent = new PlagiarismContent();
        plagiarismContent.setDocument1(document);
        plagiarismContent.setDocument2(temp);
        plagiarismContent.setRate(result);
        plagiarismContent.setAssignment(document.getAssignment());
        if (result > 80.0) {
            document.setMarkAsPlagiarized(true);
            temp.setMarkAsPlagiarized(true);
            documentRepository.save(document);
            documentRepository.save(temp);
        }
        plagiarismContentRepository.save(plagiarismContent);
    }

}
