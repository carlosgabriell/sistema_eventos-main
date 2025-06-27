

public class User {
    private String username;
    private String password;
    private String role; // "ADMIN" ou "PARTICIPANTE"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        // Converter para maiúsculas para compatibilidade com o ENUM do MySQL
        this.role = role.toUpperCase();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
