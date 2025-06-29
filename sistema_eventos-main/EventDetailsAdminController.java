import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDetailsAdminController {

    @FXML private Label eventNameLabel;
    @FXML private ImageView eventBanner;
    @FXML private Label eventDateTimeLabel;
    @FXML private Label eventLocalLabel;
    @FXML private Label eventCategoryLabel;
    @FXML private TextArea eventDescriptionArea;

    private Event1 event;

    public void setEvent(Event1 event) {
        this.event = event;
        loadEventDetails();
    }

    private void loadEventDetails() {
        eventNameLabel.setText(event.getName());

        if (event.getBannerPath() != null && !event.getBannerPath().isEmpty()) {
            System.out.println("Caminho do banner: " + event.getBannerPath()); // DEBUG
            File bannerFile = new File(event.getBannerPath());
            if (bannerFile.exists()) {
                Image image = new Image(bannerFile.toURI().toString());
                eventBanner.setImage(image);
            } else {
                System.out.println("Banner não encontrado em: " + bannerFile.getAbsolutePath());
            }
        } else {
            System.out.println("Caminho do banner está vazio.");
        }

        eventDateTimeLabel.setText("Data e Hora: " + event.getDate() + " - " + event.getTime());
        eventLocalLabel.setText("Local: " + event.getLocal());
        eventCategoryLabel.setText("Categoria: " + event.getCategory());
        eventDescriptionArea.setText(event.getDescription());
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) eventNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditEvent.fxml"));
            Parent root = loader.load();

            EditEventController controller = loader.getController();
            controller.setEventToEdit(event);

            Stage stage = (Stage) eventNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEvent() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Exclusão");
        confirm.setHeaderText("Deseja realmente excluir este evento?");
        confirm.setContentText("Essa ação não poderá ser desfeita.");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try (Connection conn = Database.connect();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE id = ?")) {

                stmt.setInt(1, event.getId());
                stmt.executeUpdate();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Sucesso");
                success.setHeaderText(null);
                success.setContentText("Evento excluído com sucesso!");
                success.showAndWait();

                handleBack();

            } catch (SQLException e) {
                e.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erro");
                error.setHeaderText("Erro ao excluir o evento.");
                error.setContentText(e.getMessage());
                error.showAndWait();
            }
        }
    }

    @FXML
    private void handleShowInterestedUsers() {
        List<String> interestedUsers = new ArrayList<>();

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT u.username FROM interesses i JOIN users u ON i.user_id = u.id WHERE i.event_id = ?")) {

            stmt.setInt(1, event.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                interestedUsers.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(interestedUsers.isEmpty() ?
                List.of("Nenhum interessado encontrado.") : interestedUsers);

        VBox box = new VBox(10, new Label("Pessoas Interessadas:"), listView);
        box.setPrefSize(300, 400);
        box.setStyle("-fx-padding: 10;");

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Interessados no Evento");
        popup.setScene(new Scene(box));
        popup.showAndWait();
    }
}
