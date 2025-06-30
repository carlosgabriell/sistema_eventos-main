# 📅 Sistema de Gerenciamento de Eventos - JavaFX

![Java](https://img.shields.io/badge/Java-17-blue?style=flat-square)
![JavaFX](https://img.shields.io/badge/JavaFX-17-green?style=flat-square)
![SQLite](https://img.shields.io/badge/SQLite-Database-orange?style=flat-square)
![Status](https://img.shields.io/badge/Status-%20Finalizado-green?style=flat-square)

---

## 📖 Sobre o Projeto

Este é um sistema desktop desenvolvido em **Java + JavaFX** para gerenciamento de eventos com funcionalidades completas tanto para **administradores** quanto para **participantes**.

### 💼 Administrador pode:
- Cadastrar eventos com banner, data, hora, local, descrição e categoria
- Editar ou excluir eventos
- Visualizar lista de interessados em cada evento

### 🧑‍💻 Participante pode:
- Visualizar todos os eventos disponíveis
- Filtrar por eventos que demonstrou interesse
- Ver detalhes completos (com banner, descrição, local, data e hora)
- Demonstrar interesse em eventos

---

## ⚙️ Funcionalidades

### 👨‍💼 Painel do Administrador
- 🔐 Login seguro
- ➕ Criação de eventos
- 📝 Edição e 🗑 exclusão de eventos
- 👀 Visualização completa dos detalhes do evento
- 📋 Lista de usuários interessados em cada evento

### 👥 Painel do Participante
- 📋 Lista de eventos disponíveis
- 🔍 Filtro: "Todos" ou "Meus Interesses"
- 🖼 Visualização completa com banner
- ✅ Botão "Tenho Interesse"

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **JavaFX 17**
- **FXML**
- **JDBC (SQLite ou MySQL)**
- **SceneBuilder** (para design das telas)

---

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 17 ou superior instalado
- JavaFX SDK (exemplo: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/))
- Driver MySQL Connector/J (exemplo: `mysql-connector-j-9.3.0.jar`)
- Banco de dados MySQL local configurado e em execução
- IDE de sua preferência (IntelliJ, Eclipse, VSCode) ou terminal para executar o JAR

---

### Passos

1. Clone o projeto e entre na pasta:  
`git clone https://github.com/seu-usuario/sistema_eventos.git`  
`cd sistema_eventos`

2. Compile o código, substituindo `CAMINHO_DO_JAVAFX_LIB` pelo caminho da pasta `lib` do JavaFX SDK no seu computador:  
`javac --module-path "CAMINHO_DO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml *.java`

3. Crie um arquivo chamado `manifest.txt` com o seguinte conteúdo (deixe uma linha em branco no final):  
`Main-Class: Main`

4. Gere o arquivo JAR executável:  
`jar cfm sistema_eventos-main.jar manifest.txt *.class *.fxml`

5. Execute a aplicação substituindo `CAMINHO_DO_JAVAFX_LIB` pelo caminho do JavaFX SDK e `CAMINHO_DO_DRIVER_MYSQL` pelo caminho do driver MySQL Connector/J no seu computador:  
`java --module-path "CAMINHO_DO_JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml -cp sistema_eventos-main.jar;"CAMINHO_DO_DRIVER_MYSQL" Main`

---


Se preferir, execute o arquivo `run.bat` presente na pasta do projeto para rodar a aplicação com um clique.

---

### Observações

- Certifique-se que o banco de dados MySQL está rodando e as credenciais no código estão configuradas corretamente.  
- O JavaFX SDK deve estar corretamente instalado e seu caminho configurado na execução.
