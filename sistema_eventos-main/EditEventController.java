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
import java.time.LocalDate;

public class EditEventController {

    @FXML private TextField nameField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextField localField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<String> categoryField;
    @FXML private ImageView bannerPreview;
    @FXML private Label bannerPathLabel;
    @FXML private Button selectBannerButton;

    private File selectedBannerFile;
    private Event1 eventToEdit;

    @FXML
    public void initialize() {
        Tooltip tooltip = new Tooltip("Use um banner com 1080x520 px");
        selectBannerButton.setTooltip(tooltip);

        if (categoryField != null && categoryField.getItems().isEmpty()) {
            categoryField.getItems().addAll("Cultura", "Esporte", "Tecnologia", "Educação", "Outro");
        }
    }

    public void setEventToEdit(Event1 event) {
        this.eventToEdit = event;

        nameField.setText(event.getName());
        datePicker.setValue(LocalDate.parse(event.getDate()));
        timeField.setText(event.getTime());
        localField.setText(event.getLocal());
        descriptionArea.setText(event.getDescription());
        categoryField.setValue(event.getCategory());

        if (event.getBannerPath() != null && !event.getBannerPath().isEmpty()) {
            try {
                selectedBannerFile = new File(event.getBannerPath());
                Image image = new Image("file:" + event.getBannerPath());
                bannerPreview.setImage(image);
                bannerPathLabel.setText(event.getBannerPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    private void handleSave() {
        String name = nameField.getText();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";
        String time = timeField.getText();
        String local = localField.getText();
        String description = descriptionArea.getText();
        String category = categoryField.getValue();
        String bannerPath = (selectedBannerFile != null) ? selectedBannerFile.getAbsolutePath() : null;

        if (name.isBlank() || date.isBlank() || time.isBlank() || category == null || category.isBlank()) {
            showAlert("Erro", "Preencha os campos obrigatórios: Nome, Data, Horário e Categoria.");
            return;
        }

        try (Connection conn = Database.connect()) {
            String sql = "UPDATE events SET name = ?, date = ?, time = ?, local = ?, description = ?, category = ?, banner_path = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, local);
            stmt.setString(5, description);
            stmt.setString(6, category);
            stmt.setString(7, bannerPath);
            stmt.setInt(8, eventToEdit.getId());

            stmt.executeUpdate();

            showAlert("Sucesso", "Evento atualizado com sucesso!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetailsAdmin.fxml"));
            Parent root = loader.load();

            EventDetailsAdminController controller = loader.getController();
            controller.setEvent(eventToEdit);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao atualizar o evento.");
        }
    }

    @FXML
    private void handleCancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetailsAdmin.fxml"));
            Parent root = loader.load();

            EventDetailsAdminController controller = loader.getController();
            controller.setEvent(eventToEdit);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao cancelar e voltar.");
        }
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
