// ISP: EventLeadTool implements only IEventsClient — no irrelevant methods.
public class EventLeadTool implements IEventsClient {
    private final EventPlanner planner;

    public EventLeadTool(EventPlanner planner) {
        this.planner = planner;
    }

    @Override
    public void createEvent(String name, double budget) {
        planner.create(name, budget);
    }

    @Override
    public int getEventsCount() {
        return planner.count();
    }
}
