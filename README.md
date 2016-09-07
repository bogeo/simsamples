# Dynamic simulation model examples
These Java classes realize some simple dynamic simulation models. 

Software requirements: Java Development Kit 1.6 or higher.
The framework is kept simple and does not require any third-party components.

## Basic modeling approaches
* Difference equation based simulation
* Cellular Automata referring to discrete state-sets
* Cellular elevation grid (still experimental)
* Simple circuit analysis (node voltage analysis)
* Simple agent-based evolutionary example
* Discrete event-based simulation (event queues) 

## Example applications
Find the executable samples in the package de.hsbo.geo.simsamples.applications, e.g.

* Simulation of simple physical systems described by differential equations (numerical solution): Bouncing ball, Mass-spring-damper system;
* Simple model showing demographic development (population pyramid) controlled by birth and mortality rates;
* Simple predator-prey system (Lotka-Volterra model);
* Simple population dynamics (Verhulst model and a cellular population development model); 
* "Game of Life" as prominent Cellular Automaton;
* Cellular elevation grid examples (evolutionary optimum finding algorithm, simple erosion model);
* Simple transient circuit simulation (step response if a resistor-capacitor-element).

## Visualization 
Simulation results will be shown in the command-line. Time series can easily be copied to the clipboard and thus be pasted to Excel or other applications. For some of the applications, interactive X3DOM visualizations can be generated. 

## License information
See the 'LICENSE'-file.

## Contact
Benno Schmidt, Bochum University of Applied Sciences, Department of Geodesy, Lennershofstr. 140, D-44801 Bochum
