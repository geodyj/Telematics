/**
 * 
 */
package top.geomatics.toast.internal.backend.emergency;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.geomatics.toast.core.ICoreConstants;
import top.geomatics.toast.core.LogUtility;
import top.geomatics.toast.core.emergency.IEmergencyCenter;
import top.geomatics.toast.core.emergency.IEmergencyConstants;

/**
 * @author whudyj
 *
 */
public class EmergencyServlet extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = getParameter(request, response, ICoreConstants.ID_PARAMETER);
		int latitude = Integer.parseInt(getParameter(request, response, IEmergencyConstants.LATITUDE_PARAMETER));
		int longitude = Integer.parseInt(getParameter(request, response, IEmergencyConstants.LONGITUDE_PARAMETER));
		int heading = Integer.parseInt(getParameter(request, response, IEmergencyConstants.HEADING_PARAMETER));
		int speed = Integer.parseInt(getParameter(request, response, IEmergencyConstants.SPEED_PARAMETER));
		double lat = latitude /100000.0;
		double lon = longitude /100000.0;
		
		
		LogUtility.logInfo(this,"Emergency: " + id + " (" + lat + "N, " + lon + "E) " + heading + "deg " + speed + "kph");
		PrintWriter writer = response.getWriter();
		writer.print("Help is on its way !");
		response.setContentType(ICoreConstants.CONTENT_TYPE_PLAIN);
	}

	private String getParameter(HttpServletRequest request, HttpServletResponse response, String parameter) throws IOException,ServletException {
		String value = request.getParameter(parameter);
		if (value == null || value.length() == 0)
		{
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, ICoreConstants.MISSING_PARAMETER + parameter);
			throw new ServletException(ICoreConstants.MISSING_PARAMETER + parameter);
		}
			
		return value;
	}
}
