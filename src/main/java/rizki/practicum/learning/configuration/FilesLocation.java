package rizki.practicum.learning.configuration;

public interface FilesLocation {
    interface UserPhoto {
        public final String LOCATION = "photo";
        public final String[] FILE_EXTENSION_ALLOWED = {"image/jpeg","image/jpg","image/png"};
    }
}
