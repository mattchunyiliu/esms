package kg.kundoluk.school.model.audit;


import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.security.jwt.JwtUser;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AppRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {


        RevEntity revision = (RevEntity) o;

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        revision.setAuditorId(jwtUser.getId());

    }
}
