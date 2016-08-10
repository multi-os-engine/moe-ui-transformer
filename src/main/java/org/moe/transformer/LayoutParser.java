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

import org.moe.transformer.DeviceInfo.Display;
import org.moe.transformer.DeviceInfo.eiOSDeviceName;
import org.moe.transformer.DeviceInfo.iOSDisplay;
import org.moe.transformer.Layouts.ViewGroup;
import org.moe.transformer.XmlBuilders.XsltTransformedBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class LayoutParser {
    private Display display;
    private Document document;
    private String filePath;

    /**
     * Function: saveXML
     * Return type: void
     * Purpose: to save the modified xml document via rewriting the whole document
     */
    public void saveXML() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;

        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);

        System.out.println("Done");
    }

    public void build(String file) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {

        eiOSDeviceName displayName = iOSDisplay.GetEIOSDeviceName(CmdParser.GetParam("device_type"));
        display = iOSDisplay.GetDeviceDisplay(displayName);
        filePath = file;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;


        builder = factory.newDocumentBuilder();
        FileInputStream is = new FileInputStream(file);
        document = builder.parse(new InputSource(is));


        XPathFactory xpathFactory = XPathFactory.newInstance();
        // XPath to find empty text nodes.
        XPathExpression xpathExp = xpathFactory.newXPath().compile(
                "//text()[normalize-space(.) = '']");
        NodeList emptyTextNodes = (NodeList)
                xpathExp.evaluate(document, XPathConstants.NODESET);

        // Remove each empty text node from document.
        for (int i = 0; i < emptyTextNodes.getLength(); i++) {
            Node emptyTextNode = emptyTextNodes.item(i);
            emptyTextNode.getParentNode().removeChild(emptyTextNode);
        }

        List<ViewGroup> viewGroup = XsltTransformedBuilder.BuildMultiViews(document, display);
        XsltTransformedBuilder.GenXmlDocumentMultiViews(document, viewGroup);
    }
}
