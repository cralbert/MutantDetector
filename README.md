# MutantDetector
Rest service para deteccion de mutantes

# URL:
http://mutantdetector.us-east-2.elasticbeanstalk.com

# Servicios

	http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/info

Metodo GET que devuelve informacion de la configuracion del detector de mutantes
 
Respuesta: {'Secuense':{secuencia}, 'DnaRepetitions':{repeticiones}, 'CantValid':{cantidad}}
Donde:
	secuencia: Es la secuencia de ADN aceptada para considerar un humano como mutante, por ej "ABDC", por default es ATGC
	repeticiones: Cantidad de repeticiones de la secuencia de adn para considerar a un humano como mutante. Por default es 4
	cantidad: Cantidad de secuencia de adn que se tienen que presentar para considerar un humano como mutante
	
	http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/validate

Metodo POST que valida si una secuencia de adn contiene adn mutante. Debe definirse un json en el body del request. La secuencia debe ser de NxN
Ej
{
"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}


	http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/dna/{secuense}/cant/{cant}

Metodo GET que configura la secuencia de adn valida y la cantidad de repeticiones que debe contener para considerar un humano como mutante
Donde: 	secuense: Secuencia de adn, por ej AFB
		cant: cantidad de repeticiones de adn
		
ej: http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/dna/ATGC/cant/3

	http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/cant/{cant}
Metodo GET que configura la cantidad de secuencias que se deben dar en el adn para que un humano sea considerado un mutante
Donde: cant: es la cantidad de secuencias.

Ej: http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/cant/4

# PENDIENTE:

Almacenamiento de adn de los humanos registrados. Solo se crearon las entidades basicas (bean, repository, service)

