package com.sorec.concentrateur.tfj.repository;

import com.sorec.concentrateur.tfj.domain.Messageerreur;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Messageerreur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageerreurRepository extends JpaRepository<Messageerreur, Long>, JpaSpecificationExecutor<Messageerreur> {
}
