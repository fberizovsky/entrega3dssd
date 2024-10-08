package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {


}