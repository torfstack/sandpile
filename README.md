# Fractal Generation using Sandpiles

A sandpile is a (N x N) grid, filled with non-negative integer values. 
A *normalized* sandpile contains no values greater than 3. If a cell contains a value greater than 3, the value 4 is subtracted from it and the value of all neighboring cells is incremented by 1.

```
030    040    202    212
343 -> 404 -> 040 -> 101
030    040    202    212
```

### Using the java implementation
Build Main.java
```
javac Java/src/Main.java
```
Run it
```
java Java/src/Main --dimension <dim> --sand <pile>
```
with
```
<dim> : image output will have size <dim>x<dim> (Java/fractal.png)
<pile> : start value of center cell, all other cells are initialized to 0
```

### Using the python implementation
Requires numpy and scipy.misc
