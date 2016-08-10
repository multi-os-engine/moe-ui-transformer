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
import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;

public class Constraint{
    public String id;
    public String firstItemId;
    public String secondItemId;
    public eConstrainAttributes firstAttribute;
    public eConstrainAttributes secondAttribute;
    public int constant;
    public int priority;

    public Constraint(){
        id = OSXStringProcessor.CreateId();
        firstItemId = null;
        secondItemId = null;
        constant=0;
        priority = 1000;
    }

    public String getFirstAttribute(){
        return getConstrainAttribute(firstAttribute);
    }

    public String getSecondAttribute(){
        return getConstrainAttribute(secondAttribute);
    }

    private static String getConstrainAttribute(eConstrainAttributes attribute){
        switch (attribute){
            case top:
                return "top";
            case bottom:
                return "bottom";
            case left:
                return "left";
            case right:
                return "right";
            case leading:
                return "leading";
            case topleading:
                return "topleading";
            case trailing:
                return "trailing";
            case width:
                return "width";
            case height:
                return "height";
            case centerX:
                return "centerX";
            case centerY:
                return "centerY";
        }
        return null;
    }
}
