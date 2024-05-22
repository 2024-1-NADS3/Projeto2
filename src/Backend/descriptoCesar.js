function descriptoCesar(texto, chave) {
  const alfabeto =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  let resultado = "";

  for (let i = 0; i < texto.length; i++) {
    let caractereAtual = texto[i];
    let indiceAtual = alfabeto.indexOf(caractereAtual);

    // Verifica se o caractere está no alfabeto
    if (indiceAtual !== -1) {
      // Aplica a descriptografia apenas a caracteres do alfabeto
      let indiceDescriptografado = (indiceAtual - chave) % alfabeto.length;
      if (indiceDescriptografado < 0) {
        indiceDescriptografado += alfabeto.length;
      }
      resultado += alfabeto[indiceDescriptografado];
    } else {
      // Se não estiver no alfabeto, adiciona o caractere ao resultado sem descriptografá-lo
      resultado += caractereAtual;
    }
  }

  return resultado;
}

module.exports = descriptoCesar;
