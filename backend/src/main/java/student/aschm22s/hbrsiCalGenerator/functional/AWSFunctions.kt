package student.aschm22s.hbrsiCalGenerator.functional

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService
import student.aschm22s.hbrsiCalGenerator.email.service.EmailSendingService
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.StudiengangService
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDTO
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService
import java.util.*

@Configuration
open class AWSFunctions(
    private val calenderExportService: CalenderExportService,
    private val emailSendingService: EmailSendingService,
    private val studiengangService: StudiengangService,
    private val veranstaltungsService: VeranstaltungsService
) {
    private val blacklistedEMails = arrayOf("a@andrevr.de", "moin@meister.ovh")

    @Bean
    open fun getCalenderOverEmailFun(): (VeranstaltungsIdsAndEmailDTO) -> String {
        return fun(veranstaltungsIdsAndEmailDTO: VeranstaltungsIdsAndEmailDTO): String {
            return if (Arrays.stream(blacklistedEMails)
                    .anyMatch { x: String -> veranstaltungsIdsAndEmailDTO.email == x }
            ) {
                "ne"
            } else emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmailDTO)
        }
    }

    @Bean
    open fun getAllStundiengaenge(): () -> List<Studiengang> {
        return fun(): List<Studiengang> {
            return studiengangService.findAllStudiengaenge()
        }
    }

    @Bean
    open fun getVeranstaltungen(): () -> List<Veranstaltung?>? {
        return fun(): List<Veranstaltung>{
            return veranstaltungsService.findAll()
        }
    }

    @Bean
    open fun getVeranstaltungByStudiengang(): (String) -> List<Veranstaltung> {
        return fun (studiengang: String): List<Veranstaltung> {
            return veranstaltungsService.findAllByStudiengang(studiengang)
        }
    }
}
