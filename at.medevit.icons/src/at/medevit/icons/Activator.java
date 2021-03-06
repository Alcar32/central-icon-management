/*******************************************************************************
 * Copyright (c) 2012 Marco Descher.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Marco Descher - initial API and implementation
 ******************************************************************************/
package at.medevit.icons;

import org.eclipse.osgi.framework.log.FrameworkLog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	public static final String BUNDLE_ID = "at.medevit.icons";
	
	static private Activator defaultInstance;

	private ServiceTracker<?, ?> logTracker = null;
	private BundleContext context;

	public Activator() {
		defaultInstance = this;
	}

	public static Activator getDefault() {
		return defaultInstance;
	}

	/*
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
	}

	/*
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		if (logTracker != null) {
			logTracker.close();
			logTracker = null;
		}

		context = null;
	}

	/**
	 * provide access to current bundle for resource loading purposes
	 */
	public Bundle getBundle() {
		return context.getBundle();
	}

	public FrameworkLog getFrameworkLog() {
		if (logTracker == null) {
			if (context == null)
				return null;
			logTracker = new ServiceTracker<Object, Object>(context,
					FrameworkLog.class.getName(), null);
			logTracker.open();
		}
		return (FrameworkLog) logTracker.getService();
	}
}
