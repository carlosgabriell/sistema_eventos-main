import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateEventController {

    @FXML private TextField nameField;
    @FXML private TextField localField; // <- NOVO CAMPO ADICIONADO
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextArea descriptionArea;

    @FXML
    private void handleSaveEvent() {
        String name = nameField.getText();
        String local = localField.getText(); // <- CAPTURA O LOCAL
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";
        String time = timeField.getText();
        String description = descriptionArea.getText();

        if (name.isBlank() || date.isBlank() || time.isBlank()) {
            showAlert("Erro", "Preencha os campos obrigatórios: Nome, Data e Horário.");
            return;
        }

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO events (name, local, date, time, description) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, local);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, description);
            stmt.executeUpdate();

            showAlert("Sucesso", "Evento criado com sucesso!");

            // Voltar para o Dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro", "Não foi possível salvar o evento.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao carregar o Dashboard.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
