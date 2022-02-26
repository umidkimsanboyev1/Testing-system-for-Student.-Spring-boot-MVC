package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.entity.organization.Organization;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {


    Organization findOrganizationByIdAndDeletedFalseOrderByIdAsc(Long id);

    List<Organization> findAllByDeletedFalse();


    @Transactional
    @Modifying
    @Query(value = "Update Organization o SET o.deleted = true WHERE o.id=:id")
    void deleteOrganization(Long id);


//    @Transactional
//    @Modifying
//    @Query(value = "Update Organization o SET o.name =:dto. WHERE o.id=:id")
//    void updateOrganization(OrganizationUpdateDto dto);
}
