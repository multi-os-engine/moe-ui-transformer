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

package org.moe.transformer.Utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Constraint {
    private static final String FIRST_ITEM = "firstItem";
    private static final String SECOND_ITEM = "secondItem";
    private static final String FIRST_ATTR = "firstAttribute";
    private static final String SECOND_ATTR = "secondAttribute";
    private static final String CONSTANT= "constant";
    private static final String MULTIPLIER = "multiplier";
    private static final String PRIORITY = "priority";

    private static final String LEADING = "leading";
    private static final String TRAILING = "trailing";
    private static final String LEADING_MARGIN = "leadingMargin";
    private static final String TRAILING_MARGIN = "trailingMargin";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private static final String TOP_MARGIN = "topMargin";
    private static final String BOTTOM_MARGIN = "bottomMargin";

    private static final String CENTER_X = "centerX";
    private static final String CENTER_Y = "centerY";

    public String firstItem = "";
    public String secondItem = "";
    public String firstAttr = "";
    public String secondAttr = "";
    public Integer constant = 0;
    public Integer multi = 1;
    public Integer priority = 1000;

    public Constraint(Node n, String first) {
        if (n != null) {
            NamedNodeMap attrs = n.getAttributes();
            firstItem = StoryboardAnalyzer.getAttribute(attrs, FIRST_ITEM) == "" ? first :
                    StoryboardAnalyzer.getAttribute(attrs, FIRST_ITEM);
            secondItem = StoryboardAnalyzer.getAttribute(attrs, SECOND_ITEM);
            firstAttr = StoryboardAnalyzer.getAttribute(attrs, FIRST_ATTR);
            secondAttr = StoryboardAnalyzer.getAttribute(attrs, SECOND_ATTR);

            if (StoryboardAnalyzer.getAttribute(attrs, CONSTANT) != "") {
                constant = Integer.parseInt(StoryboardAnalyzer.getAttribute(attrs, CONSTANT));
            }
            if (StoryboardAnalyzer.getAttribute(attrs, MULTIPLIER) != "") {
                multi = Integer.parseInt(StoryboardAnalyzer.getAttribute(attrs, MULTIPLIER));
            }
            if (StoryboardAnalyzer.getAttribute(attrs, PRIORITY) != "") {
                priority = Integer.parseInt(StoryboardAnalyzer.getAttribute(attrs, PRIORITY));
            }
        }
    }

    public Constraint(Node n) {
        this(n, "");
    }

    private boolean isAlignedInside(String firstId, String secondId,
                                    String side) {
        return firstItem.equals(firstId) && secondItem.equals(secondId)
                && firstAttr.equals(side)
                && secondAttr.equals(side);
    }

    public boolean isAlignedLeftInside(String parentId, String childId) {
        return isAlignedInside(parentId, childId, LEADING)
                || isAlignedInside(childId, parentId, LEADING);
    }

    public boolean isAlignedRightInside(String parentId, String childId) {
        return isAlignedInside(parentId, childId, TRAILING)
                || isAlignedInside(childId, parentId, TRAILING);
    }

    public boolean isAlignedBottomInside(String parentId, String childId) {
        return (isAlignedInside(parentId, childId, BOTTOM)
                || isAlignedInside(childId, parentId, BOTTOM));
    }

    public boolean isAlignedTopInside(String parentId, String childId) {
        return (isAlignedInside(parentId, childId, TOP)
                || isAlignedInside(childId, parentId, TOP));
    }

    public boolean isAlignedCenterXInside(String parentId, String childId) {
        return (isAlignedInside(parentId, childId, CENTER_X)
                || isAlignedInside(childId, parentId, CENTER_X));
    }

    public boolean isAlignedCenterYInside(String parentId, String childId) {
        return (isAlignedInside(parentId, childId, CENTER_Y)
                || isAlignedInside(childId, parentId, CENTER_Y));
    }

    public boolean isCentered(String parentId, String childId, boolean centeredX) {
        return (firstItem.equals(parentId) || firstItem.equals("")) && secondItem.equals(childId)
                && firstAttr.equals(secondAttr)
                && constant == 0
                && (centeredX && firstAttr.equals(CENTER_X) || !centeredX && firstAttr.equals(CENTER_Y));
    }

    private boolean isAligned(String elementId, String side) {
        return (firstItem.equals(elementId) && firstAttr.equals(side))
                || (secondItem.equals(elementId) && secondAttr.equals(side));
    }

    public boolean isAlignedLeft(String elementId) {
        return isAligned(elementId, LEADING);
    }

    public boolean isAlignedRight(String elementId) {
        return isAligned(elementId, TRAILING);
    }

    public boolean isAlignedTop(String elementId) {
        return isAligned(elementId, TOP);
    }

    public boolean isAlignedBottom(String elementId) {
        return isAligned(elementId, BOTTOM);
    }

    private boolean hasEqualConstraints(String firstId, String secondId, String scale) {
        boolean constraintWithBothIds = false;
        boolean rightScale = false;

        if ((firstItem.equals(firstId) && secondItem.equals(secondId)) ||
                (firstItem.equals(secondId) && secondItem.equals(firstId))) {
            constraintWithBothIds = true;
        }

        if (firstAttr.equals(scale) && secondAttr.equals(scale)) {
            rightScale = true;
        }

        return constraintWithBothIds && rightScale;
    }

    public boolean hasEqualWidth(String firstId, String secondId) {
        return hasEqualConstraints(firstId, secondId, "width");
    }

    public boolean hasEqualHeight(String firstId, String secondId) {
        return hasEqualConstraints(firstId, secondId, "height");
    }


    public boolean isSecondItemRight() {
        return firstAttr.equals(TRAILING) && secondAttr.equals(LEADING) && constant <= 0;
    }

    public boolean isFirstItemRight() {
        return firstAttr.equals(LEADING) && secondAttr.equals(TRAILING) && constant >= 0;
    }

    public boolean isSecondItemBelow() {
        return firstAttr.equals(BOTTOM) && secondAttr.equals(TOP) && constant <= 0;
    }

    public boolean isFirstItemBelow() {
        return firstAttr.equals(TOP) && secondAttr.equals(BOTTOM) && constant >= 0;
    }

    public boolean isDataOK() {
        return true;
    }
}
