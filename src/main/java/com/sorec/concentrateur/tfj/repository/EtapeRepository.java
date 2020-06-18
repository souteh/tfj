package com.sorec.concentrateur.tfj.repository;

import com.sorec.concentrateur.tfj.domain.Etape;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etape entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapeRepository extends JpaRepository<Etape, Long>, JpaSpecificationExecutor<Etape> {
}
