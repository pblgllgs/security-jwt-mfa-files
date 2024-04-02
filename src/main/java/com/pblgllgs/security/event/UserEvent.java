package com.pblgllgs.security.event;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.entity.UserEntity;
import com.pblgllgs.security.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {

    private UserEntity user;
    protected EventType eventType;
    private Map<?,?> data;
}
