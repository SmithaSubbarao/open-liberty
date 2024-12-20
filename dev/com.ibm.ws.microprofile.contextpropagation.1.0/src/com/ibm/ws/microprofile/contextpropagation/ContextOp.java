/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.microprofile.contextpropagation;

import com.ibm.websphere.ras.annotation.Trivial;

/**
 * Describes an operation that is performed with regard to establishing context on a thread.
 */
@Trivial
public enum ContextOp {
    /**
     * Thread context of the specified type is cleared from the thread of execution
     * before performing the action/task.
     */
    CLEARED,

    /**
     * Thread context of the specified type is captured from the requesting thread
     * and propagated to the thread of execution before performing the action/task.
     */
    PROPAGATED,

    /**
     * Thread context of the specified type is ignored and left unchanged.
     */
    UNCHANGED
}
