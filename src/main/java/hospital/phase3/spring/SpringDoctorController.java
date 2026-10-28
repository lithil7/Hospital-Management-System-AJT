
package hospital.phase3.spring;

import hospital.phase3.db.HospitalDAO;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringDoctorController {

    private final GreetingService greetingService;

    @Autowired
    public SpringDoctorController(GreetingService greetingService) {

        this.greetingService = greetingService;
    }

    @GetMapping("/doctors")
    public String doctors(Model model) throws SQLException {

        HospitalDAO dao = new HospitalDAO();

        model.addAttribute(
            "message",
            greetingService.message()
        );

        model.addAttribute(
            "rows",
            dao.list("doctors")
        );

        return "springDoctors";
    }
}
