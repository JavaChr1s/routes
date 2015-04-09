/**
 * 
 */
package de.javachr1s.routes.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

/**
 * @author Chris
 *
 */
public class Location {

	private double x,y;
	private String name;
	private Set<Location> connections = new HashSet<Location>();
	
	public Location(final double x, final double y, final String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}

	public Set<Location> getConnections() {
		return connections;
	}

	public void setConnections(final Set<Location> connections) {
		for(final Location location : connections) {
			location.addConnection(this);
		}
		this.connections.addAll(connections);
	}
	
	public void addConnection(final Location connection) {
		connections.add(connection);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
