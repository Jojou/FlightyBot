/*

 */
package com.partridgetech.common;

import java.util.Arrays;

/**
 * Constants for bots intentions.
 *
 */
public enum FlightyIntention {

    RapidExpansion(true), SafelyFloat(true), Scout(false), Attack(false), HeadHome(false);

    private final boolean isMaster;

    FlightyIntention(final boolean isTheMaster) {
        this.isMaster = isTheMaster;
    }

    public boolean getIsMaster() {
        return this.isMaster;
    }

    public static FlightyIntention getIntention(String input) {
        return Arrays.stream(FlightyIntention.values()).filter(intention -> intention.toString()
                .equalsIgnoreCase(input)).findAny().orElseThrow(IllegalStateException::new);
    }

}
