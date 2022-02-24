package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.organization.Organization;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
}
