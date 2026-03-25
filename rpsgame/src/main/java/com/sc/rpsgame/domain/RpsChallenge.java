package com.sc.rpsgame.domain;

import com.sc.rpsgame.enums.GameResult;
import com.sc.rpsgame.enums.RoPaSis;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data 
@Entity
@RequiredArgsConstructor
public class RpsChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private final User user;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="rpsplay_id")
    private final Rps rps;

    private final RoPaSis opponent;

    private final GameResult gameResult;

    public RpsChallenge() {
        user = null;
        rps = null;
        opponent = null;
        gameResult = null;
    }

    
}
