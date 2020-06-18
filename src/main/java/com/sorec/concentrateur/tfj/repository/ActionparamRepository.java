package com.sorec.concentrateur.tfj.repository;

import com.sorec.concentrateur.tfj.domain.Actionparam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Actionparam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionparamRepository extends JpaRepository<Actionparam, Long>, JpaSpecificationExecutor<Actionparam> {
}
