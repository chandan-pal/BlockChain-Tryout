package com.blockchain.tryout.service;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockchain.tryout.model.Block;
import com.blockchain.tryout.model.BlockChain;
import com.blockchain.tryout.util.BlockChainUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class BlockChainService {

	private BlockChain blockChain;
	
	@Autowired
	public BlockChainService() {
		blockChain = new BlockChain();
		createBlock(1, "0");
	}
	
	public BlockChain getChain() {
		return blockChain;
	}

	/**
	 * Creates the block.
	 *
	 * @param nonce the nonce
	 * @param prevHash the prev hash
	 * @return the block
	 */
	public Block createBlock(int nonce, String prevHash) {
		Block block = Block.builder()
				.index(blockChain.size()+1)
				.timestamp(Instant.now().getEpochSecond())
				.proof(nonce)
				.previousHash(prevHash)
				.build();
		blockChain.add(block);
		return block;
	}
	
	/**
	 * Gets the previous block.
	 *
	 * @return the previous block
	 */
	public Block getPreviousBlock() {
		return blockChain.get(blockChain.size()-1);
	}
	
	/**
	 * Proof of work.
	 *
	 * @param prevProof the prev proof
	 * @return the int
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public int proofOfWork(int prevProof) throws NoSuchAlgorithmException {
		int newProof = 1;
		boolean checkProof = false;
		while(!checkProof) {
			String input = BigDecimal.valueOf(Math.pow(newProof, 2) - Math.pow(prevProof, 2)).toBigInteger().toString();
			String hashOperation = BlockChainUtil.getSHA256Hash(input);
			if (hashOperation.startsWith("0000")) {
				checkProof = true;
			} else {
				newProof++;
			}
		}
		return newProof;
	}
	
	
	/**
	 * Checks if is chain valid.
	 *
	 * @return true, if is chain valid
	 * @throws JsonProcessingException the json processing exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public boolean isChainValid() throws JsonProcessingException, NoSuchAlgorithmException {
		Block prevBlock = this.blockChain.get(0);
		for (int i=1; i<blockChain.size(); i++) {
			Block block = blockChain.get(i);
			if (!block.getPreviousHash().equals(BlockChainUtil.hash(prevBlock))) {
				return false;
			}
			int prevProof = prevBlock.getProof();
			int proof = block.getProof();
			String input = BigDecimal.valueOf(Math.pow(proof, 2) - Math.pow(prevProof, 2)).toBigInteger().toString();
			String hashOperation = BlockChainUtil.getSHA256Hash(input);
			if (!hashOperation.startsWith("0000")) {
				return false;
			}
			prevBlock = block;
		}
		return true;
	}
}
