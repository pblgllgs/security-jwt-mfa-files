package com.pblgllgs.security.event.listener;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.event.UserEvent;
import com.pblgllgs.security.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final EmailService service;

    @EventListener
    public void onUserEvent(UserEvent event) {
        switch (event.getEventType()) {
            case REGISTRATION -> service.sendNewAccountEmail(
                    event.getUser().getFirstName(),
                    event.getUser().getEmail(),
                    (String) event.getData().get("key")
            );
            case RESETPASSWORD -> service.sendPasswordResetEmail(
                    event.getUser().getFirstName(),
                    event.getUser().getEmail(),
                    (String) event.getData().get("key")
            );
            default -> {
            }
        }
    }
}
