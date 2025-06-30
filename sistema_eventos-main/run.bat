@echo off
set JAVA_FX_LIB="C:\Program Files\Java\javafx-sdk-21.0.7\lib"

echo === Executando app.jar...
java --module-path %JAVA_FX_LIB% --add-modules javafx.controls,javafx.fxml -jar app.jar
pause
