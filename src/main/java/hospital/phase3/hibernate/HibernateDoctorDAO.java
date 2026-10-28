
package hospital.phase3.hibernate;

import hospital.phase3.db.DB;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateDoctorDAO {

    private static final SessionFactory factory =
            new Configuration()

            .setProperty(
                "hibernate.connection.driver_class",
                "com.mysql.cj.jdbc.Driver"
            )

            .setProperty(
                "hibernate.connection.url",
                DB.value("db.url")
            )

            .setProperty(
                "hibernate.connection.username",
                DB.value("db.user")
            )

            .setProperty(
                "hibernate.connection.password",
                DB.value("db.password")
            )

            .setProperty(
                "hibernate.dialect",
                "org.hibernate.dialect.MySQL8Dialect"
            )

            .setProperty(
                "hibernate.hbm2ddl.auto",
                "validate"
            )

            .addAnnotatedClass(DoctorEntity.class)
            .buildSessionFactory();

    public List<DoctorEntity> getAllDoctors() {

        try (Session session = factory.openSession()) {

            return session.createQuery(
                "FROM DoctorEntity ORDER BY doctorId",
                DoctorEntity.class
            ).list();
        }
    }
}
