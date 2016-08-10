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

import java.util.*;

public class ViewGroup extends View {
    protected List<View> viewList;

    public void addChildView(View view){
        view.setParent(this);
        viewList.add(view);
    }

    public View getChildView(int i){
        return viewList.get(i);
    }

    public int getChildViewCount(){
        return viewList.size();
    }

    public ViewGroup(Rect rect){

        super(rect);
        viewList = new ArrayList();
    }

    public ViewGroup(int width, int height){
        super(width, height);
        viewList = new ArrayList();
    }
}
