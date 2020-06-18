package com.sorec.concentrateur.tfj.repository;

import com.sorec.concentrateur.tfj.domain.Action;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Action entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRepository extends JpaRepository<Action, Long>, JpaSpecificationExecutor<Action> {
}
