package utils;

public class TestUtils {

    public static String createPetJson(long id, String name, String status) {
        if (id < 0) {
            throw new IllegalArgumentException("ID değeri negatif olamaz.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name boş olamaz.");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status boş olamaz.");
        }

        return String.format("{\"id\":%d,\"name\":\"%s\",\"status\":\"%s\"}", id, name, status);
    }

    public static String createInvalidPetJsonWithoutName(long id, String status) {
            return String.format("{\"id\":%d, \"status\": \"%s\"}", id, status);
        }

    public static String createInvalidPetJsonWithoutStatus(long id, String name) {
            return String.format("{\"id\":%d, \"name\": \"%s\"}", id, name);
        }

    public static String createInvalidPetJsonWithInvalidIdType(String id, String name, String status) {
            return String.format("{\"id\":\"%s\", \"name\": \"%s\", \"status\": \"%s\"}", id, name, status);
        }

    public static String createInvalidPetJsonWithInvalidFormat(long id, String name, String status) {
            return String.format("{\"id\":%d \"name\": \"%s\", \"status\": \"%s\"}", id, name, status); // Virgül eksik
        }

}
