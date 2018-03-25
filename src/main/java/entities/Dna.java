package entities;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Dna{

	public Dna() {}
	
	private List<String> dna = null;

	public List<String> getDna() {
		return dna;
	}

	public void setDna(List<String> dna) {
		this.dna = dna;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("dna", dna).toString();
	}
}
