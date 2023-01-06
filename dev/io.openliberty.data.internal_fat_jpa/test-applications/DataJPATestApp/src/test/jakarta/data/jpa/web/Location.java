/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package test.jakarta.data.jpa.web;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

/**
 *
 */
@Embeddable
public class Location {

    @Embedded
    public Address address;

    @Column(columnDefinition = "DECIMAL(8,5) NOT NULL")
    public float latitude;

    @Column(columnDefinition = "DECIMAL(8,5) NOT NULL")
    public float longitude;

    public Location() {
    }

    Location(float latitude, float longitude, Address address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    @Override
    public String toString() {
        return address + " | " + latitude + ", " + longitude;
    }
}