package rizki.practicum.learning.service.storage;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.FilesLocationConfig;
import rizki.practicum.learning.util.hash.MD5;

@Service
@Qualifier("StorageService")
public class StorageServiceImpl implements StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Getter Path rootLocation = Paths.get(FilesLocationConfig.Image.LOCATION);;

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException(StorageServiceMessage.COULDNT_INITIALIZE_STORAGE);
        }
    }

    @Override
    public ArrayList<String> store(MultipartFile[] file, String filename) throws FileFormatException {
        String filename_folder = MD5.generate(filename);
        String folder_location = this.rootLocation+"/"+filename_folder;
        ArrayList<String> filenames = new ArrayList<>();
        for (MultipartFile filex : file) {
            String file_location = folder_location+"/"+filex.getOriginalFilename()+"."+FilenameUtils.getExtension(filex.getOriginalFilename());
            try {
                File fileCheck = new File(folder_location);
                if(!fileCheck.isDirectory()){
                    fileCheck.mkdir();
                }
                File fileSave = new File(file_location);
                FileUtils.writeByteArrayToFile(fileSave, filex.getBytes());
                filenames.add(file_location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filenames;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    } // to be implemented

    @Override
    public String load(String filename) throws IOException, InvalidFormatException {
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

    @Override
    public void delete(String filename) {
        try {
            String[] splitter = filename.split("/");
            String folder = splitter[0] +"/" + splitter[1] +"/" + splitter[2];
            File file = new File(folder);
            if (file.isDirectory()) {
              FileSystemUtils.deleteRecursively(file);
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus file");
        }
    }
}
