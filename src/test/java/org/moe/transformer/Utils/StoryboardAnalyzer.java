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

import org.moe.transformer.Layouts.Rect;
import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StoryboardAnalyzer {

    private static final int DEFAULT_VIEW_WIDTH = 600;
    private static final int DEFAULT_VIEW_HEIGHT = 600;

    private static List<String> getControllersLayoutGuides(Node controller) {
        List<String> layoutGuides = new ArrayList<String>();
        NodeList children = controller.getChildNodes();

        if (children == null) {
            return layoutGuides;
        }

        for (int j = 0; j < children.getLength(); j++) {
            Node child = children.item(j);
            if (child.getNodeName().equals("layoutGuides")) {
                NodeList guides = child.getChildNodes();
                if (guides != null) {
                    for (int k = 0; k < guides.getLength(); k++) {
                        layoutGuides.add(getNodeId(guides.item(k)));
                    }
                }
            }
        }
        return layoutGuides;
    }

    private static String getNodeId(Node n) {
        return getAttribute(n.getAttributes(), "id");
    }

    private static List<String> getIdsOfTag(Node n, String tag) {
        List<String> retVal = new ArrayList<String>();
        if (n == null || n.getNodeType() != Node.ELEMENT_NODE) {
            return retVal;
        }
        NodeList nodes = ((Element)n).getElementsByTagName(tag);
        for (int i = 0; i < nodes.getLength(); i++) {
            retVal.add(getNodeId(nodes.item(i)));
        }
        return retVal;
    }

    private static List<String> getConstraintVisibleElements(Node constraint) {
        Node constraintsNode = constraint.getParentNode();
        List<String> retVal = new ArrayList<String>();
        if (constraintsNode == null) {
            return retVal;
        }
        Node mainView = constraintsNode.getParentNode();
        if (mainView == null) {
            return retVal;
        }
        retVal.add(getNodeId(mainView));
        retVal.addAll(getIdsOfTag(mainView, "view"));
        retVal.addAll(getIdsOfTag(mainView, "button"));
        retVal.addAll(getIdsOfTag(mainView, "label"));
        return retVal;
    }

    private static boolean checkControllersConstraints(Node controller, List<String> guideIds) {
        boolean retVal = true;
        if (controller.getNodeType() != Node.ELEMENT_NODE) {
            return false;
        }
        String controllerId = getNodeId(controller);
        NodeList constraints = ((Element)controller).getElementsByTagName("constraint");
        List<String> visibleIds = new ArrayList<String>();
        visibleIds.add(controllerId);
        visibleIds.addAll(guideIds);
        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            List<String> localVis = getConstraintVisibleElements(constraints.item(i));
            if (localVis.isEmpty()) {
                continue;
            }
            Constraint c = new Constraint(constraints.item(i), localVis.get(0));
            localVis.addAll(visibleIds);
            retVal &= localVis.contains(c.firstItem)
                    && (localVis.contains(c.secondItem) || c.secondItem.equals(""));
        }
        return retVal;
    }

    public static boolean areConstraintsGood(Document doc) {
        boolean retVal = true;
        NodeList controllers = doc.getElementsByTagName("viewController");

        if (controllers != null) {
            for (int i = 0; i < controllers.getLength(); i++) {
                List<String> layoutGuides = getControllersLayoutGuides(controllers.item(i));
                retVal &= checkControllersConstraints(controllers.item(i), layoutGuides);
            }
        }
        return retVal;
    }

    public static boolean hasHeightOrWidthConstraints(Document doc) {
        NodeList constraints = doc.getElementsByTagName("constraint");

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if ((c.firstAttr.equals("width") || c.firstAttr.equals("height"))
                    && c.secondItem.equals("")) {
                return true;
            }
        }
        return false;
    }



    private static List<NodeAndId> getNodeHierarchy(Node n) {
        List<NodeAndId> ids = new ArrayList<NodeAndId>();
        Node tmp = n;
        while (tmp != null && !tmp.getNodeName().equals("scene")) {
            NamedNodeMap attrs = tmp.getAttributes();
            if (attrs != null && attrs.getNamedItem("id") != null) {
                ids.add(new NodeAndId(tmp, attrs.getNamedItem("id").getNodeValue()));
            }
            tmp = tmp.getParentNode();
        }
        return ids;
    }

