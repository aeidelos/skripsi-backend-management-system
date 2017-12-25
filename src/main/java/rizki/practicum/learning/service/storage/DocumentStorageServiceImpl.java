package rizki.practicum.learning.service.storage;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

import java.nio.file.Paths;

@Service
@Qualifier("DocumentStorageService")
public class DocumentStorageServiceImpl extends StorageServiceImpl implements StorageService {
    DocumentStorageServiceImpl(){
        this.rootLocation = Paths.get(FilesLocationConfig.Document.LOCATION);
    }

    @Override
    public String store(MultipartFile file) {
        if(ArrayUtils.contains(FilesLocationConfig.Document.FILE_EXTENSION_ALLOWED,file.getContentType())){
            return super.store(file);
        }else{
            return null;
        }
    }
}
