/** Variável express que chama a biblioteca express */
const express = require("express");
/** Variável bodyParser que chama a biblioteca body parser */
const bodyParser = require("body-parser");
/** Variável sqlite3 que chama a biblioteca sqlite3 do tipo verbose */
const sqlite3 = require("sqlite3").verbose();

/** Cria uma instância express */
const app = express();

/** Caminho para o arquivo do banco de dados SQLite */
const DB_PATH = "bancoskl.db";

/** Middleware para analisar o corpo das solicitações POST */
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

/** Inicializa o banco de dados */
let db = new sqlite3.Database(DB_PATH, (err) => {
  if (err) {
    return console.error(err.message);
  }
  console.log("Conectado ao banco de dados SQlite.");

  db.run(
    "CREATE TABLE IF NOT EXISTS usuarios (nome TEXT, email TEXT, senha TEXT)",
    (err) => {
      if (err) {
        return console.error(err.message);
      }
      console.log("Tabela de usuários criada.");
    },
  );
});

db.run(
  `INSERT INTO usuarios(email, senha) VALUES(?, ?)`,
  ["skl@gmail.com", "123456"],
  (err) => {
    if (err) {
      return console.error(err.message);
    }
    console.log("Email fornecido com sucesso!");
  },
);

app.post("/cadastro", (req, res) => {
  const { nome, email, senha } = req.body;

  /** Adiciona um novo usuário ao banco de dados */
  db.run(
    `INSERT INTO usuarios(nome, email, senha) VALUES(?, ?, ?)`,
    [nome, email, senha],
    (err) => {
      if (err) {
        return console.error(err.message);
      }

      res.json({
        status: "sucesso",
        message: "Usuário cadastrado com sucesso!",
      });
    },
  );
});

app.post("/login", (req, res) => {
  const { login: email, password: senha } = req.body;

  /** Verifica o email e a senha com o banco de dados */
  db.get(
    `SELECT * FROM usuarios WHERE email = ? AND senha = ?`,
    [email, senha],
    (err, row) => {
      if (err) {
        throw err;
      }

      if (row) {
        res.json({ status: "sucesso", message: "Login bem-sucedido!" });
      } else {
        res.json({ status: "erro", message: "Email ou senha incorretos." });
      }
    },
  );
});

app.post("/esqueceu-senha", (req, res) => {
  const { email, novaSenha } = req.body;

  console.log(`E-mail fornecido: ${email}`);

  /** Atualiza a senha do usuário no banco de dados*/
  db.run(
    `UPDATE usuarios SET senha = ? WHERE email = ?`,
    [novaSenha, email],
    (err) => {
      if (err) {
        throw err;
      }

      res.json({ status: "sucesso", message: "Senha redefinida com sucesso!" });
    },
  );
});

app.get("/", function (req, res) {
  res.send("SKL");
});

/** Inicializa o servidor e gera uma mensagem se o servidor estiver rodando */
app.listen(() => console.log("Servidor rodando"));
