/**
 * 
 */
package de.javachr1s.routes.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.javachr1s.routes.data.Location;

/**
 * @author Chris
 *
 */
public class RouteUtil {

	
	public static List<Location> calculateRoute(final Location start, final Location destination) {
		List<Location> route = calculateRouteInternal(start, destination, Integer.MAX_VALUE);
		
		Collections.reverse(route);
		return route;
	}
	
	public static List<Location> calculateShortestRoute(final Location start, final Location destination) {
		List<Location> route;
		List<Location> tmpRoute = null;
		int maxLength = Integer.MAX_VALUE;
		do {
			route = tmpRoute;
			tmpRoute = calculateRouteInternal(start, destination, maxLength);
			maxLength = tmpRoute.size() - 1;
		} while(!tmpRoute.isEmpty());

		Collections.reverse(route);
		return route;
	}

	private static List<Location> calculateRouteInternal(final Location start, final Location destination, final int maxLength) {
		final List<Location> route = new ArrayList<Location>();
		checkLocation(start, destination, route, new HashSet<Location>(), maxLength, 0);
		return route;
	}
	
	private static boolean checkLocation(final Location location, final Location destination, final List<Location> route, final Set<Location> checked, final int maxLength, final int length) {
		if(location.equals(destination)) {
			return true;
		} else {
			final Set<Location> connections = location.getConnections();
			for(final Location connection : connections) {
				if(checked.contains(connection)) {
					continue;
				}
				checked.add(connection);
				if(maxLength > length && checkLocation(connection, destination, route, checked, maxLength, length+1)) {
					route.add(connection);
					return true;
				}
				checked.remove(connection);
			}
		}
		return false;
	}
}
