package cl.usach.tvreactions.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.usach.tvreactions.entities.Tvshow;

public interface TvshowRepository extends PagingAndSortingRepository<Tvshow, Integer>{

}
