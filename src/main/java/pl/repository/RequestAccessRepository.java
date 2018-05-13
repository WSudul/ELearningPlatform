package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.model.RequestAccess;

import java.util.List;

@Repository
public interface RequestAccessRepository extends JpaRepository<RequestAccess, Long> {

    List<RequestAccess> findAllByUseridAndSubjectid(Long userid, Long subjectid);

    List<RequestAccess> findAllByRoleidAndSubjectid(Long roleid, Long subjectid);

    @Modifying
    @Transactional
    @Query("UPDATE RequestAccess SET roleid = 201 WHERE idaccess = ?1")
    void removeUserFromSubject(Long idaccess);
}
