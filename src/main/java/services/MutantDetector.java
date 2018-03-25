package services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import entities.Dna;
import entities.ForbiddenException;
import entities.ResourceNotFoundException;

@RestController
@RequestMapping("/mutant")
public class MutantDetector {
    
	@RequestMapping(value = "/validate", method =  RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
    public void validate(@RequestBody(required=false) Dna dna) {
		if (dna == null || dna.getDna().isEmpty()) {
			throw new ResourceNotFoundException();
		}
        if (!Detector.instance().isMutant(dna.getDna().toArray(new String[dna.getDna().size()]))) {
        	throw new ForbiddenException(); 
        }
    }
	
	@RequestMapping(value="test", method = RequestMethod.GET)
	public ResponseEntity<Dna> test() {
		Dna dna = new Dna();
		List<String> dd = Arrays.asList(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"});
		dna.setDna(dd);
		return new ResponseEntity<Dna>(dna, HttpStatus.OK);
	}
	
	@RequestMapping(value="dna/{dna}/cant/{cant}", method = RequestMethod.GET)
	public ResponseEntity<String> defineDnaSecuense(@PathVariable("dna") String dna, @PathVariable("cant") Integer cant) {
		Detector.instance().defineDna(dna);
		Detector.instance().defineDnaRepited(cant);
		return new ResponseEntity<String>(getInformationDetector(), HttpStatus.OK);
	}
	
	@RequestMapping(value="cant/{cant}", method = RequestMethod.GET)
	public ResponseEntity<String> defineCantSecuense(@PathVariable("cant") int cant) {
		Detector.instance().defineCantSecuense(cant);
		return new ResponseEntity<String>(getInformationDetector(), HttpStatus.OK);
	}
	
	@RequestMapping(value="info", method = RequestMethod.GET)
	public ResponseEntity<String> defineCantSecuense() {
		return new ResponseEntity<String>(getInformationDetector(), HttpStatus.OK);
	}
	
	private String getInformationDetector() {
		return  "{'Secuense':" + Detector.instance().secuense() +
				", 'DnaRepetitions':" + Detector.instance().repetitions() +
				", 'CantValid':" + Detector.instance().cant().toString() + "}";
	}
}
