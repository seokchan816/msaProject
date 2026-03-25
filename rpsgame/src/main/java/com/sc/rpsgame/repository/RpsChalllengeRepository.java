package com.sc.rpsgame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.rpsgame.domain.RpsChallenge;

public interface RpsChalllengeRepository extends JpaRepository<RpsChallenge,Long>{

    List<RpsChallenge> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
