package repositories;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import model.DnaInfo;

@EnableScan
public interface DnaInfoRepository extends CrudRepository<DnaInfo, String> {

	List<DnaInfo> findById(String id);
	
	List<DnaInfo> findByDna(String dna);
}
