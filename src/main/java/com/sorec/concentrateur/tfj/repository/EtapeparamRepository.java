package com.sorec.concentrateur.tfj.repository;

import com.sorec.concentrateur.tfj.domain.Etapeparam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etapeparam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapeparamRepository extends JpaRepository<Etapeparam, Long>, JpaSpecificationExecutor<Etapeparam> {
}
