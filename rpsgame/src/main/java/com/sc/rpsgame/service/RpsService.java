package com.sc.rpsgame.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sc.rpsgame.domain.RpsChallenge;
import com.sc.rpsgame.domain.User;
import com.sc.rpsgame.enums.GameResult;
import com.sc.rpsgame.enums.RoPaSis;
import com.sc.rpsgame.event.EventDispatcher;
import com.sc.rpsgame.event.RpsSolvedEvent;
import com.sc.rpsgame.repository.RpsChalllengeRepository;
import com.sc.rpsgame.repository.UserRepository;
import com.sc.rpsgame.util.RpsRule;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RpsService {
    private final RandomGeneratorService randomGeneratorService;
	private final RpsChalllengeRepository rpsChallengeRepository;
	private final UserRepository userRepository;
	private final EventDispatcher eventDispatcher;

	
	private RoPaSis createRandomRps() {
		return randomGeneratorService.getRoPaSis();
	}
	
	@Transactional
	public Map<String, String> checkChallenge(RpsChallenge rpsChallenge) {
		Map<String, String> map = new HashMap<String, String>();
		Optional<User> user = userRepository.findByAlias(rpsChallenge.getUser().getAlias());
		
		Assert.isNull(rpsChallenge.getGameResult(), "완료된 상태를 보낼 수 없습니다!!");
		RoPaSis computerChoice = createRandomRps();
		GameResult gameResult = checkScore(rpsChallenge.getRps().getChallenge(), computerChoice);
		
		RpsChallenge checkedChallenge = 
				new RpsChallenge(user.orElse(rpsChallenge.getUser()), rpsChallenge.getRps(), computerChoice, gameResult);
		
		rpsChallengeRepository.save(checkedChallenge);
		
		eventDispatcher.send(new RpsSolvedEvent(checkedChallenge.getId(),checkedChallenge.getUser().getId(), checkedChallenge.getUser().getAlias(), checkedChallenge.getGameResult().getCommentary()));

		map.put("opponent", computerChoice.getCommentary());
		map.put("outcome", checkedChallenge.getGameResult().getCommentary());
		map.put("userId", "" + checkedChallenge.getUser().getId());
		
		return map;
	}
	
	private GameResult checkScore(RoPaSis userRps, RoPaSis computerRps) {
		return RpsRule.checkMap.get(userRps).get(computerRps);
	}
	
	public List<RpsChallenge> getStatsForUser(String userAlias) {
		// TODO Auto-generated method stub
		return rpsChallengeRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}
}
