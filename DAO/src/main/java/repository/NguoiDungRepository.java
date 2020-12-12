package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import model.NguoiDung;

@Repository
public interface NguoiDungRepository extends PagingAndSortingRepository<NguoiDung, Long> {

	Page<NguoiDung> findByIsDeletedFalse(Pageable pageable);

}
