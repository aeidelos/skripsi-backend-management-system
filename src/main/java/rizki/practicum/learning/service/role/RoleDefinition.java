package rizki.practicum.learning.service.role;

public interface RoleDefinition {
    interface HeadLaboratory {
        public final String initial = "kalab";
        public final String description = "Kepala Laboratorium";
    }
    interface CoordinatorAssistance {
        public final String initial = "koas";
        public final String description = "Koordinator Asisten";
    }
    interface Assistance {
        public final String initial = "asprak";
        public final String description = "Asisten Praktikum";
    }
    interface Practican {
        public final String initial = "mhs";
        public final String description = "Mahasiswa";
    }
}
