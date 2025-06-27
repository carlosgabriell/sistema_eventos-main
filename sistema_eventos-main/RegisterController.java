import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox<String> roleChoiceBox;

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleChoiceBox.getValue();

        // Mapeando o valor do ChoiceBox para o ENUM do banco
        if (role.equals("Organizador")) {
            role = "ADMIN";
        } else if (role.equals("Participante")) {
            role = "PARTICIPANTE";
        }

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
            System.out.println("Usuário registrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Mantendo os nomes amigáveis no ChoiceBox
        roleChoiceBox.setItems(FXCollections.observableArrayList("Organizador", "Participante"));
    }
}
