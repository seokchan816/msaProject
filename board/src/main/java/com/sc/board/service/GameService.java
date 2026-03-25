package com.sc.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sc.board.domain.BadgeCard;
import com.sc.board.domain.GameStats;
import com.sc.board.domain.ScoreCard;
import com.sc.board.enums.Badge;
import com.sc.board.repository.BadgeCardRepository;
import com.sc.board.repository.ScoreCardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {
    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;

    public void newChallengeForUser(final Long userId, final String alias, final Long challengeId, final String outcome) {	
		// 처음엔 답이 맞았을 때만 점수를 줌
		if (outcome.equals("승")) {
			ScoreCard scoreCard = new ScoreCard(userId, alias, challengeId);
			scoreCardRepository.save(scoreCard);
			log.info("사용자 ID {}, 점수 {} 점, 답안 ID {}", userId, scoreCard.getScore(), challengeId);
			List<BadgeCard> badgeCards = processForBadges(userId, challengeId);
		}
	}

    private List<BadgeCard> processForBadges(final Long userId, final Long challengeId) {
		List<BadgeCard> badgeCards = new ArrayList<>();

		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("사용자 ID {} 의 새로운 점수 {}", userId, totalScore);

		List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		// 점수 기반 배지
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_RPS, totalScore, 10, userId).ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_RPS, totalScore, 50, userId).ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_RPS, totalScore, 100, userId).ifPresent(badgeCards::add);
  
		// 첫 번째 정답 배지
		if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_CHALLENGE)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_CHALLENGE, userId);
			badgeCards.add(firstWonBadge);
		}
		

		return badgeCards;
	}

    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge,
			final int score, final int scoreThreshold, final Long userId) {
		if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

    private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

    private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("사용자 ID {} 새로운 배지 획득: {}", userId, badge);
		return badgeCard;
	}

    public GameStats retrieveStatsForUser(final Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}
}
