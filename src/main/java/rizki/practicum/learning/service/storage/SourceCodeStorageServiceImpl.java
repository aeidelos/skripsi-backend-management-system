package rizki.practicum.learning.service.storage;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

import java.nio.file.Paths;

@Service
@Qualifier("SourceCodeStorageService")
public class SourceCodeStorageServiceImpl extends StorageServiceImpl implements StorageService {
    SourceCodeStorageServiceImpl(){
        this.rootLocation = Paths.get(FilesLocationConfig.SourceCode.LOCATION);
    }

    @Override
    public String store(MultipartFile file) {
        if(ArrayUtils.contains(FilesLocationConfig.SourceCode.FILE_EXTENSION_ALLOWED,file.getContentType())){
            return super.store(file);
        }else{
            return null;
        }
    }
}
