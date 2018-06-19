package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.model.Access;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findAllByUseridAndSubjectid(Long userid, Long subjectid);

    List<Access> findAllByRoleidAndSubjectid(Long roleid, Long subjectid);


    @Modifying
    @Transactional
    @Query("UPDATE Access SET roleid = 1 WHERE idaccess = ?1")
    public void removeUserFromSubject(Long idaccess);

}

