/*

 */
package com.partridgetech.common;

import java.util.Arrays;

/**
 * Constants representing the types of messages that can be received.
 */
public enum InputHeader {

    Welcome, React, Goodbye, Default;

    InputHeader() {

    }

    public InputHeader getInputHeader(final String input) {
        return Arrays.stream(InputHeader.values()).filter(inputHead -> input.contains(inputHead.toString())).findFirst().get();
    }
}
