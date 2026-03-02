/** ISP: client interface for event-lead operations only. */
public interface IEventsClient {
    void createEvent(String name, double budget);

    int getEventsCount();
}
