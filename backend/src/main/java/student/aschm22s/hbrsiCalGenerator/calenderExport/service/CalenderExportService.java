package student.aschm22s.hbrsiCalGenerator.calenderExport.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.calenderExport.domain.CustomCalenderBase;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals.LoggedGeneration;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals.TrackService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.service.AppointmentService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CalenderExportService {
    private final VeranstaltungsService veranstaltungsService;
    private final AppointmentService appointmentService;
    private final TrackService trackService;

    public CalenderExportService(VeranstaltungsService veranstaltungsService, AppointmentService appointmentService, TrackService trackService) {
        this.veranstaltungsService = veranstaltungsService;
        this.appointmentService = appointmentService;
        this.trackService = trackService;
    }

    public Calendar createCalenderForVeranstaltungen(List<Veranstaltung> veranstaltungen) {
        ArrayList<StundenplanEintrag> stundenplanEintragArrayList = new ArrayList<>();
        ArrayList<Appointment> stundenplanDateMNRepoArrayList = new ArrayList<>();

        for (Veranstaltung x : veranstaltungen) {
            stundenplanEintragArrayList.addAll(veranstaltungsService.findByVeranstaltung(x));
        }

        for (StundenplanEintrag x : stundenplanEintragArrayList) {
            stundenplanDateMNRepoArrayList.addAll(appointmentService.findByStundenplanEintrag(x));
        }

        CustomCalenderBase customCalenderBase = new CustomCalenderBase();

        for (Appointment x : stundenplanDateMNRepoArrayList) {
            Veranstaltung veranstaltung;
            StundenplanEintrag stundenplanEintrag;

            stundenplanEintrag = stundenplanEintragArrayList.stream()
                    .filter(j -> x.getStundenplanEintrag().getId().equals(j.getId()))
                    .findFirst()
                    .orElse(null);

            if (stundenplanEintrag == null)
                continue;

            veranstaltung = veranstaltungen.stream()
                    .filter(j -> stundenplanEintrag.getVeranstaltung().getId().equals(j.getId()))
                    .findFirst()
                    .orElse(null);

            if (veranstaltung == null)
                continue;

            customCalenderBase.addEventToCalender(
                    veranstaltung.getName(),
                    new DateTime(x.getDate().getTime()),
                    new DateTime(x.getDate().getTime() + (stundenplanEintrag.getBis().getTime() - stundenplanEintrag.getVon().getTime())),
                    veranstaltung.getName(),
                    veranstaltung.getProf(),
                    stundenplanEintrag.getRaum());
        }

        return customCalenderBase.getiCalender();
    }

    public byte[] createCalender(VeranstaltungsIdsDTO veranstaltungsIdsDTO) throws IOException, NoSuchAlgorithmException {
        if (veranstaltungsIdsDTO.getVeranstaltungsIds().size() == 0)
            return new byte[0x00];

        List<Veranstaltung> veranstaltungen = veranstaltungsService.findByIdsIn(veranstaltungsIdsDTO.getVeranstaltungsIds());
        Calendar calenderToReturn = createCalenderForVeranstaltungen(veranstaltungen);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calenderToReturn, byteArrayOutputStream);

        if (!veranstaltungsIdsDTO.isNotrack()){
            LoggedGeneration loggedGeneration = new LoggedGeneration();
            loggedGeneration.setVeranstaltungen(veranstaltungen);
            if (veranstaltungsIdsDTO instanceof VeranstaltungsIdsAndEmailDTO) {
                MessageDigest digest;
                try{
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

                loggedGeneration.setEmail(hexString.toString());
            }
            trackService.generateLogEntry(loggedGeneration);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public String createCalenderAsCSV(VeranstaltungsIdsDTO veranstaltungsIdsDTO) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tag;von;bis;raum;veranstaltung;wer\n");

        ArrayList<Veranstaltung> veranstaltungIterable = (ArrayList<Veranstaltung>) veranstaltungsService.findByIdsIn(veranstaltungsIdsDTO.getVeranstaltungsIds());
        ArrayList<StundenplanEintrag> stundenplanEintragArrayList = new ArrayList<>();
        ArrayList<Appointment> stundenplanDateMNRepoArrayList = new ArrayList<>();

        for (Veranstaltung x : veranstaltungIterable) {
            stundenplanEintragArrayList.addAll(veranstaltungsService.findByVeranstaltung(x));
        }

        for (StundenplanEintrag x : stundenplanEintragArrayList) {
            stundenplanDateMNRepoArrayList.addAll(appointmentService.findAllByStundenplanEintragOrderByDateAsc(x));
        }

        for (Appointment x : stundenplanDateMNRepoArrayList) {
            // Need to get this value from appplication.properties.
            // This is a timeframe where every Event in a Week should take place, so I can extract information to make
            // a standard Stundenplan. English vocabulary 11/10
            if (x.getDate().getTime() < 1634508000000L || x.getDate().getTime() > 1635109200000L) {
                continue;
            }

            Veranstaltung veranstaltung;
            StundenplanEintrag stundenplanEintrag;

            stundenplanEintrag = stundenplanEintragArrayList.stream()
                    .filter(j -> Objects.equals(x.getStundenplanEintrag().getId(), j.getId()))
                    .findFirst()
                    .orElse(null);

            if (stundenplanEintrag == null)
                continue;

            veranstaltung = veranstaltungIterable.stream()
                    .filter(j -> Objects.equals(stundenplanEintrag.getVeranstaltung().getId(), j.getId()))
                    .findFirst()
                    .orElse(null);

            if (veranstaltung == null)
                continue;

            DateTime von = new DateTime(x.getDate());
            String vonString = von.toString();
            stringBuilder.append(new SimpleDateFormat("EEEE").format(x.getDate()));
            stringBuilder.append(";");
            vonString = vonString.substring(9, 11) + ":" + vonString.substring(11, 13);
            stringBuilder.append(vonString);
            stringBuilder.append(";");
            String bis = new DateTime(x.getDate().getTime() + (stundenplanEintrag.getBis().getTime() - stundenplanEintrag.getVon().getTime())).toString();
            bis = bis.substring(9, 11) + ":" + bis.substring(11, 13);
            stringBuilder.append(bis);
            stringBuilder.append(";");
            stringBuilder.append(stundenplanEintrag.getRaum());
            stringBuilder.append(";");
            stringBuilder.append(veranstaltung.getName());
            stringBuilder.append(";");
            stringBuilder.append(veranstaltung.getProf());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
