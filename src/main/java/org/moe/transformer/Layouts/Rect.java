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

public class Rect {
    public int x;
    public int y;
    public int width;
    public int height;

    public Rect(){
        x=0; y=0;
        width = 0;
        height = 0;
    }

    public Rect(int width, int height){
        this.width = width;
        this.height = height;
        x=0; y=0;
    }

    public Rect(int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        this.x=x;
        this.y=y;
    }
}
