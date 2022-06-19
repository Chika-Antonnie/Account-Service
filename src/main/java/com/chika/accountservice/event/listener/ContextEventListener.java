package com.chika.accountservice.event.listener;



import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContextEventListener {

    @EventListener
    public void handleContextStartEvent(ContextStartedEvent ctxStartEvt) {
        System.out.println("Initializing customer");
    }
}
