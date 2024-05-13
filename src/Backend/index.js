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
 * Carrega a variável de ambiente (com os dados de email e senha)
 */
require("dotenv").config();

/**
 * Função que gera um token para depois redefinir a senha
 */
function gerarToken() {
  return crypto.randomBytes(5).toString("hex");
}

/**
 * Função que atualiza o banco de dados com um novo token de redefinição de senha
 */
function atualizarBancoDeDados(db, email, token) {
  let sql = `UPDATE usuarios SET tokenRedefinicaoSenha = ?, expiracaoTokenRedefinicaoSenha = ? WHERE email = ?`;
  db.run(sql, [token, Date.now() + 3600000, email], function (err) {
    if (err) {
      throw err;
    }
  });
}

/*
 * Função para enviar o email para o usuário
 */
function enviarEmail(email, nome, token) {
  let remetenteEnviandoEmail = nodemailer.createTransport({
    service: "gmail",
    host: "smtp.gmail.com",
    port: 587,
    auth: {
      user: process.env.email,
      pass: process.env.senha_email,
    },
  });

  let OpcoesEmail = {
    from: '"SKL - Fecap Social" <skl.projetopi@gmail.com',
    to: email,
    subject: "Token para redefinição de senha - App Fecap Social",
    text:
      "Olá, " +
      nome +
      "!\n\n" +
      "Recebemos uma solicitação de redefinição de senha do seu app Fecap Social.\n\n" +
      "Se você fez essa solicitação, digite o seguinte código no campo token da tela do app para alterar sua senha. E informe sua nova senha pelo app.\n\n " +
      "TOKEN: " +
      token +
      "\n\n" +
      "Se você não solicitou isso, ignore este email e sua senha permanecerá a mesma.\n",
  };

  remetenteEnviandoEmail.sendMail(OpcoesEmail, function (err) {
    if (err) {
      console.error("Erro ao enviar email: " + err.message);
      return;
    }

    console.log("Email enviado com sucesso!");
  });
}

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

/**
 * Rota GET que retorna todos os dados da tabela usuarios do Banco de dados
 */
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
 * Rota cadastro do usuário
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
 * Rota Login do usuário
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
  const token = gerarToken();

  let sql = `SELECT nome FROM usuarios WHERE email = ?`;
  db.get(sql, [email], (err, row) => {
    if (err) {
      throw err;
    }

    if (row) {
      let nome = row.nome;

      atualizarBancoDeDados(db, email, token);
      enviarEmail(email, nome, token);

      res.json({
        status: "sucesso",
        message: "Email de redefinição de senha enviado!",
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
 * Rota para atualizar o cadastro do usuário
 */
app.put("/atualizarUsuario", function (req, res) {
  var id = req.body.id_user;
  var nome = req.body.nome;
  var email = req.body.email;

  sql = `UPDATE usuarios SET nome = ?, email = ? WHERE id_user = ?`;

  db.run(sql, [nome, email, id], function (err) {
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
  var id = req.query.id_user;

  sql = `DELETE FROM usuarios WHERE id_user = ?`;

  db.run(sql, id, function (err) {
    if (err) {
      res.send("Erro na exclusão: " + err);
    } else {
      res.send("Usuário excluído com sucesso!");
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
