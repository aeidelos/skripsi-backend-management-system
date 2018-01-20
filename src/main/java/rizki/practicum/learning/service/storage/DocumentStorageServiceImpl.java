package rizki.practicum.learning.service.storage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@Qualifier("DocumentStorageService")
public class DocumentStorageServiceImpl extends StorageServiceImpl implements StorageService {
    DocumentStorageServiceImpl(){
        this.rootLocation = Paths.get(FilesLocationConfig.Document.LOCATION);
    }

    @Override
    public String store(MultipartFile file, String filename) {
        if(ArrayUtils.contains(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED,file.getContentType())){
            return super.store(file, filename);
        }else{
            return null;
        }
    }

    @Override
    public String load(String filename) throws IOException, InvalidFormatException {
        FileInputStream fis = new FileInputStream("media/document/"+filename);
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        return extractor.getText();
    }


}
