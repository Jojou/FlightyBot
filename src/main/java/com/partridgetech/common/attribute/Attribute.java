package com.partridgetech.common.attribute;


/**
 * Attributes comprising the bots.
 */
public enum Attribute {

    GENERATION("generation"),
    NAME("name"),
    TIME("time"),
    VIEW("view"),
    ENERGY("energy"),
    MASTER("master"),
    SLAVES("slaves"),
    INTENTION("intention"),
    DEFAULT_MOVE("defaultmove"),
    COUNTDOWN_TO_DEFAULT_MOVE_CHANGE("countdowntodefaultmovechange"),
    MOVE_HISTORY("movehistory"),
    TIME_CREATED("timecreated"),
    ORBITPOSITION("orbitposition"),
    TIME_LIMIT("apocalypse"),
    ROUND("round"),
    MAX_SLAVES("maxslaves"),
    SIZE("size"),
    DIRECTION("direction"),
    TEXT("text"),
    MAP_MEMORY("mapmemory"),
    VIEW_DIMENSION("viewdimension"),
    TARGET_CELL("targetcell"),
    TARGET_COOLDOWN("targetcooldown"),
    TIME_ELAPSED("timeelapsed");

    private final String name;

    /**
     * Constructor.
     *
     * @param name
     */
    Attribute(final String name) {
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
