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

import org.moe.transformer.LayoutInfo.eConstrainAttributes;
import org.moe.transformer.LayoutInfo.eGravity;
import org.moe.transformer.LayoutInfo.eLinearLayoutOrientation;
import org.moe.transformer.LayoutInfo.eViewSizeType;

import java.util.ArrayList;
import java.util.List;

public class LinearLayout extends ViewGroup {
    private eLinearLayoutOrientation orientation;
    private List<Constraint> constraintList;
    public void addConstrain(Constraint constrain){
        constraintList.add(constrain);
    }

    public Constraint getConstraint(int i){
        return constraintList.get(i);
    }

    public int getConstraintCount(){
        return constraintList.size();
    }

    public eLinearLayoutOrientation getOrientation(){
        return orientation;
    }

    public LinearLayout(Rect rect, eLinearLayoutOrientation orientation){
        super(rect);
        this.orientation = orientation;
        constraintList = new ArrayList();
    }

    public LinearLayout(int width, int height, eLinearLayoutOrientation orientation){
        super(width, height);
        this.orientation = orientation;
        constraintList = new ArrayList();
    }

    public void buildViewsPositions(){
        if(this.getChildViewCount()>0) {
            View vFirst = this.getChildView(0);

            if(vFirst.getWidthSizeType() == eViewSizeType.FIX_SIZE && vFirst.getHeightSizeType() == eViewSizeType.FIX_SIZE) {
                vFirst.setRect(new Rect(0 + vFirst.marginLeft, 0 + vFirst.marginTop, vFirst.getWidth(), vFirst.getHeight()));
            }
            else if(vFirst.getWidthSizeType() == eViewSizeType.FILL_PARENT && vFirst.getHeightSizeType() == eViewSizeType.FILL_PARENT) {
                vFirst.setRect(new Rect(0 + vFirst.marginLeft, 0 + vFirst.marginTop, vFirst.getWidth() - (vFirst.marginRight + vFirst.marginLeft), vFirst.getHeight() - (vFirst.marginBottom + vFirst.marginTop)));
            }
            else if(vFirst.getWidthSizeType() == eViewSizeType.FIX_SIZE && vFirst.getHeightSizeType() == eViewSizeType.FILL_PARENT){
                vFirst.setRect(new Rect(0 + vFirst.marginLeft, 0 + vFirst.marginTop, vFirst.getWidth(), vFirst.getHeight() - (vFirst.marginBottom + vFirst.marginTop)));
            }
            else if(vFirst.getWidthSizeType() == eViewSizeType.FILL_PARENT && vFirst.getHeightSizeType() == eViewSizeType.FIX_SIZE){
                vFirst.setRect(new Rect(0 + vFirst.marginLeft, 0 + vFirst.marginTop, vFirst.getWidth() - (vFirst.marginRight + vFirst.marginLeft), vFirst.getHeight()));
            }


            for (int i = 0; i < this.getChildViewCount() - 1; i++) {
                View v1 = this.getChildView(i);
                View v2 = this.getChildView(i + 1);

                switch (this.orientation) {
                    case HORIZONTAL:
                        if(v2.getWidthSizeType() == eViewSizeType.FIX_SIZE && v2.getHeightSizeType() == eViewSizeType.FIX_SIZE) {
                            v2.setRect(new Rect(v1.rect.x + v1.getWidth() + v1.marginRight + v2.marginLeft, v2.marginTop, v2.getWidth(), v2.getHeight()));
                        }
                        else if(v2.getWidthSizeType() == eViewSizeType.FILL_PARENT && v2.getHeightSizeType() == eViewSizeType.FILL_PARENT) {
                            v2.setRect(new Rect(v1.rect.x + v1.getWidth() + v1.marginRight + v2.marginLeft, v2.marginTop, v2.getWidth() - (v2.getMarginLeft()+v2.getMarginRight()), v2.getHeight() - (v2.getMarginTop() + v2.getMarginBottom())));
                        }
                        else if(v2.getWidthSizeType() == eViewSizeType.FIX_SIZE && v2.getHeightSizeType() == eViewSizeType.FILL_PARENT){
                            v2.setRect(new Rect(v1.rect.x + v1.getWidth() + v1.marginRight + v2.marginLeft, v2.marginTop, v2.getWidth(), v2.getHeight() - (v2.getMarginTop() + v2.getMarginBottom())));
                        }
                        else if(v2.getWidthSizeType() == eViewSizeType.FILL_PARENT && v2.getHeightSizeType() == eViewSizeType.FIX_SIZE){
                            v2.setRect(new Rect(v1.rect.x + v1.getWidth() + v1.marginRight + v2.marginLeft, v2.marginTop, v2.getWidth() - (v2.getMarginLeft()+v2.getMarginRight()), v2.getHeight()));
                        }
                        break;
                    case VERTICAL:
                        if(v2.getWidthSizeType() == eViewSizeType.FIX_SIZE && v2.getHeightSizeType() == eViewSizeType.FIX_SIZE) {
                            v2.setRect(new Rect(v2.marginLeft, v1.rect.y + v1.getHeight() + v1.marginBottom + v2.marginTop, v2.getWidth(), v2.getHeight()));
                        }
                        else if(v2.getWidthSizeType() == eViewSizeType.FILL_PARENT && v2.getHeightSizeType() == eViewSizeType.FILL_PARENT) {
                            v2.setRect(new Rect(v2.marginLeft, v1.rect.y + v1.getHeight() + v1.marginBottom + v2.marginTop, v2.getWidth() - (v2.getMarginLeft()+v2.getMarginRight()), v2.getHeight() - (v2.getMarginTop() + v2.getMarginBottom())));
                        }
                        else if(v2.getWidthSizeType() == eViewSizeType.FIX_SIZE && v2.getHeightSizeType() == eViewSizeType.FILL_PARENT){
                            v2.setRect(new Rect(v2.marginLeft, v1.rect.y + v1.getHeight() + v1.marginBottom + v2.marginTop, v2.getWidth(), v2.getHeight() - (v2.getMarginTop() + v2.getMarginBottom())));
                        }
                        else if(v2.getWidthSizeType() == eViewSizeType.FILL_PARENT && v2.getHeightSizeType() == eViewSizeType.FIX_SIZE){
                            v2.setRect(new Rect(v2.marginLeft, v1.rect.y + v1.getHeight() + v1.marginBottom + v2.marginTop, v2.getWidth() - (v2.getMarginLeft()+v2.getMarginRight()), v2.getHeight()));
                        }
                        break;
                }

            }
        }
    }

