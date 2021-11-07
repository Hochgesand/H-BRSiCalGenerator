package student.aschm22s.hbrsiCalGenerator.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.models.CustomCalender.CustomCalenderBase;
import student.aschm22s.hbrsiCalGenerator.models.DAOObjects.VeranstaltungsIds;
import student.aschm22s.hbrsiCalGenerator.models.StundenplanDatumMN;
import student.aschm22s.hbrsiCalGenerator.models.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.repository.StundenplanDateMNRepository;
import student.aschm22s.hbrsiCalGenerator.repository.StundenplanRepository;
import student.aschm22s.hbrsiCalGenerator.repository.VeranstaltungsRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class CalenderGeneratorService {

    private final VeranstaltungsRepository veranstaltungsRepo;
    private final StundenplanRepository stundenplanRepo;
    private final StundenplanDateMNRepository stundenplanDateMNRepo;

    public CalenderGeneratorService(VeranstaltungsRepository veranstaltungsRepo, StundenplanRepository stundenplanRepo, StundenplanDateMNRepository stundenplanDateMNRepo) {
        this.veranstaltungsRepo = veranstaltungsRepo;
        this.stundenplanRepo = stundenplanRepo;
        this.stundenplanDateMNRepo = stundenplanDateMNRepo;
    }

    public Calendar createCalenderForVeranstaltungen(List<Integer> Ids) {
        ArrayList<Veranstaltung> veranstaltungIterable = (ArrayList<Veranstaltung>) veranstaltungsRepo.findByIdIn(Ids);
        ArrayList<StundenplanEintrag> stundenplanEintragArrayList = new ArrayList<>();
        ArrayList<StundenplanDatumMN> stundenplanDateMNRepoArrayList = new ArrayList<>();

        for (Veranstaltung x : veranstaltungIterable) {
            stundenplanEintragArrayList.addAll((Collection<? extends StundenplanEintrag>) stundenplanRepo.findByVeranstaltung(x));
        }

        for (StundenplanEintrag x : stundenplanEintragArrayList) {
            stundenplanDateMNRepoArrayList.addAll((Collection<? extends StundenplanDatumMN>) stundenplanDateMNRepo.findByStundenplanEintrag(x));
        }

        CustomCalenderBase customCalenderBase = new CustomCalenderBase();

        for (StundenplanDatumMN x : stundenplanDateMNRepoArrayList) {
            Veranstaltung veranstaltung;
            StundenplanEintrag stundenplanEintrag;

            stundenplanEintrag = stundenplanEintragArrayList.stream()
                    .filter(j -> x.getStundenplanEintrag().getId() == j.getId())
                    .findFirst()
                    .orElse(null);

            if (stundenplanEintrag == null)
                continue;

            veranstaltung = veranstaltungIterable.stream()
                    .filter(j -> stundenplanEintrag.getVeranstaltung().getId() == j.getId())
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

    public byte[] createCalender(VeranstaltungsIds veranstaltungsIds) throws IOException {
        if (veranstaltungsIds.getVeranstaltungsIds().size() == 0)
            return new byte[0x00];

        Calendar calenderToReturn = createCalenderForVeranstaltungen(veranstaltungsIds.getVeranstaltungsIds());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calenderToReturn, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public String createCalenderAsCSV(VeranstaltungsIds veranstaltungsIds) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tag;von;bis;raum;veranstaltung;wer\n");

        ArrayList<Veranstaltung> veranstaltungIterable = (ArrayList<Veranstaltung>) veranstaltungsRepo.findByIdIn(veranstaltungsIds.getVeranstaltungsIds());
        ArrayList<StundenplanEintrag> stundenplanEintragArrayList = new ArrayList<>();
        ArrayList<StundenplanDatumMN> stundenplanDateMNRepoArrayList = new ArrayList<>();

        for (Veranstaltung x : veranstaltungIterable) {
            stundenplanEintragArrayList.addAll((Collection<? extends StundenplanEintrag>) stundenplanRepo.findByVeranstaltung(x));
        }

        for (StundenplanEintrag x : stundenplanEintragArrayList) {
            stundenplanDateMNRepoArrayList.addAll((Collection<? extends StundenplanDatumMN>) stundenplanDateMNRepo.findAllByStundenplanEintragOrderByDateAsc(x));
        }

        for (StundenplanDatumMN x : stundenplanDateMNRepoArrayList) {
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
