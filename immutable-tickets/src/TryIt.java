import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demonstrates immutability:
 * - Direct mutation (setters) no longer compiles.
 * - Service "updates" return new instances; original is unchanged.
 * - External modification of the tags list has no effect on the ticket.
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket(
                "TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created  : " + t);

        // All "updates" return NEW instances — originals stay pristine
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAssigned : " + assigned);
        System.out.println("Escalated: " + escalated);
        System.out.println("Original : " + t); // unchanged

        // External mutation of the returned tags list has NO effect
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nWARN: tags list was mutable (should not happen)");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nGood: tags list is unmodifiable — immutability preserved.");
        }
        System.out.println("Final ticket: " + escalated);
    }
}
