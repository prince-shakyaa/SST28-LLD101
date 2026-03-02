/**
 * ClubConsole depends on minimal role interfaces (ISP).
 * Each tool variable is typed to the exact interface it uses — no fat
 * dependency.
 */
public class ClubConsole {
    private final BudgetLedger ledger;
    private final MinutesBook minutes;
    private final EventPlanner events;

    public ClubConsole(BudgetLedger ledger, MinutesBook minutes, EventPlanner events) {
        this.ledger = ledger;
        this.minutes = minutes;
        this.events = events;
    }

    public void run() {
        IFinanceClient treasurer = new TreasurerTool(ledger);
        IMinutesClient secretary = new SecretaryTool(minutes);
        IEventsClient lead = new EventLeadTool(events);

        treasurer.addIncome(5000, "sponsor");
        secretary.addMinutes("Meeting at 5pm");
        lead.createEvent("HackNight", 2000);

        System.out.println("Summary: ledgerBalance=" + ledger.balanceInt()
                + ", minutes=" + minutes.count()
                + ", events=" + lead.getEventsCount());
    }
}
