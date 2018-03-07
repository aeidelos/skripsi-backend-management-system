package rizki.practicum.learning.service.storage;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Qualifier("SourceCodeStorageService")
public class SourceCodeStorageServiceImpl extends StorageServiceImpl implements StorageService {
    SourceCodeStorageServiceImpl(){
        this.rootLocation = Paths.get(FilesLocationConfig.SourceCode.LOCATION);
    }

    @Override
    public ArrayList<String> store(MultipartFile[] file, String filename) throws FileFormatException {
        String file_ext = FilenameUtils.getExtension(file[0].getOriginalFilename());
        if(Arrays.asList(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED).contains(file_ext)){
            return super.store(file,filename);
        }else{
            throw new FileFormatException();
        }
    }

    @Override
    public String load(String filename) throws IOException, InvalidFormatException {
        return FileUtils.fileRead(filename);
    }
}
