package com.pblgllgs.security.entity;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pblgllgs.security.domain.RequestContext;
import com.pblgllgs.security.exception.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class Auditable {

    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist(){
        var userId = RequestContext.getUserId();
        if (userId == null) {
            throw new ApiException("Cannot persist without user ID");
        }
        setCreatedBy(userId);
        setCreatedAt(LocalDateTime.now());
        setUpdatedBy(userId);
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void beforeUpdate(){
        var userId = RequestContext.getUserId();
        if (userId == null) {
            throw new ApiException("Cannot update without user ID");
        }
        setUpdatedBy(userId);
        setUpdatedAt(LocalDateTime.now());
    }

}
