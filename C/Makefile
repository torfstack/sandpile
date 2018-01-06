CXX = g++
CFLAGS = -O3 -g
PNGFLAGS = `libpng-config --cflags --ldflags`

all:
	$(CXX) src/sandpile.cpp -o bin/sandpile $(CFLAGS) $(PNGFLAGS)
clean:
	rm bin/*