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

package org.moe.transformer;

import org.moe.transformer.LayoutInfo.eLayoutOrientation;
import org.moe.transformer.LayoutInfo.eLinearLayoutOrientation;
import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementsParser {

    public static Node getRootView(Document doc) {
        NodeList viewList = doc.getElementsByTagName("view");
        Node rootView = null;
        for (int i = 0; i < viewList.getLength(); i++) {
            rootView = viewList.item(i);
            break;
        }
        return rootView;
    }


    public static Node getViewControllerInScene(Element scene) {
        NodeList viewList = scene.getElementsByTagName("viewController");
        if (viewList.getLength() == 0)
            viewList = scene.getElementsByTagName("tableViewController");
        if (viewList.getLength() == 0)
            viewList = scene.getElementsByTagName("navigationController");
        Node rootView = null;
        for (int i = 0; i < viewList.getLength(); i++) {
            rootView = viewList.item(i);
            break;
        }
        return rootView;
    }

    public static Node getRootViewInScene(Element scene) {
        NodeList viewList = scene.getElementsByTagName("view");
        Node rootView = null;
        for (int i = 0; i < viewList.getLength(); i++) {
            rootView = viewList.item(i);
            break;
        }
        return rootView;
    }
    //navigationItem
    public static Node getNavigationItemInViewController(Element viewController) {
        NodeList viewList = viewController.getChildNodes();
        for (int i=0; i<viewList.getLength(); i++) {
            if (viewList.item(i).getNodeName().equals("navigationItem"))
                return viewList.item(i);
        }
        return null;
    }

    public static Node getTableViewCellContentViewInTableCell(Element scene) {
        NodeList viewList = scene.getElementsByTagName("tableViewCellContentView");
        Node rootView = null;
        for (int i = 0; i < viewList.getLength(); i++) {
            rootView = viewList.item(i);
            break;
        }
        return rootView;
    }


    // 1 get SubViewElements
    public static NodeList getSubViewElementsFromView(Node view) {
        NodeList subviewsChildNodes = null;

        if (view != null) {
            NodeList layoutChildNodes = view.getChildNodes();
            for (int i = 0; i < layoutChildNodes.getLength(); i++) {
                Node elemView = layoutChildNodes.item(i);
                if (elemView.getNodeName().equals("subviews")) {
                    subviewsChildNodes = elemView.getChildNodes();
                    break;
                }
            }
        }
        return subviewsChildNodes;
    }

    //tableViewController
    public static Node getTableViewFromTableViewController(Element tableViewController) {
        NodeList tableViewList = tableViewController.getElementsByTagName("tableView");
        for(int i=0; i<tableViewList.getLength(); i++){
            Node tableView = tableViewList.item(i);
            if(tableView != null) return tableView;
        }
        return null;
    }
    public static NodeList getTableViewCellElementsFromTableViewController(Element tableView) {
        NodeList tableViewCells = tableView.getElementsByTagName("tableViewCell");
        return tableViewCells;
    }

    public static Node getElementById(Node view, String elementId) {
        try {
            Node atr = view.getAttributes().getNamedItem("id");
            if (atr != null)
                if (atr.getTextContent().equals(elementId))
                    return view;

            NodeList childNodes = view.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node retVal = getElementById(childNodes.item(i), elementId);
                if (retVal != null)
                    return retVal;
            }
            return null;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    public static Node getConstraintsElement(Node view) {
        NodeList layoutChildNodes = view.getChildNodes();
        for (int i = 0; i < layoutChildNodes.getLength(); i++) {
            Node elemView = layoutChildNodes.item(i);
            if (elemView.getNodeName().equals("constraints")) {
                return elemView;
            }
        }
        return null;
    }

    public static Node getConnectionsElement(Node view) {
        NodeList layoutChildNodes = view.getChildNodes();
        for (int i = 0; i < layoutChildNodes.getLength(); i++) {
            Node elemView = layoutChildNodes.item(i);
            if (elemView.getNodeName().equals("connections")) {
                return elemView;
            }
        }
        return null;
    }

    public static Node getViewController(Document doc) {
        NodeList viewList = doc.getElementsByTagName("viewController");
        Node rootView = null;
        for (int i = 0; i < viewList.getLength(); i++) {
            rootView = viewList.item(i);
            break;
        }
        return rootView;
    }


    //2 get ViewOrientation
    public static eLayoutOrientation getViewOrientation(Node nodeView) {
        eLayoutOrientation orientation = null;
        String strOrentation = nodeView.getAttributes().getNamedItem("android_orientation")
                .getTextContent();

        if (strOrentation.equals("vertical")) {
            orientation = eLayoutOrientation.VERTICAL;
        } else if (strOrentation.equals("horizontal")) {
            orientation = eLayoutOrientation.HORIZONTAL;
        } else if (strOrentation.equals("relative")) {
            orientation = eLayoutOrientation.RELATIVE;
        }
        return orientation;
    }

    public static eLinearLayoutOrientation getLinearLayoutOrientation(Element nodeView) {
        eLinearLayoutOrientation orientation = null;
        String strOrentation = nodeView.getAttributes().getNamedItem("android_orientation")
                .getTextContent();
        nodeView.removeAttribute("android_orientation");
        if (strOrentation.equals("vertical")) {
            orientation = eLinearLayoutOrientation.VERTICAL;
        } else if (strOrentation.equals("horizontal")) {
            orientation = eLinearLayoutOrientation.HORIZONTAL;
        }
        return orientation;
    }

    public static void createConnectionsElement(Document document, Node element) {
        //connections
        Node connections = document.createElement("connections");
        element.appendChild(connections);
    }

    public static void addActionToElementConnections(Document document, Node element, String actionName, String destinationElementId, String eventType) {
        Element connectionsElement = (Element) ElementsParser.getConnectionsElement(element);
        if (connectionsElement == null) {
            createConnectionsElement(document, element);
            connectionsElement = (Element) ElementsParser.getConnectionsElement(element);
        }
        Element actionElement = document.createElement("action");

        //<action selector="burttonPushed" destination="vXZ-lx-hvc" eventType="touchUpInside" id="d6m-40-Wkx"/>
        actionElement.setAttribute("selector", actionName + ":");
        actionElement.setAttribute("destination", destinationElementId);
        actionElement.setAttribute("eventType", eventType);
        actionElement.setAttribute("id", OSXStringProcessor.CreateId());

        if(connectionsElement != null){
            connectionsElement.appendChild(actionElement);
        }

    }

   /* public static void addOutletToRootViewController(Document document, String outletName, String destinationElementId){
        Element viewController = (Element)getViewController(document);
        Element connectionsElement = (Element)ElementsParser.getConnectionsElement(viewController);
        if(connectionsElement == null) {
            createConnectionsElement(document, viewController);
            connectionsElement = (Element) ElementsParser.getConnectionsElement(viewController);
        }

        Element outletElement = document.createElement("outlet");

        outletElement.setAttribute("property", outletName);
        outletElement.setAttribute("destination", destinationElementId);
        outletElement.setAttribute("id", OSXStringProcessor.CreateId());
        //<outlet property="my_lable" destination="yH3-rC-gC6" id="DRd-gn-ESb"/>
        connectionsElement.appendChild(outletElement);
        //viewController.appendChild(connectionsElement);
    }*/

    public static void addOutletToRootViewController(Document document, Element viewController, String outletName, String destinationElementId) {
        Element connectionsElement = (Element) ElementsParser.getConnectionsElement(viewController);
        if (connectionsElement == null) {
            createConnectionsElement(document, viewController);
            connectionsElement = (Element) ElementsParser.getConnectionsElement(viewController);
        }

        Element outletElement = document.createElement("outlet");

        outletElement.setAttribute("property", outletName);
        outletElement.setAttribute("destination", destinationElementId);
        outletElement.setAttribute("id", OSXStringProcessor.CreateId());

        if(connectionsElement != null){
            //<outlet property="my_lable" destination="yH3-rC-gC6" id="DRd-gn-ESb"/>
            connectionsElement.appendChild(outletElement);
            //viewController.appendChild(connectionsElement);
        }

    }

    public static Element getConstraintsElementFromElement(Element element) {
        Element constrainsElement = null;
        NodeList constrainsElementList = element.getElementsByTagName("constraints");
        if(constrainsElementList.getLength() > 0)
            constrainsElement = (Element)constrainsElementList.item(0);
        return constrainsElement;
    }

    public static Element getConnectionsElementFromElement(Element element) {
        Element constrainsElement = null;
        NodeList constrainsElementList = element.getElementsByTagName("connections");
        if(constrainsElementList.getLength() > 0)
            constrainsElement = (Element)constrainsElementList.item(0);
        return constrainsElement;
    }
}
