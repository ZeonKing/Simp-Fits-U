package com.example.simpfitsu.classes;

public class Story {
    private String story;
    private String option[];

    public Story(String story, String[] option) {
        this.story = story;
        this.option = option;

    }

    public String getStory() {
        return story;
    }

    public String[] getOptions() {
        return option;
    }
}
