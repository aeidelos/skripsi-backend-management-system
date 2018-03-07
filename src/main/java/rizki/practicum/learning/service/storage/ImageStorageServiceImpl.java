package rizki.practicum.learning.service.storage;

import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Qualifier("ImageStorageService")
public class ImageStorageServiceImpl extends StorageServiceImpl implements StorageService {
    ImageStorageServiceImpl(){
        this.rootLocation = Paths.get(FilesLocationConfig.Image.LOCATION);
    }

    @Override
    public ArrayList<String> store(MultipartFile[] file, String filename) throws FileFormatException {
        String file_ext = FilenameUtils.getExtension(file[0].getOriginalFilename());
        if(Arrays.asList(FilesLocationConfig.Image.FILE_EXTENSION_ALLOWED).contains(file_ext)){
            return super.store(file, filename);
        }else{
            throw new FileFormatException();
        }
    }
}
