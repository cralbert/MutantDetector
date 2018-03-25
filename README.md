# MutantDetector
Rest service para deteccion de mutantes
El servicio permite validar si una secuencia de adn en formato json corresponde con un mutante. Ademas se ofrecen otros servicios para configurar el detector de adn mutante, permitiendo indicar que codigo adn es considerado mutante, que cantidad de ese codigo se tiene que repetir para que sea considerado y cantidad de esas secuencias.

# URL:
http://mutantdetector.us-east-2.elasticbeanstalk.com

# Servicios

	http://mutantdetector.us-east-2.elasticbeanstalk.com/rest/mutant/info

Metodo GET que devuelve informacion de la configuracion del detector de mutantes
 
Respuesta: {'Secuense':{secuencia}, 'DnaRepetitions':{repeticiones}, 'CantValid':{cantidad}}
Donde:
	secuencia: Es la secuencia de codigo adn es considerado como mutante, por ej "XY", por defauld es ATGC. Es decir, si se define como mutante el codigo XY, y una secuencia de adn contiene ese codigo, por ej ABFYG, es considerado mutante.
	repeticiones: Cantidad de repeticiones de la secuencia de adn para considerar a un humano como mutante. Por defauld es 4. Es decir, un codigo ABYYYYC es considerado mutante, pero ABYYYC no lo es
	cantidad: Cantidad de secuencia minima de adn que se tienen que presentar para considerar un humano como mutante.
	
Ej Si se define secuencia: XY, cantidad de repeticiones 4, y secuencia minima es 3. La siguiente secuencia es considerada mutante
{
"dna":["ATXXXA","CAGTGC","TTATGT","AGAYYY","CCCCTA","XXXCTG"]
}
Pero la siguiente secuencia no es considerada mutante, ya que no contiene los codigos X o Y
{
"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
	
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

# Consideraciones

El servicio fue testeado con POSTMAN

