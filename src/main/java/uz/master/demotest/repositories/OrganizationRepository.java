package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.organization.Organization;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findOrganizationById(Long id);

    //List<Organization> findOrganizationsByCreatedAtExists ();

    Organization findOrganizationByName(String name);

}
