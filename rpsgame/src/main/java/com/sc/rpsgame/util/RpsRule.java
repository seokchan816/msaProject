package com.sc.rpsgame.util;

import java.util.HashMap;
import java.util.Map;

import com.sc.rpsgame.enums.GameResult;
import com.sc.rpsgame.enums.RoPaSis;

public class RpsRule {
    private static Map<RoPaSis, GameResult> userRock = new HashMap<>();
	private static  Map<RoPaSis, GameResult> userPaper = new HashMap<>();
	private static  Map<RoPaSis, GameResult> userScissors = new HashMap<>();
	public static   Map<RoPaSis, Map<RoPaSis, GameResult>> checkMap = new HashMap<>();
	
	static {
		userRock.put(RoPaSis.PAPER, GameResult.LOST);
		userRock.put(RoPaSis.ROCK, GameResult.TIE);
		userRock.put(RoPaSis.SCISSORS, GameResult.WON);
		
		userPaper.put(RoPaSis.PAPER, GameResult.TIE);
		userPaper.put(RoPaSis.ROCK, GameResult.WON);
		userPaper.put(RoPaSis.SCISSORS, GameResult.LOST);
		
		userScissors.put(RoPaSis.PAPER, GameResult.WON);
		userScissors.put(RoPaSis.ROCK, GameResult.LOST);
		userScissors.put(RoPaSis.SCISSORS, GameResult.TIE);
		
		checkMap.put(RoPaSis.ROCK, userRock);
		checkMap.put(RoPaSis.PAPER, userPaper);
		checkMap.put(RoPaSis.SCISSORS, userScissors);
	}
}
