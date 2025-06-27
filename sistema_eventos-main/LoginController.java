import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        System.out.println("Tentando login com email: " + email);

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT role FROM users WHERE email = ? AND password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Usuário logado com papel: '" + role + "'");

                // Caso role tenha espaços ou case diferente, melhor limpar:
                role = role != null ? role.trim() : "";

                String fxml;
                if ("ADMIN".equalsIgnoreCase(role)) {
                    fxml = "/Dashboard.fxml"; // Confirme se esse arquivo está no pacote resources correto
                } else {
                    fxml = "/Participant.fxml";
                }

                mudarTela(fxml);

            } else {
                mostrarAlertaErro("Login inválido", "Email ou senha incorretos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro no Login", "Não foi possível conectar ao banco de dados.");
        }
    }

    private void mudarTela(String caminhoFXML) {
        try {
            System.out.println("Mudando para a tela: " + caminhoFXML);
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Não foi possível carregar a tela: " + caminhoFXML);
        }
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void goToRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
