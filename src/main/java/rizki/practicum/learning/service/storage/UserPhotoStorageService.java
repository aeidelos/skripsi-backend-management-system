package rizki.practicum.learning.service.storage;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocation;

import java.nio.file.Paths;

@Service
@Qualifier("UserPhotoStorageService")
public class UserPhotoStorageService extends StorageService implements StorageServiceInterface{
    UserPhotoStorageService(){
        this.rootLocation = Paths.get(FilesLocation.UserPhoto.LOCATION);
    }

    @Override
    public String store(MultipartFile file) {
        if(ArrayUtils.contains(FilesLocation.UserPhoto.FILE_EXTENSION_ALLOWED,file.getContentType())){
            return super.store(file);
        }else{
            return null;
        }
    }
}
