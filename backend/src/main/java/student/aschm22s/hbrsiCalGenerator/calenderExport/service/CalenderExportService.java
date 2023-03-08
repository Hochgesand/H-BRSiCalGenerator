package student.aschm22s.hbrsiCalGenerator.calenderExport.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.calenderExport.domain.CustomCalenderBase;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals.LoggedGeneration;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals.TrackService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.StudiengangService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service.StundenplanService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class CalenderExportService {
    private final VeranstaltungsService veranstaltungsService;
    private final StundenplanService stundenplanService;
    private final StudiengangService studiengangService;
    private final TrackService trackService;

    public CalenderExportService(VeranstaltungsService veranstaltungsService, StundenplanService stundenplanService, StudiengangService studiengangService, TrackService trackService) {
        this.veranstaltungsService = veranstaltungsService;
        this.stundenplanService = stundenplanService;
        this.studiengangService = studiengangService;
        this.trackService = trackService;
    }

    public Calendar createCalenderForVeranstaltungenEventRecurring(List<Veranstaltung> veranstaltungen) {
        CustomCalenderBase customCalenderBase = new CustomCalenderBase();
        var stundenplanMap = new HashMap<String, LinkedList<StundenplanEintrag>>();

        for (Veranstaltung x : veranstaltungen) {
            stundenplanService.findByVeranstaltung(x).stream().forEach(stundenplanEintrag -> {
                var hash = stundenplanEintrag.getHashWithoutId();
                var list = stundenplanMap.get(hash);
                if (list == null) {
                    stundenplanMap.put(hash, new LinkedList<>());
                    list = stundenplanMap.get(hash);
                }

                list.add(stundenplanEintrag);
            });

            stundenplanMap.forEach((hash, liste) -> {
                var firstEvent = liste.get(0);
                customCalenderBase.addRecurringEventToCalendar(
                        firstEvent.getVeranstaltung().getName(),
                        new DateTime(firstEvent.getVon().toEpochSecond(ZoneOffset.ofHours(1))),
                        new DateTime(firstEvent.getBis().toEpochSecond(ZoneOffset.ofHours(1))),
                        firstEvent.getVeranstaltung().getName(),
                        firstEvent.getVeranstaltung().getProf(),
                        firstEvent.getRaum(),
                        liste.size()
                );
            });
        }

        return customCalenderBase.getiCalender();
    }

    public byte[] createCalender(VeranstaltungsIdsDTO veranstaltungsIdsDTO) throws IOException, NoSuchAlgorithmException {
        if (veranstaltungsIdsDTO.getVeranstaltungsIds().size() == 0)
            return new byte[0x00];

        List<Veranstaltung> veranstaltungen = veranstaltungsService.findByIdsIn(veranstaltungsIdsDTO.getVeranstaltungsIds());
        Calendar calenderToReturn = createCalenderForVeranstaltungenEventRecurring(veranstaltungen);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calenderToReturn, byteArrayOutputStream);

        if (!veranstaltungsIdsDTO.isNotrack()) {
            LoggedGeneration loggedGeneration = new LoggedGeneration();
            StringBuilder stringBuilder = new StringBuilder();
            for (Veranstaltung x : veranstaltungen) {
                stringBuilder.append(x.getName()).append(",");
            }
            loggedGeneration.setVeranstaltungen(stringBuilder.toString());
            loggedGeneration.setTimestamp(new Timestamp(System.currentTimeMillis()));
            if (veranstaltungsIdsDTO instanceof VeranstaltungsIdsAndEmailDTO) {
                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException ignored) {
                    return byteArrayOutputStream.toByteArray();
                }
                final byte[] hash = digest.digest(((VeranstaltungsIdsAndEmailDTO) veranstaltungsIdsDTO).getEmail().getBytes());
                final StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    final String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1)
                        hexString.append('0');
                    hexString.append(hex);
                }

                loggedGeneration.setHashedemail(hexString.toString());
            }
            if (veranstaltungen.stream().findFirst().isPresent()) {
                loggedGeneration.setStudiengang(veranstaltungen.stream().findFirst().get().getStudiengang().getName());
            }
            trackService.generateLogEntry(loggedGeneration);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
