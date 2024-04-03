package com.pblgllgs.security.repository;

import com.pblgllgs.security.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity,Long> {
    Optional<CredentialEntity> getCredentialByUserEntityId(Long userId);
}
