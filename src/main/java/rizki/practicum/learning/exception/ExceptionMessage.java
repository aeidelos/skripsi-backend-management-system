package rizki.practicum.learning.exception;

public interface ExceptionMessage {

    String CREATED = " berhasil ditambahkan";
    String CREATED_FAIL = " gagal ditambahkan";
    String UPDATED = " berhasil diubah";
    String UPDATED_FAIL = " gagal diubah";
    String DELETED = " berhasil dihapus";
    String DELETED_FAIL = " gagal dihapus";

    interface User {
        String DATA_NAME = "Pengguna";
        String USER_CREATED = DATA_NAME+CREATED;
        String USER_CREATED_FAIL = DATA_NAME+CREATED_FAIL;
        String USER_UPDATED = DATA_NAME+UPDATED;
        String USER_UPDATED_FAIL = DATA_NAME+UPDATED_FAIL;
    }
    interface Auth {
        String LOGIN_SUCCESS = "Login Success";
        String LOGIN_FAIL = "Login Gagal";
    }
    interface Practicum{
        String DATA_NAME = "Praktikum";
        String PRACTICUM_CREATED = "Praktikum berhasil ditambahkan";
        String PRACTICUM_CREATED_FAIL = "Praktikum  gagal ditambahkan";
        String PRACTICUM_UPDATED = "MPraktikum berhasil diubah";
        String PRACTICUM_UPDATED_FAIL = "Praktikum gagal diubah";
        String PRACTICUM_DELETED = "Praktikum berhasil dihapus";
        String PRACTICUM_DELETED_FAIL = "Praktikum gagal dihapus";
        interface Course {
            String COURSE_CREATED = "Mata kuliah berhasil ditambahkan";
            String COURSE_CREATED_FAIL = "Mata kuliah gagal ditambahkan";
            String COURSE_UPDATED = "Mata kuliah berhasil diubah";
            String COURSE_UPDATED_FAIL = "Mata kuliah gagal diubah";
            String COURSE_DELETED = "Mata kuliah berhasil dihapus";
            String COURSE_DELETED_FAIL = "Mata kuliah gagal dihapus";
        }
        interface Classroom {
            String DATA_NAME = "Kelas praktikum";
            String CLASSROOM_CREATED = DATA_NAME+CREATED;
            String CLASSROOM_CREATED_FAIL = DATA_NAME+CREATED_FAIL;
            String CLASSROOM_UPDATED = DATA_NAME+UPDATED;
            String CLASSROOM_UPDATED_FAIL = DATA_NAME+UPDATED_FAIL;
            String CLASSROOM_DELETED = DATA_NAME+DELETED;
            String CLASSROOM_DELETED_FAIL = DATA_NAME+DELETED_FAIL;
        }
    }
}
