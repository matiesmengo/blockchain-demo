package com.mengo.blockchain.pow;

import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class Chain {

    private final Stack<Block> blockchain = new Stack<>();

    public void createBlock(final String data) {
        final Block block = new Block(getPreviousHash(), data);
        block.mineBlock();

        if (block.validateBlock() &&
                block.getPreviousHash().equals(getPreviousHash())) {
            blockchain.add(block);
        } else {
            System.out.println("Not valid block");
        }
    }


    public void printChain() {
        System.out.println("The block chain: ");
        blockchain.forEach(block ->
                System.out.println("Valid: " + block.validateBlock() +
                        "  --  Hash: " + block.getHash()));
    }

    private String getPreviousHash() {
        return blockchain.isEmpty()
                ? "0"
                : blockchain.get(blockchain.size() - 1).getPreviousHash();
    }
}