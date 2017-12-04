package rizki.practicum.learning.service.storage;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;

@Service
@Qualifier("StorageService")
public class StorageServiceImpl implements StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    Path rootLocation = Paths.get(FilesLocationConfig.UserPhoto.LOCATION);;

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException(StorageServiceMessage.COULDNT_INITIALIZE_STORAGE);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename;
        try {
            filename = rizki.practicum.learning.util.hash.MD5.generate(file.getOriginalFilename())+"."+
                    FilenameUtils.getExtension(file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException(StorageServiceMessage.SAVE_RESOURCE_FAIL);
        }
        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new RuntimeException(StorageServiceMessage.LOAD_RESOURCE_FAIL);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(StorageServiceMessage.LOAD_RESOURCE_FAIL);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
