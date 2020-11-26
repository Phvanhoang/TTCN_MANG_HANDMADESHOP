package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.NguoiDung;

@Repository
public interface NguoiDungRepository extends CrudRepository<NguoiDung, Long> {

}
