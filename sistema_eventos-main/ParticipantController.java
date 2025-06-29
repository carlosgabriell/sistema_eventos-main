import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.sql.*;

public class ParticipantController {

    @FXML
    private VBox eventContainer;

    @FXML
    private Label userNameLabel;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    public void initialize() {
        String nomeUsuario = Session.getInstance().getNome();
        userNameLabel.setText(nomeUsuario != null ? nomeUsuario : "Usuário");

        filterComboBox.getItems().addAll("Todos", "Meus Interesses");
        filterComboBox.getSelectionModel().selectFirst();

        loadEvents("Todos");
    }

    @FXML
    private void onFilterChanged() {
        String filtroSelecionado = filterComboBox.getSelectionModel().getSelectedItem();
        loadEvents(filtroSelecionado);
    }

    private void loadEvents(String filtro) {
        eventContainer.getChildren().clear();

        try (Connection conn = Database.connect()) {
            PreparedStatement stmt;

            if ("Meus Interesses".equals(filtro)) {
                String sql = "SELECT e.id, e.name, e.date, e.time, e.description, e.local, e.banner_path, e.category " +
                             "FROM events e " +
                             "JOIN interesses i ON e.id = i.event_id " +
                             "WHERE i.user_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Session.getInstance().getUserId());
            } else {
                String sql = "SELECT id, name, date, time, description, local, banner_path, category FROM events";
                stmt = conn.prepareStatement(sql);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String description = rs.getString("description");
                String local = rs.getString("local");
                String bannerPath = rs.getString("banner_path");
                String category = rs.getString("category");

                Event1 event = new Event1(id, name, date, time, description, local, bannerPath, category);

                HBox card = createEventCard(event);
                eventContainer.getChildren().add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private HBox createEventCard(Event1 event) {
        HBox box = new HBox(10);
        box.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        box.setPrefHeight(80);

        Label name = new Label(event.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label dateTime = new Label(event.getDate() + " - " + event.getTime());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button detailsButton = new Button("Ver Detalhes");
        detailsButton.setOnAction(e -> openEventDetails(event));

        VBox textBox = new VBox(name, dateTime);

        box.getChildren().addAll(textBox, spacer, detailsButton);

        return box;
    }

    private void openEventDetails(Event1 event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventDetails.fxml"));
            Parent root = loader.load();

            EventDetailsController controller = loader.getController();
            controller.setEvent(event);

            Stage stage = (Stage) eventContainer.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        Session.getInstance().clear();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) eventContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
