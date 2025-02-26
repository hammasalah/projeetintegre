package com.example.projetjavafx.root.organizer;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class EventStats {
    private final IntegerProperty eventId;
    private final StringProperty eventName;
    private final IntegerProperty totalParticipants;
    private final IntegerProperty maleCount;
    private final IntegerProperty femaleCount;

    public EventStats(int eventId, String eventName, int total, int male, int female) {
        this.eventId = new SimpleIntegerProperty(eventId);
        this.eventName = new SimpleStringProperty(eventName);
        this.totalParticipants = new SimpleIntegerProperty(total);
        this.maleCount = new SimpleIntegerProperty(male);
        this.femaleCount = new SimpleIntegerProperty(female);
    }

    // Property getters
    public StringProperty eventNameProperty() { return eventName; }
    public IntegerProperty totalParticipantsProperty() { return totalParticipants; }
    public IntegerProperty maleCountProperty() { return maleCount; }
    public IntegerProperty femaleCountProperty() { return femaleCount; }

    // Standard getters
    public int getTotalParticipants() { return totalParticipants.get(); }
    public int getMaleCount() { return maleCount.get(); }
    public int getFemaleCount() { return femaleCount.get(); }

    @Override
    public String toString() {
        return eventName.get();
    }
}
