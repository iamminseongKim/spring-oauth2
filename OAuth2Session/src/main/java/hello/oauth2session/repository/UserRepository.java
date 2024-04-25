package hello.oauth2session.repository;

import hello.oauth2session.dto.SiteType;
import hello.oauth2session.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndSiteType(String username, SiteType siteType);
}
