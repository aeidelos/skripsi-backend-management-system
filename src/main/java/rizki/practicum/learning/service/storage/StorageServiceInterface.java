package rizki.practicum.learning.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageServiceInterface {

        void init();

        void store(MultipartFile file);

        Stream<Path> loadAll();

        Path load(String filename);

        org.springframework.core.io.Resource loadAsResource(String filename);

        void deleteAll();
}
