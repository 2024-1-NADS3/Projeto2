function criptoCesar(texto, chave) {
  // Verifica se o texto não está vazio ou indefinido
  if (!texto || texto.length === 0) {
    return "Texto vazio ou ausente";
  }

  const alfabeto =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  let resultado = "";

  for (let i = 0; i < texto.length; i++) {
    let caractereAtual = texto[i];
    let indiceAtual = alfabeto.indexOf(caractereAtual);

    // Verifica se o caractere está no alfabeto
    if (indiceAtual !== -1) {
      // Aplica a criptografia apenas a caracteres do alfabeto
      let indiceCifrado = (indiceAtual + chave) % alfabeto.length;
      if (indiceCifrado < 0) {
        indiceCifrado += alfabeto.length; // Ajusta para deslocamentos negativos
      }
      resultado += alfabeto[indiceCifrado];
    } else {
      // Se não estiver no alfabeto, adiciona o caractere ao resultado sem criptografá-lo
      resultado += caractereAtual;
    }
  }

  return resultado;
}

module.exports = criptoCesar;
