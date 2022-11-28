package com.rebalcomb.control;

import java.math.BigInteger;
import java.security.SecureRandom;


public class RSAUtil {
    //todo Встановлення довжини ключа
    private final static BigInteger bitCount = new BigInteger("512");

    //todo Оголошення змінних необхідних для алгоритму RSA
    private BigInteger p;
    private BigInteger q;
    private BigInteger module;
    private BigInteger eulerNumber;
    private BigInteger publicKey;
    private BigInteger privateKey;

    //todo Конструктор (ініціалізація змінних)
    public RSAUtil(){

        //todo p - псевдовипадкове просто число
        p = getRandomPrimaryNumber();

        //todo q - псевдовипадкове просто число
        q = getRandomPrimaryNumber();

        //todo module = p * q
        module = getCulcModule();

        //todo число Ейлера = (p - 1) * (q - 1)
        eulerNumber = getEulerNumber();

        //todo ініціалізаці публічного ключа
        publicKey = genPublicKey();

        //todo ініціалізаці приватного ключа
        privateKey = genPrivateKey();
    }

    //todo публічний метод доступа для модуля
    public BigInteger getModule() {
        return module;
    }

    //todo публічний метод доступа для публічного ключа
    public BigInteger getPublicKey() {
        return publicKey;
    }

    //todo публічний метод доступа для приватного ключа
    public BigInteger getPrivateKey() {
        return privateKey;
    }

    //todo Генерація псевдовипадкового простого числа
    private BigInteger getRandomPrimaryNumber() {
        return BigInteger.probablePrime(bitCount.intValue(), new SecureRandom());
    }

    //todo обчислення модуля
    private BigInteger getCulcModule() {
        return p.multiply(q);
    }

    //todo обчислення числа Ейлера
    private BigInteger getEulerNumber() {
        BigInteger bigInteger = BigInteger.ONE;
        bigInteger = bigInteger.multiply(p.subtract(BigInteger.ONE));
        bigInteger = bigInteger.multiply(q.subtract(BigInteger.ONE));
        return bigInteger;
    }

    //todo обчислення публічного ключа
    public BigInteger genPublicKey() {
        BigInteger exponent;
        while (true) {
            exponent = BigInteger.probablePrime(bitCount.intValue(),  new SecureRandom());
            if (exponent.gcd(eulerNumber).equals(BigInteger.ONE))
                break;
        }
        return exponent;
    }

    //todo обчислення приватного ключа
    public BigInteger genPrivateKey() {
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
