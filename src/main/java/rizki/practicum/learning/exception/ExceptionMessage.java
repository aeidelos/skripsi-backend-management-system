package rizki.practicum.learning.exception;

public interface ExceptionMessage {
    interface User {
        public String USER_CREATED = "Pengguna berhasil ditambahkan";
        public String USER_UPDATED = "Pengguna berhasil diubah";
    }
    interface Auth {
        public String LOGIN_SUCCESS = "Login Success";
        public String LOGIN_FAIL = "Login Gagal";
    }
}
