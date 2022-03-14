package com.blockchain.tryout.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockchain.tryout.model.Block;
import com.blockchain.tryout.model.BlockChain;
import com.blockchain.tryout.service.BlockChainService;
import com.blockchain.tryout.util.BlockChainUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class BlockChainController {
	
	@Autowired
	BlockChainService blockChainService;
	
	/**
	 * Mine block.
	 *
	 * @return the block
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws JsonProcessingException the json processing exception
	 */
	@GetMapping(value = "/mine-block")
	public Block mineBlock() throws NoSuchAlgorithmException, JsonProcessingException {
		Block prevBlock = blockChainService.getPreviousBlock();
		int prevProof = prevBlock.getProof();
		int proof = blockChainService.proofOfWork(prevProof);
		String prevHash = BlockChainUtil.hash(prevBlock);
		return blockChainService.createBlock(proof, prevHash);
	}
	
	/**
	 * Gets the chain.
	 *
	 * @return the chain
	 */
	@GetMapping(value = "get-chain")
	public BlockChain getChain() {
		return blockChainService.getChain();
	}
	
	@GetMapping("/is-valid")
	public boolean isBlockChainValid() throws JsonProcessingException, NoSuchAlgorithmException {
		return blockChainService.isChainValid();
	}
}
