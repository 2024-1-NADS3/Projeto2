/** Variável express que chama a biblioteca express */
const express = require("express");

/** Variável bodyParser que chama a biblioteca body parser */
const bodyParser = require("body-parser");

/** Variável sqlite3 que chama a biblioteca sqlite3 */
const sqlite3 = require("sqlite3").verbose();

/** Importa o módulo "nodemailer" para o envio de emails (esqueceu senha) */
const nodemailer = require("nodemailer");

/** Importa o módulo "crypto" para lidar com criptografia */
const crypto = require("crypto");

/**  Define a constante PORT para usar a porta 3000 como padrão */
const PORT = process.env.PORT || 3000;

/** Cria uma instância express */
const app = express();

/*
 * Importa o módulo "dotenv" e chama a função config()
 * Isso carrega as variáveis de ambiente do arquivo ".env" (com os dados de email e senha)
 */
require("dotenv").config();

/*
 * Cria um objeto enviarEmailRemetente usando a função createTransport do módulo nodemailer
 * Esse objeto é usado para enviar emails ao usuário
 */
const enviarEmailRemetente = nodemailer.createTransport({
  service: "gmail",
  host: "smtp.gmail.com",
  port: 587,
  auth: {
    user: process.env.email_usuario,
    pass: process.env.senha_email_usuario,
  },
});

const mailOptions = {
  from: '"SKL projeto" <skl.projetopi@gmail.com',
  to: "skl.projetopi@gmail.com, skl.projetopi@gmail.com",
  subject: "Olá",
  text: "Olá",
  html: "<b>Olá, tudo bem?</br>",
};

/** Middleware para analisar o corpo das solicitações POST */
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

/** Cria uma conexão com o banco de dados SQLite */
let db = new sqlite3.Database("./bancoskl.db", (err) => {
  if (err) {
    console.error(err.message);
  }
  console.log("Conectado ao banco de dados SQLite.");
});

app.get("/tudo", function (req, res) {
  res.header("Access-Control-Allow-Origin", "*");
  db.all(`SELECT * FROM usuarios`, [], (err, rows) => {
    if (err) {
      res.send(err);
    }
    res.send(rows);
  });
});

/**
 * Rota cadastro
 */
app.post("/cadastro", (req, res) => {
  const { nome, email, senha } = req.body;

  // Verifica se o e-mail já existe
  let sql = `SELECT * FROM usuarios WHERE email = ?`;
  db.get(sql, [email], (err, row) => {
    if (err) {
      return console.error(err.message);
    }

    // Se o e-mail já existir, envia uma mensagem de erro
    if (row) {
      res.json({
        status: "erro",
        message: "Email já cadastrado!",
      });
    } else {
      // Se o e-mail não existir, adiciona o novo usuário
      sql = `INSERT INTO usuarios(nome, email, senha) VALUES(?, ?, ?)`;
      db.run(sql, [nome, email, senha], (err) => {
        if (err) {
          return console.error(err.message);
        }

        res.json({
          status: "sucesso",
          message: "Usuário cadastrado com sucesso!",
        });
      });
    }
  });
});

/**
 * Rota Login
 */
app.post("/login", (req, res) => {
  const { login: email, password: senha } = req.body;

  let sql = `SELECT * FROM usuarios WHERE email = ? AND senha = ?`;
  db.get(sql, [email, senha], (err, row) => {
    if (err) {
      throw err;
    }

    if (row) {
      res.json({
        status: "sucesso",
        id_usuario: row.id_user,
        email_usuario: email,
        nome_usuario: row.nome,
        message: "Login bem-sucedido!",
      });
    } else {
      res.json({ status: "erro", message: "Email ou senha incorretos." });
    }
  });
});

/**
 * Rota Esqueceu a senha
 */
