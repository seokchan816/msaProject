package com.sc.rpsgame.domain;

import com.sc.rpsgame.enums.RoPaSis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data   //게터 세터 등등 모든 게 포함
@Entity
@RequiredArgsConstructor
public class Rps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rpsplay_id")
    private Long id;

    private final RoPaSis challenge;

    public Rps() {
        this( null);
    }

}
