package com.sorec.concentrateur.tfj.repository;

import com.sorec.concentrateur.tfj.domain.Statutenum;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Statutenum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatutenumRepository extends JpaRepository<Statutenum, Long>, JpaSpecificationExecutor<Statutenum> {
}
