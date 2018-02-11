package rizki.practicum.learning.service.storage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.codehaus.plexus.util.FileUtils;
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
import java.util.Arrays;

@Service
@Qualifier("DocumentStorageService")
public class DocumentStorageServiceImpl extends StorageServiceImpl implements StorageService {
    DocumentStorageServiceImpl() {
        this.rootLocation = Paths.get(FilesLocationConfig.Document.LOCATION);
    }

    @Override
    public String store(MultipartFile file, String filename) throws FileFormatException {
        String file_ext = FileUtils.getExtension(file.getOriginalFilename());
        if(Arrays.asList(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED).contains(file_ext)){
            return super.store(file, filename);
        }else{
            throw new FileFormatException();
        }
    }

    @Override
    public String load(String filename) throws IOException, InvalidFormatException {
        String file_ext = FileUtils.getExtension(filename);
        if(file_ext.equalsIgnoreCase("doc") || file_ext.equalsIgnoreCase("docx")){
            FileInputStream fis = new FileInputStream(filename);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            return extractor.getText();
        }else if(file_ext.equalsIgnoreCase("pdf")){
            File file = new File(filename);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }else{
            throw new FileFormatException();
        }
    }

    private String cleansing(String content){
        return null;
    }
}
