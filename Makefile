.PHONY: *.run

build:
	mkdir -p $@

%.run: %
	java -cp build $< -- $(filter-out $@, $(MAKECMDGOALS))

%:: %.java build
	javac -d build $<