package rizki.practicum.learning.service.storage;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

        void init();

        String store(MultipartFile file, String filename) throws FileFormatException;

        Stream<Path> loadAll();

        String load(String filename) throws IOException, InvalidFormatException;

        org.springframework.core.io.Resource loadAsResource(String filename);

        void deleteAll();
}
