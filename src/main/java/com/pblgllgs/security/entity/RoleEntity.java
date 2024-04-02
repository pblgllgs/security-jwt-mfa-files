package com.pblgllgs.security.entity;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pblgllgs.security.enums.Authority;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "roles")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends Auditable{
    private String name;
    private Authority authorities;
}
