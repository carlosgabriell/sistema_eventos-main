import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateEventController {

    @FXML private TextField nameField;
    @FXML private TextField localField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextArea descriptionArea;
    @FXML private ImageView bannerPreview;
    @FXML private Button selectBannerButton;
    @FXML private Label bannerPathLabel;
    @FXML private ComboBox<String> categoryComboBox;  // NOVO CAMPO

    private File selectedBannerFile;

    @FXML
    public void initialize() {
        // Inicializa ComboBox com as categorias
        categoryComboBox.getItems().addAll("Cultura", "Esporte", "Tecnologia", "Educação", "Outro");

        Tooltip tooltip = new Tooltip("Use um banner com 1080px de largura por 520px de altura para melhor visualização.");
        selectBannerButton.setTooltip(tooltip);
    }

    @FXML
    private void handleSelectBanner() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Banner");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(nameField.getScene().getWindow());
        if (file != null) {
            selectedBannerFile = file;
            Image image = new Image(file.toURI().toString());
            bannerPreview.setImage(image);
            bannerPathLabel.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleSaveEvent() {
        String name = nameField.getText();
        String local = localField.getText();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";
        String time = timeField.getText();
        String description = descriptionArea.getText();
        String bannerPath = (selectedBannerFile != null) ? selectedBannerFile.getAbsolutePath() : null;
        String category = categoryComboBox.getValue();

        if (name.isBlank() || date.isBlank() || time.isBlank() || category == null || category.isBlank()) {
            showAlert("Erro", "Preencha os campos obrigatórios: Nome, Data, Horário e Categoria.");
            return;
        }

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO events (name, local, date, time, description, banner_path, category) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, local);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, description);
            stmt.setString(6, bannerPath);
            stmt.setString(7, category);
            stmt.executeUpdate();

            showAlert("Sucesso", "Evento criado com sucesso!");

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

    @FXML
    private void handleCancel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Não foi possível voltar para o Dashboard.");
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
