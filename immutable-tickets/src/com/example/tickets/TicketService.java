package com.example.tickets;

import java.util.List;

/**
 * Service layer — never mutates a ticket after creation.
 * All "updates" produce a new {@link IncidentTicket} via {@code toBuilder()}.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        return IncidentTicket.builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(List.of("NEW"))
                .build();
    }

    /** Returns a NEW ticket escalated to CRITICAL — original is unchanged. */
    public IncidentTicket escalateToCritical(IncidentTicket t) {
        List<String> updatedTags = new java.util.ArrayList<>(t.getTags());
        updatedTags.add("ESCALATED");
        return t.toBuilder()
                .priority("CRITICAL")
                .tags(updatedTags)
                .build();
    }

    /** Returns a NEW ticket with the assignee set — original is unchanged. */
    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
