package rizki.practicum.learning.service.storage;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

import java.nio.file.Paths;

@Service
@Qualifier("UserPhotoStorageService")
public class UserPhotoStorageServiceImpl extends StorageServiceImpl implements StorageService {
    UserPhotoStorageServiceImpl(){
        this.rootLocation = Paths.get(FilesLocationConfig.UserPhoto.LOCATION);
    }

    @Override
    public String store(MultipartFile file) {
        if(ArrayUtils.contains(FilesLocationConfig.UserPhoto.FILE_EXTENSION_ALLOWED,file.getContentType())){
            return super.store(file);
        }else{
            return null;
        }
    }
}
