import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.sql.*;

public class EventDetailsController {

    @FXML private Label eventNameLabel;
    @FXML private ImageView eventBanner;
    @FXML private Label eventDateTimeLabel;
    @FXML private Label eventLocalLabel;
    @FXML private Label eventCategoryLabel;
    @FXML private TextArea eventDescriptionArea;
    @FXML private Button interestButton;

    private Event1 event;

    public void setEvent(Event1 event) {
        this.event = event;
        loadEventDetails();
    }

    private void loadEventDetails() {
        eventNameLabel.setText(event.getName());

        if (event.getBannerPath() != null && !event.getBannerPath().isEmpty()) {
            try {
                Image image = new Image("file:" + event.getBannerPath());
                eventBanner.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        eventDateTimeLabel.setText(event.getDate() + " - " + event.getTime());
        eventLocalLabel.setText("Local: " + event.getLocal());
        eventCategoryLabel.setText("Categoria: " + event.getCategory());
        eventDescriptionArea.setText(event.getDescription());
    }

    @FXML
    private void handleInterest() {
        try (Connection conn = Database.connect()) {
            String sql = "INSERT INTO interesses (user_id, event_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Session.getInstance().getUserId());
            stmt.setInt(2, event.getId());
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Interesse Registrado");
            alert.setHeaderText(null);
            alert.setContentText("Você demonstrou interesse neste evento!");
            alert.showAndWait();

            interestButton.setDisable(true);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao registrar interesse.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Participant.fxml"));
            Stage stage = (Stage) eventNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
