package rizki.practicum.learning.configuration;

public interface FilesLocationConfig {
    interface Image {
        final String LOCATION = "photo";
        final String[] FILE_EXTENSION_ALLOWED = {"image/jpeg","image/jpg","image/png"};
    }
    interface Document {
        final String LOCATION = "document";
        final String[] FILE_EXTENSION_ALLOWED = {"application/msword","application/pdf"};
    }
}