//    private static NodeList getConstraints (Node n) {
//        NodeList constraints = n.getChildNodes();
//        for (int i = 0; i < constraints.getLength(); i++) {
//            Node constraintsNode = constraints.item(i);
//            if (constraintsNode.getNodeName().equals("constraints") &&
//                    constraintsNode.getNodeType() == Node.ELEMENT_NODE) {
//                return ((Element) constraintsNode).getElementsByTagName("constraint");
//            }
//        }
//        return null;
//    }




    private static List<String> getIds(List<NodeAndId> list) {
        if (list == null) {
            return null;
        }
        List<String> ids = new ArrayList<String>();
        for (NodeAndId n : list) {
            ids.add(n.id);
        }
        return ids;
    }

    private static List<NodeAndId> subtractCommonParents(List<NodeAndId> hierarchy1, List<NodeAndId> hierarchy2) {
        if (hierarchy1 == null || hierarchy2 == null) {
            return null;
        }
        List<NodeAndId> parents = new ArrayList<NodeAndId>(hierarchy1);
        parents.retainAll(hierarchy2);
        hierarchy1.removeAll(parents);
        hierarchy2.removeAll(parents);
        return parents;
    }

    // if <hor> is true returns true if <secondItem> is right to the <firstItem>
    // if <hor> is false returns true if <secondItem> is below the <firstItem>
    private static boolean doesFollow(Node firstItem, Node secondItem, boolean hor) {
        List<NodeAndId> mainNodes = getNodeHierarchy(firstItem);
        List<NodeAndId> nodesToCheck = getNodeHierarchy(secondItem);
        List<NodeAndId> p = subtractCommonParents(mainNodes, nodesToCheck);
        if (p == null) {
            //No common parents in the hierarchy
            return false;
        }

        List<NodeAndId> nodes = new ArrayList<NodeAndId>(mainNodes);
        nodes.addAll(nodesToCheck);
        nodes.addAll(p);
        Map<String, List<String>> bonds = getBonds(nodes, hor);

        List<String> mainNodesIds = getIds(mainNodes);
        List<String> nodesToCheckIds = getIds(nodesToCheck);
        return hasBond(bonds, mainNodesIds, nodesToCheckIds);
    }

    private static Map<String, List<String>> getBonds(List<NodeAndId> nodes, boolean hor) {
        Map<String, List<String>> bonds = new HashMap<String,List<String>>();
        int num = 0;
        while (num < nodes.size()) {
            NodeAndId tmp = nodes.get(num);
            NodeList constraints = getConstraints(tmp.n);

            if (constraints != null) {
                for (int i = 0; i < constraints.getLength(); i++) {
                    Node constraint = constraints.item(i);
                    Constraint c = new Constraint(constraint, "");
                    if (hor && c.isFirstItemRight() || !hor && c.isFirstItemBelow()) {
                        addBond(bonds, c.secondItem, c.firstItem);
                    }
                    if (hor && c.isSecondItemRight() || !hor && c.isSecondItemBelow()) {
                        addBond(bonds, c.firstItem, c.secondItem);
                    }
                }
            }
            num++;
        }
        return bonds;
    }

    private static boolean hasBond(Map<String, List<String>> bonds, List<String> start, List<String> finish) {
        if (bonds == null || start == null || finish == null) {
            return false;
        }

        Set<String> tmp = new HashSet<String>(start);
        Set<String> next = new HashSet<String>();
        Set<String> old = new HashSet<String>();
        while (tmp.size() > 0) {
            old.addAll(tmp);
            for (String val : tmp) {
                List<String> bonded = bonds.get(val);
                if (bonded != null) {
                    next.addAll(bonded);
                }
            }
            //delete items we already checked
            Set<String> repeated = new HashSet<String>(old);
            repeated.retainAll(next);
            next.removeAll(repeated);

            //check if we have match
            List<String> checkList = new ArrayList<String>(finish);
            checkList.retainAll(next);
            if (checkList.size() > 0) {
                return true;
            }
            tmp = next;
        }
        return false;
    }

    private static void addBond(Map<String, List<String>> bonds, String key, String val) {
        if (bonds == null || key == null || val == null) {
            return;
        }
        List<String> vals = new ArrayList<String>();
        if (bonds.containsKey(key)) {
            vals = bonds.get(key);
            if (!vals.contains(val)) {
                vals.add(val);
            }
        } else {
            vals.add(val);
            bonds.put(key, vals);
        }
    }

    private static boolean isRightToByRect(Node n, Node toCheck) {

        return false;
    }

    public static boolean isRightTo(Node n, Node toCheck) {
        return doesFollow(n, toCheck, true) ||
                !doesFollow(toCheck, n, true) && isRightToByRect(n, toCheck);
    }

    public static boolean isBelow(Node n, Node toCheck) {
        return doesFollow(n, toCheck, false);
    }

    public static boolean isCenteredInView(Element view, Element item) {
        if (view == null || item == null) {
            return false;
        }
        NodeList constraints = getConstraints(view);

        if (constraints == null) {
            return false;
        }

        boolean centeredX = false;
        boolean centeredY = false;
        String viewId = view.getAttribute("id");
        String itemId = item.getAttribute("id");
        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i), viewId);

            if (c.isCentered(viewId, itemId, true)) {
                centeredX = true;
            }
            if (c.isCentered(viewId, itemId, false)) {
                centeredY = true;
            }
        }
        return centeredY & centeredX;
    }

//    public static Element getFirstParentWithConstraints(Node n) {
//        if (n == null) {
//            return null;
//        }
//        List<NodeAndId> hierarchy = getNodeHierarchy(n);
//        Element parent = null;
//        for (NodeAndId node : hierarchy) {
//            if (!node.n.isSameNode(n) && node.n.getNodeType() == Node.ELEMENT_NODE &&
//                    getConstraints(node.n) != null) {
//                parent = (Element)node.n;
//                break;
//            }
//        }
//        return parent;
//    }

    /*
    Checks if element <item> fills parent <parent> horizontally (if <hor> is true)
    or vertically (<hor> is false). Only constraints from <parent> are used for checking.
    */
