package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.StundenplanDateMNRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.StundenplanRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.VeranstaltungsRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.CustomCalender.CustomCalenderBase;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.StundenplanDatumMN;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class CalenderGeneratorService {

    @Autowired
    private VeranstaltungsRepo veranstaltungsRepo;
    @Autowired
    private StundenplanRepo stundenplanRepo;
    @Autowired
    private StundenplanDateMNRepo stundenplanDateMNRepo;

    public Calendar createCalenderForVeranstaltungen(List<Integer> Ids){
        ArrayList<Veranstaltung> veranstaltungIterable = (ArrayList<Veranstaltung>) veranstaltungsRepo.findByIdIn(Ids);
        ArrayList<StundenplanEintrag> stundenplanEintragArrayList = new ArrayList<>();
        ArrayList<StundenplanDatumMN> stundenplanDateMNRepoArrayList = new ArrayList<>();

        for (Veranstaltung x : veranstaltungIterable) {
            stundenplanEintragArrayList.addAll((Collection<? extends StundenplanEintrag>) stundenplanRepo.findByVeranstaltung(x));
        }

        for (StundenplanEintrag x: stundenplanEintragArrayList) {
            stundenplanDateMNRepoArrayList.addAll((Collection<? extends StundenplanDatumMN>) stundenplanDateMNRepo.findByStundenplanEintrag(x));
        }

        CustomCalenderBase customCalenderBase = new CustomCalenderBase();

        for (StundenplanDatumMN x: stundenplanDateMNRepoArrayList) {
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
                    new DateTime(x.getDate().getTime() + stundenplanEintrag.getBis().getTime()),
                    veranstaltung.getName(),
                    veranstaltung.getProf(),
                    stundenplanEintrag.getRaum());
        }

        return customCalenderBase.getiCalender();
    }
}