app.post("/esqueceu-senha", (req, res) => {
  const { email } = req.body;
  const token = crypto.randomBytes(20).toString("hex");

  let sql = `SELECT nome FROM usuarios WHERE email = ?`;
  db.get(sql, [email], (err, row) => {
    if (err) {
      throw err;
    }

    if (row) {
      let nome = row.nome;

      sql = `UPDATE usuarios SET tokenRedefinicaoSenha = ?, expiracaoTokenRedefinicaoSenha = ? WHERE email = ?`;
      db.run(sql, [token, Date.now() + 3600000, email], function (err) {
        if (err) {
          throw err;
        }

        if (this.changes > 0) {
          let mailOptions = {
            to: email,
            subject: "Redefinição de Senha",
            text:
              "Olá, " +
              nome +
              ",\n\n" +
              "Recebemos uma solicitação de redefinição de senha do seu app Fecap Social.\n\n" +
              "Se você fez essa solicitação, digite o seguinte código no campo token da tela do app para alterar sua senha. E informe sua nova senha pelo app: " +
              token +
              "\n\n" +
              "Se você não solicitou isso, ignore este email e sua senha permanecerá a mesma.\n",
          };

          console.log("Enviando email para: " + email); // Log antes de enviar o email

          enviarEmailRemetente.sendMail(mailOptions, function (err) {
            if (err) {
              console.error("Erro ao enviar email: " + err.message); // Log em caso de erro
              return;
            }

            console.log("Email enviado com sucesso!"); // Log após o envio do email

            res.json({
              status: "sucesso",
              message: "Email de redefinição de senha enviado!",
            });
          });
        } else {
          res.json({ status: "erro", message: "Email não encontrado." });
        }
      });
    } else {
      res.json({ status: "erro", message: "Email não encontrado." });
    }
  });
});

/**
 * Rota Redefinir senha
 */
app.post("/redefinir-senha", (req, res) => {
  const { token, novaSenha } = req.body;

  let sql = `SELECT * FROM usuarios WHERE tokenRedefinicaoSenha = ? AND expiracaoTokenRedefinicaoSenha > ?`;
  db.get(sql, [token, Date.now()], (err, row) => {
    if (err) {
      throw err;
    }

    if (row) {
      let sqlUpdate = `UPDATE usuarios SET senha = ?, tokenRedefinicaoSenha = NULL, expiracaoTokenRedefinicaoSenha = NULL WHERE email = ?`;
      db.run(sqlUpdate, [novaSenha, row.email], (err) => {
        if (err) {
          throw err;
        }

        res.json({
          status: "sucesso",
          message: "Senha redefinida com sucesso!",
        });
      });
    } else {
      res.json({ status: "erro", message: "Token inválido ou expirado." });
    }
  });
});

/**
 * Atualizar o cadastro do usuário
 */
app.put("/atualizarUsuario", function (req, res) {
  var id = req.body.id;
  var nome = req.body.nome;
  var email = req.body.email;
  var senha = req.body.senha;
  sql = `UPDATE usuarios SET nome="${nome}", email="${email}", senha="${senha}" WHERE id=${id}`;

  db.all(sql, [], (err, rows) => {
    if (err) {
      res.send("Erro na atualização: " + err);
    } else {
      res.send("Usuário atualizado!");
    }
  });
});

/**
 * Rota deletar usuário
 */
app.delete("/deletarUsuario", function (req, res) {
  var id = req.body.id;
  sql = `DELETE FROM usuarios WHERE id_user=${id}`;
  db.all(sql, [], (err, rows) => {
    if (err) {
      res.send("Erro na exclusão: " + err);
    } else {
      res.send("Usuário excluído!");
    }
  });
});

/**
 * Rota para testar o servidor mostrando a mensagem SKL
 */
app.get("/", function (req, res) {
  res.send("SKL");
});

/**
 * Rota cadastro do evento
 */
app.post("/cadastroEvento", (req, res) => {
  const { evento, data, cidade, logradouro, numero } = req.body;

  // Verifica se o evento com a mesma data já existe
  let sql = `SELECT * FROM calendario WHERE evento = ? AND data = ?`;
  db.get(sql, [evento, data], (err, row) => {
    if (err) {
      return console.error(err.message);
    }

    // Se o evento com a mesma data já existir, envia uma mensagem de erro
    if (row) {
      res.json({
        status: "erro",
        message: "Cadastro de evento já existe!",
      });
    } else {
      // Se o evento com a mesma data não existir, adiciona o novo evento
      sql = `INSERT INTO calendario(evento, data, cidade, logradouro, numero) VALUES(?, ?, ?, ?, ?)`;
      db.run(sql, [evento, data, cidade, logradouro, numero], (err) => {
        if (err) {
          return console.error(err.message);
        }

        res.json({
          status: "sucesso",
          message: "Evento cadastrado com sucesso!",
        });
      });
    }
  });
});

/** Inicializa o servidor e gera uma mensagem se o servidor estiver rodando */
app.listen(PORT, function () {
  console.log(`Servidor rodando na porta ${PORT}`);
});
