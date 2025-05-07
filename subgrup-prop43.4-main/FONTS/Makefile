CLASS_INPUT =   ./Src/Domini/Classes/*.java \
                ./Src/Domini/Controladors/*.java \
                ./Src/Persistencia/Classes/*.java \
                ./Src/Persistencia/Controladors/*.java \
                ./Src/Presentacio/Vistes/*.java \
                ./Src/Presentacio/Controladors/*.java \
                ./Src/Drivers/*.java

CLASS_OUTPUT =	../EXE/out

JAR_OUTPUT =    ../EXE/out/jars

PROGRAM_OUTPUT = 	../EXE/

LIBS =	./Libs/junit-4.13.2.jar:./Libs/hamcrest-core.1.3.jar

JUNIT_TESTS =   ./Src/Tests/*.java

all:
	javac --release 11 -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	javac --release 11 -cp $(LIBS) -d $(CLASS_OUTPUT) $(CLASS_INPUT) $(JUNIT_TESTS)

jars:
	javac --release 11 -d $(CLASS_OUTPUT) $(CLASS_INPUT) ./Src/Drivers/*.java
	jar cmvf ./Src/Drivers/DriverCtrlDomini.mf $(JAR_OUTPUT)DriverCtrlDomini.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./Src/Drivers/DriverCtrlAlgorisme.mf $(JAR_OUTPUT)DriverCtrlAlgorisme.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./Src/Drivers/DriverCtrlPersistencia.mf $(JAR_OUTPUT)DriverCtrlPersistencia.jar -C $(CLASS_OUTPUT) .
	jar cmvf ./Src/Drivers/DriverCtrlPresentacio.mf $(JAR_OUTPUT)DriverCtrlPresentacio.jar -C $(CLASS_OUTPUT) .

compilaPrograma:
	javac --release 11 -cp $(LIBS) -d $(CLASS_OUTPUT) $(CLASS_INPUT)
	jar cmvf ./Src/Drivers/DriverCtrlPresentacio.mf $(JAR_OUTPUT)PROP43.4.jar -C $(CLASS_OUTPUT) .
	java -jar $(PROGRAM_OUTPUT)PROP43.4.jar

executaPrograma:
	java -jar $(PROGRAM_OUTPUT)PROP43.4.jar


executaDriverCtrlDomini: jars
	java -jar $(JAR_OUTPUT)DriverCtrlDomini.jar

executaDriverCtrlAlgorisme: jars
	java -jar $(JAR_OUTPUT)DriverCtrlAlgorisme.jar

executaDriverCtrlPersistencia: jars
	java -jar $(JAR_OUTPUT)DriverCtrlPersistencia.jar

executaDriverCtrlPresentacio: jars
	java -jar $(JAR_OUTPUT)DriverCtrlPresentacio.jar

clean:
	rm -r ../EXE/out/*

distclean:
	rm -r ../EXE/*

