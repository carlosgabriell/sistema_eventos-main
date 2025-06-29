import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class DashboardController {

    @FXML private TableView<Event1> eventTable;
    @FXML private TableColumn<Event1, String> nameColumn;
    @FXML private TableColumn<Event1, String> dateColumn;
    @FXML private TableColumn<Event1, String> timeColumn;
    @FXML private Label userNameLabel;

    private ObservableList<Event1> eventList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        String nomeUsuario = Session.getInstance().getNome();
        userNameLabel.setText(nomeUsuario != null ? nomeUsuario : "Usuário");

        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate()));
        timeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTime()));

        loadEventsFromDatabase();
        eventTable.setItems(eventList);
    }

    private void loadEventsFromDatabase() {
        eventList.clear();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, local, date, time, description, category, banner_path FROM events")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String local = rs.getString("local");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String bannerPath = rs.getString("banner_path");

                Event1 event = new Event1(id, name, date, time, description, local, bannerPath, category);

                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showEventDescription() {
        Event1 selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um evento para visualizar.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetailsAdmin.fxml"));
            Parent root = loader.load();

            EventDetailsAdminController controller = loader.getController();
            controller.setEvent(selectedEvent);

            Stage stage = (Stage) eventTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEvent.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) eventTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        Session.getInstance().clear();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) eventTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
