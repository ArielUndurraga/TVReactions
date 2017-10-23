package cl.usach.tvreactions.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import cl.usach.tvreactions.entities.Admin;

public interface AdminRepository extends PagingAndSortingRepository<Admin, Integer>{

}
