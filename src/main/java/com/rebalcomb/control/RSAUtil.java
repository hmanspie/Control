package com.rebalcomb.control;

import java.math.BigInteger;
import java.security.SecureRandom;


public class RSAUtil {
    //todo Встановлення довжини ключа
    private final static BigInteger bitCount = new BigInteger("512");

    //todo Оголошення змінних необхідних для алгоритму RSA


    //todo Конструктор (ініціалізація змінних)
    public RSAUtil(){
    }

    //todo публічний метод доступа для модуля

    //todo Генерація псевдовипадкового простого числа
    public BigInteger getRandomPrimaryNumber() {
        return BigInteger.probablePrime(bitCount.intValue(), new SecureRandom());
    }

    //todo обчислення модуля
    public static BigInteger getCulcModule(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    //todo обчислення числа Ейлера
    public static BigInteger getEulerNumber(BigInteger p, BigInteger q) {
        BigInteger bigInteger = BigInteger.ONE;
        bigInteger = bigInteger.multiply(p.subtract(BigInteger.ONE));
        bigInteger = bigInteger.multiply(q.subtract(BigInteger.ONE));
        return bigInteger;
    }

    //todo обчислення публічного ключа
    public static BigInteger genPublicKey(BigInteger eulerNumber) {
        BigInteger exponent;
        while (true) {
            exponent = BigInteger.probablePrime(bitCount.intValue(),  new SecureRandom());
            if (exponent.gcd(eulerNumber).equals(BigInteger.ONE))
                break;
        }
        return exponent;
    }

    //todo обчислення приватного ключа
    public static BigInteger genPrivateKey(BigInteger publicKey, BigInteger eulerNumber) {
        return publicKey.modInverse(eulerNumber);
    }

    //todo метод для шифрування = Message^PublicKeyMod(module)

    public static String encrypt(String value, BigInteger publicKey, BigInteger module) {
        BigInteger M = new BigInteger(value);
        return M.modPow(publicKey, module).toString();
    }

    //todo метод для дешифрування = CryptoText^PrivateKeyMod(module)
    public static String decrypt(String value, BigInteger privateKey, BigInteger module) {
        BigInteger C = new BigInteger(value);
        return C.modPow(privateKey, module).toString();
    }

    public static String signature(String value, BigInteger privateKey, BigInteger module){
        BigInteger M = new BigInteger(value);
        return M.modPow(privateKey, module).toString();
    }

    public static String checkSignature(String signature, BigInteger publicKey, BigInteger module){
        BigInteger S = new BigInteger(signature);
        return S.modPow(publicKey, module).toString();
    }
}
