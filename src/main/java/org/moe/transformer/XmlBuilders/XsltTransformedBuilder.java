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

package org.moe.transformer.XmlBuilders;

import org.moe.transformer.DeviceInfo.Display;
import org.moe.transformer.ElementsParser;
import org.moe.transformer.Layouts.Action;
import org.moe.transformer.Layouts.Constraint;
import org.moe.transformer.Layouts.LinearLayout;
import org.moe.transformer.LayoutInfo.eLinearLayoutOrientation;
import org.moe.transformer.Layouts.Outlet;
import org.moe.transformer.Layouts.Rect;
import org.moe.transformer.Layouts.InternalConstraint;
import org.moe.transformer.LayoutInfo.eViewSizeType;
import org.moe.transformer.Layouts.Segue;
import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;
import org.w3c.dom.Document;
import org.moe.transformer.Layouts.View;
import org.moe.transformer.Layouts.ViewGroup;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import org.w3c.dom.Element;

import java.io.File;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XsltTransformedBuilder {

    private static Map<String, String> viewControllersMap = new HashMap<String, String>();
    private static String controllerId = null;

    private static final Map<String, Integer> defaultElementsWidths;
    private static final Map<String, Integer> defaultElementsHeights;

    static {
        Map<String, Integer> wMap = new HashMap<String, Integer>();
        wMap.put("activityIndicatorView", 20);
        wMap.put("datePicker", 600);
        wMap.put("mapView", 240);
        wMap.put("navigationBar", 600);
        wMap.put("pageControl", 39);
        wMap.put("pickerView", 414);
        wMap.put("progressView", 150);
        wMap.put("searchBar", 600);
        wMap.put("segmentedControl", 123);
        wMap.put("slider", 118);
        wMap.put("stepper", 94);
        wMap.put("switch", 51);
        wMap.put("tabBar", 150);
        wMap.put("toolbar", 600);
        defaultElementsWidths = Collections.unmodifiableMap(wMap);

        Map<String, Integer> hMap = new HashMap<String, Integer>();
        hMap.put("activityIndicatorView", 20);
        hMap.put("datePicker", 162);
        hMap.put("mapView", 128);
        hMap.put("navigationBar", 44);
        hMap.put("pageControl", 37);
        hMap.put("pickerView", 162);
        hMap.put("progressView", 2);
        hMap.put("searchBar", 44);
        hMap.put("segmentedControl", 29);
        hMap.put("slider", 31);
        hMap.put("stepper", 29);
        hMap.put("switch", 31);
        hMap.put("tabBar", 150);
        hMap.put("toolbar", 44);
        defaultElementsHeights = Collections.unmodifiableMap(hMap);
    }

    private static int getDefaultElementWidthByType(String elementType) {

        if(elementType.equals("activityIndicatorView")) return defaultElementsWidths.get("activityIndicatorView");
        else if(elementType.equals("datePicker"))return defaultElementsWidths.get("datePicker");
        else if(elementType.equals("mapView"))return defaultElementsWidths.get("mapView");
        else if(elementType.equals("navigationBar"))return defaultElementsWidths.get("navigationBar");
        else if(elementType.equals("pickerView"))return defaultElementsWidths.get("pickerView");
        else if(elementType.equals("progressView"))return defaultElementsWidths.get("progressView");
        else if(elementType.equals("searchBar"))return defaultElementsWidths.get("searchBar");
        else if(elementType.equals("segmentedControl"))return defaultElementsWidths.get("segmentedControl");
        else if(elementType.equals("slider"))return defaultElementsWidths.get("slider");
        else if(elementType.equals("stepper"))return defaultElementsWidths.get("stepper");
        else if(elementType.equals("switch"))return defaultElementsWidths.get("switch");
        else if(elementType.equals("tabBar"))return defaultElementsWidths.get("tabBar");
        else if(elementType.equals("button"))return -1;
        else if(elementType.equals("label"))return -1;
        else if(elementType.equals("textField"))return -1;
        else if(elementType.equals("pageControl"))return -1;
        return -1;
    }

    private static int getDefaultElementHeightByType(String elementType) {

        if(elementType.equals("activityIndicatorView")) return defaultElementsHeights.get("activityIndicatorView");
        else if(elementType.equals("datePicker"))return defaultElementsHeights.get("datePicker");
        else if(elementType.equals("mapView"))return defaultElementsHeights.get("mapView");
        else if(elementType.equals("navigationBar"))return defaultElementsHeights.get("navigationBar");
        else if(elementType.equals("pageControl"))return defaultElementsHeights.get("pageControl");
        else if(elementType.equals("pickerView"))return defaultElementsHeights.get("pickerView");
        else if(elementType.equals("progressView"))return defaultElementsHeights.get("progressView");
        else if(elementType.equals("searchBar"))return defaultElementsHeights.get("searchBar");
        else if(elementType.equals("segmentedControl"))return defaultElementsHeights.get("segmentedControl");
        else if(elementType.equals("slider"))return defaultElementsHeights.get("slider");
        else if(elementType.equals("stepper"))return defaultElementsHeights.get("stepper");
        else if(elementType.equals("switch"))return defaultElementsHeights.get("switch");
        else if(elementType.equals("tabBar"))return defaultElementsHeights.get("tabBar");
        else if(elementType.equals("button"))return -1;
        else if(elementType.equals("label"))return -1;
        else if(elementType.equals("textField"))return -1;
        return -1;
    }

    public static List<ViewGroup> BuildMultiViews(Document document, Display display){
        Rect rect = new Rect();
        rect.width = display.width;
        rect.height = display.height;
        List<ViewGroup> screans = new ArrayList();

        NodeList sceneList = document.getElementsByTagName("scene");
        for(int i=0; i<sceneList.getLength(); i++){
            Element scene = (Element)sceneList.item(i);
            String viewName = scene.getAttribute("viewName");
            scene.removeAttribute("viewName");
            Element viewController = (Element) ElementsParser.getViewControllerInScene((Element)scene);
            if(viewController != null) {
                String viewControllerId = viewController.getAttributes().getNamedItem("id").getTextContent();
                viewControllersMap.put(viewName, viewControllerId);
            }
        }

        for(int i=0; i<sceneList.getLength(); i++) {
            Element scene = (Element) sceneList.item(i);
            Element viewController = (Element) ElementsParser.getViewControllerInScene((Element) scene);
            String typeController = viewController.getNodeName();

            if (typeController.equals("viewController")) {

                Element rootView = (Element) ElementsParser.getRootViewInScene((Element) scene);
                NodeList layoutChildNodes = ElementsParser.getSubViewElementsFromView(rootView);
                eLinearLayoutOrientation orientation = ElementsParser.getLinearLayoutOrientation(rootView);
                String elementId = rootView.getAttributes().getNamedItem("id").getTextContent();
                controllerId = viewController.getAttributes().getNamedItem("id").getTextContent();
                LinearLayout viewGroup = new LinearLayout(rect, orientation);
                viewGroup.setRect(new Rect(0, 0, display.width, display.height));
                viewGroup.setId(elementId);
                rootView.removeAttribute("android_width");
                rootView.removeAttribute("android_height");
                rootView.removeAttribute("android_marginLeft");
                rootView.removeAttribute("android_marginRight");
                rootView.removeAttribute("android_marginTop");
                rootView.removeAttribute("android_marginBottom");
                rootView.removeAttribute("android_text");
                rootView.removeAttribute("android_textSize");

                Element navigationItem = (Element)ElementsParser.getNavigationItemInViewController(viewController);
                if(navigationItem != null) {
                    if(layoutChildNodes != null && layoutChildNodes.getLength()>0){
                        int navigationItemShift = 65;
                        String name = layoutChildNodes.item(0).getNodeName();
                        Node mtopNode = layoutChildNodes.item(0).getAttributes().getNamedItem("android_marginTop");
                        String mtop = null;
                        if (mtopNode != null) {
                            mtop = mtopNode.getTextContent();
                            mtop = mtop.replaceAll("[^0-9]", "");
                            if(!mtop.isEmpty())
                                navigationItemShift = navigationItemShift + Integer.parseInt(mtop);
                        }
                        Element elem = (Element)layoutChildNodes.item(0);
                        elem.setAttribute("android_marginTop", String.valueOf(navigationItemShift));
                    }
                }

                Node segueTypeAttribute = rootView.getAttributes().getNamedItem("segue_type");
                Node segueDestinationAttribute = rootView.getAttributes().getNamedItem("segue_destination");
                Node segueIdentifierAttribute = rootView.getAttributes().getNamedItem("segue_identifier");
                String segue_type = null;
                String segue_destination = null;
                if (segueTypeAttribute != null) {
                    if (segueDestinationAttribute != null) {
                        segue_type = segueTypeAttribute.getTextContent();
                        segue_destination = segueDestinationAttribute.getTextContent();

                        if (segue_type != null && segue_destination != null)
                            if (!segue_type.isEmpty() && !segue_destination.isEmpty()) {
                                if (viewControllersMap.get(segue_destination) != null) {
                                    Segue s = new Segue();
                                    s.kind = segue_type;
                                    s.destination = viewControllersMap.get(segue_destination);
                                    if(segueIdentifierAttribute != null){
                                        String segueIdentifier = segueIdentifierAttribute.getTextContent();
                                        if(segueIdentifier != null) s.segue_identifier = segueIdentifier;
                                    }
                                    viewGroup.addSegue(s);
                                }
                            }
                    }
                }
                rootView.removeAttribute("segue_type");
                rootView.removeAttribute("segue_destination");


                _build(viewGroup, layoutChildNodes, document);
                bulidRects(viewGroup);
                if (viewGroup instanceof LinearLayout) {
                    LinearLayout vg = (LinearLayout) viewGroup;
                    vg.buildViewsPositions();
                    vg.buildConstrains();
                }
                for (int j = 0; j < viewGroup.getChildViewCount(); j++) {
                    if (viewGroup.getChildView(j) instanceof LinearLayout) {
                        LinearLayout vg = (LinearLayout) viewGroup.getChildView(j);
                        vg.buildViewsPositions();
                        vg.buildConstrains();
                    }
                }

                screans.add(viewGroup);
            }

            else if (typeController.equals("navigationController")) {

                LinearLayout vg = new LinearLayout(600, 600, eLinearLayoutOrientation.VERTICAL);
                Node segueTypeAttribute = viewController.getAttributes().getNamedItem("segue_type");
                Node segueDestinationAttribute = viewController.getAttributes().getNamedItem("segue_destination");
                Node segueIdentifierAttribute = viewController.getAttributes().getNamedItem("segue_identifier");
                String segue_type = null;
                String segue_destination = null;
                if (segueTypeAttribute != null) {
                    if (segueDestinationAttribute != null) {
                        segue_type = segueTypeAttribute.getTextContent();
                        segue_destination = segueDestinationAttribute.getTextContent();

                        if (segue_type != null && segue_destination != null)
                            if (!segue_type.isEmpty() && !segue_destination.isEmpty()) {
                                if (viewControllersMap.get(segue_destination) != null) {
                                    Segue s = new Segue();
                                    s.kind = segue_type;
                                    s.destination = viewControllersMap.get(segue_destination);
                                    if(segueIdentifierAttribute != null){
                                        String segueIdentifier = segueIdentifierAttribute.getTextContent();
                                        if(segueIdentifier != null) s.segue_identifier = segueIdentifier;
                                    }
                                    vg.addSegue(s);
                                }
                            }
                    }
                }
                viewController.removeAttribute("segue_type");
                viewController.removeAttribute("segue_destination");
                screans.add(vg);
            }

            else if (typeController.equals("tableViewController")) {

                LinearLayout viewGroup = new LinearLayout(600, 600, eLinearLayoutOrientation.VERTICAL);
                NodeList tableViewCellList = ElementsParser.getTableViewCellElementsFromTableViewController(viewController);
                int l = tableViewCellList.getLength();
                for(int j=0; j<l; j++) {
                    Element tableViewCell = (Element)tableViewCellList.item(j);
                    String tableViewCellHeight = tableViewCell.getAttribute("android_height");
                    if(!tableViewCellHeight.isEmpty() && tableViewCellHeight != null)
                        tableViewCellHeight = tableViewCellHeight.replaceAll("[^0-9]", "");
                    else tableViewCellHeight = "40";
                    LinearLayout tableViewCellViewGroup = new LinearLayout(600, Integer.parseInt(tableViewCellHeight), eLinearLayoutOrientation.HORIZONTAL);



                    if(tableViewCell != null) {
                        //tableViewCellContentView
                        NodeList tableViewCellContentViewList = tableViewCell.getElementsByTagName("tableViewCellContentView");
                        if(tableViewCellContentViewList.getLength() > 0){
                            Element tableViewCellContentView = (Element)tableViewCellContentViewList.item(0);

                            tableViewCellViewGroup.setId(tableViewCellContentView.getAttribute("id"));
                            String cellSegueType = tableViewCell.getAttribute("segue_type");
                            String cellSegueDestination = tableViewCell.getAttribute("segue_destination");
                            String segueIdentifier = tableViewCell.getAttribute("segue_destination");
                            if (cellSegueType != null && cellSegueDestination != null)
                                if (!cellSegueType.isEmpty() && !cellSegueDestination.isEmpty()) {
                                    if (viewControllersMap.get(cellSegueDestination) != null) {
                                        Segue tableViewCellSegue = new Segue();
                                        tableViewCellSegue.kind = cellSegueType;
                                        tableViewCellSegue.destination = viewControllersMap.get(cellSegueDestination);
                                        if(segueIdentifier != null)tableViewCellSegue.segue_identifier = segueIdentifier;
                                        tableViewCellViewGroup.addSegue(tableViewCellSegue);
                                    }
                                }

                            NodeList subviewsNodes = tableViewCell.getElementsByTagName("subviews");
                            if(subviewsNodes.getLength() > 0) {
                                Element sv = (Element) subviewsNodes.item(0);
                                if (sv != null) {
                                    NodeList layoutChildNodes = sv.getChildNodes();
                                    if (layoutChildNodes != null) {
                                        _build(tableViewCellViewGroup, layoutChildNodes, document);
                                        bulidRects(tableViewCellViewGroup);
                                        tableViewCellViewGroup.buildViewsPositions();
                                        tableViewCellViewGroup.buildConstrains();

                                        for (int k = 0; k < tableViewCellViewGroup.getChildViewCount(); k++) {
                                            if (tableViewCellViewGroup.getChildView(k) instanceof LinearLayout) {
                                                LinearLayout vg = (LinearLayout) tableViewCellViewGroup.getChildView(k);
                                                vg.buildViewsPositions();
                                                vg.buildConstrains();
                                            }
                                        }
                                    }
                                }

                                viewGroup.addChildView(tableViewCellViewGroup);
                            }

                        }

                    }

                }

                screans.add(viewGroup);
            }
        }

        return screans;
    }

    private static void bulidRects(LinearLayout viewGroup){
        eLinearLayoutOrientation orientation = viewGroup.getOrientation();
        List<Integer> flexibaleViewWidth = new ArrayList();
        List<Integer> flexibaleViewHeight = new ArrayList();
        int lenWidth = 0, lenHeight = 0;

        for(int i=0; i<viewGroup.getChildViewCount(); i++){

            if(viewGroup.getChildView(i).getWidth() == -1)
                flexibaleViewWidth.add(i);
            else {
                if(orientation == eLinearLayoutOrientation.HORIZONTAL)
                    lenWidth += viewGroup.getChildView(i).getWidth();
            }
            if(viewGroup.getChildView(i).getHeight() == -1){
                flexibaleViewHeight.add(i);
            }
            else{
                if(orientation == eLinearLayoutOrientation.VERTICAL)
                    lenHeight += viewGroup.getChildView(i).getHeight();
            }
        }


        for(int i=0; i<flexibaleViewWidth.size(); i++){
            int w = viewGroup.getWidth();
            if(orientation == eLinearLayoutOrientation.HORIZONTAL)
                w = (viewGroup.getWidth()-lenWidth)/flexibaleViewWidth.size();
            viewGroup.getChildView(flexibaleViewWidth.get(i)).setWidth(w);
        }
        for(int i=0; i<flexibaleViewHeight.size(); i++){
            int h = viewGroup.getHeight();
            if(orientation == eLinearLayoutOrientation.VERTICAL)
                h = (viewGroup.getHeight()-lenHeight)/flexibaleViewHeight.size();
            viewGroup.getChildView(flexibaleViewHeight.get(i)).setHeight(h);
        }

        for(int i=0; i<viewGroup.getChildViewCount(); i++) {
            if (viewGroup.getChildView(i) instanceof LinearLayout)
                bulidRects((LinearLayout) viewGroup.getChildView(i));
        }
    }

    private static void buldMargins(LinearLayout viewGroup) {
        for(int i=0; i<viewGroup.getChildViewCount(); i++) {
            View v = viewGroup.getChildView(i);
            Rect oldr = v.getRect();

            int shift_x = (v.getMarginLeft() + v.getMarginRight())/2;
            int shift_y = (v.getMarginTop() + v.getMarginBottom())/2;
            v.setRect(new Rect(oldr.x + shift_x, oldr.y + shift_y, oldr.width - shift_x*2, oldr.height - shift_y));
            if (viewGroup.getChildView(i) instanceof LinearLayout)
                buldMargins((LinearLayout) viewGroup.getChildView(i));
        }
    }

    private static ViewGroup _build(ViewGroup viewGroup, NodeList layoutChildNodes, Document document){

        Map<String, String> imgMapFiles = getImageFilesMap(OSXStringProcessor.GetProjectDrawablePath());

        for (int i = 0; i < layoutChildNodes.getLength(); i++) {

            Element view = (Element) layoutChildNodes.item(i);
            if(view.getNodeName().equals("imageView")){
                NamedNodeMap imgNodeMap = view.getAttributes();
                String image_background = null;
                if(imgNodeMap != null) {
                    Node elem = imgNodeMap.getNamedItem("android_image_background");
                    if (elem != null){
                        image_background = elem.getTextContent();
                        if(image_background != null){
                            view.setAttribute("image", imgMapFiles.get(image_background));
                        }
                    }
                }

                continue;
            }

            int width=0;
            int height=0;
            eViewSizeType wViewSizeType = null;
            eViewSizeType hViewSizeType = null;
            Node elementNodeId = view.getAttributes().getNamedItem("id");
            if(elementNodeId == null) continue;
            String elementId = elementNodeId.getTextContent();
            //iboutlet
            String iboutlet = null;
            String ibaction = null;
            String segue_type = null;
            String segue_destination = null;
            String gravity = null;
            String segue_identifier = null;
            Node iboutletAttribute = view.getAttributes().getNamedItem("iboutlet");
            Node ibactionAttribute = view.getAttributes().getNamedItem("ibaction");
            Node segueTypeAttribute = view.getAttributes().getNamedItem("segue_type");
            Node segueDestinationAttribute = view.getAttributes().getNamedItem("segue_destination");
            Node segueIdentifierAttribute = view.getAttributes().getNamedItem("segue_identifier");
            Node gravityAttribute = view.getAttributes().getNamedItem("android_gravity");
            if(iboutletAttribute != null) iboutlet = iboutletAttribute.getTextContent();
            if(ibactionAttribute != null) ibaction = ibactionAttribute.getTextContent();
            if(segueTypeAttribute != null) segue_type = segueTypeAttribute.getTextContent();
            if(segueDestinationAttribute != null) segue_destination = segueDestinationAttribute.getTextContent();
            if(segueIdentifierAttribute != null) segue_identifier = segueIdentifierAttribute.getTextContent();
            if(gravityAttribute != null) gravity = gravityAttribute.getTextContent();
            view.removeAttribute("iboutlet");
            view.removeAttribute("ibaction");
            view.removeAttribute("segue_type");
            view.removeAttribute("segue_destination");
            view.removeAttribute("android_gravity");

            String elementWidthType = view.getAttributes().getNamedItem("android_width").getTextContent();
            String elementHeightType = view.getAttributes().getNamedItem("android_height").getTextContent();
            view.removeAttribute("android_width");
            view.removeAttribute("android_height");

            String marginLeft = null;
            String marginRight = null;
            String marginTop = null;
            String marginBottom = null;

            Node marginLeftAttribute = view.getAttributes().getNamedItem("android_marginLeft");
            Node marginRightAttribute = view.getAttributes().getNamedItem("android_marginRight");
            Node marginTopAttribute = view.getAttributes().getNamedItem("android_marginTop");
            Node marginBottomAttribute = view.getAttributes().getNamedItem("android_marginBottom");


            if(marginLeftAttribute != null) {
                marginLeft = marginLeftAttribute.getTextContent();
                marginLeft = marginLeft.replaceAll("[^0-9]", "");
            }if(marginRightAttribute != null) {
                marginRight = marginRightAttribute.getTextContent();
                marginRight = marginRight.replaceAll("[^0-9]", "");
            }if(marginTopAttribute != null) {
                marginTop = marginTopAttribute.getTextContent();
                marginTop = marginTop.replaceAll("[^0-9]", "");
            }if(marginBottomAttribute != null) {
                marginBottom = marginBottomAttribute.getTextContent();
                marginBottom = marginBottom.replaceAll("[^0-9]", "");
            }

            view.removeAttribute("android_marginLeft");
            view.removeAttribute("android_marginRight");
            view.removeAttribute("android_marginTop");
            view.removeAttribute("android_marginBottom");

            if(elementWidthType.equals("fill_parent")){
                width = -1;
                wViewSizeType = eViewSizeType.FILL_PARENT;
            }
            else if(elementWidthType.equals("match_parent")){
                width = -1;
                wViewSizeType = eViewSizeType.FILL_PARENT;
            }
            else if (elementWidthType.equals("wrap_content")) {
                width = getDefaultElementWidthByType(view.getNodeName());

                if (width == -1) {

                    Node androidTextNode = view.getAttributes().getNamedItem("android_text");
                    String elementText = null;
                    String elementTextSize = null;
                    if(androidTextNode != null) {
                        elementText = androidTextNode.getTextContent();
                        elementTextSize = view.getAttributes().getNamedItem("android_textSize").getTextContent();

                        elementTextSize = elementTextSize.replaceAll("[^0-9]", "");

                        width = calculateWidthByContent(elementText, elementTextSize);
                    }
                    else width = 100;

                    Node androidPagesNode = view.getAttributes().getNamedItem("numberOfPages");
                    if(androidPagesNode != null) {
                        String numberOfPages = androidPagesNode.getTextContent();
                        width = Integer.parseInt(numberOfPages) * 15;
                    }

                }

                wViewSizeType = eViewSizeType.FIX_SIZE;
            }
            else{
                elementWidthType = elementWidthType.replaceAll("[^0-9]", "");

                width = Integer.parseInt(elementWidthType);
                wViewSizeType = eViewSizeType.FIX_SIZE;
            }

            if(elementHeightType.equals("fill_parent")){
                height = -1;
                hViewSizeType = eViewSizeType.FILL_PARENT;
            }
            else if(elementHeightType.equals("match_parent")){
                height = -1;
                hViewSizeType = eViewSizeType.FILL_PARENT;
            }
            else if (elementHeightType.equals("wrap_content")) {
                height = getDefaultElementHeightByType(view.getNodeName());

                if (height == -1) {

                    Node androidTextNode = view.getAttributes().getNamedItem("android_text");
                    String elementTextSize = null;
                    if(androidTextNode != null) {
                        elementTextSize = view.getAttributes().getNamedItem("android_textSize").getTextContent();
                        elementTextSize = elementTextSize.replaceAll("[^0-9]", "");

                        height = calculateHeightByContent(elementTextSize);
                    }
                    else height = 50;

                }

                hViewSizeType = eViewSizeType.FIX_SIZE;
            }
            else{
                elementHeightType = elementHeightType.replaceAll("[^0-9]", "");

                height = Integer.parseInt(elementHeightType);
                hViewSizeType = eViewSizeType.FIX_SIZE;
            }

            if(view.getNodeName().equals("tableView")){

                for(int j=0; j<view.getChildNodes().getLength(); j++) {
                    Node tableNode = view.getChildNodes().item(j);
                    if(tableNode.getNodeName().equals("prototypes"))
                        for(int k=0; k<tableNode.getChildNodes().getLength(); k++){
                            Element prototypesNode = (Element)tableNode.getChildNodes().item(k);
                            if(prototypesNode.getNodeName().equals("tableViewCell")){
                                String tableSegueTypeStr = null;
                                String tableSegueDestinationStr = null;
                                Node tableSegueTypeAttribute = prototypesNode.getAttributes().getNamedItem("segue_type");
                                Node tableSegueDestinationAttribute = prototypesNode.getAttributes().getNamedItem("segue_destination");
                                Node tableSegueIdentifierAttribute = prototypesNode.getAttributes().getNamedItem("segue_identifier");
                                if(tableSegueTypeAttribute != null && tableSegueDestinationAttribute != null) {

                                    tableSegueTypeStr = tableSegueTypeAttribute.getTextContent();
                                    tableSegueDestinationStr = tableSegueDestinationAttribute.getTextContent();
                                    if(!tableSegueTypeStr.isEmpty() && !tableSegueDestinationStr.isEmpty()) {
                                        Element connectionsElementTableViewCell = document.createElement("connections");
                                        Element segueElement = document.createElement("segue");
                                        segueElement.setAttribute("id", OSXStringProcessor.CreateId());
                                        segueElement.setAttribute("kind", tableSegueTypeStr);
                                        segueElement.setAttribute("destination", viewControllersMap.get(tableSegueDestinationStr));
                                        if(tableSegueIdentifierAttribute != null){
                                            String tableSegueIdentifier = tableSegueIdentifierAttribute.getTextContent();
                                            if(tableSegueIdentifier != null)segueElement.setAttribute("identifier", tableSegueIdentifier);
                                        }
                                        connectionsElementTableViewCell.appendChild(segueElement);
                                        prototypesNode.appendChild(connectionsElementTableViewCell);
                                    }
                                }
                            }
                        }
                }
            }



            if(view.getNodeName().equals("view")) {
                eLinearLayoutOrientation orientation = ElementsParser.getLinearLayoutOrientation(view);
                LinearLayout vg = new LinearLayout(width, height, orientation);
                if(marginLeft != null)
                    if(!marginLeft.isEmpty())vg.setMarginLeft(Integer.parseInt(marginLeft));
                if(marginRight != null)
                    if(!marginRight.isEmpty())vg.setMarginRight(Integer.parseInt(marginRight));
                if(marginTop != null)
                    if(!marginTop.isEmpty())vg.setMarginTop(Integer.parseInt(marginTop));
                if(marginBottom != null)
                    if(!marginBottom.isEmpty())vg.setMarginBottom(Integer.parseInt(marginBottom));

                if(iboutlet != null)
                    if(!iboutlet.isEmpty()){
                        Outlet o = new Outlet();
                        o.property = iboutlet;
                        o.destination = vg.getId();
                        vg.addOutlet(o);
                    }

                if(segue_type != null && segue_destination != null)
                    if(!segue_type.isEmpty() && !segue_destination.isEmpty()){
                        Segue s = new Segue();
                        s.kind = segue_type;
                        s.destination = viewControllersMap.get(segue_destination);
                        if(segue_identifier != null) s.segue_identifier = segue_identifier;
                        vg.addSegue(s);
                    }

                if(ibaction != null)
                    if(!ibaction.isEmpty()){
                        String[] actions = ibaction.split("\\|");
                        for (int k=0; k<actions.length; k++) {
                            String []event_handler = actions[k].split("-");
                            if(event_handler.length == 2) {
                                Action a = new Action();
                                a.eventType = event_handler[0];
                                a.selector = event_handler[1];// + ":";
                                a.destination = controllerId;
                                vg.addAction(a);
                            }
                        }
                    }

                vg.setId(elementId);
                vg.setWidthSizeType(wViewSizeType);
                vg.setHeightSizeType(hViewSizeType);
                vg.setGravity(gravity);

                NodeList childNodes = ElementsParser.getSubViewElementsFromView(view);
                _build(vg, childNodes, document);
                viewGroup.addChildView(vg);
            }

            else{
                View v = new View(width, height);
                v.setId(elementId);
                if(marginLeft != null)
                    if(!marginLeft.isEmpty())v.setMarginLeft(Integer.parseInt(marginLeft));
                if(marginRight != null)
                    if(!marginRight.isEmpty())v.setMarginRight(Integer.parseInt(marginRight));
                if(marginTop != null)
                    if(!marginTop.isEmpty())v.setMarginTop(Integer.parseInt(marginTop));
                if(marginBottom != null)
                    if(!marginBottom.isEmpty())v.setMarginBottom(Integer.parseInt(marginBottom));


                if(iboutlet != null)
                    if(!iboutlet.isEmpty()){
                        Outlet o = new Outlet();
                        o.property = iboutlet;
                        o.destination = v.getId();
                        v.addOutlet(o);
                    }

                if(segue_type != null && segue_destination != null)
                    if(!segue_type.isEmpty() && !segue_destination.isEmpty()){
                        Segue s = new Segue();
                        s.kind = segue_type;
                        s.destination = viewControllersMap.get(segue_destination);
                        if(segue_identifier != null) s.segue_identifier = segue_identifier;
                        v.addSegue(s);
                    }

                if(ibaction != null)
                    if(!ibaction.isEmpty()){

                        String[] actions = ibaction.split("\\|");
                        for (int k=0; k<actions.length; k++) {
                            String []event_handler = actions[k].split("-");
                            if(event_handler.length == 2) {
                                Action a = new Action();
                                a.eventType = event_handler[0];
                                a.selector = event_handler[1];// + ":";
                                a.destination = controllerId;
                                v.addAction(a);
                            }
                        }
                    }

                v.setWidthSizeType(wViewSizeType);
                v.setHeightSizeType(hViewSizeType);
                v.setGravity(gravity);
                viewGroup.addChildView(v);
            }
        }

        return viewGroup;
    }



    public static int calculateWidthByContent(String content, String fontSize){
        try{
            int textwidth = content.length() * (Integer.parseInt(fontSize) / 2) + 5;
            return textwidth;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int calculateHeightByContent(String fontSize){
        try{
            int textHeight = Integer.parseInt(fontSize)*2;
            return textHeight;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static String getHeightWrapContent(int fontSize){
        try{
            int h = fontSize*2;
            return Integer.toString(h);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return Integer.toString(30);
        }
    }

    public static Document GenXmlDocumentMultiViews(Document document, List<ViewGroup> viewGroupList){

        Map<String, String> imgFilesMap = getImageFilesMap(OSXStringProcessor.GetProjectDrawablePath());

        Element docElem = null;
        NodeList sceneList = document.getElementsByTagName("scene");
        if(viewGroupList.size() == sceneList.getLength()) {
            for (int l = 0; l < sceneList.getLength(); l++) {
                Node scene = sceneList.item(l);
                ViewGroup viewGroup = viewGroupList.get(l);
                String initialViewController = null;
                String viewControllerName = null;

                Element viewControllerElem = (Element)ElementsParser.getViewControllerInScene((Element) scene);

                if(viewControllerElem.getTagName().equals("navigationController")){
                    Element connectionsElementNavigationController = document.createElement("connections");

                    for (int j = 0; j < viewGroup.getSegueCount(); j++) {
                        Segue s = viewGroup.getSegue(j);
                        Element segueElement = document.createElement("segue");
                        segueElement.setAttribute("id", s.id);
                        segueElement.setAttribute("kind", s.kind);
                        segueElement.setAttribute("destination", s.destination);
                        if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                        segueElement.setAttribute("relationship", "rootViewController");
                        connectionsElementNavigationController.appendChild(segueElement);
                    }
                    viewControllerElem.appendChild(connectionsElementNavigationController);

                    initialViewController = viewControllerElem.getAttribute("initialViewController");
                    viewControllerName = viewControllerElem.getAttribute("viewController");
                    if (!viewControllerName.isEmpty() && !viewControllerName.equals("")) {
                        String[] viewControllerNameArr = viewControllerName.split("\\.");
                        if (viewControllerNameArr.length > 0)
                            viewControllerName = viewControllerNameArr[viewControllerNameArr.length - 1];
                    }

                    if (!viewControllerName.isEmpty() && !viewControllerName.equals(""))
                        viewControllerElem.setAttribute("customClass", viewControllerName);

                    if (initialViewController.equals("true")) {
                        NodeList docs = document.getElementsByTagName("document");
                        docElem = (Element) docs.item(0);
                        docElem.setAttribute("initialViewController", viewControllerElem.getAttribute("id"));
                    }
                }
                else if(viewControllerElem.getTagName().equals("tableViewController")){
                    Element tableView = (Element) ElementsParser.getTableViewFromTableViewController(viewControllerElem);
                    Element connectionsTableViewElement = document.createElement("connections");


                    if(tableView != null) {

                        initialViewController = viewControllerElem.getAttribute("initialViewController");
                        viewControllerName = viewControllerElem.getAttribute("viewController");
                        if (!viewControllerName.isEmpty() && !viewControllerName.equals("")) {
                            String[] viewControllerNameArr = viewControllerName.split("\\.");
                            if (viewControllerNameArr.length > 0)
                                viewControllerName = viewControllerNameArr[viewControllerNameArr.length - 1];
                        }

                        if (!viewControllerName.isEmpty() && !viewControllerName.equals(""))
                            viewControllerElem.setAttribute("customClass", viewControllerName);

                        if (initialViewController.equals("true")) {
                            NodeList docs = document.getElementsByTagName("document");
                            docElem = (Element) docs.item(0);
                            docElem.setAttribute("initialViewController", viewControllerElem.getAttribute("id"));
                        }

                        tableView.setAttribute("key", "view");
                        NodeList tableViewCellList = ElementsParser.getTableViewCellElementsFromTableViewController(tableView);
                        int tvList = tableViewCellList.getLength();
                        if (viewGroup.getChildViewCount() == tvList) {
                            for (int k = 0; k < tvList; k++) {
                                ViewGroup cellView = (ViewGroup) viewGroup.getChildView(k);
                                Element cellContentElement = (Element) ElementsParser.getTableViewCellContentViewInTableCell((Element) tableViewCellList.item(k));
                                Element tableViewCell = (Element) tableViewCellList.item(k);
                                tableViewCell.setAttribute("rowHeight", Integer.toString(cellView.getRect().height));

                                if(cellContentElement != null) {
                                    Element constrainsElement = ElementsParser.getConstraintsElementFromElement(cellContentElement);
                                    if(constrainsElement == null) constrainsElement = document.createElement("constraints");

                                    for (int i = 0; i < ((View) cellView).getInternalConstraintCount(); i++) {
                                        InternalConstraint ic = ((View) cellView).getInternalConstraint(i);
                                        Element internalConstraintElement = document.createElement("constraint");
                                        internalConstraintElement.setAttribute("id", ic.id);
                                        internalConstraintElement.setAttribute("firstAttribute", ic.getFirstAttribute());
                                        internalConstraintElement.setAttribute("priority", String.valueOf(ic.priority));
                                        if (ic.constant < 0) ic.constant = ic.constant * (-1);
                                        internalConstraintElement.setAttribute("constant", String.valueOf(ic.constant));
                                        constrainsElement.appendChild(internalConstraintElement);
                                    }

                                    Element connectionsTableViewCellElement = ElementsParser.getConnectionsElementFromElement(cellContentElement);
                                    if(connectionsTableViewCellElement == null) connectionsTableViewCellElement = document.createElement("connections");

                                    for (int j = 0; j < ((View) cellView).getSegueCount(); j++) {
                                        Segue s = ((View) cellView).getSegue(j);
                                        Element segueElement = document.createElement("segue");
                                        segueElement.setAttribute("id", s.id);
                                        segueElement.setAttribute("kind", s.kind);
                                        segueElement.setAttribute("destination", s.destination);
                                        if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                                        connectionsTableViewCellElement.appendChild(segueElement);
                                    }

                                    if (cellView instanceof LinearLayout) {
                                        LinearLayout ll = (LinearLayout) cellView;
                                        for (int j = 0; j < ll.getConstraintCount(); j++) {
                                            Constraint c = ll.getConstraint(j);
                                            Element constraintElement = document.createElement("constraint");

                                            constraintElement.setAttribute("id", c.id);
                                            constraintElement.setAttribute("firstItem", c.firstItemId);
                                            constraintElement.setAttribute("secondItem", c.secondItemId);
                                            constraintElement.setAttribute("firstAttribute", c.getFirstAttribute());
                                            constraintElement.setAttribute("secondAttribute", c.getSecondAttribute());
                                            constraintElement.setAttribute("priority", String.valueOf(c.priority));
                                            constraintElement.setAttribute("constant", String.valueOf(c.constant));
                                            constrainsElement.appendChild(constraintElement);
                                        }


                                    }
                                    tableViewCellList.item(k).appendChild(constrainsElement);
                                    tableViewCellList.item(k).appendChild(connectionsTableViewCellElement);

                                    Element connectionsElement = document.createElement("connections");
                                    for (int i = 0; i < ((View) cellView).getActionCount(); i++) {
                                        Action a = ((View) cellView).getAction(i);
                                        Element actionElement = document.createElement("action");
                                        actionElement.setAttribute("id", a.id);
                                        actionElement.setAttribute("selector", a.selector);
                                        actionElement.setAttribute("destination", a.destination);
                                        actionElement.setAttribute("eventType", a.eventType);
                                        connectionsElement.appendChild(actionElement);
                                    }

                                    tableViewCellList.item(k).appendChild(connectionsElement);
                                }
                                for (int i = 0; i < ((View) cellView).getOutletCount(); i++) {
                                    Outlet o = ((View) cellView).getOutlet(i);
                                    ElementsParser.addOutletToRootViewController(document, viewControllerElem, o.property, o.destination);
                                }

                                for (int i = 0; i < cellView.getChildViewCount(); i++) {
                                    if (cellView.getChildView(i) instanceof ViewGroup)
                                        GenXmlDocumentBuildMultiViews(document, viewControllerElem, cellContentElement, (ViewGroup) cellView.getChildView(i));
                                    else {
                                        Element childElement = (Element)ElementsParser.getElementById(cellContentElement, cellView.getChildView(i).getId());
                                        if (childElement != null) {

                                           /* Element rectElementView = document.createElement("rect");
                                            rectElementView.setAttribute("key", "frame");
                                            rectElementView.setAttribute("x", String.valueOf(cellView.getChildView(i).getRect().x));
                                            rectElementView.setAttribute("y", String.valueOf(cellView.getChildView(i).getRect().y));
                                            rectElementView.setAttribute("width", String.valueOf(cellView.getChildView(i).getRect().width));
                                            rectElementView.setAttribute("height", String.valueOf(cellView.getChildView(i).getRect().height));
                                            childElement.appendChild(rectElementView);*/

                                            if (cellView.getChildView(i).getInternalConstraintCount() > 0) {
                                                Element constrainsChildElement = ElementsParser.getConstraintsElementFromElement(childElement);
                                                if(constrainsChildElement == null) constrainsChildElement = document.createElement("constraints");

                                                for (int j = 0; j < cellView.getChildView(i).getInternalConstraintCount(); j++) {
                                                    InternalConstraint ic = cellView.getChildView(i).getInternalConstraint(j);
                                                    Element internalConstraintElement = document.createElement("constraint");
                                                    internalConstraintElement.setAttribute("id", ic.id);
                                                    internalConstraintElement.setAttribute("firstAttribute", ic.getFirstAttribute());
                                                    internalConstraintElement.setAttribute("priority", String.valueOf(ic.priority));
                                                    if (ic.constant < 0)
                                                        ic.constant = ic.constant * (-1);
                                                    internalConstraintElement.setAttribute("constant", String.valueOf(ic.constant));
                                                    constrainsChildElement.appendChild(internalConstraintElement);
                                                }
                                                childElement.appendChild(constrainsChildElement);
                                            }
                                            Element connectionsElementView = document.createElement("connections");
                                            for (int j = 0; j < cellView.getChildView(i).getActionCount(); j++) {
                                                Action a = cellView.getChildView(i).getAction(j);
                                                Element actionElement = document.createElement("action");
                                                actionElement.setAttribute("id", a.id);
                                                actionElement.setAttribute("selector", a.selector);
                                                actionElement.setAttribute("destination", a.destination);
                                                actionElement.setAttribute("eventType", a.eventType);
                                                connectionsElementView.appendChild(actionElement);
                                            }

                                            for (int j = 0; j < cellView.getChildView(i).getSegueCount(); j++) {
                                                Segue s = cellView.getChildView(i).getSegue(j);
                                                Element segueElement = document.createElement("segue");
                                                segueElement.setAttribute("id", s.id);
                                                segueElement.setAttribute("kind", s.kind);
                                                segueElement.setAttribute("destination", s.destination);
                                                if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                                                connectionsTableViewElement.appendChild(segueElement);
                                            }

                                            for (int j = 0; j < cellView.getChildView(i).getOutletCount(); j++) {
                                                Outlet o = cellView.getChildView(i).getOutlet(j);
                                                ElementsParser.addOutletToRootViewController(document, viewControllerElem, o.property, o.destination);
                                            }
                                            childElement.appendChild(connectionsElementView);
                                        }

                                    }
                                }

                            }
                        }
                    }
                    if(tableView != null)
                        tableView.appendChild(connectionsTableViewElement);

                }
                else if(viewControllerElem.getTagName().equals("viewController")) {


                    Element rootElement = (Element) ElementsParser.getRootViewInScene((Element) scene);
                    if (rootElement == null) {
                        continue;
                    }

                    initialViewController = rootElement.getAttribute("initialViewController");
                    viewControllerName = rootElement.getAttribute("viewController");

                    rootElement.removeAttribute("initialViewController");
                    rootElement.removeAttribute("viewController");
                    if (!viewControllerName.isEmpty() && !viewControllerName.equals("")) {
                        String[] viewControllerNameArr = viewControllerName.split("\\.");
                        if (viewControllerNameArr.length > 0)
                            viewControllerName = viewControllerNameArr[viewControllerNameArr.length - 1];
                    }
                    viewControllerElem = (Element) ElementsParser.getViewControllerInScene((Element) scene);
                    if(viewControllerElem != null) {
                        if (!viewControllerName.isEmpty() && !viewControllerName.equals(""))
                            viewControllerElem.setAttribute("customClass", viewControllerName);

                        if (initialViewController.equals("true")) {
                            NodeList docs = document.getElementsByTagName("document");
                            docElem = (Element) docs.item(0);
                            docElem.setAttribute("initialViewController", viewControllerElem.getAttribute("id"));
                        }
                        Element connectionsElementMainView = document.createElement("connections");
                        for (int j = 0; j < viewGroup.getSegueCount(); j++) {
                            Segue s = viewGroup.getSegue(j);
                            Element segueElement = document.createElement("segue");
                            segueElement.setAttribute("id", s.id);
                            segueElement.setAttribute("kind", s.kind);
                            segueElement.setAttribute("destination", s.destination);
                            if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                            connectionsElementMainView.appendChild(segueElement);
                        }
                        viewControllerElem.appendChild(connectionsElementMainView);

                        rootElement.setAttribute("key", "view");
                        Element rectElement = document.createElement("rect");
                        rectElement.setAttribute("key", "frame");
                        rectElement.setAttribute("x", String.valueOf(viewGroup.getRect().x));
                        rectElement.setAttribute("y", String.valueOf(viewGroup.getRect().y));
                        rectElement.setAttribute("width", String.valueOf(viewGroup.getRect().width));
                        rectElement.setAttribute("height", String.valueOf(viewGroup.getRect().height));
                        rootElement.appendChild(rectElement);

                        Element autoresizingMaskElement = document.createElement("autoresizingMask");
                        autoresizingMaskElement.setAttribute("key", "autoresizingMask");
                        autoresizingMaskElement.setAttribute("widthSizable", "YES");
                        autoresizingMaskElement.setAttribute("heightSizable", "YES");
                        rootElement.appendChild(autoresizingMaskElement);



                        Element constrainsElement = ElementsParser.getConstraintsElementFromElement(rootElement);
                        if(constrainsElement == null) constrainsElement = document.createElement("constraints");


                        for (int i = 0; i < ((View) viewGroup).getInternalConstraintCount(); i++) {
                            //InternalConstraint ic = viewGroup.getChildView(i).getInternalConstraint(i);
                            InternalConstraint ic = ((View) viewGroup).getInternalConstraint(i);
                            Element internalConstraintElement = document.createElement("constraint");
                            internalConstraintElement.setAttribute("id", ic.id);
                            internalConstraintElement.setAttribute("firstAttribute", ic.getFirstAttribute());
                            internalConstraintElement.setAttribute("priority", String.valueOf(ic.priority));
                            if (ic.constant < 0) ic.constant = ic.constant * (-1);
                            internalConstraintElement.setAttribute("constant", String.valueOf(ic.constant));
                            constrainsElement.appendChild(internalConstraintElement);
                        }

                        if (viewGroup instanceof LinearLayout) {
                            LinearLayout ll = (LinearLayout) viewGroup;
                            for (int j = 0; j < ll.getConstraintCount(); j++) {
                                Constraint c = ll.getConstraint(j);
                                Element constraintElement = document.createElement("constraint");

                                constraintElement.setAttribute("id", c.id);
                                constraintElement.setAttribute("firstItem", c.firstItemId);
                                constraintElement.setAttribute("secondItem", c.secondItemId);
                                constraintElement.setAttribute("firstAttribute", c.getFirstAttribute());
                                constraintElement.setAttribute("secondAttribute", c.getSecondAttribute());
                                constraintElement.setAttribute("priority", String.valueOf(c.priority));
                                constraintElement.setAttribute("constant", String.valueOf(c.constant));
                                constrainsElement.appendChild(constraintElement);
                            }
                        }
                        rootElement.appendChild(constrainsElement);


                        Element connectionsElement = document.createElement("connections");
                        for (int i = 0; i < ((View) viewGroup).getActionCount(); i++) {
                            Action a = ((View) viewGroup).getAction(i);
                            Element actionElement = document.createElement("action");
                            actionElement.setAttribute("id", a.id);
                            actionElement.setAttribute("selector", a.selector);
                            actionElement.setAttribute("destination", a.destination);
                            actionElement.setAttribute("eventType", a.eventType);
                            connectionsElement.appendChild(actionElement);
                        }

                        rootElement.appendChild(connectionsElement);

                        for (int i = 0; i < ((View) viewGroup).getOutletCount(); i++) {
                            Outlet o = ((View) viewGroup).getOutlet(i);
                            ElementsParser.addOutletToRootViewController(document, viewControllerElem, o.property, o.destination);
                        }

                        for (int i = 0; i < viewGroup.getChildViewCount(); i++) {
                            if (viewGroup.getChildView(i) instanceof ViewGroup)
                                GenXmlDocumentBuildMultiViews(document, viewControllerElem, rootElement, (ViewGroup) viewGroup.getChildView(i));
                            else {
                                Element childElement = (Element)ElementsParser.getElementById(rootElement, viewGroup.getChildView(i).getId());
                                if (childElement != null) {
                                    Element rectElementView = document.createElement("rect");
                                    rectElementView.setAttribute("key", "frame");
                                    rectElementView.setAttribute("x", String.valueOf(viewGroup.getChildView(i).getRect().x));
                                    rectElementView.setAttribute("y", String.valueOf(viewGroup.getChildView(i).getRect().y));
                                    rectElementView.setAttribute("width", String.valueOf(viewGroup.getChildView(i).getRect().width));
                                    rectElementView.setAttribute("height", String.valueOf(viewGroup.getChildView(i).getRect().height));
                                    childElement.appendChild(rectElementView);
                                }

                                if (viewGroup.getChildView(i).getInternalConstraintCount() > 0) {
                                    Element constrainsChildElement = ElementsParser.getConstraintsElementFromElement(childElement);
                                    if(constrainsChildElement == null) constrainsChildElement = document.createElement("constraints");

                                    for (int j = 0; j < viewGroup.getChildView(i).getInternalConstraintCount(); j++) {
                                        InternalConstraint ic = viewGroup.getChildView(i).getInternalConstraint(j);
                                        Element internalConstraintElement = document.createElement("constraint");
                                        internalConstraintElement.setAttribute("id", ic.id);
                                        internalConstraintElement.setAttribute("firstAttribute", ic.getFirstAttribute());
                                        internalConstraintElement.setAttribute("priority", String.valueOf(ic.priority));
                                        if (ic.constant < 0) ic.constant = ic.constant * (-1);
                                        internalConstraintElement.setAttribute("constant", String.valueOf(ic.constant));
                                        constrainsChildElement.appendChild(internalConstraintElement);
                                    }
                                    if (childElement != null) {
                                        childElement.appendChild(constrainsChildElement);
                                    }
                                }
                                Element connectionsElementView = document.createElement("connections");
                                for (int j = 0; j < viewGroup.getChildView(i).getActionCount(); j++) {
                                    Action a = viewGroup.getChildView(i).getAction(j);
                                    Element actionElement = document.createElement("action");
                                    actionElement.setAttribute("id", a.id);
                                    actionElement.setAttribute("selector", a.selector);
                                    actionElement.setAttribute("destination", a.destination);
                                    actionElement.setAttribute("eventType", a.eventType);
                                    connectionsElementView.appendChild(actionElement);
                                }

                                for (int j = 0; j < viewGroup.getChildView(i).getSegueCount(); j++) {
                                    Segue s = viewGroup.getChildView(i).getSegue(j);
                                    Element segueElement = document.createElement("segue");
                                    segueElement.setAttribute("id", s.id);
                                    segueElement.setAttribute("kind", s.kind);
                                    segueElement.setAttribute("destination", s.destination);
                                    if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                                    connectionsElementView.appendChild(segueElement);
                                }

                                for (int j = 0; j < viewGroup.getChildView(i).getOutletCount(); j++) {
                                    Outlet o = viewGroup.getChildView(i).getOutlet(j);
                                    ElementsParser.addOutletToRootViewController(document, viewControllerElem, o.property, o.destination);
                                }

                                if (childElement != null) {
                                    childElement.appendChild(connectionsElementView);
                                }
                            }
                        }
                    }
                }
            }
        }

        List<String> imgColl = new ArrayList<String>(imgFilesMap.values());
        if(imgColl.size()>0) {
            Element resourcesElement = document.createElement("resources");
            for (int i = 0; i < imgColl.size(); i++) {
                Element imageElement = document.createElement("image");
                imageElement.setAttribute("name", imgColl.get(i));
                resourcesElement.appendChild(imageElement);
            }

            NodeList docs = document.getElementsByTagName("document");
            docElem = (Element)docs.item(0);
            if(docElem != null)
                docElem.appendChild(resourcesElement);
        }


        return null;
    }


    private static Document GenXmlDocumentBuildMultiViews(Document document, Element viewControllerElem, Element rootNode, ViewGroup viewGroup){

        Element element = (Element)ElementsParser.getElementById(rootNode, viewGroup.getId());
        if(element != null) {
            element.setAttribute("translatesAutoresizingMaskIntoConstraints", "NO");
            Element rectElement = document.createElement("rect");
            rectElement.setAttribute("key", "frame");
            rectElement.setAttribute("x", String.valueOf(viewGroup.getRect().x));
            rectElement.setAttribute("y", String.valueOf(viewGroup.getRect().y));
            rectElement.setAttribute("width", String.valueOf(viewGroup.getRect().width));
            rectElement.setAttribute("height", String.valueOf(viewGroup.getRect().height));
            element.appendChild(rectElement);

            Element constrainsElement = ElementsParser.getConstraintsElementFromElement(element);
            if(constrainsElement == null) constrainsElement = document.createElement("constraints");


            for (int i = 0; i < ((View) viewGroup).getInternalConstraintCount(); i++) {
                InternalConstraint ic = ((View) viewGroup).getInternalConstraint(i);
                Element internalConstraintElement = document.createElement("constraint");
                internalConstraintElement.setAttribute("id", ic.id);
                internalConstraintElement.setAttribute("firstAttribute", ic.getFirstAttribute());
                internalConstraintElement.setAttribute("priority", String.valueOf(ic.priority));
                if (ic.constant < 0) ic.constant = ic.constant * (-1);
                internalConstraintElement.setAttribute("constant", String.valueOf(ic.constant));
                constrainsElement.appendChild(internalConstraintElement);
            }

            if (viewGroup instanceof LinearLayout) {
                LinearLayout ll = (LinearLayout) viewGroup;
                for (int j = 0; j < ll.getConstraintCount(); j++) {
                    Constraint c = ll.getConstraint(j);
                    Element constraintElement = document.createElement("constraint");

                    constraintElement.setAttribute("id", c.id);
                    constraintElement.setAttribute("firstItem", c.firstItemId);
                    constraintElement.setAttribute("secondItem", c.secondItemId);
                    constraintElement.setAttribute("firstAttribute", c.getFirstAttribute());
                    constraintElement.setAttribute("secondAttribute", c.getSecondAttribute());
                    constraintElement.setAttribute("priority", String.valueOf(c.priority));
                    constraintElement.setAttribute("constant", String.valueOf(c.constant));
                    constrainsElement.appendChild(constraintElement);
                }
            }
            element.appendChild(constrainsElement);

            Element connectionsElement = document.createElement("connections");
            for (int i = 0; i < ((View) viewGroup).getActionCount(); i++) {
                Action a = ((View) viewGroup).getAction(i);
                Element actionElement = document.createElement("action");
                actionElement.setAttribute("id", a.id);
                actionElement.setAttribute("selector", a.selector);
                actionElement.setAttribute("destination", a.destination);
                actionElement.setAttribute("eventType", a.eventType);
                connectionsElement.appendChild(actionElement);
            }

            for (int i = 0; i < ((View) viewGroup).getSegueCount(); i++) {
                Segue s = ((View) viewGroup).getSegue(i);
                Element segueElement = document.createElement("segue");
                segueElement.setAttribute("id", s.id);
                segueElement.setAttribute("kind", s.kind);
                segueElement.setAttribute("destination", s.destination);
                if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                connectionsElement.appendChild(segueElement);
            }

            element.appendChild(connectionsElement);
        }
        for (int i = 0; i < ((View)viewGroup).getOutletCount(); i++) {
            Outlet o = ((View)viewGroup).getOutlet(i);
            ElementsParser.addOutletToRootViewController(document, viewControllerElem, o.property, o.destination);
        }

        for (int i = 0; i < viewGroup.getChildViewCount(); i++) {
            if(viewGroup.getChildView(i) instanceof ViewGroup)
                GenXmlDocumentBuildMultiViews(document, viewControllerElem, rootNode, (ViewGroup) viewGroup.getChildView(i));
            else{
                Element childElement = (Element)ElementsParser.getElementById(rootNode, viewGroup.getChildView(i).getId());
                if(childElement != null) {
                    Element rectElementView = document.createElement("rect");
                    rectElementView.setAttribute("key", "frame");
                    rectElementView.setAttribute("x", String.valueOf(viewGroup.getChildView(i).getRect().x));
                    rectElementView.setAttribute("y", String.valueOf(viewGroup.getChildView(i).getRect().y));
                    rectElementView.setAttribute("width", String.valueOf(viewGroup.getChildView(i).getRect().width));
                    rectElementView.setAttribute("height", String.valueOf(viewGroup.getChildView(i).getRect().height));
                    childElement.appendChild(rectElementView);

                    if (viewGroup.getChildView(i).getInternalConstraintCount() > 0) {
                        Element constrainsChildElement = ElementsParser.getConstraintsElementFromElement(childElement);
                        if(constrainsChildElement == null) constrainsChildElement = document.createElement("constraints");

                        for (int j = 0; j < viewGroup.getChildView(i).getInternalConstraintCount(); j++) {
                            InternalConstraint ic = viewGroup.getChildView(i).getInternalConstraint(j);
                            Element internalConstraintElement = document.createElement("constraint");
                            internalConstraintElement.setAttribute("id", ic.id);
                            internalConstraintElement.setAttribute("firstAttribute", ic.getFirstAttribute());
                            internalConstraintElement.setAttribute("priority", String.valueOf(ic.priority));
                            if (ic.constant < 0) ic.constant = ic.constant * (-1);
                            internalConstraintElement.setAttribute("constant", String.valueOf(ic.constant));
                            constrainsChildElement.appendChild(internalConstraintElement);
                        }
                        childElement.appendChild(constrainsChildElement);
                    }
                    if (viewGroup.getChildView(i).getActionCount() > 0 || viewGroup.getChildView(i).getSegueCount() > 0) {

                        Element connectionsElementView = document.createElement("connections");
                        for (int j = 0; j < viewGroup.getChildView(i).getActionCount(); j++) {
                            Action a = viewGroup.getChildView(i).getAction(j);
                            Element actionElement = document.createElement("action");
                            actionElement.setAttribute("id", a.id);
                            actionElement.setAttribute("selector", a.selector);
                            actionElement.setAttribute("destination", a.destination);
                            actionElement.setAttribute("eventType", a.eventType);
                            connectionsElementView.appendChild(actionElement);
                        }

                        for (int j = 0; j < viewGroup.getChildView(i).getSegueCount(); j++) {
                            Segue s = viewGroup.getChildView(i).getSegue(j);
                            Element segueElement = document.createElement("segue");
                            segueElement.setAttribute("id", s.id);
                            segueElement.setAttribute("kind", s.kind);
                            segueElement.setAttribute("destination", s.destination);
                            if(s.segue_identifier != null) segueElement.setAttribute("identifier", s.segue_identifier);
                            connectionsElementView.appendChild(segueElement);
                        }

                        childElement.appendChild(connectionsElementView);
                    }
                }
                for (int j = 0; j < viewGroup.getChildView(i).getOutletCount(); j++) {
                    Outlet o = viewGroup.getChildView(i).getOutlet(j);
                    ElementsParser.addOutletToRootViewController(document, viewControllerElem, o.property, o.destination);
                }
            }
        }
        return null;
    }

    private static Map<String, String> getImageFilesMap(String res_path){
        Map<String, String>img = new HashMap();
        File inputFilePath = new File(res_path);
        if(inputFilePath.exists()) {
            File [] listImg = inputFilePath.listFiles();
            for(int i=0; i<listImg.length; i++) {

                String []n = listImg[i].getName().split("\\.");
                if(n.length > 1)
                    img.put(n[0], listImg[i].getName());
            }
        }
        return img;
    }
}
