package com.learning.a1blockaway;

import java.util.ArrayList;

public class ObservableCollection {
    private ArrayList<Request> mData = new ArrayList<>();
    private ChangeListener mListener;
    public enum Operation {ADD, REMOVE, NONE};
    public Operation operation = Operation.NONE;
    public int index;


    public void add(Request r){
        mData.add(r);
        operation = Operation.ADD;
        mListener.onChange();
    }

    public void remove(int index){
        mData.remove(index);
        operation = Operation.REMOVE;
        this.index = index;
        mListener.onChange();
    }

    public Request get(int index){
        return mData.get(index);
    }

    public int size(){
        return mData.size();
    }

    public void setListener(ChangeListener listener){
        this.mListener = listener;
    }

    public interface ChangeListener{
        void onChange();
    }
}
