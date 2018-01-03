package rizki.practicum.learning.service.plagiarism;

import info.debatty.java.stringsimilarity.JaroWinkler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.service.storage.StorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlagiarismServiceImpl implements PlagiarismService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    @Qualifier("DocumentStorageService")
    private StorageService storageService;

    @Override
    public Document checkPlagiarism(String idDocument) throws Exception {
        Document document = documentRepository.findOne(idDocument);
        List<Document> documents = (ArrayList) documentRepository.findAllByAssignment(document.getAssignment().getId());
        for(Document temp : documents){
            if(!document.getPlagiarism().containsKey(temp.getId())){
                this.documentCheckPlagiarism(document,temp);
            }
        }
        return document;
    }

    @Override
    public void BatchProcessing(String idAssignment) throws Exception {
        List<Document> documents = documentRepository.findAllByAssignment(idAssignment);
        for(Document document : documents){
            for(Document temp: documents){
                if(!document.getPlagiarism().containsKey(temp.getId())){
                    this.documentCheckPlagiarism(document,temp);
                }
            }
            documents.remove(document);
        }
    }

    private void documentCheckPlagiarism(Document document, Document temp){
        if(!document.getPlagiarism().containsKey(temp.getId())){
            double result = this.countPlagiarism(document.getFilename(),temp.getFilename());
            Map documentPlagiarism = document.getPlagiarism();
            documentPlagiarism.put(temp, result);
            Map documentTempPlagiarism = temp.getPlagiarism();
            documentTempPlagiarism.put(document,result);
            temp.setPlagiarism(documentTempPlagiarism);
            document.setPlagiarism(documentPlagiarism);
            documentRepository.save(document);
            documentRepository.save(temp);
        }
    }

    private double countPlagiarism(String urlFile1, String urlFile2){
        JaroWinkler jaroWinkler = new JaroWinkler();
        String file1 = null, file2 = null;
        try {
            file1 = storageService.load(urlFile1);
            file2 = storageService.load(urlFile2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return ((jaroWinkler.distance(file1,file2))*100);
    }
}
