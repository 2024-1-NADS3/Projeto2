package com.example.telas_iniciais;

public class Cripto {
    private int shift;

    /**
     *Construtor que define o valor do shift
     */
    public Cripto(int shift) {
        this.shift = shift;
    }

    /**
     * Método para criptografar o texto
     */
    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base + shift) % 26 + base);
            }
            cipherText.append(c);
        }
        return cipherText.toString();
    }

    /**
     *Método para descriptografar o texto
     */
    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base - shift + 26) % 26 + base);
            }
            plainText.append(c);
        }
        return plainText.toString();
    }
}