    public void buildConstrains() {
        switch(this.orientation){
            case VERTICAL:
                setVerticalConstrains();
                break;
            case HORIZONTAL:
                setHorizontalConstrains();
                break;
        }
    }

    private void setHorizontalConstrains() {
        String layoutId = this.id;
        List<Integer> equalIndexWidth = new ArrayList<Integer>();
        List<Integer> equalIndexHeight = new ArrayList<Integer>();
        for(int i=0; i<viewList.size(); i++){
            View view = viewList.get(i);

           /* Constraint constrain = new Constraint();
            constrain.firstItemId = view.id;
            constrain.firstAttribute = eConstrainAttributes.top;
            constrain.secondItemId = layoutId;
            constrain.secondAttribute = eConstrainAttributes.top;
            constrain.priority = 950;
            constrain.constant = this.marginTop + view.marginTop;
            constraintList.add(constrain);*/

            if(view.getWidthSizeType() == eViewSizeType.FIX_SIZE) setWidthConstrain(view, view.getWidth());
            else equalIndexWidth.add(i);
            if(view.getHeightSizeType() == eViewSizeType.FIX_SIZE){
                setHeightConstrain(view, view.getHeight());

                Constraint constrainGravity = new Constraint();
                constrainGravity.firstItemId = view.id;
                constrainGravity.secondItemId = layoutId;
                constrainGravity.priority = 999;
                constrainGravity.constant = this.marginTop + view.marginTop;
                switch(view.getGravity()){
                    case top:
                        constrainGravity.firstAttribute = eConstrainAttributes.top;
                        constrainGravity.secondAttribute = eConstrainAttributes.top;
                        constraintList.add(constrainGravity);
                        break;
                    case bottom:
                        constrainGravity.firstAttribute = eConstrainAttributes.bottom;
                        constrainGravity.secondAttribute = eConstrainAttributes.bottom;
                        constraintList.add(constrainGravity);

                        view.getRect().y = view.getParent().getHeight() - view.getHeight();

                        break;
                    case center:
                        constrainGravity.firstAttribute = eConstrainAttributes.centerY;
                        constrainGravity.secondAttribute = eConstrainAttributes.centerY;
                        constrainGravity.constant = 0; //this.getHeight()/2 - view.getHeight()/2;
                        constraintList.add(constrainGravity);

                        view.getRect().y = Math.round(view.getParent().getHeight() / 2 - view.getHeight() / 2);

                        break;
                    case none:
                        Constraint constrain = new Constraint();
                        constrain.firstItemId = view.id;
                        constrain.firstAttribute = eConstrainAttributes.top;
                        constrain.secondItemId = layoutId;
                        constrain.secondAttribute = eConstrainAttributes.top;
                        constrain.priority = 950;
                        constrain.constant = view.marginTop; //this.marginTop + view.marginTop;
                        constraintList.add(constrain);

                        equalIndexHeight.add(i);
                        Constraint constrain2 = new Constraint();
                        constrain2.firstItemId = layoutId;
                        constrain2.firstAttribute = eConstrainAttributes.bottom;
                        constrain2.secondItemId = view.id;
                        constrain2.secondAttribute = eConstrainAttributes.bottom;
                        constrain2.priority = 900;
                        constrain2.constant = view.marginBottom; //this.marginBottom + view.marginBottom;
                        constraintList.add(constrain2);
                    default:
                        /*constrainGravity.firstAttribute = eConstrainAttributes.top;
                        constrainGravity.secondAttribute = eConstrainAttributes.top;
                        constrainGravity.priority = 950;
                        constraintList.add(constrainGravity);*/
                }
            }
            else{
                Constraint constrain = new Constraint();
                constrain.firstItemId = view.id;
                constrain.firstAttribute = eConstrainAttributes.top;
                constrain.secondItemId = layoutId;
                constrain.secondAttribute = eConstrainAttributes.top;
                constrain.priority = 950;
                constrain.constant = this.marginTop + view.marginTop;
                constraintList.add(constrain);

                equalIndexHeight.add(i);
                Constraint constrain2 = new Constraint();
                constrain2.firstItemId = layoutId;
                constrain2.firstAttribute = eConstrainAttributes.bottom;
                constrain2.secondItemId = view.id;
                constrain2.secondAttribute = eConstrainAttributes.bottom;
                constrain2.priority = 900;
                constrain2.constant = this.marginBottom + view.marginBottom;
                constraintList.add(constrain2);
            }
        }

        if(viewList.size() > 0) {
            Constraint constraintFirst = new Constraint();
            constraintFirst.firstItemId = viewList.get(0).id;
            constraintFirst.firstAttribute = eConstrainAttributes.leading;
            constraintFirst.secondItemId = layoutId; //viewList.get(viewList.size() - 1).id;
            constraintFirst.secondAttribute = eConstrainAttributes.leading;
            constraintFirst.priority = 950;
            constraintFirst.constant = (this.marginLeft + viewList.get(0).marginLeft);
            constraintList.add(constraintFirst);

            Constraint constraintLast = new Constraint();
            constraintLast.firstItemId = layoutId;
            constraintLast.firstAttribute = eConstrainAttributes.trailing;
            constraintLast.secondItemId = viewList.get(viewList.size() - 1).id;
            constraintLast.secondAttribute = eConstrainAttributes.trailing;
            constraintLast.priority = 900;
            constraintLast.constant = this.marginRight + viewList.get(viewList.size() - 1).marginRight;
            constraintList.add(constraintLast);


        }
        if(viewList.size() > 1){
            for (int i = 0; i < viewList.size()-1; i++) {
                View viewFirst = viewList.get(i);
                View viewSecond = viewList.get(i+1);

                Constraint constrain = new Constraint();
                constrain.firstItemId = viewFirst.id;
                constrain.firstAttribute = eConstrainAttributes.trailing;
                constrain.secondItemId = viewSecond.id;
                constrain.secondAttribute = eConstrainAttributes.leading;
                constrain.priority = 940;
                constrain.constant = (-1)*(viewFirst.marginRight + viewSecond.marginLeft);
                constraintList.add(constrain);
            }
        }

        for(int i=0; i<equalIndexWidth.size()-1; i++){
            int indexFirst = equalIndexWidth.get(i);
            int indexSecond = equalIndexWidth.get(i+1);
            Constraint equalWidthConstraint = new Constraint();
            equalWidthConstraint.firstItemId = viewList.get(indexFirst).id;
            equalWidthConstraint.firstAttribute = eConstrainAttributes.width;
            equalWidthConstraint.secondItemId = viewList.get(indexSecond).id;
            equalWidthConstraint.secondAttribute = eConstrainAttributes.width;
            equalWidthConstraint.constant = viewList.get(indexFirst).rect.width - viewList.get(indexSecond).rect.width;
            equalWidthConstraint.priority = 500;
            this.addConstrain(equalWidthConstraint);
        }
        for(int i=0; i<equalIndexHeight.size()-1; i++){
            int indexFirst = equalIndexHeight.get(i);
            int indexSecond = equalIndexHeight.get(i+1);
            Constraint equalHeightConstraint = new Constraint();
            equalHeightConstraint.firstItemId = viewList.get(indexFirst).id;
            equalHeightConstraint.firstAttribute = eConstrainAttributes.height;
            equalHeightConstraint.secondItemId = viewList.get(indexSecond).id;
            equalHeightConstraint.secondAttribute = eConstrainAttributes.height;
            equalHeightConstraint.constant = viewList.get(indexFirst).rect.height - viewList.get(indexSecond).rect.height;
            equalHeightConstraint.priority = 500;
            this.addConstrain(equalHeightConstraint);
        }
        equalIndexWidth.clear();
        equalIndexHeight.clear();
    }

