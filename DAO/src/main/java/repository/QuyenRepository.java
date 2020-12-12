package repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.DacQuyen;

@Repository
public interface QuyenRepository extends CrudRepository<DacQuyen, Long>{

	ArrayList<DacQuyen> findByIsDeletedFalse();

}
