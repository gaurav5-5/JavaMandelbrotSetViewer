.PHONY: compile run

all: clean compile run

compile:
	mkdir -p bin && cd src && javac App.java -d ../bin

run:
	java -cp './bin' 'App'

clean:
	rm -rf bin/