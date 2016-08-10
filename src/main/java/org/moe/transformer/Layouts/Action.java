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

public class Action {
    public String id;
    public String eventType;
    public String destination;
    public String selector;

    public Action(){
        id = OSXStringProcessor.CreateId();
        eventType = null;
        destination = null;
        selector = null;
    }

    // <action selector="BtnClicked:" destination="vXZ-lx-hvc" eventType="touchUpInside" id="jxC-73-LXo"/>
    // <xrt:ibaction="Event1-HandlerName1|Event2-HandlerName2|..."/>
}
