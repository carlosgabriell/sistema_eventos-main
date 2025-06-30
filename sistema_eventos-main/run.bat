@echo off
cd /d "%~dp0"
java --module-path "C:/Program Files/Java/javafx-sdk-21.0.7/lib" --add-modules javafx.controls,javafx.fxml -cp sistema_eventos-main.jar;"C:\Users\Gabriel Oliveira\Downloads\mysql-connector-j-9.3.0\mysql-connector-j-9.3.0.jar" Main
pause
