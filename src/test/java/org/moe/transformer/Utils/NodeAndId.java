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

import org.w3c.dom.Node;

class NodeAndId {
    public Node n;
    public String id;

    NodeAndId(Node _n, String _id) {
        n = _n;
        id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof NodeAndId)) {
            return false;
        }

        NodeAndId n = (NodeAndId)o;
        if (id == null && n.id == null || id.equals(n.id)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