    private void setVerticalConstrains() {
        List<Integer> equalIndexWidth = new ArrayList<Integer>();
        List<Integer> equalIndexHeight = new ArrayList<Integer>();
        //Rect parentRect = this.rect;
        String layoutId = this.id;
        if(viewList.size() >= 1){
            Constraint constraintFirst = new Constraint();
            constraintFirst.firstItemId = viewList.get(0).id;
            constraintFirst.firstAttribute = eConstrainAttributes.top;
            constraintFirst.secondItemId = layoutId;
            constraintFirst.secondAttribute = eConstrainAttributes.top;
            constraintFirst.priority = 950;
            constraintFirst.constant = viewList.get(0).marginTop;
            constraintList.add(constraintFirst);

            Constraint constraintLast = new Constraint();
            constraintLast.firstItemId = layoutId;
            constraintLast.firstAttribute = eConstrainAttributes.bottom;
            constraintLast.secondItemId = viewList.get(viewList.size()-1).id;
            constraintLast.secondAttribute = eConstrainAttributes.bottom;
            constraintLast.priority = 900;
            constraintLast.constant = this.marginBottom + viewList.get(viewList.size()-1).marginBottom;
            constraintList.add(constraintLast);

            for (int i = 0; i < viewList.size(); i++) {

                if(viewList.get(i).getWidthSizeType() == eViewSizeType.FIX_SIZE){
                    setWidthConstrain(viewList.get(i), viewList.get(i).getWidth());

                    Constraint constraintGravity = new Constraint();
                    constraintGravity.firstItemId = viewList.get(i).id;
                    constraintGravity.secondItemId = layoutId;
                    constraintGravity.priority = 1000;

                    switch(viewList.get(i).getGravity()){
                        case left:
                            constraintGravity.firstAttribute = eConstrainAttributes.leading;
                            constraintGravity.secondAttribute = eConstrainAttributes.leading;
                            constraintList.add(constraintGravity);
                            break;
                        case right:
                            constraintGravity.firstAttribute = eConstrainAttributes.trailing;
                            constraintGravity.secondAttribute = eConstrainAttributes.trailing;
                            constraintList.add(constraintGravity);

                            viewList.get(i).getRect().x = viewList.get(i).getParent().getWidth() - viewList.get(i).getWidth();

                            break;
                        case center:
                            constraintGravity.firstAttribute = eConstrainAttributes.centerX; //eConstrainAttributes.leading;
                            constraintGravity.secondAttribute = eConstrainAttributes.centerX; //eConstrainAttributes.leading;
                            constraintGravity.constant = 0; //this.getWidth()/2 - viewList.get(i).getWidth()/2;
                            constraintList.add(constraintGravity);

                            viewList.get(i).getRect().x = Math.round(viewList.get(i).getParent().getWidth() / 2
                                    - viewList.get(i).getWidth() / 2);

                            break;
                        case none:
                            Constraint constraintLeft = new Constraint();
                            constraintLeft.firstItemId = viewList.get(i).id;
                            constraintLeft.firstAttribute = eConstrainAttributes.leading;
                            constraintLeft.secondItemId = layoutId;
                            constraintLeft.secondAttribute = eConstrainAttributes.leading;
                            constraintLeft.priority = 950;
                            constraintLeft.constant = this.marginLeft + viewList.get(i).marginLeft;//this.marginRight + viewList.get(i).marginRight;
                            constraintList.add(constraintLeft);

                            Constraint constraintRight = new Constraint();
                            constraintRight.firstItemId = layoutId;
                            constraintRight.firstAttribute = eConstrainAttributes.trailing;
                            constraintRight.secondItemId = viewList.get(i).id;
                            constraintRight.secondAttribute = eConstrainAttributes.trailing;
                            constraintRight.priority = 900;
                            constraintRight.constant = this.marginRight + viewList.get(i).marginRight; //this.marginLeft + viewList.get(i).marginLeft;
                            constraintList.add(constraintRight);
                        default:
                    }
                }
                else{
                    equalIndexWidth.add(i);

                    Constraint constraintLeft = new Constraint();
                    constraintLeft.firstItemId = viewList.get(i).id;
                    constraintLeft.firstAttribute = eConstrainAttributes.leading;
                    constraintLeft.secondItemId = layoutId;
                    constraintLeft.secondAttribute = eConstrainAttributes.leading;
                    constraintLeft.priority = 950;
                    constraintLeft.constant = this.marginLeft + viewList.get(i).marginLeft;//this.marginRight + viewList.get(i).marginRight;
                    constraintList.add(constraintLeft);

                    Constraint constraintRight = new Constraint();
                    constraintRight.firstItemId = layoutId;
                    constraintRight.firstAttribute = eConstrainAttributes.trailing;
                    constraintRight.secondItemId = viewList.get(i).id;
                    constraintRight.secondAttribute = eConstrainAttributes.trailing;
                    constraintRight.priority = 900;
                    constraintRight.constant = this.marginRight + viewList.get(i).marginRight; //this.marginLeft + viewList.get(i).marginLeft;
                    constraintList.add(constraintRight);

                }
                if(viewList.get(i).getHeightSizeType() == eViewSizeType.FIX_SIZE) setHeightConstrain(viewList.get(i), viewList.get(i).getHeight());
                else equalIndexHeight.add(i);
            }

            for (int i = 0; i < viewList.size()-1; i++) {
                Constraint constraint = new Constraint();
                constraint.firstItemId = viewList.get(i).id;
                constraint.firstAttribute = eConstrainAttributes.bottom;
                constraint.secondItemId = viewList.get(i+1).id;
                constraint.secondAttribute = eConstrainAttributes.top;
                constraint.priority = 950;
                constraint.constant = - viewList.get(i).marginBottom - viewList.get(i+1).marginTop;
                constraintList.add(constraint);
            }
        }

        for(int i=0; i<equalIndexWidth.size()-1; i++){
            int indexFirst = equalIndexWidth.get(i);
            int indexSecond = equalIndexWidth.get(i+1);
            Constraint equalWidthConstraint = new Constraint();
            equalWidthConstraint.firstItemId = viewList.get(indexFirst).id;
            equalWidthConstraint.firstAttribute = eConstrainAttributes.width;
            equalWidthConstraint.secondItemId = viewList.get(indexSecond).id;
            equalWidthConstraint.secondAttribute = eConstrainAttributes.width;
            equalWidthConstraint.constant = viewList.get(indexFirst).rect.width - viewList.get(indexSecond).rect.width;
            equalWidthConstraint.priority = 500;
            this.addConstrain(equalWidthConstraint);
        }
        for(int i=0; i<equalIndexHeight.size()-1; i++){
            int indexFirst = equalIndexHeight.get(i);
            int indexSecond = equalIndexHeight.get(i+1);
            Constraint equalHeightConstraint = new Constraint();
            equalHeightConstraint.firstItemId = viewList.get(indexFirst).id;
            equalHeightConstraint.firstAttribute = eConstrainAttributes.height;
            equalHeightConstraint.secondItemId = viewList.get(indexSecond).id;
            equalHeightConstraint.secondAttribute = eConstrainAttributes.height;
            equalHeightConstraint.constant = viewList.get(indexFirst).rect.height - viewList.get(indexSecond).rect.height;
            equalHeightConstraint.priority = 500;
            this.addConstrain(equalHeightConstraint);
        }
        equalIndexWidth.clear();
        equalIndexHeight.clear();
    }

    public void setWidthConstrain(View v, int width){
        InternalConstraint constraint = new InternalConstraint();
        constraint.firstAttribute = eConstrainAttributes.width;
        constraint.constant = width;
        v.addInternalConstrain(constraint);
    }

    public void setHeightConstrain(View v, int height){
        InternalConstraint constraint = new InternalConstraint();
        constraint.firstAttribute = eConstrainAttributes.height;
        constraint.constant = height;
        v.addInternalConstrain(constraint);
    }
}
