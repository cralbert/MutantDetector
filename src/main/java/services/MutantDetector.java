package services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import entities.Dna;
import entities.ForbiddenException;
import repositories.DnaInfoRepository;

@RestController
@RequestMapping("/mutant")
public class MutantDetector {
    
	@Autowired
    private DnaInfoRepository repository;
	
	@RequestMapping(value = "/validate", method =  RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
    public void validate(@RequestBody(required=false) Dna dna) {
        if (!new Detector().isMutant(dna.getDna().toArray(new String[dna.getDna().size()]))) {
        	throw new ForbiddenException(); 
        }
    }
	
	@RequestMapping(value="test", method = RequestMethod.GET)
	public Dna test() {
		Dna dna = new Dna();
		List<String> dd = Arrays.asList(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"});
		dna.setDna(dd);
		return dna;
	}
}
