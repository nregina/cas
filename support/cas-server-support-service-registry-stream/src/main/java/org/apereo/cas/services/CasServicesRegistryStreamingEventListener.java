package org.apereo.cas.services;

import org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceLoadedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceSavedEvent;
import org.apereo.cas.util.spring.CasEventListener;

/**
 * Interface for {@code DefaultCasServicesRegistryStreamingEventListener} to allow spring {@code @Async} support to use JDK proxy.
 * @author Hal Deadman
 * @since 6.5.0
 */
public interface CasServicesRegistryStreamingEventListener extends CasEventListener {

    /**
     * Handle cas registered service loaded event.
     *
     * @param event the event
     */
    void handleCasRegisteredServiceLoadedEvent(CasRegisteredServiceLoadedEvent event);

    /**
     * Handle cas registered service saved event.
     *
     * @param event the event
     */
    void handleCasRegisteredServiceSavedEvent(CasRegisteredServiceSavedEvent event);

    /**
     * Handle cas registered service deleted event.
     *
     * @param event the event
     */
    void handleCasRegisteredServiceDeletedEvent(CasRegisteredServiceDeletedEvent event);
}