//    public static boolean fillsParent(Element parent, Node item, boolean hor) {
//        if (item == null || parent == null) {
//            return false;
//        }
//        boolean alignedFirstSide = false;
//        boolean alignedSecondSide = false;
//        String parentId = parent.getAttribute("id");
//        String childId = getAttribute(item.getAttributes(), "id");
//        NodeList constraints = getConstraints(parent);
//
//        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
//            Constraint c = new Constraint(constraints.item(i), parentId);
//            if (hor && c.isAlignedLeftInside(parentId, childId)
//                    || !hor && c.isAlignedBottomInside(parentId, childId)) {
//                alignedFirstSide = true;
//            }
//            if (hor && c.isAlignedRightInside(parentId, childId)
//                    || !hor && c.isAlignedTopInside(parentId, childId)) {
//                alignedSecondSide = true;
//            }
//        }
//        return alignedFirstSide && alignedSecondSide;
//    }
//
//    /*
//    Finds first parent with constraints and checks if element <item> fills it horizontally (if <hor> is true)
//    or vertically (<hor> is false). Only constraints from <parent> are used for checking.
//    */
//    public static boolean fillsParent(Node item, boolean hor) {
//        Element parent = getFirstParentWithConstraints(item);
//        return fillsParent(parent, item, hor);
//    }
//
//    public static boolean isAligned(Element parent, Node item, AlignSide side) {
//        if (item == null || parent == null) {
//            return false;
//        }
//
//        String parentId = parent.getAttribute("id");
//        String childId = getAttribute(item.getAttributes(), "id");
//        NodeList constraints = getConstraints(parent);
//        boolean retVal = false;
//
//        int i = 0;
//        while ((i < (constraints == null ? 0 : constraints.getLength())) && !retVal) {
//            Constraint c = new Constraint(constraints.item(i), parentId);
//            switch (side) {
//                case LEFT: retVal = c.isAlignedLeftInside(parentId, childId);
//                    break;
//                case RIGHT: retVal = c.isAlignedRightInside(parentId, childId);
//                    break;
//                case TOP: retVal = c.isAlignedTopInside(parentId, childId);
//                    break;
//                case BOTTOM: retVal = c.isAlignedBottomInside(parentId, childId);
//                    break;
//            }
//            i++;
//        }
//        return retVal;
//    }
//
//    public static boolean isAligned(Node item, AlignSide side) {
//        Element parent = getFirstParentWithConstraints(item);
//        return isAligned(parent, item, side);
//    }
//
//
//
//    public static double getSizeProportion(Document doc, Node item1, Node item2, boolean hor) {
//        String parameter = hor ? "width" : "height";
//        List<NodeAndId> hierarchy1 = getNodeHierarchy(item1);
//        List<NodeAndId> hierarchy2 = getNodeHierarchy(item2);
//        subtractCommonParents(hierarchy1, hierarchy2);
//        List<String> item1Chain = getIds(hierarchy1);
//        List<String> item2Chain = getIds(hierarchy2);
//        NodeList constraints = doc.getElementsByTagName("constraint");
//        for (int i = 0; i < constraints.getLength(); i++) {
//            Constraint c = new Constraint(constraints.item(i));
//            if (c.firstAttr.equals(c.secondAttr) && c.firstAttr.equals(parameter)) {
//                if (item1Chain.contains(c.firstItem) && item2Chain.contains(c.secondItem)) {
//                    return c.multi;
//                }
//                if (item1Chain.contains(c.secondItem) && item2Chain.contains(c.firstItem)) {
//                    return 1 / c.multi;
//                }
//            }
//        }
//        return 0.0;
//    }

    //<--============================================New Analyzer For Transfromer=============================================-->

    // Searches for <state> tag in <button> elements and compares title attribute with a given caption
    // Returns id of the first found button, that has a state with a given caption as a title
    public static Node getButtonByCaption(Document doc, String caption) {
        NodeList buttons = doc.getElementsByTagName("button");
        for (int i = 0; i < buttons.getLength(); i++) {
            Node btn = buttons.item((i));
            if (btn.getNodeType() == Node.ELEMENT_NODE) {
                NodeList states = ((Element) btn).getElementsByTagName("state");
                for (int j = 0; j < states.getLength(); j++) {
                    Node state = states.item(j);
                    NamedNodeMap attrs = state.getAttributes();
                    if (attrs != null &&
                            attrs.getNamedItem("title").getNodeValue().equals(caption)) {
                        return btn;
                    }
                }
            }
        }
        return null;
    }

    // Searches for text attribute in <label> elements and compares with a given caption
    // Returns id of the first found label, that has a text with a given caption
    public static Node getLabelByCaption(Document doc, String caption) {
        NodeList labels = doc.getElementsByTagName("label");
        for (int i = 0; i < labels.getLength(); i++) {
            Node label = labels.item((i));
            if (label.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attrs = label.getAttributes();
                if (attrs != null &&
                        attrs.getNamedItem("text").getNodeValue().equals(caption)) {
                    return label;
                }
            }
        }
        return null;
    }

    // Searches for text attribute in <textField> elements and compares with a given caption
    // Returns id of the first found textField, that has a text with a given caption
    public static Node getTextFieldByCaption(Document doc, String caption) {
        NodeList labels = doc.getElementsByTagName("textField");
        for (int i = 0; i < labels.getLength(); i++) {
            Node label = labels.item((i));
            if (label.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attrs = label.getAttributes();
                if (attrs != null &&
                        attrs.getNamedItem("text").getNodeValue().equals(caption)) {
                    return label;
                }
            }
        }
        return null;
    }

    public static Node getBarButtonItemByCaption(Document doc, String caption) {
        NodeList barButtonItems = doc.getElementsByTagName("barButtonItem");
        for (int i = 0; i < barButtonItems.getLength(); i++) {
            Node barButtonItem = barButtonItems.item((i));
            if (barButtonItem.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attrs = barButtonItem.getAttributes();
                if (attrs != null &&
                        attrs.getNamedItem("title").getNodeValue().equals(caption)) {
                    return barButtonItem;
                }
            }
        }
        return null;
    }


    public static boolean checkIfTagExists(Element parent, String tag) {
        NodeList tags = parent.getElementsByTagName(tag);
        return tags.getLength() != 0;
    }

    private static boolean checkRGBColorTag(NamedNodeMap attrs, String expectedColorInHex) {
        Double red = Double.parseDouble(attrs.getNamedItem("red").getNodeValue());
        Double green = Double.parseDouble(attrs.getNamedItem("green").getNodeValue());
        Double blue = Double.parseDouble(attrs.getNamedItem("blue").getNodeValue());

        Double expectedRed = Double.parseDouble(OSXStringProcessor.ColorToR(expectedColorInHex));
        Double expectedGreen = Double.parseDouble(OSXStringProcessor.ColorToG(expectedColorInHex));
        Double expectedBlue = Double.parseDouble(OSXStringProcessor.ColorToB(expectedColorInHex));

        if (red.equals(expectedRed) && green.equals(expectedGreen) && blue.equals(expectedBlue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkBackgroundColor(Node element, String expectedColorInHex) {
        if (element.getNodeType() == Node.ELEMENT_NODE) {
            NodeList colors = ((Element) element).getElementsByTagName("color");
            for (int i = 0; i < colors.getLength(); i++) {
                Node tag = colors.item(i);
                NamedNodeMap attrs = tag.getAttributes();
                if (attrs != null &&
                        attrs.getNamedItem("key").getNodeValue().equals("backgroundColor") &&
                        checkRGBColorTag(attrs, expectedColorInHex)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkTextColor(Node element, String expectedColorInHex) {
        if (element.getNodeName().equals("button") && element.getNodeType() == Node.ELEMENT_NODE) {
            NodeList colors = ((Element) element).getElementsByTagName("color");
            for (int i = 0; i < colors.getLength(); i++) {
                Node tag = colors.item(i);
                NamedNodeMap attrs = tag.getAttributes();
                if (attrs != null &&
                        attrs.getNamedItem("key").getNodeValue().equals("titleColor") &&
                        checkRGBColorTag(attrs, expectedColorInHex)) {
                    return true;
                }
            }
        }
        if ((element.getNodeName().equals("label") ||
                element.getNodeName().equals("textField")) &&
                element.getNodeType() == Node.ELEMENT_NODE) {
            NodeList colors = ((Element) element).getElementsByTagName("color");
            for (int i = 0; i < colors.getLength(); i++) {
                Node tag = colors.item(i);
                NamedNodeMap attrs = tag.getAttributes();
                if (attrs != null &&
                        attrs.getNamedItem("key").getNodeValue().equals("textColor") &&
                        checkRGBColorTag(attrs, expectedColorInHex)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getCaption(Node n) {
        String retVal = "CaptionNotFound";
        if (n == null) {
            return retVal;
        }
        if (n.getNodeName().equals("button") && n.getNodeType() == Node.ELEMENT_NODE) {
            NodeList children = n.getChildNodes();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeName().equals("state")) {
                        retVal = getAttribute(child.getAttributes(), "title");
                    }
                }
            }
        }
        if (n.getNodeName().equals("label")) {
            retVal = getAttribute(n.getAttributes(), "text");
        }
        return retVal;
    }

    public static boolean checkTextSize(Node element, int expectedTextSize) {
        int valueToCheck;

        if ((element.getNodeName().equals("button") ||
                element.getNodeName().equals("label") ||
                element.getNodeName().equals("textField")) &&
                element.getNodeType() == Node.ELEMENT_NODE) {
            NodeList children = element.getChildNodes();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeName().equals("fontDescription")) {
                        valueToCheck = Integer.parseInt(getAttribute(child.getAttributes(), "pointSize"));
                        return valueToCheck == expectedTextSize;
                    }
                }
            }
        }

        return false;
    }

    public static boolean checkTextStyle(Node element, String expectedTextStyle) {
        String valueToCheck;

        if ((element.getNodeName().equals("button") ||
                element.getNodeName().equals("label") ||
                element.getNodeName().equals("textField")) &&
                element.getNodeType() == Node.ELEMENT_NODE) {
            NodeList children = element.getChildNodes();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeName().equals("fontDescription")) {
                        valueToCheck = getAttribute(child.getAttributes(), "type");
                        return valueToCheck.equals(expectedTextStyle);
                    }
                }
            }
        }

        return false;
    }

    public static String getAttribute(NamedNodeMap attrs, String name) {
        return (attrs == null || attrs.getNamedItem(name) == null)
                ? ""
                : attrs.getNamedItem(name).getNodeValue();
    }


    private static NodeList getConstraints(Node element) {
        NodeList constraints = element.getChildNodes();
        for (int i = 0; i < constraints.getLength(); i++) {
            Node constraintsNode = constraints.item(i);
            if (constraintsNode.getNodeName().equals("constraints") &&
                    constraintsNode.getNodeType() == Node.ELEMENT_NODE) {
                return ((Element) constraintsNode).getElementsByTagName("constraint");
            }
        }
        return null;
    }

    public static boolean hasHeightConstraint(Node element) {
        NodeList constraints = getConstraints(element);

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.firstAttr.equals("height")) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasWidthConstraint(Node element) {
        NodeList constraints = getConstraints(element);

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.firstAttr.equals("width")) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks if element <item> fills parent vertically. Only constraints from <parent> are used for checking.
     */
    private static boolean fillsParentByHeight(Element parent, Node element) {
        if (element == null || parent == null
                || hasHeightConstraint(element)) {
            return false;
        }

        boolean alignedTop = false;
        boolean alignedBottom = false;
        String elementId = getAttribute(element.getAttributes(), "id");
        NodeList constraints = getConstraints(parent);

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedTop(elementId)) {
                alignedTop = true;
            }
            if (c.isAlignedBottom(elementId)) {
                alignedBottom = true;
            }
        }
        return alignedTop && alignedBottom;
    }

    /*
     * Checks if element <item> fills parent <parent> horizontally. Only constraints from <parent> are used for checking.
     */
    private static boolean fillsParentByWidth(Element parent, Node element) {
        if (element == null || parent == null
                || hasWidthConstraint(element)) {
            return false;
        }

        boolean alignedLeft = false;
        boolean alignedRight = false;
        String elementId = getAttribute(element.getAttributes(), "id");
        NodeList constraints = getConstraints(parent);

        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedLeft(elementId)) {
                alignedLeft = true;
            }
            if (c.isAlignedRight(elementId)) {
                alignedRight = true;
            }
        }
        return alignedLeft && alignedRight;
    }

    public static Element getFirstParentWithConstraints(Node n) {
        if (n == null) {
            return null;
        }
        List<NodeAndId> hierarchy = getNodeHierarchy(n);
        Element parent = null;
        for (NodeAndId node : hierarchy) {
            if (!node.n.isSameNode(n) && node.n.getNodeType() == Node.ELEMENT_NODE &&
                    getConstraints(node.n) != null) {
                parent = (Element)node.n;
                break;
            }
        }
        return parent;
    }

    /*
     * Finds first parent with constraints and checks if element <item> fills it horizontally
     * or vertically. Only constraints from <parent> are used for checking.
     */
    public static boolean fillsParent(Node element, Orientation orientaion) {
        Element parent = getFirstParentWithConstraints(element);

        if (orientaion == Orientation.VERTICAL) {
            return fillsParentByHeight(parent, element);
        } else {
            return fillsParentByWidth(parent, element);
        }
    }

    private static boolean checkRectMargins(Node element, int generalWidth, int generalHeight, Margins margins ) {
        int valueToCheck;
        boolean rightRectHeight;
        boolean rightRectWidth;

        NodeList children = element.getChildNodes();
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals("rect")) {
                    valueToCheck = Integer.parseInt(getAttribute(child.getAttributes(), "height"));
                    rightRectHeight = (valueToCheck == (generalHeight - margins.getTopMargin() - margins.getBottomMargin()));

                    valueToCheck = Integer.parseInt(getAttribute(child.getAttributes(), "width"));
                    rightRectWidth = (valueToCheck == (generalWidth - margins.getLeftMargin() - margins.getRightMargin()));

                    return rightRectHeight && rightRectWidth;
                }
            }
        }
        return false;
    }

    private static boolean checkMarginsConstraints(Node element, Margins margins) {
        Node parent = getFirstParentWithConstraints(element);

        if (parent == null) {
            return false;
        }

        NodeList constraints = getConstraints(parent);
        String elementId = getAttribute(element.getAttributes(), "id");

        boolean rightTopConstraint = false;
        boolean rightBottomConstraint = false;
        boolean rightRightConstraint = false;
        boolean rightLeftConstraint = false;

        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedTop(elementId) &&
                    c.constant.equals(margins.getTopMargin()) &&
                    c.priority == 950) {
                rightTopConstraint = true;
            }
            if (c.isAlignedBottom(elementId) &&
                    c.constant.equals(margins.getBottomMargin()) &&
                    c.priority == 900) {
                rightBottomConstraint = true;
            }
            if (c.isAlignedLeft(elementId) &&
                    c.constant.equals(margins.getLeftMargin()) &&
                    c.priority == 950) {
                rightLeftConstraint = true;
            }
            if (c.isAlignedRight(elementId) &&
                    c.constant.equals(margins.getRightMargin()) &&
                    c.priority == 900) {
                rightRightConstraint = true;
            }
        }

        return rightTopConstraint && rightBottomConstraint
                && rightRightConstraint && rightLeftConstraint;
    }

    public static boolean checkMargins(Node element, ElementScale scale, Margins margins) {
        boolean rightRectMargins = true;
        boolean allMarginConstraintsSet;

        if (scale == ElementScale.FILL_PARENT) {
            rightRectMargins = checkRectMargins(element, DEFAULT_VIEW_WIDTH, DEFAULT_VIEW_HEIGHT, margins);
        }

        // Check if all constraints set
        allMarginConstraintsSet = checkMarginsConstraints(element, margins);

        return rightRectMargins && allMarginConstraintsSet;
    }

    public static boolean checkEqualConstraints(Node firstElement, Node secondElement, boolean checkWidthConstraint) {
        Element firstParent = getFirstParentWithConstraints(firstElement);
        Element secondParent = getFirstParentWithConstraints(secondElement);

        if(firstParent == null || secondParent == null ||
                !firstParent.getAttribute("id").equals(secondParent.getAttribute("id"))) {
            return false;
        }

        if (checkWidthConstraint) {
            return checkWidthEqualConstraints(firstParent, firstElement, secondElement);
        } else {
            return checkHeightEqualConstraints(firstParent, firstElement, secondElement);
        }
    }


    private static boolean checkWidthEqualConstraints(Element parent, Node firstElement, Node secondElement) {
        String firstElementId = getAttribute(firstElement.getAttributes(), "id");
        String secondElementId = getAttribute(secondElement.getAttributes(), "id");
        NodeList constraints = getConstraints(parent);

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.hasEqualWidth(firstElementId, secondElementId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkHeightEqualConstraints(Element parent, Node firstElement, Node secondElement) {
        String firstElementId = getAttribute(firstElement.getAttributes(), "id");
        String secondElementId = getAttribute(secondElement.getAttributes(), "id");
        NodeList constraints = getConstraints(parent);

        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.hasEqualHeight(firstElementId, secondElementId)) {
                return true;
            }
        }
        return false;
    }

    public static Node getChildTag(Node element, String tagName) {
        NodeList childNodes = element.getChildNodes();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node child = childNodes.item(i);
                if (child.getNodeName().equals(tagName)) {
                    return child;
                }
            }
        }

        return null;
    }

    public static boolean checkSegue(Node element, String segueKind) {
        Node connectionsTag = getChildTag(element, "connections");

        if (connectionsTag == null) {
            return false;
        }

        Node segueTag = getChildTag(connectionsTag, "segue");

        if (segueTag == null) {
            return false;
        }

        return hasRightSegue(segueTag, segueKind);
    }

    private static boolean hasRightSegue(Node segue, String segueKind) {
        ArrayList<String> viewControllersIds = new ArrayList<String>();

        Document doc = segue.getOwnerDocument();
        NodeList viewControllers = doc.getElementsByTagName("viewController");
        NodeList tableViewControllers = doc.getElementsByTagName("tableViewController");

        for (int i = 0; i < (viewControllers == null ? 0 : viewControllers.getLength()); i++) {
            viewControllersIds.add(getNodeId(viewControllers.item(i)));
        }
        for (int i = 0; i < (tableViewControllers == null ? 0 : tableViewControllers.getLength()); i++) {
            viewControllersIds.add(getNodeId(tableViewControllers.item(i)));
        }

        Node parentViewController = getParentController(segue);

        if (parentViewController == null) {
            return false;
        }

        String parentViewControllerId = getNodeId(parentViewController);

        NamedNodeMap attrs = segue.getAttributes();
        if (attrs != null &&
                viewControllersIds.contains(attrs.getNamedItem("destination").getNodeValue()) &&
                !parentViewControllerId.equals(attrs.getNamedItem("destination").getNodeValue()) &&
                attrs.getNamedItem("kind").getNodeValue().equals(segueKind)) {

            if (!parentViewController.getNodeName().equals("navigationController")) {
                return true;
            } else if (attrs.getNamedItem("relationship").getNodeValue().equals("rootViewController")) {
                return true;
            }
            
        }

        return false;
    }

    private static Node getParentController(Node element) {
        List<NodeAndId> hierarchy = getNodeHierarchy(element);
        Node parentController = null;
        for (NodeAndId node : hierarchy) {
            if (!node.n.isSameNode(element) && node.n.getNodeType() == Node.ELEMENT_NODE &&
                    node.n.getNodeName().contains("Controller")) {
                parentController = node.n;
                break;
            }
        }

        return parentController;
    }

    public static boolean checkSegueIdentifier(Node element, String segueIdentifier) {
        Node connectionsTag = getChildTag(element, "connections");

        if (connectionsTag == null) {
            return false;
        }

        Node segueTag = getChildTag(connectionsTag, "segue");

        if (segueTag == null) {
            return false;
        }


        NamedNodeMap segueAttributes = segueTag.getAttributes();
        if (segueAttributes.getNamedItem("identifier").getNodeValue().equals(segueIdentifier)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean hasRightIBOutlet(Node element, Node outlet, String property) {
        String destinationId = getNodeId(element);

        NamedNodeMap attrs = outlet.getAttributes();
        if (attrs != null &&
                attrs.getNamedItem("destination").getNodeValue().equals(destinationId) &&
                attrs.getNamedItem("property").getNodeValue().equals(property)) {
            return true;
        }

        return false;
    }

    public static boolean checkIBOutlet(Node element, String property) {
        Node viewController = getParentController(element);

        if (viewController == null) {
            return false;
        }

        Node connectionsTag = getChildTag(viewController, "connections");

        if (connectionsTag == null) {
            return false;
        }

        Node outletTag = getChildTag(connectionsTag, "outlet");

        if (outletTag == null) {
            return false;
        }

        return hasRightIBOutlet(element, outletTag, property);
    }

    private static boolean hasRightIBAction(Node action, String eventType, String selector) {
        Node viewController = getParentController(action);

        if (viewController == null) {
            return false;
        }

        String destinationId = getNodeId(viewController);

        NamedNodeMap attrs = action.getAttributes();
        if (attrs != null &&
                attrs.getNamedItem("destination").getNodeValue().equals(destinationId) &&
                attrs.getNamedItem("eventType").getNodeValue().equals(eventType) &&
                attrs.getNamedItem("selector").getNodeValue().equals(selector)) {
            return true;
        }

        return false;
    }

    public static boolean checkIBAction(Node element, String eventType, String selector) {
        Node connectionsTag = getChildTag(element, "connections");

        if (connectionsTag == null) {
            return false;
        }

        Node actionTag = getChildTag(connectionsTag, "action");

        return hasRightIBAction(actionTag, eventType, selector);
    }

    public static boolean checkRect(Node element, Rect rect) {
        Node rectTag = getChildTag(element, "rect");
        NamedNodeMap attrs = rectTag.getAttributes();

        String x = Integer.toString(rect.x);
        String y = Integer.toString(rect.y);
        String height = Integer.toString(rect.height);
        String width = Integer.toString(rect.width);

        if (attrs.getNamedItem("x").getNodeValue().equals(x) &&
                attrs.getNamedItem("y").getNodeValue().equals(y) &&
                attrs.getNamedItem("height").getNodeValue().equals(height) &&
                attrs.getNamedItem("width").getNodeValue().equals(width)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean checkGravity(Node element, Orientation orientation, Gravity gravity) {
        Element parent = getFirstParentWithConstraints(element);

        if (parent == null) {
            return false;
        }

        NodeList constraints = getConstraints(parent);

        if (constraints == null) {
            return false;
        }

        String elementId = getNodeId(element);
        String parentId = getNodeId(parent);

        if (orientation == Orientation.VERTICAL) {

            switch (gravity) {
                case RIGHT:
                    return checkGravityRight(elementId, parentId, constraints);
                case LEFT:
                    return checkGravityLeft(elementId, parentId, constraints);
                case CENTER:
                    return checkGravityCenterX(elementId, parentId, constraints);
            }

        } else if (orientation == Orientation.HORIZONTAL) {

            switch (gravity) {
                case TOP:
                    return checkGravityTop(elementId, parentId, constraints);
                case BOTTOM:
                    return checkGravityBottom(elementId, parentId, constraints);
                case CENTER:
                    return checkGravityCenterY(elementId, parentId, constraints);
            }
        }

        return false;
    }

    private static boolean checkGravityRight(String elementId, String parentId, NodeList constraints) {
        boolean isAlignedTopInside = false;
        boolean isAlignedRightInside = false;

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedTop(elementId) &&
                    c.priority == 950) {
                isAlignedTopInside = true;
            }
            if (c.isAlignedRightInside(parentId, elementId) &&
                    c.priority == 1000) {
                isAlignedRightInside = true;
            }
        }

        return isAlignedTopInside && isAlignedRightInside;
    }

    private static boolean checkGravityLeft(String elementId, String parentId, NodeList constraints) {
        boolean isAlignedTopInside = false;
        boolean isAlignedLeftInside = false;

        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedTop(elementId) &&
                    c.priority == 950) {
                isAlignedTopInside = true;
            }
            if (c.isAlignedLeftInside(parentId, elementId) &&
                    c.priority == 1000) {
                isAlignedLeftInside = true;
            }
        }

        return isAlignedTopInside && isAlignedLeftInside;
    }

    private static boolean checkGravityTop(String elementId, String parentId, NodeList constraints) {
        boolean isAlignedLeftInside = false;
        boolean isAlignedTopInside = false;

        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedLeftInside(parentId, elementId) &&
                    c.priority == 950) {
                isAlignedLeftInside = true;
            }
            if (c.isAlignedTopInside(parentId, elementId) &&
                    c.priority == 999) {
                isAlignedTopInside = true;
            }
        }

        return isAlignedLeftInside && isAlignedTopInside;
    }

    private static boolean checkGravityBottom(String elementId, String parentId, NodeList constraints) {
        boolean isAlignedRightInside = false;
        boolean isAlignedBottomInside = false;

        for (int i = 0; i < (constraints == null ? 0 : constraints.getLength()); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedRightInside(parentId, elementId) &&
                    c.priority == 900) {
                isAlignedRightInside = true;
            }
            if (c.isAlignedBottomInside(parentId, elementId) &&
                    c.priority == 999) {
                isAlignedBottomInside = true;
            }
        }

        return isAlignedRightInside && isAlignedBottomInside;
    }


    private static boolean checkGravityCenterX(String elementId, String parentId, NodeList constraints) {
        boolean isAlignedCenterInside = false;

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedCenterXInside(parentId, elementId) &&
                    c.priority == 1000 ) {
                isAlignedCenterInside = true;
            }
        }

        return isAlignedCenterInside;
    }

    private static boolean checkGravityCenterY(String elementId, String parentId, NodeList constraints) {
        boolean isAlignedCenterInside = false;

        if (constraints == null) {
            return false;
        }

        for (int i = 0; i < constraints.getLength(); i++) {
            Constraint c = new Constraint(constraints.item(i));
            if (c.isAlignedCenterYInside(parentId, elementId) &&
                    c.priority == 999) {
                isAlignedCenterInside = true;
            }
        }

        return isAlignedCenterInside;
    }

    private static boolean checkDrawableInResources(Document doc, String drawableFileName) {
        Node resourcesNode = doc.getElementsByTagName("resources").item(0);
        NodeList resourcesList = resourcesNode.getChildNodes();

        if (resourcesList == null) {
            return false;
        }

        for (int i = 0; i < resourcesList.getLength(); i++) {
            Node resource = resourcesList.item(i);
            NamedNodeMap attrs = resource.getAttributes();
            if (resource.getNodeName().equals("image") &&
                    attrs.getNamedItem("name").getNodeValue().equals(drawableFileName)) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkDrawableInElement(Node element, String drawableFileName) {

        if (element.getNodeName().equals("button") && element.getNodeType() == Node.ELEMENT_NODE) {
            NodeList children = element.getChildNodes();
            for (int i = 0; i < (children == null ? 0 : children.getLength()); i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals("state") &&
                        getAttribute(child.getAttributes(), "image").equals(drawableFileName)) {
                    return true;
                }
            }
        }
        if (element.getNodeName().equals("barButtonItem") && element.getNodeType() == Node.ELEMENT_NODE &&
                getAttribute(element.getAttributes(), "image").equals(drawableFileName)) {
            return true;
        }
        if (element.getNodeName().equals("imageView") && element.getNodeType() == Node.ELEMENT_NODE &&
                getAttribute(element.getAttributes(), "image").equals(drawableFileName)) {
            return true;
        }

        return false;
    }

    public static boolean checkDrawable(Node element, String drawableFileName) {

        boolean drawableInResouces = checkDrawableInResources(element.getOwnerDocument(), drawableFileName);

        boolean drawableInElement = checkDrawableInElement(element, drawableFileName);

        return drawableInResouces && drawableInElement;
    }

    public static boolean checkRestorationIdentifier(Node element, String restorationIdentifier) {
        String result = getAttribute(element.getAttributes(), "restorationIdentifier");
        if (result.equals(restorationIdentifier)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStoryboardIdentifier(Node element, String storyboardIdentifier) {
        String result = getAttribute(element.getAttributes(), "storyboardIdentifier");
        if (result.equals(storyboardIdentifier)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkReuseIdentifier(Node element, String restorationIdentifier) {
        String result = getAttribute(element.getAttributes(), "reuseIdentifier");
        if (result.equals(restorationIdentifier)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkTableViewCellStyle(Node element, String style) {
        String result = getAttribute(element.getAttributes(), "style");

        String styleName = "";

        if (style.equals("Basic")) styleName = "IBUITableViewCellStyleDefault";
        if (style.equals("RightDetail")) styleName = "IBUITableViewCellStyleValue1";
        if (style.equals("LeftDetail")) styleName = "IBUITableViewCellStyleValue2";
        if (style.equals("Subtitle")) styleName = "IBUITableViewCellStyleSubtitle";


        if (result.equals(styleName)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkProgressViewProgress(Node element, double progress) {
        String result = getAttribute(element.getAttributes(), "progress");
        if (Double.parseDouble(result) == progress) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPageControlPagesNumber(Node element, int numberOfPages) {
        String result = getAttribute(element.getAttributes(), "numberOfPages");
        if (Integer.parseInt(result) == numberOfPages) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPageControlCurrentPage(Node element, int currentPage) {
        String result = getAttribute(element.getAttributes(), "currentPage");
        if (Integer.parseInt(result) == currentPage) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkElementWidth(Node element, int width) {
        Node rect = getChildTag(element, "rect");
        String result = getAttribute(rect.getAttributes(), "width");
        if (Integer.parseInt(result) == width) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkCustomClass(Node element, String customClassName) {
        String result = getAttribute(element.getAttributes(), "customClass");
        if (result.equals(customClassName)) {
            return true;
        } else {
            return false;
        }
    }

}
