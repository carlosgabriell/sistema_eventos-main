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

- Java JDK 17 ou superior
- IDE de sua preferência (IntelliJ, Eclipse ou VSCode)
- Banco de dados SQLite ou MySQL
- JavaFX configurado no projeto

### Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/sistema_eventos.git
cd sistema_eventos

# Abra o projeto na sua IDE e execute a classe Main.java
