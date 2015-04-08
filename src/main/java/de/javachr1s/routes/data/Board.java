/**
 * 
 */
package de.javachr1s.routes.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Chris
 *
 */
public class Board {

	private double x,y;
	
	@Autowired
	private List<Location> locations;
	
	public Board(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public List<Location> getLocations() {
		return locations;
	}
}
