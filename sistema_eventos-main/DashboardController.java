import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class DashboardController {

    @FXML private TableView<Event1> eventTable;
    @FXML private TableColumn<Event1, String> nameColumn;
    @FXML private TableColumn<Event1, String> dateColumn;
    @FXML private TableColumn<Event1, String> timeColumn;

    private ObservableList<Event1> eventList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar as colunas para usar as propriedades de Event1
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));

        loadEventsFromDatabase();
    }

    private void loadEventsFromDatabase() {
        eventList.clear();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, date, time, description, local FROM events")) {

            while (rs.next()) {
                Event1 event = new Event1(
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("description"),
                        rs.getString("local")
                );
                eventList.add(event);
            }

            eventTable.setItems(eventList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Não foi possível carregar os eventos.");
        }
    }

    @FXML
    private void addEvent() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CreateEvent.fxml"));
            Stage stage = (Stage) eventTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Não foi possível abrir a tela de criação de evento.");
        }
    }

    @FXML
    private void showEventDescription() {
        Event1 selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert(AlertType.WARNING, "Aviso", "Selecione um evento para ver a descrição.");
            return;
        }

        String description = selectedEvent.getDescription();
        String local = selectedEvent.getLocal();

        String message = "Descrição: " + (description != null ? description : "Sem descrição") +
                         "\nLocal: " + (local != null ? local : "Não informado");

        showAlert(AlertType.INFORMATION, selectedEvent.getName(), message);
    }

    @FXML
    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) eventTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Não foi possível fazer logout.");
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
