/* Copyright (C) 2009  Axel Müller <axel.mueller@avanux.de> 
 * 
 * This file is part of LiveTracker.
 * 
 * LiveTracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LiveTracker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LiveTracker.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.avanux.livetracker.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.avanux.livetracker.ConfigurationConstants;
import de.avanux.livetracker.TrackingManager;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(AdminServlet.class);

    private Thread trackingManagerThread;
    
    private Thread loadManagerThread;
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        log.debug("Received new configuration.");
        String minTimeInterval = request.getParameter(ConfigurationConstants.MIN_TIME_INTERVAL);
        if (minTimeInterval != null) {
            Configuration.getInstance().setMinTimeInterval(Long.parseLong(minTimeInterval));
        }

        String minDistance = request.getParameter(ConfigurationConstants.MIN_DISTANCE);
        if (minDistance != null) {
            Configuration.getInstance().setMinDistance(Long.parseLong(minDistance));
        }

        Configuration.getInstance().setMessageToUsers(request.getParameter(ConfigurationConstants.MESSAGE_TO_USERS));

        String redirectTarget = request.getContextPath() + "/Admin.jsp";
        log.debug("Redirecting " + redirectTarget);
        response.sendRedirect(redirectTarget);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        log.debug("String tracking manager ...");
        this.trackingManagerThread = new Thread(new TrackingManager());
        this.trackingManagerThread.setName("TrackingManager");
        this.trackingManagerThread.start();
        log.debug("... tracking manager started.");
        log.debug("String load manager ...");
        this.loadManagerThread = new Thread(new LoadManager());
        this.loadManagerThread.setName("LoadManager");
        this.loadManagerThread.start();
        log.debug("... load manager started.");
    }

    @Override
    public void destroy() {
        super.destroy();
        log.debug("Interrupting tracking manager ...");
        this.trackingManagerThread.interrupt();
        log.debug("... tracking manager interrupted.");
        log.debug("Interrupting load manager ...");
        this.loadManagerThread.interrupt();
        log.debug("... load manager interrupted.");
    }
}
