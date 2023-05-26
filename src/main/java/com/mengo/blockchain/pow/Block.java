package com.mengo.blockchain.pow;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Getter
public class Block {
    private final String difficultyTarget = "00000";

    private final long timeStamp;
    private int nonce;

    private final String previousHash;
    private String hash;
    private final String data;

    public Block(final String previousHash, final String data) {
        this.timeStamp = new Date().getTime();

        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
    }

    public boolean validateBlock() {
        return this.hash.substring(0, difficultyTarget.length())
                .equals(difficultyTarget);
    }

    // Proof-of-Work (PoW)
    public void mineBlock() {
        mineBlockEasy();
    }

    public void mineBlockEasy() {
        while (!validateBlock()) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! Nonce: " + nonce + ", Hash: " + hash);
    }

    public void mineBlockWithTime() {
        final long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < 5000) { // Mine for a maximum of 5 seconds
            nonce++;
            hash = calculateHash();
            elapsedTime = System.currentTimeMillis() - startTime;

            if (validateBlock()) {
                System.out.println("Block mined! Nonce: " + nonce + ", Hash: " + hash);
                return;
            }
        }

        System.out.println("Mining failed. Block not mined within time limit.");
    }


    // Define cryptographic hash algorithms (like SHA-256 in this case)
    public String calculateHash() {
        return applySha256(previousHash + timeStamp + nonce + data);
    }

    private String applySha256(final String input) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Digest sha256 to Input
            final byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            final StringBuilder hexString = new StringBuilder();
            for (final byte hashByte : hash) {
                final String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}