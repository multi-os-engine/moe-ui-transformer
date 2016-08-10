/*
Copyright 2014-2016 Intel Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.moe.transformer.Layouts;

import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;
import org.moe.transformer.LayoutInfo.eViewSizeType;
import org.moe.transformer.LayoutInfo.eGravity;
import java.util.ArrayList;
import java.util.List;

public class View {
    protected String id;
    protected eViewSizeType widthSizeType;
    protected eViewSizeType heightSizeType;
    protected Rect rect;
    protected int marginLeft;
    protected int marginRight;
    protected int marginTop;
    protected int marginBottom;
    protected View parent;
    protected eGravity gravity;

    protected List<Action> actionsList;
    public Action getAction(int i){ return actionsList.get(i); }
    public void addAction(Action action){
        actionsList.add(action);
    }

    protected List<Outlet> outletsList;
    public Outlet getOutlet(int i){ return outletsList.get(i); }
    public void addOutlet(Outlet outlet){ outletsList.add(outlet); }

    protected List<Segue> seguesList;
    public Segue getSegue(int i){ return seguesList.get(i); }
    public void addSegue(Segue segue){ seguesList.add(segue); }

    protected List<InternalConstraint> internalConstraintList;
    public InternalConstraint getInternalConstraint(int i){
        return internalConstraintList.get(i);
    }
    public void addInternalConstrain(InternalConstraint constrain){
        internalConstraintList.add(constrain);
    }

    public int getActionCount(){
        return actionsList.size();
    }
    public int getOutletCount(){
        return outletsList.size();
    }
    public int getSegueCount(){ return seguesList.size(); }
    public int getInternalConstraintCount(){
        return internalConstraintList.size();
    }


    public eGravity getGravity(){ return gravity; }
    public void setGravity(eGravity gravity){ this.gravity = gravity; }

    public void setGravity(String gravity){
        if(gravity != null) {
            if (gravity.equals("left")) {
                this.gravity = eGravity.left;
            } else if (gravity.equals("right")) {
                this.gravity = eGravity.right;
            } else if (gravity.equals("top")) {
                this.gravity = eGravity.top;
            } else if (gravity.equals("bottom")) {
                this.gravity = eGravity.bottom;
            } else if (gravity.equals("center")) {
                this.gravity = eGravity.center;
            }
        }
    }

    public View getParent(){
        return parent;
    }

    protected void setParent(View parent){
        this.parent = parent;
    }

    public View(Rect rect){
        this.id = OSXStringProcessor.CreateId();
        this.rect = rect;
        internalConstraintList = new ArrayList();
        outletsList = new ArrayList();
        actionsList = new ArrayList();
        seguesList = new ArrayList();
        widthSizeType = eViewSizeType.FIX_SIZE;
        heightSizeType = eViewSizeType.FIX_SIZE;

        marginLeft = 0;
        marginRight = 0;
        marginTop = 0;
        marginBottom = 0;

        gravity = eGravity.none;
    }

    public View(int width, int height){
        this.id = OSXStringProcessor.CreateId();
        this.rect = new Rect(width, height);
        internalConstraintList = new ArrayList();
        outletsList = new ArrayList();
        actionsList = new ArrayList();
        seguesList = new ArrayList();
        marginLeft = 0;
        marginRight = 0;
        marginTop = 0;
        marginBottom = 0;

        gravity = eGravity.none;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public int getWidth(){ return rect.width; }
    public int getHeight(){ return rect.height; }

    public void setWidth(int width){
        rect.width = width;
    }
    public void setHeight(int height){
        rect.height = height;
    }

    public Rect getRect(){ return rect; }
    public void setRect(Rect rect){
        this.rect = rect;
    }

    public eViewSizeType getWidthSizeType(){
        return this.widthSizeType;
    }
    public void setWidthSizeType(eViewSizeType widthSizeType){
        this.widthSizeType = widthSizeType;
    }

    public eViewSizeType getHeightSizeType(){
        return this.heightSizeType;
    }
    public void setHeightSizeType(eViewSizeType heightSizeType){
        this.heightSizeType = heightSizeType;
    }


    public int getMarginLeft(){
        return marginLeft;
    }
    public int getMarginRight(){
        return marginRight;
    }
    public int getMarginTop(){
        return marginTop;
    }
    public int getMarginBottom(){
        return marginBottom;
    }

    public void setMarginLeft(int marginLeft){
        this.marginLeft = marginLeft;
    }
    public void setMarginRight(int marginRight){
        this.marginRight = marginRight;
    }
    public void setMarginTop(int marginTop){
        this.marginTop = marginTop;
    }
    public void setMarginBottom(int marginBottom){
        this.marginBottom = marginBottom;
    }

}
