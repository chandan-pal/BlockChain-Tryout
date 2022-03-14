package com.blockchain.tryout.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Block {
	private int index;
	private long timestamp;
	private int proof;
	private String previousHash;

}
