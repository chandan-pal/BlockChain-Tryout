package com.blockchain.tryout.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.blockchain.tryout.model.Block;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlockChainUtil {
	
	/**
	 * Gets the SHA 256 hash.
	 *
	 * @param input the input
	 * @return the SHA 256 hash
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getSHA256Hash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return bytesToHex(md.digest(input.getBytes(StandardCharsets.UTF_8)));
	}
	
	/**
	 * Bytes to hex.
	 *
	 * @param hash the hash
	 * @return the string
	 */
	public static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	/**
	 * Hash.
	 *
	 * @param block the block
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String hash(Block block) throws JsonProcessingException, NoSuchAlgorithmException {
		ObjectMapper mapper = new ObjectMapper();
		String strJson = mapper.writeValueAsString(block);
		return getSHA256Hash(strJson);
	}
}
