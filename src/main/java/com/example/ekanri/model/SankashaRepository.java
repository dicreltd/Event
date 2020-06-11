package com.example.ekanri.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SankashaRepository extends JpaRepository <Sankasha, Integer>{
	List<Sankasha> findByEid(int eid);
}
