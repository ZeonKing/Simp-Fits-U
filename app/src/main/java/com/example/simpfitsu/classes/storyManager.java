package com.example.simpfitsu.classes;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static com.example.simpfitsu.MainMenu.current;
public class storyManager implements Iterable<String> {


    private static List<String>  storyList= new ArrayList<>();
    public storyManager(){

        storyList.add("story1");
        storyList.add("story2");
        storyList.add("story3");
        storyList.add("story4");
        storyList.add("story5");
    }
    public void shuffle(){
        Collections.shuffle(storyList); //Shuffles the story
    }
    public void startGame()
    {
       shuffle();
       draw();
       discard();

    }
    public void discard() {
        if(isEmpty()==false){
            storyList.remove(0);
        }
    }
    public List<String> getStory() {
        return storyList;
    }

    public boolean isEmpty()
    {
        if(storyList.isEmpty())
            return true;
        else return false;
    }
    public void draw(){
        if(isEmpty()==false)
            current=storyList.get(0);
    }


    @NonNull
    @Override
    public Iterator<String> iterator() {
        return storyList.iterator();
    }
}
