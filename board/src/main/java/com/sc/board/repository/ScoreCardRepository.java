package com.sc.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sc.board.domain.LeaderBoardRow;
import com.sc.board.domain.ScoreCard;

public interface ScoreCardRepository extends JpaRepository<ScoreCard,Long>{

    /**
	 * ScoreCard 의 점수를 합해서 주어진 사용자의 총 점수를 조회
	 *
	 * @param userId 총 점수를 조회하고자 하는 사용자의 ID
	 * @return 주어진 사용자의 총 점수
	 */
	@Query("SELECT SUM(s.score) FROM com.sc.board.domain.ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") final Long userId);

	/**
	 * 사용자와 사용자의 총 점수를 나타내는 {@link LeaderBoardRow} 리스트를 조회
	 *
	 * @return 높은 점수 순으로 정렬된 리더 보드
	 */
	@Query("SELECT NEW com.sc.board.domain.LeaderBoardRow(s.userId, s.alias, SUM(s.score)) "
			+ "FROM com.sc.board.domain.ScoreCard s "
			+ "GROUP BY s.userId, s.alias ORDER BY SUM(s.score) DESC LIMIT 10")
	List<LeaderBoardRow> findFirst10();

	/**
	 * 주어진 사용자의 모든 ScoreCard 를 조회
	 *
	 * @param userId 사용자 ID
	 * @return 주어진 사용자의 최근 순으로 정렬된 ScoreCard 리스트
	 */
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
