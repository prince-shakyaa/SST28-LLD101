package com.example.tickets;

import java.util.List;

/**
 * Immutable incident ticket.
 * All fields are private final. There are no setters.
 * Use the nested {@link Builder} for construction.
 * All validation is centralised in {@code Builder.build()}.
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags; // defensive copy — never leaks
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;

    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        this.tags = b.tags == null ? List.of() : List.copyOf(b.tags);
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    // ---- Getters (no setters) -----------------------------------------------

    public String getId() {
        return id;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    /** Returns an unmodifiable view — tags cannot be mutated externally. */
    public List<String> getTags() {
        return tags;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public boolean isCustomerVisible() {
        return customerVisible;
    }

    public Integer getSlaMinutes() {
        return slaMinutes;
    }

    public String getSource() {
        return source;
    }

    /**
     * Returns a builder pre-populated from this ticket so callers can produce
     * a modified copy without mutating the original.
     */
    public Builder toBuilder() {
        return new Builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .description(description)
                .priority(priority)
                .tags(tags)
                .assigneeEmail(assigneeEmail)
                .customerVisible(customerVisible)
                .slaMinutes(slaMinutes)
                .source(source);
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    // =========================================================================
    // Builder
    // =========================================================================

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        // Required
        private String id;
        private String reporterEmail;
        private String title;
        // Optional
        private String description;
        private String priority = "MEDIUM";
        private List<String> tags;
        private String assigneeEmail;
        private boolean customerVisible = false;
        private Integer slaMinutes;
        private String source = "CLI";

        private Builder() {
        }

        public Builder id(String val) {
            this.id = val;
            return this;
        }

        public Builder reporterEmail(String val) {
            this.reporterEmail = val;
            return this;
        }

        public Builder title(String val) {
            this.title = val;
            return this;
        }

        public Builder description(String val) {
            this.description = val;
            return this;
        }

        public Builder priority(String val) {
            this.priority = val;
            return this;
        }

        public Builder tags(List<String> val) {
            this.tags = val;
            return this;
        }

        public Builder assigneeEmail(String val) {
            this.assigneeEmail = val;
            return this;
        }

        public Builder customerVisible(boolean val) {
            this.customerVisible = val;
            return this;
        }

        public Builder slaMinutes(Integer val) {
            this.slaMinutes = val;
            return this;
        }

        public Builder source(String val) {
            this.source = val;
            return this;
        }

        /** Single validation point — all rules enforced here before construction. */
        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");
            if (assigneeEmail != null) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }
            return new IncidentTicket(this);
        }
    }
}
