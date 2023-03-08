package student.aschm22s.hbrsiCalGenerator.calenderExport.domain;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.text.ParseException;

public class CustomCalenderBase {
    private final String MAKER = "André Schmitz (a@andrevr.de)";
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

    public void addEventToCalender(String eventName, DateTime startTime, DateTime endTime, String modul, String prof, String raum) {
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

    public void addRecurringEventToCalendar(String eventName, DateTime startTime, DateTime endTime, String modul, String prof, String raum, int recurrForWeeks) {
        // Hole mir unsere Timezone
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Berlin");

        // Kreiere das Event
        VEvent vEvent = new VEvent(startTime, endTime, eventName);
        vEvent.getProperties().add(uidGenerator.generateUid());
        String recurrencePattern = "FREQ=WEEKLY;INTERVAL=1;COUNT=" + recurrForWeeks;
        vEvent.getProperties().add(new Description(modul + " in Raum: " + raum + " bei: " + prof));
        vEvent.getProperties().add(new Location("raum"));
        try {
            vEvent.getProperties().add(new RRule(recurrencePattern));
        } catch (final ParseException ex) {
            System.out.println("An Error occured while setting the amount of recurring times.");
        }

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
