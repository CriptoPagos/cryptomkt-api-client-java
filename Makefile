JC = javac
EJEMPLOS_CLASSES = classes
JFLAGS = -g
EJEMPLO_DIR = 1_publicos
EJEMPLO = Ejemplo_001_mercados

all:
	mkdir -p build
	$(JC) $(JFLAGS) -classpath lib/json-simple-1.1.1.jar:. src/cl/criptopagos/cryptomkt/*.java src/cl/criptopagos/cryptomkt/Client/*.java -d build
	cd build && jar -cf cryptomkt.jar *
	mv build/cryptomkt.jar lib

test: all
	mkdir -p $(EJEMPLOS_CLASSES)
	$(JC) $(JFLAGS) -classpath lib/json-simple-1.1.1.jar:lib/cryptomkt.jar:. ejemplos/$(EJEMPLO_DIR)/$(EJEMPLO).java -d $(EJEMPLOS_CLASSES)
	java -cp $(EJEMPLOS_CLASSES):lib/json-simple-1.1.1.jar:lib/cryptomkt.jar $(EJEMPLO)

clean:
	rm -rf $(EJEMPLOS_CLASSES) build lib/cryptomkt.jar
