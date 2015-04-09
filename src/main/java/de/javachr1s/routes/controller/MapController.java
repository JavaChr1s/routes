package de.javachr1s.routes.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import de.javachr1s.routes.data.Board;
import de.javachr1s.routes.data.Location;
import de.javachr1s.routes.util.RouteUtil;

/**
 * @author Chris
 *
 */
public class MapController extends GenericForwardComposer<Div> {
	
	private Button start;
	
	public void doAfterCompose(final Div div) throws Exception {
		final Board board = (Board) SpringUtil.getBean("board");
		
		final double boardX = board.getX();
		final double boardY = board.getY();
		
		final List<Location> connectedLocations = new ArrayList<Location>();
		
		for(final Location location : board.getLocations()) {
			final double xPercent = 100 / boardX * location.getX();
			final double yPercent = 100 / boardY * location.getY();
			
			final Hbox locationContainer = new Hbox();
			locationContainer.setSclass("location");
			locationContainer.setLeft(xPercent + "%");
			locationContainer.setTop(yPercent + "%");
			final Button button = new Button(" ");
			button.addEventListener(Events.ON_CLICK, new MyEventListener(location));
			
			
			locationContainer.appendChild(button);
			
			for(final Location connection : location.getConnections()) {
				if(!connectedLocations.contains(connection)) {
					final double connectionXPercent = 100 / boardX * connection.getX();
					final double connectionYPercent = 100 / boardY * connection.getY();
					Clients.evalJavaScript(String.format("drawLine(%s, %s, %s, %s)", xPercent, yPercent, connectionXPercent, connectionYPercent));				
				}
			}
			connectedLocations.add(location);
			
			final Label label = new Label(location.getName());			
			label.setSclass("label");
			locationContainer.appendChild(label);
			
			div.appendChild(locationContainer);
		}
	}
	
	private class MyEventListener implements EventListener {

		private Location location;
		
		public MyEventListener(final Location location) {
			this.location = location;
		}
		
		@Override
		public void onEvent(final Event event) throws Exception {
			if(start == null) {
				start = (Button) event.getTarget();
				start.setAttribute("location", location);
				start.setSclass("start");
			} else {
				calculatePath((Location) start.getAttribute("location"), location);
				start.setSclass("");
				start = null;
			}
		}

		private void calculatePath(final Location start, final Location destination) {
			final List<Location> route = RouteUtil.calculateShortestRoute(start, destination);
			Messagebox.show(String.format("Route:\n%s\n\nSteps: %s", route.toString(), route.size()));
		}
		
	}
}
