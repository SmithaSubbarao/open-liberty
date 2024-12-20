/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package jsf.container.nojsf.web;

import javax.servlet.annotation.WebServlet;

import componenttest.app.FATServlet;
import jsf.container.somelib.SomeLibClass;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/TestServlet")
public class TestServlet extends FATServlet {

    public void testServletWorking() {
        System.out.println("Servlet is reachable");
    }

    public void useExternalLib() {
        new SomeLibClass().printClassloader();
    }
}
