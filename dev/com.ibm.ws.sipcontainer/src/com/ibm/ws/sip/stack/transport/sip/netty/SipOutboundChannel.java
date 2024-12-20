/*******************************************************************************
 * Copyright (c) 2008, 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.ws.sip.stack.transport.sip.netty;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.ibm.websphere.channelfw.ChannelData;
import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;
import com.ibm.ws.sip.stack.transport.GenericEndpointImpl;
import com.ibm.wsspi.channelfw.ChannelFramework;
import com.ibm.wsspi.channelfw.exception.ChannelException;

/**
 * base class for outbound channels of any transport
 * 
 * @author ran
 */
public abstract class SipOutboundChannel 
{
	/** class logger */
	 
	protected static final TraceComponent tc = Tr.register(SipOutboundChannel.class);
	
	
	private ChannelData m_config = null;

	/**
	 * constructor
	 */
	public SipOutboundChannel(ChannelData config) {
		m_config = config;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.wsspi.channelfw.Channel#start()
	 */
	public void start() throws ChannelException {
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.wsspi.channelfw.Channel#stop(long)
	 */
	public void stop(long millisec) throws ChannelException {
		if (TraceComponent.isAnyTracingEnabled() && tc.isEventEnabled()) {
            Tr.event(tc, "Stop channel: " + this + " time=" + millisec);
        }
	  
	    signalNoConnections();
	}

	/**
     * Send an event to the channel framework that there are no more active
     * connections on this quiesced channel instance. This will allow an early
     * final chain stop instead of waiting the full quiesce timeout length.
     */
    private void signalNoConnections() {    	
    	EventAdmin engine = GenericEndpointImpl.getEventAdmin(); 
    	 Map<String, Object> eventProps = new HashMap<String, Object>(1);
         eventProps.put(ChannelFramework.EVENT_CHANNELNAME, m_config.getExternalName());
         
         if (engine != null) {
        	 Event event = new Event(ChannelFramework.EVENT_STOPCHAIN.toString(), eventProps);
             engine.postEvent(event);
         }
    }
    
	/**
	 * @see com.ibm.wsspi.channelfw.Channel#init()
	 */
	public void init() throws ChannelException {
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.wsspi.channelfw.Channel#destroy()
	 */
	public void destroy() throws ChannelException {
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.wsspi.channelfw.Channel#update(com.ibm.websphere.channelfw.ChannelData)
	 */
	public void update(ChannelData cc) {
		//super.setConfig(channelData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.wsspi.channelfw.Channel#getApplicationInterface()
	 */
	public Class getApplicationInterface() {
	// there is no application interface
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.wsspi.channelfw.OutboundChannel#getApplicationAddress()
	 */
	public Class[] getApplicationAddress() {
		return null;
	}
	
	 /*
     * @see com.ibm.wsspi.channelfw.Channel#getName()
     */
    public String getName() {
        return m_config.getName();
    }
}
