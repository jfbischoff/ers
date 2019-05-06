/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2016 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.vsp.ers.bev.testscenario;/*
 * created by jbischoff, 09.10.2018
 */

import com.google.common.collect.ImmutableList;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.contrib.ev.EvUnits;
import org.matsim.contrib.ev.data.Charger;
import org.matsim.contrib.ev.data.ChargerImpl;
import org.matsim.contrib.ev.data.file.ChargerWriter;
import org.matsim.contrib.ev.fleet.ElectricFleetWriter;
import org.matsim.contrib.ev.fleet.ElectricVehicle;
import org.matsim.contrib.ev.fleet.ElectricVehicleSpecification;
import org.matsim.contrib.ev.fleet.ImmutableElectricVehicleSpecification;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateChargers {

    public static void main(String[] args) {
        String folder = "D:/ers/ev-test/";

        Network network = NetworkUtils.createNetwork();
        new MatsimNetworkReader(network).readFile(folder + "network-osm.xml.gz");


        Charger charger = new ChargerImpl(Id.create(113273 + "charger", Charger.class), EvUnits.W_PER_kW * 50, 2, network.getLinks().get(Id.createLinkId(113273)), network.getLinks().get(Id.createLinkId(113273)).getCoord(), "fast");
        Charger charger2 = new ChargerImpl(Id.create(74836 + "charger", Charger.class), EvUnits.W_PER_kW * 50, 2, network.getLinks().get(Id.createLinkId(74836)), network.getLinks().get(Id.createLinkId(74836)).getCoord(), "fast");
        Charger chargert = new ChargerImpl(Id.create(113273 + "truckcharger", Charger.class), EvUnits.W_PER_kW * 200, 2, network.getLinks().get(Id.createLinkId(113273)), network.getLinks().get(Id.createLinkId(113273)).getCoord(), "truck");
        Charger chargert2 = new ChargerImpl(Id.create(74836 + "truckcharger", Charger.class), EvUnits.W_PER_kW * 200, 2, network.getLinks().get(Id.createLinkId(74836)), network.getLinks().get(Id.createLinkId(74836)).getCoord(), "truck");
        List<Charger> chargers = new ArrayList<>();
        chargers.add(charger);
        chargers.add(charger2);
        chargers.add(chargert);
        chargers.add(chargert2);
        new ChargerWriter(chargers).write(folder + "test-chargers.xml");
        ImmutableList<String> chargingTypes = ImmutableList.<String>builder().add("fast").add("slow").build();
        ElectricVehicleSpecification ev = ImmutableElectricVehicleSpecification.newBuilder().id(Id.create("testEV1", ElectricVehicle.class)).batteryCapacity(30 * EvUnits.J_PER_kWh).initialSoc(30 * EvUnits.J_PER_kWh).chargerTypes(chargingTypes).vehicleType("car").build();
        new ElectricFleetWriter(Collections.singletonList(ev).stream()).write(folder + "test_evs.xml");

    }
}
