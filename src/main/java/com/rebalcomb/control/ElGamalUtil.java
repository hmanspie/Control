package com.rebalcomb.control;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class ElGamalUtil {

    public void getCypher()
    {
            Scanner scan = new Scanner(System.in);
            Integer p, g, M, k, secretKey, publicKey;

            System.out.println("Enter p:");
            p = scan.nextInt();
            System.out.println("Enter g:");
            g = scan.nextInt();
            System.out.println("Enter M(essage):");
            M = scan.nextInt();
            System.out.println("Enter SecretKey:");
            secretKey = scan.nextInt();
            System.out.println("Enter k:");
            k = scan.nextInt();

            publicKey = (int) Math.pow(g,secretKey) % p;

            System.out.println("p = " + p);
            System.out.println("g = " + g);
            System.out.println("M = " + M);
            System.out.println("k = " + k);
            System.out.println("SecretKey = " + secretKey);
            System.out.println("PublicKey = g^SecKey = " + publicKey);

            // CALCULATION

            Integer r = (int) Math.pow(g,k) % p;

            BigInteger pKeyBI = new BigInteger(publicKey.toString());
            BigInteger mBI = new BigInteger(M.toString());
            BigInteger pBI = new BigInteger(p.toString());

            BigInteger s = (mBI.multiply(pKeyBI.pow(k))).mod(pBI);
            System.out.println("s = " + s);

            System.out.println("Закодоване  (r,s) = (" + r + ", " + s + ")");


            ecnrypt(r,s, p, secretKey);

        }

        private  void ecnrypt(Integer r, BigInteger s, Integer p,Integer secretKey) {
            BigInteger rBI = new BigInteger(r.toString());
            BigInteger decoded = s.multiply((rBI.pow(p-1-secretKey))).mod(BigInteger.valueOf(p));

            System.out.println("decoded = " + decoded.toString());
        }

    }

