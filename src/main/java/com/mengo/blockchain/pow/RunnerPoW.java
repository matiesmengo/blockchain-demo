package com.mengo.blockchain.pow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Proof-of-Work (PoW)
@Component
public class RunnerPoW implements CommandLineRunner {
    @Autowired
    private Chain chain;

    @Override
    public void run(final String... args) {
        chain.createBlock("message1");
        chain.createBlock("message2");
        chain.createBlock("message3");

        chain.printChain();
    }
}