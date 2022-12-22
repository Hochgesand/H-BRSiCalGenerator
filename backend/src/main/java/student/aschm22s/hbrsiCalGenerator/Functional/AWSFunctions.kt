package student.aschm22s.hbrsiCalGenerator.Functional

import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService
import student.aschm22s.hbrsiCalGenerator.email.service.EmailSendingService
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDTO
import java.util.*

class AWSFunctions(
    private val calenderExportService: CalenderExportService,
    private val emailSendingService: EmailSendingService
) {
    val blacklistedEMails = arrayOf("a@andrevr.de", "moin@meister.ovh")

    fun getCalenderOverEmailFun(): (VeranstaltungsIdsAndEmailDTO) -> String {
        return fun(veranstaltungsIdsAndEmailDTO: VeranstaltungsIdsAndEmailDTO): String {
            return if (Arrays.stream(blacklistedEMails)
                    .anyMatch { x: String -> veranstaltungsIdsAndEmailDTO.email == x }
            ) {
                "ne"
            } else emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmailDTO)
        }
    }


}
