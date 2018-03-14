/**
 * 
 */
package top.geomatics.toast.internal.backend.emergency.bundle;

import org.osgi.service.http.HttpService;

import top.geomatics.toast.core.ICoreConstants;
import top.geomatics.toast.core.LogUtility;
import top.geomatics.toast.core.PropertyManager;
import top.geomatics.toast.core.UrlBuilder;
import top.geomatics.toast.core.emergency.IEmergencyCenter;
import top.geomatics.toast.core.emergency.IEmergencyConstants;
import top.geomatics.toast.internal.backend.emergency.EmergencyServlet;

/**
 * @author whudyj
 *
 */
public class Component {
	private HttpService http;
	private String servletAlias;


	public void setHttp(HttpService value) {
		http = value;
	}

	protected void shutdown() {
		http.unregister(servletAlias);
	}

	protected void startup() {
		try {
			String servletRoot = PropertyManager.getProperty(ICoreConstants.BACK_END_URL_PROPERTY,
					ICoreConstants.BACK_END_URL_DEFAULT);
			UrlBuilder urlBuilder = new UrlBuilder(servletRoot);
			urlBuilder.appendPath(IEmergencyConstants.EMERGENCY_FUNCTION);
			servletAlias = urlBuilder.getPath();

			EmergencyServlet servlet = new EmergencyServlet();
			http.registerServlet(servletAlias, servlet, null, null);
			LogUtility.logDebug(this, "Registered EmergencyServlet at " + servletAlias);
		} catch (Exception e) {
			LogUtility.logError(this, "Error registering servlet with HttpService", e);
		}
	}
}
