package com.example.simpfitsu.classes;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static com.example.simpfitsu.MainMenu.current;
public class storyManager implements Iterable<Story> {

    private static List<Story>  storyList= new ArrayList<>();
    String[] options = {"option1", "option2", "option3", "option4"};
    public storyManager(){
        storyList.add(new Story("story1",options));
        storyList.add(new Story("story2",options));
        storyList.add(new Story("story3",options));
        storyList.add(new Story("story4",options));
        storyList.add(new Story("story5",options));
    }
    public void shuffle(){
        Collections.shuffle(storyList); //Shuffles the story
    }
    public void startGame()
    {
       shuffle();
       discard();
       draw();
    }
    public void discard() {
        if(isEmpty()==false){
            storyList.remove(0);
        }
    }
    public List<Story> getStory() {
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
    public Iterator<Story> iterator() {
        return storyList.iterator();
    }
}
