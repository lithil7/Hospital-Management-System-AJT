
package hospital.phase3.spring;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    public String message() {

        return "Spring Dependency Injection is working! "
             + "Doctors are loaded from hospital_db.";
    }
}
