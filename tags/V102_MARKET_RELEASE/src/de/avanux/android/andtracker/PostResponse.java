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
package de.avanux.android.andtracker;

import java.io.IOException;

public class PostResponse extends PropertiesStringParser {

    private static final String TAG = "LiveTracker:PostResponse";
    
    public PostResponse(String propertiesString) throws IOException {
        super(propertiesString);
    }
    
    public long getMinTimeInterval() {
        return Long.parseLong(getProperties().getProperty(ConfigurationConstants.MIN_TIME_INTERVAL));
    }
    
    public int getTrackerCount() {
        return Integer.parseInt(getProperties().getProperty("trackerCount"));
    }
    
}
