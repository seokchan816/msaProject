package com.sc.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sc.board.domain.LeaderBoardRow;
import com.sc.board.repository.ScoreCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaderBoardService {
    private final ScoreCardRepository scoreCardRepository;

    public List<LeaderBoardRow> getCurrentLeaderBoard(){
        return scoreCardRepository.findFirst10();
    }
}
