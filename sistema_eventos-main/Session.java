public class Session {
    private static Session instance = null;
    private int userId;
    private String email;
    private String nome;
    private String role;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getNome() { return nome; }
    public String getRole() { return role; }

    public void setUser(int userId, String email, String nome, String role) {
        this.userId = userId;
        this.email = email;
        this.nome = nome;
        this.role = role;
    }

    public void clear() {
        userId = 0;
        email = null;
        nome = null;
        role = null;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
}
