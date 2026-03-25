package com.sc.rpsgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.rpsgame.domain.Rps;

public interface RpsRepository extends JpaRepository<Rps,Long>{

}
