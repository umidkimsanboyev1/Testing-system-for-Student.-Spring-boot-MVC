package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.entity.organization.Organization;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

//    Organization findOrganizationByIdAndDeletedNot(Long id);

    List<Organization> findAllByDeletedNot(Boolean deleted);

//    Organization findOrganizationByNameAndDeletedNot(String name);


    @Transactional
    @Modifying
    @Query(value = "Update Organization o SET o.deleted = true WHERE o.id=:id")
    void deleteOrganization(Long id);
}
