package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.CustomCalender;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

public class CustomCalenderBase {
    private final String MAKER = "André Schmitz (a@andrevr.de)";
    private String eventName;
    private DateTime startTime;
    private DateTime endTime;
    private String modul;
    private String prof;
    private String raum;
    private final UidGenerator uidGenerator;
    private final Calendar iCalender;

    public CustomCalenderBase() {
        uidGenerator = new RandomUidGenerator();
        // Kreiere den Kalender
        iCalender = new Calendar();
        iCalender.getProperties().add(new ProdId(MAKER));
        iCalender.getProperties().add(Version.VERSION_2_0);
        iCalender.getProperties().add(CalScale.GREGORIAN);
    }

    public void addEventToCalender(String eventName, DateTime startTime, DateTime endTime, String modul, String prof, String raum){
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.modul = modul;
        this.prof = prof;
        this.raum = raum;

        // Hole mir unsere Timezone
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Berlin");

        // Kreiere das Event
        VEvent vEvent = new VEvent(startTime, endTime, eventName);
        vEvent.getProperties().add(uidGenerator.generateUid());
        vEvent.getProperties().add(new Description(modul + " in Raum: " + raum + " bei: " + prof));

        // füge die Timezone dem Event hinzu
        VTimeZone tz = timezone.getVTimeZone();
        vEvent.getProperties().add(tz.getTimeZoneId());

        // füge dem Kalender das Event hinzu
        iCalender.getComponents().add(vEvent);
    }

    public Calendar getiCalender() {
        // Gebe den Kalender zurück
        return iCalender;
    }
}
