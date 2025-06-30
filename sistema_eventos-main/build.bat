@echo off
set JAVA_FX_LIB="C:\Program Files\Java\javafx-sdk-21.0.7\lib"

echo === Limpando diretório de build antigo...
rmdir /s /q out
mkdir out

echo === Compilando arquivos Java...
javac --module-path %JAVA_FX_LIB% --add-modules javafx.controls,javafx.fxml -d out *.java

echo === Copiando arquivos FXML e resources...
xcopy *.fxml out /Y >nul
xcopy *.png out /Y >nul
xcopy *.jpg out /Y >nul

echo === Criando arquivo .jar...
jar --create --file=app.jar --main-class=Main -C out .

echo === .JAR criado com sucesso!
pause
