package net.mcbbs.cocoaui.pluginmessage.listener;

public enum Priority {
    LOW(1), MIDDLE(2), HIGH(3);
    private int value;

    private Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
