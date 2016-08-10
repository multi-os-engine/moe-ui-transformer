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

import org.moe.transformer.Layouts.Rect;
import org.moe.transformer.Utils.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;

public class UITransformerTest {

    final String RES_PATH = "/src/main/resources/AndroidLayoutTests/";
    final String TRANSFORMER_RES__PATH = "/src/main/resources/UITransformer-res/";
    final String OUT_FORMAT_STORYBOARD = "storyboard";
    final String OUT_PATH = "/build/tests/";

    final Double EPSILON = 1E-10;

//    private String convertLayout(String layoutName, String outFilename) throws IOException {
//        String curDir = new File(".").getCanonicalPath();
//        String fileToCheck = curDir + OUT_PATH +  outFilename + "." + OUT_FORMAT_STORYBOARD;
//        String[] args = new String[]{
//                "--ixml-res-path=" + curDir + RES_PATH + layoutName,
//                "--out-format=" + OUT_FORMAT_STORYBOARD,
//                "--out-filename=" + outFilename,
//                "--out-path=" + curDir + OUT_PATH,
//                "--xslt-res-path=" + curDir + XSLT_PATH,
//        };
//
//        OSXUITransformer.main(args);
//        return fileToCheck;
//    }
//
//
//    private Document getConvertedDocument(String layoutName, String outName) {
//        InputStream in = null;
//        Document doc = null;
//        try {
//            String fileToCheck = convertLayout(layoutName, outName);
//
//            File outputFile = new File(fileToCheck);
//            assertTrue("Output file doesn't exist", outputFile.exists());
//
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            dbf.setIgnoringElementContentWhitespace(true);
//            DocumentBuilder builder = dbf.newDocumentBuilder();
//            in = new BufferedInputStream(new FileInputStream(fileToCheck));
//            doc = builder.parse(new InputSource(in));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            System.out.println("Failed to parse generated file");
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (Exception e) {
//                    System.out.println("Failed to close file stream");
//                    e.printStackTrace();
//                }
//            }
//        }
//        assertNotNull("Couldn't parse file", doc);
//        return doc;
//    }
//
//    private Node testOnlyOneItem(Element el, String tag) {
//        String prevTag = el.getTagName();
//        NodeList list = el.getElementsByTagName(tag);
//        assertNotNull("No " + tag + " found in " + prevTag + " element", list);
//        assertEquals("Only 1 " + tag + " expected", list.getLength(), 1);
//        return list.item(0);
//    }
//
//
//
//
//
//
//    private void checkFillsParent(Node element, boolean fillsHor, boolean fillsVert) {
//        checkFillsParent(element, fillsHor, fillsVert, true);
//    }
//
//    private void checkFillsParent(Node element, boolean fillsHor, boolean fillsVert, boolean checkTrueOnly) {
//        String name = StoryboardAnalyzer.getCaption(element);
//
//        if (fillsHor || !checkTrueOnly) {
//            assertEquals(name + (fillsHor ? " doesn't fill" : " fills") + " parent horizontally",
//                    fillsHor,
//                    StoryboardAnalyzer.fillsParent(element, true));
//        }
//        if (fillsVert || !checkTrueOnly) {
//            assertEquals(name + (fillsVert ? " doesn't fill" : " fills") + " parent vertically",
//                    fillsVert,
//                    StoryboardAnalyzer.fillsParent(element, false));
//        }
//    }
//
//    private void initialCheckConstraints(Document doc) {
//        assertTrue("Some constraints are declared in the wrong scope",
//                StoryboardAnalyzer.areConstraintsGood(doc));
//        assertFalse("Some of the sizes (height or width) are defined directly via constraint",
//                StoryboardAnalyzer.hasHeightOrWidthConstraints(doc));
//    }
//
//    @Test
//    public void emptyLayoutTest() {
//        //Layout with nothing
//        Document doc = getConvertedDocument("res-empty_linear_layout", "Empty");
//
//        Element scenes = (Element) testOnlyOneChildWithTag(doc.getDocumentElement(), "scenes");
//        Element scene = (Element) testOnlyOneChildWithTag(scenes, "scene");
//        Element objects = (Element) testOnlyOneChildWithTag(scene, "objects");
//        Element viewController = (Element) testOnlyOneChildWithTag(objects, "viewController");
//        Element view = (Element) testOnlyOneChildWithTag(viewController, "view");
//
//        assertTrue("Some constraints are declared in the wrong scope",
//                StoryboardAnalyzer.areConstraintsGood(doc));
//        assertFalse("Some of the sizes (height or width) are defined directly via constraint",
//                StoryboardAnalyzer.hasHeightOrWidthConstraints(doc));
//
//        assertNotNull("View returned is null", view);
//    }
//
//    @Test
//    public void labelTest() {
//        //Layout with centered label with text "Test"
//        Document doc = getConvertedDocument("res-label_linear_layout", "Label");
//
//        Element scene = (Element) testOnlyOneItem(doc.getDocumentElement(), "scene");
//        Element viewController = (Element) testOnlyOneItem(scene, "viewController");
//        Element view = (Element) testOnlyOneItem(viewController, "view");
//        Element label = (Element) testOnlyOneItem(view, "label");
//
//        assertTrue("Some constraints are declared in the wrong scope",
//                StoryboardAnalyzer.areConstraintsGood(doc));
//
//        assertFalse("Some of the sizes (height or width) are defined directly via constraint",
//                StoryboardAnalyzer.hasHeightOrWidthConstraints(doc));
//
//        assertFalse("Label centered inside view",
//                StoryboardAnalyzer.isCenteredInView(view, label));
//        assertEquals("Label text is not correct", "Test", label.getAttribute("text"));
//    }
//
//    @Test
//    public void buttonTest() {
//        //Layout with centered button with text "Test"
//        Document doc = getConvertedDocument("res-button_linear_layout", "Button");
//
//        Element scene = (Element) testOnlyOneItem(doc.getDocumentElement(), "scene");
//        Element viewController = (Element) testOnlyOneItem(scene, "viewController");
//        Element view = (Element) testOnlyOneItem(viewController, "view");
//        Element button = (Element) testOnlyOneItem(view, "button");
//
//        assertTrue("Some constraints are declared in the wrong scope",
//                StoryboardAnalyzer.areConstraintsGood(doc));
//        assertFalse("Some of the sizes (height or width) are defined directly via constraint",
//                StoryboardAnalyzer.hasHeightOrWidthConstraints(doc));
//
//        assertTrue("Button doesn't fill parent horizontally",
//                StoryboardAnalyzer.fillsParent(view, button, true));
//        assertTrue("Button doesn't fill parent vertically",
//                StoryboardAnalyzer.fillsParent(view, button, false));
//        Node state = testOnlyOneItem(button, "state");
//
//        assertEquals("Button text is not correct", "Test",
//                StoryboardAnalyzer.getAttribute(state.getAttributes(), "title"));
//    }
//
//    @Test
//    public void hor_test() {
//
//        System.out.println("Running test 1 for horizontal linear layout");
//        Document doc = getConvertedDocument("res-horizontal_linear_layout", "HTest1");
//
//        Node firstBtn = getButtonCheck(doc, "Test Button Left");
//        Node secondBtn = getButtonCheck(doc, "Test Button Middle");
//        Node thirdBtn = getButtonCheck(doc, "Test Button Right");
//        System.out.println("All buttons found");
//
//        initialCheckConstraints(doc);
//
//        checkHorizontalOrder(firstBtn, secondBtn, true);
//        checkHorizontalOrder(secondBtn, thirdBtn, true);
//        checkHorizontalOrder(firstBtn, thirdBtn, true);
//        System.out.println("No errors in button order found");
//
//        checkFillsParent(firstBtn, false, true);
//        checkFillsParent(secondBtn, false, true);
//        checkFillsParent(thirdBtn, false, true);
//
//        assertTrue("Left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(firstBtn, AlignSide.LEFT));
//        assertTrue("Right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(thirdBtn, AlignSide.RIGHT));
//
//        /*
//        //check widths
//        Double a = StoryboardAnalyzer.getSizeProportion(doc, firstBtn, secondBtn, true);
//        Double b = StoryboardAnalyzer.getSizeProportion(doc, thirdBtn, secondBtn, true);
//        Double c = StoryboardAnalyzer.getSizeProportion(doc, firstBtn, thirdBtn, true);
//
//        a = (a == 0 ? b * c : a);
//        b = (b == 0 && c != 0 ? a / c : b);
//        assertTrue("Left button should have width about 3 times larger than middle button width",
//                a <= 3 + EPSILON);
//        assertTrue("Left button should have width about 3 times larger than middle button width",
//                a >= 3 - EPSILON);
//        assertTrue("Right button should have width about 2 times larger than middle button width",
//                b <= 2 + EPSILON);
//        assertTrue("Right button should have width about 2 times larger than middle button width",
//                b >= 2 - EPSILON);*/
//    }
//
//    @Test
//    public void vert_test() {
//
//        System.out.println("Running test 1 for vertical linear layout");
//        Document doc = getConvertedDocument("res-vertical_linear_layout", "VTest1");
//
//        Node firstBtn = getButtonCheck(doc, "Top Button");
//        Node secondBtn = getButtonCheck(doc, "Bottom Button");
//        System.out.println("All buttons found");
//
//        initialCheckConstraints(doc);
//
//        checkVerticalOrder(firstBtn, secondBtn, true);
//        System.out.println("No errors in button order found");
//
//        checkFillsParent(firstBtn, true, false);
//        checkFillsParent(secondBtn, true, false);
//
//        assertTrue("Top button is not aligned to the top side of it's parent",
//                StoryboardAnalyzer.isAligned(firstBtn, AlignSide.TOP));
//        assertTrue("Bottom button is not aligned to the bottom side of it's parent",
//                StoryboardAnalyzer.isAligned(secondBtn, AlignSide.BOTTOM));
//
//        Double prop = StoryboardAnalyzer.getSizeProportion(doc, firstBtn, secondBtn, false);
//        assertTrue("Top button and bottom button should have equal heights",
//                prop <= 1 + EPSILON);
//        assertTrue("Top button and bottom button should have equal heights",
//                prop >= 1 - EPSILON);
//    }
//
//    @Test
//    public void vert_hv_test() {
//
//        Document doc = getConvertedDocument("res-vert_hv_linear_layout", "VTestHV");
//
//        Node leftBtn = getButtonCheck(doc, "Left Button");
//        Node rightBtn = getButtonCheck(doc, "Right Button");
//        Node topBtn = getButtonCheck(doc, "Top Button");
//        Node bottomBtn = getButtonCheck(doc, "Bottom Button");
//
//        initialCheckConstraints(doc);
//        //vertical order
//        checkVerticalOrder(topBtn, bottomBtn, true);
//        checkVerticalOrder(leftBtn, topBtn, true);
//        checkVerticalOrder(rightBtn, topBtn, true);
//
//        //horizontal order
//        checkHorizontalOrder(leftBtn, rightBtn, true);
//        checkHorizontalOrder(leftBtn, topBtn, false);
//        checkHorizontalOrder(rightBtn, bottomBtn, false);
//
//        //borders
//        checkFillsParent(leftBtn, false, true);
//        checkFillsParent(rightBtn, false, true);
//        checkFillsParent(topBtn, true, false);
//        checkFillsParent(bottomBtn, true, false);
//
//        assertTrue("Left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(leftBtn, AlignSide.LEFT));
//        assertTrue("Right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(rightBtn, AlignSide.RIGHT));
//        assertTrue("Top button is not aligned to the top side of it's parent",
//                StoryboardAnalyzer.isAligned(topBtn, AlignSide.TOP));
//        assertTrue("Bottom button is not aligned to the bottom side of it's parent",
//                StoryboardAnalyzer.isAligned(bottomBtn, AlignSide.BOTTOM));
//    }
//
//    @Test
//    public void vert_vh_test() {
//
//        Document doc = getConvertedDocument("res-vert_vh_linear_layout", "VTestVH");
//
//        Node leftBtn = getButtonCheck(doc, "Left Button");
//        Node rightBtn = getButtonCheck(doc, "Right Button");
//        Node topBtn = getButtonCheck(doc, "Top Button");
//        Node bottomBtn = getButtonCheck(doc, "Bottom Button");
//
//        initialCheckConstraints(doc);
//        //vertical order
//        checkVerticalOrder(topBtn, bottomBtn, true);
//        checkVerticalOrder(bottomBtn, leftBtn, true);
//        checkVerticalOrder(bottomBtn, rightBtn, true);
//
//        //horizontal order
//        checkHorizontalOrder(leftBtn, rightBtn, true);
//        checkHorizontalOrder(leftBtn, bottomBtn, false);
//        checkHorizontalOrder(rightBtn, topBtn, false);
//
//        //borders
//        checkFillsParent(leftBtn, false, true);
//        checkFillsParent(rightBtn, false, true);
//        checkFillsParent(topBtn, true, false);
//        checkFillsParent(bottomBtn, true, false);
//
//        assertTrue("Left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(leftBtn, AlignSide.LEFT));
//        assertTrue("Right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(rightBtn, AlignSide.RIGHT));
//        assertTrue("Top button is not aligned to the top side of it's parent",
//                StoryboardAnalyzer.isAligned(topBtn, AlignSide.TOP));
//        assertTrue("Bottom button is not aligned to the bottom side of it's parent",
//                StoryboardAnalyzer.isAligned(bottomBtn, AlignSide.BOTTOM));
//    }
//
//    @Test
//    public void hor_hv_test() {
//
//        Document doc = getConvertedDocument("res-hor_hv_linear_layout", "HTestHV");
//
//        Node leftBtn = getButtonCheck(doc, "Left Button");
//        Node rightBtn = getButtonCheck(doc, "Right Button");
//        Node topBtn = getButtonCheck(doc, "Top Button");
//        Node bottomBtn = getButtonCheck(doc, "Bottom Button");
//
//        initialCheckConstraints(doc);
//        //vertical order
//        checkVerticalOrder(topBtn, bottomBtn, true);
//        checkVerticalOrder(leftBtn, topBtn, false);
//        checkVerticalOrder(rightBtn, bottomBtn, false);
//
//        //horizontal order
//        checkHorizontalOrder(leftBtn, rightBtn, true);
//        checkHorizontalOrder(leftBtn, topBtn, true);
//        checkHorizontalOrder(rightBtn, bottomBtn, true);
//
//        //borders
//        checkFillsParent(leftBtn, false, true);
//        checkFillsParent(rightBtn, false, true);
//        checkFillsParent(topBtn, true, false);
//        checkFillsParent(bottomBtn, true, false);
//
//        assertTrue("Left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(leftBtn, AlignSide.LEFT));
//        assertTrue("Right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(rightBtn, AlignSide.RIGHT));
//        assertTrue("Top button is not aligned to the top side of it's parent",
//                StoryboardAnalyzer.isAligned(topBtn, AlignSide.TOP));
//        assertTrue("Bottom button is not aligned to the bottom side of it's parent",
//                StoryboardAnalyzer.isAligned(bottomBtn, AlignSide.BOTTOM));
//    }
//
//    @Test
//    public void hor_vh_test() {
//
//        Document doc = getConvertedDocument("res-hor_vh_linear_layout", "HTestVH");
//
//        Node leftBtn = getButtonCheck(doc, "Left Button");
//        Node rightBtn = getButtonCheck(doc, "Right Button");
//        Node topBtn = getButtonCheck(doc, "Top Button");
//        Node bottomBtn = getButtonCheck(doc, "Bottom Button");
//
//        initialCheckConstraints(doc);
//        //vertical order
//        checkVerticalOrder(topBtn, bottomBtn, true);
//        checkVerticalOrder(leftBtn, bottomBtn, false);
//        checkVerticalOrder(rightBtn, topBtn, false);
//
//        //horizontal order
//        checkHorizontalOrder(leftBtn, rightBtn, true);
//        checkHorizontalOrder(topBtn, leftBtn, true);
//        checkHorizontalOrder(bottomBtn, rightBtn, true);
//
//        //borders
//        checkFillsParent(leftBtn, false, true);
//        checkFillsParent(rightBtn, false, true);
//        checkFillsParent(topBtn, true, false);
//        checkFillsParent(bottomBtn, true, false);
//
//        assertTrue("Left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(leftBtn, AlignSide.LEFT));
//        assertTrue("Right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(rightBtn, AlignSide.RIGHT));
//        assertTrue("Top button is not aligned to the top side of it's parent",
//                StoryboardAnalyzer.isAligned(topBtn, AlignSide.TOP));
//        assertTrue("Bottom button is not aligned to the bottom side of it's parent",
//                StoryboardAnalyzer.isAligned(bottomBtn, AlignSide.BOTTOM));
//    }
//
//
//    @Test
//    public void complex_test() {
//
//        Document doc = getConvertedDocument("res-complex_linear_layout", "ComplexTest");
//
//        Node leftBtn = getButtonCheck(doc, "Left Button");
//        Node rightBtn = getButtonCheck(doc, "Right Button");
//        Node topBtn = getButtonCheck(doc, "Top Button");
//        Node innerLeftBtn = getButtonCheck(doc, "Inner Left Button");
//        Node innerRightBtn = getButtonCheck(doc, "Inner Right Button");
//
//        initialCheckConstraints(doc);
//        //vertical order
//        checkVerticalOrder(topBtn, innerLeftBtn, true);
//        checkVerticalOrder(topBtn, innerRightBtn, true);
//        checkVerticalOrder(leftBtn, innerLeftBtn, false);
//        checkVerticalOrder(leftBtn, topBtn, false);
//        checkVerticalOrder(rightBtn, innerRightBtn, false);
//        checkVerticalOrder(rightBtn, topBtn, false);
//
//        //horizontal order
//        checkHorizontalOrder(leftBtn, topBtn, true);
//        checkHorizontalOrder(leftBtn, innerLeftBtn, true);
//        checkHorizontalOrder(leftBtn, innerRightBtn, true);
//        checkHorizontalOrder(leftBtn, rightBtn, true);
//        checkHorizontalOrder(innerLeftBtn, innerRightBtn, true);
//        checkHorizontalOrder(innerLeftBtn, rightBtn, true);
//        checkHorizontalOrder(topBtn, rightBtn, true);
//        checkHorizontalOrder(innerRightBtn, rightBtn, true);
//        checkHorizontalOrder(topBtn, innerLeftBtn, false);
//        checkHorizontalOrder(topBtn, innerRightBtn, false);
//
//        //borders
//        checkFillsParent(leftBtn, false, true);
//        checkFillsParent(rightBtn, false, true);
//        checkFillsParent(topBtn, true, false);
//        checkFillsParent(innerLeftBtn, false, true);
//        checkFillsParent(innerRightBtn, false, true);
//
//        assertTrue("Left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(leftBtn, AlignSide.LEFT));
//        assertTrue("Right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(rightBtn, AlignSide.RIGHT));
//        assertTrue("Top button is not aligned to the top side of it's parent",
//                StoryboardAnalyzer.isAligned(topBtn, AlignSide.TOP));
//        assertTrue("Inner left button is not aligned to the left side of it's parent",
//                StoryboardAnalyzer.isAligned(innerLeftBtn, AlignSide.LEFT));
//        assertTrue("Inner right button is not aligned to the right side of it's parent",
//                StoryboardAnalyzer.isAligned(innerRightBtn, AlignSide.RIGHT));
//    }


    //<--============================================New Tests For Transfromer=============================================-->


    private String convertLayout(String layoutPath, String outFilename, String outputFolderPathName)
            throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        String curDir = new File(".").getCanonicalPath();
        String fileToCheck = curDir + OUT_PATH + outputFolderPathName + "/" + outFilename + "." + OUT_FORMAT_STORYBOARD;
        String[] args = new String[]{
                "--ixml-res-path=" + curDir + RES_PATH + layoutPath,
                "--out-format=" + OUT_FORMAT_STORYBOARD,
                "--out-filename=" + outFilename,
                "--out-path=" + curDir + OUT_PATH + outputFolderPathName,
                "--uitransformer-res-path=" + curDir + TRANSFORMER_RES__PATH,
        };

        OSXUITransformer.main(args);
        return fileToCheck;
    }

    private int runIBtool(File storyboardPath) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("ibtool " + storyboardPath + " --write " + storyboardPath + " --update-frames");
            return proc.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
            return -1;
        }
    }

    private void runIBToolSeparately(String storyboardPath) {
        try {
            String curDir = new File(".").getCanonicalPath();
            String fileToCheck = curDir + OUT_PATH + storyboardPath + "." + OUT_FORMAT_STORYBOARD;
            File outputFile = new File(fileToCheck);
            assertEquals("ibtool stopped with error", 0, runIBtool(outputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Document getConvertedDocument(String layoutName, String outName, String outputFolderPathName, boolean runIBTool) {
        InputStream in = null;
        Document doc = null;
        try {
            String fileToCheck = null;
            try {
                fileToCheck = convertLayout(layoutName, outName, outputFolderPathName);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }

            File outputFile = new File(fileToCheck);
            assertTrue("Output file doesn't exist", outputFile.exists());

            if (runIBTool) assertEquals("ibtool stopped with error", 0, runIBtool(outputFile));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            in = new BufferedInputStream(new FileInputStream(fileToCheck));
            doc = builder.parse(new InputSource(in));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("Failed to parse generated file");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    System.out.println("Failed to close file stream");
                    e.printStackTrace();
                }
            }
        }
        assertNotNull("Couldn't parse file", doc);
        return doc;
    }

    private Node getButtonCheck(Document doc, String caption) {
        Node btn = StoryboardAnalyzer.getButtonByCaption(doc, caption);
        assertNotNull(caption + " was not found", btn);
        return btn;
    }

    private Node getLabelCheck(Document doc, String caption) {
        Node label = StoryboardAnalyzer.getLabelByCaption(doc, caption);
        assertNotNull(caption + " was not found", label);
        return label;
    }

    private Node getTextFieldCheck(Document doc, String caption) {
        Node textField = StoryboardAnalyzer.getTextFieldByCaption(doc, caption);
        assertNotNull(caption + " was not found", textField);
        return textField;
    }

    private Node testOnlyOneChildWithTag(Element el, String tag) {
        String prevTag = el.getTagName();
        NodeList list = el.getChildNodes();
        Node n = null;
        int num = 0;
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(tag)) {
                num++;
                n = list.item(i);
            }
        }
        assertNotEquals("No " + tag + " found in " + prevTag + " element", 0, num);
        assertEquals("Only 1 " + tag + " expected", 1, num);
        return n;
    }


    private void checkIfElementTransformed(Document doc, String tag) {

        // Firstly check if default storyboard hierarchy transformed
        Element scenes = (Element) testOnlyOneChildWithTag(doc.getDocumentElement(), "scenes");
        assertNotNull("scenes element doesn`t exist", scenes);

        Element scene = (Element) testOnlyOneChildWithTag(scenes, "scene");
        assertNotNull("scene element doesn`t exist", scene);

        Element objects = (Element) testOnlyOneChildWithTag(scene, "objects");
        assertNotNull("objects element doesn`t exist", objects);

        Element viewController = (Element) testOnlyOneChildWithTag(objects, "viewController");
        assertNotNull("viewController element doesn`t exist", viewController);

        assertTrue(tag + " was not found", StoryboardAnalyzer.checkIfTagExists(viewController, tag));
    }

    private Node checkControllerExistence(Document doc, String controllerName) {

        // Firstly check if default storyboard hierarchy transformed
        Node scenes = StoryboardAnalyzer.getChildTag(doc.getDocumentElement(), "scenes");
        assertNotNull("scenes element doesn`t exist", scenes);

        NodeList sceneTags = doc.getElementsByTagName("scene");

        for (int i = 0; i < sceneTags.getLength(); i++) {
            Node scene = sceneTags.item(i);

            Node objects = StoryboardAnalyzer.getChildTag(scene, "objects");

            if (objects == null) {
                return null;
            }

            Node expectedController = StoryboardAnalyzer.getChildTag(objects, controllerName);

            if (expectedController != null) {
                return expectedController;
            }
        }

        return null;
    }

    private void checkHorizontalOrder(Node leftElement, Node secondElement, boolean secondIsRight) {
        String leftName = StoryboardAnalyzer.getCaption(leftElement);
        String secondName = StoryboardAnalyzer.getCaption(secondElement);
        String errorMes = secondName
                + (secondIsRight ? " doesn't" : "")
                + " seem to be to the right of the "
                + leftName;

        assertEquals(errorMes, secondIsRight,
                StoryboardAnalyzer.isRightTo(leftElement, secondElement));
        assertFalse(leftName + " seem to be to the right of the " + secondName,
                StoryboardAnalyzer.isRightTo(secondElement, leftElement));
    }

    private void checkVerticalOrder(Node topElement, Node secondElement, boolean secondIsBelow) {
        String topName = StoryboardAnalyzer.getCaption(topElement);
        String secondName = StoryboardAnalyzer.getCaption(secondElement);
        String errorMes = secondName
                + (secondIsBelow ? " doesn't" : "")
                + " seem to be below the "
                + topName;

        assertEquals(errorMes, secondIsBelow,
                StoryboardAnalyzer.isBelow(topElement, secondElement));
        assertFalse(topName + " seem to be below the " + secondName,
                StoryboardAnalyzer.isBelow(secondElement, topElement));
    }

    private void checkElementConstrains(Node element, ElementScale horizontalScale, ElementScale verticalScale) {
        if (horizontalScale == ElementScale.FILL_PARENT) {
            checkFillsParentByWidth(element);
        } else {
            checkFixedSizeWidthConstraint(element);
        }

        if (verticalScale == ElementScale.FILL_PARENT) {
            checkFillsParentByHeight(element);
        } else {
            checkFixedSizeHeightConstraint(element);
        }
    }

    private void checkFixedSizeWidthConstraint(Node element) {
        String name = StoryboardAnalyzer.getCaption(element);

        boolean hasWidthConstraint = StoryboardAnalyzer.hasWidthConstraint(element);

        assertTrue(name + (hasWidthConstraint ? " has" : " doesn't have") + " fixed width constraint",
                hasWidthConstraint);
    }

    private void checkFixedSizeHeightConstraint(Node element) {
        String name = StoryboardAnalyzer.getCaption(element);

        boolean hasHeightConstraint = StoryboardAnalyzer.hasHeightConstraint(element);

        assertTrue(name + (hasHeightConstraint ? " has" : " doesn't have") + " fixed height constraint",
                hasHeightConstraint);
    }

    private void checkFillsParentByWidth(Node element) {
        String name = StoryboardAnalyzer.getCaption(element);

        boolean fillsHor = StoryboardAnalyzer.fillsParent(element, Orientation.HORIZONTAL);

        assertTrue(name + (fillsHor ? " fills" : " doesn't fill") + " parent horizontally",
                fillsHor);
    }

    private void checkFillsParentByHeight(Node element) {
        String name = StoryboardAnalyzer.getCaption(element);

        boolean fillsVert = StoryboardAnalyzer.fillsParent(element, Orientation.VERTICAL);

        assertTrue(name + (fillsVert ? " doesn't fill" : " fills") + " parent vertically",
                fillsVert);
    }

    private void checkBackgroundColor(Node element, String expectedColorInHex) {
        boolean rightColor = StoryboardAnalyzer.checkBackgroundColor(element, expectedColorInHex);

        assertTrue(element.getNodeName() + (rightColor ? " has" : " doesn't have") + " right background color",
                rightColor);
    }

    private void checkTextColor(Node element, String expectedColor) {
        boolean rightColor = StoryboardAnalyzer.checkTextColor(element, expectedColor);

        assertTrue(element.getNodeName() + (rightColor ? " has" : " doesn't have") + " right text color",
                rightColor);
    }

    private void checkTextSize(Node element, int expectedSize) {
        boolean rightTextSize = StoryboardAnalyzer.checkTextSize(element, expectedSize);

        assertTrue(element.getNodeName() + (rightTextSize ? " has" : " doesn't have") + " right text size",
                rightTextSize);
    }

    private void checkTextStyle(Node element, String expectedStyle) {
        boolean rightTextStyle = StoryboardAnalyzer.checkTextStyle(element, expectedStyle);

        assertTrue(element.getNodeName() + (rightTextStyle ? " has" : " doesn't have") + " right text expectedStyle",
                rightTextStyle);
    }

    private void checkMargins(Node element, ElementScale scale, Margins margins) {

            boolean rightMargins = StoryboardAnalyzer.checkMargins(element, scale, margins);

            assertTrue(element.getNodeName() + (rightMargins ? " has" : " doesn't have") + " right margins",
                    rightMargins);

    }

    private void checkEqualConstraints(Node firstElement, Node secondElement, boolean checkWidthConstraint) {

        if (checkWidthConstraint) {

            boolean rightWidthConstraints = StoryboardAnalyzer.checkEqualConstraints(firstElement, secondElement, true);

            assertTrue(firstElement.getNodeName() + firstElement.getNodeName() +
                            (rightWidthConstraints ? " has" : " doesn't have") + " right width equal constraints",
                    rightWidthConstraints);
        } else {

            boolean rightHeightConstraints = StoryboardAnalyzer.checkEqualConstraints(firstElement, secondElement, false);

            assertTrue(firstElement.getNodeName() + firstElement.getNodeName() +
                            (rightHeightConstraints ? " has" : " doesn't have") + " right height equal constraints",
                    rightHeightConstraints);
        }

    }

    private void checkSegue(Node element, String segueType) {
        boolean rightSegue = StoryboardAnalyzer.checkSegue(element, segueType);

        assertTrue(element.getNodeName() + (rightSegue ? " has" : " doesn't have") + " right segue",
                rightSegue);
    }

    private void checkSegueIdentifier(Node element, String segueIdentifier) {
        boolean rightSegueIdentifier = StoryboardAnalyzer.checkSegueIdentifier(element, segueIdentifier);

        assertTrue(element.getNodeName() + (rightSegueIdentifier ? " has" : " doesn't have") + " right segue identifier",
                rightSegueIdentifier);
    }

    private void checkIBOutlet(Node element, String property) {
        boolean rightIBOutlet = StoryboardAnalyzer.checkIBOutlet(element, property);

        assertTrue(element.getNodeName() + (rightIBOutlet ? " has" : " doesn't have") + " right IBOutlet",
                rightIBOutlet);
    }

    private void checkIBAction(Node element, String eventType, String selector) {
        boolean rightIBAction = StoryboardAnalyzer.checkIBAction(element, eventType, selector);

        assertTrue(element.getNodeName() + (rightIBAction ? " has" : " doesn't have") + " right IBAction",
                rightIBAction);
    }

    private void checkGravity(Node element, Orientation orientation, Gravity gravity) {
        boolean isGravityRight = StoryboardAnalyzer.checkGravity(element, orientation, gravity);

        assertTrue(element.getNodeName() + (isGravityRight ? " has" : " doesn't have") + " right gravity",
                isGravityRight);
    }

    private void checkRect(Node element, Rect rect) {
        boolean isRectRight = StoryboardAnalyzer.checkRect(element, rect);

        assertTrue(element.getNodeName() + (isRectRight ? " has" : " doesn't have") + " right rect",
                isRectRight);
    }

    private void checkDrawable(Node element, String drawableFileName) {
        boolean isDrawableRight = StoryboardAnalyzer.checkDrawable(element, drawableFileName);

        assertTrue(element.getNodeName() + (isDrawableRight ? " has" : " doesn't have") + " right drawable background",
                isDrawableRight);
    }

    private void checkNavigationItemsExistence(NodeList controllersList) {

        for (int i = 0; i < controllersList.getLength(); i++) {
            Node navigationItemTag = StoryboardAnalyzer.getChildTag(controllersList.item(i), "navigationItem");

            assertNotNull(controllersList.item(i).getNodeName() + " doesn't have navigation tag",
                    navigationItemTag);
        }

    }

    private void checkRestorationIdentifier(Node node, String restorationIdentifier) {
        boolean isRestorationIdentifierRight = StoryboardAnalyzer.checkRestorationIdentifier(node, restorationIdentifier);

        assertTrue(node.getNodeName() + (isRestorationIdentifierRight ? " has" : " doesn't have") + " right restorationIdentifier",
                isRestorationIdentifierRight);
    }

    private void checkStoryboardIdentifier(Node node, String storyboardIdentifier) {
        boolean isStoryboardIdentifierRight = StoryboardAnalyzer.checkStoryboardIdentifier(node, storyboardIdentifier);

        assertTrue(node.getNodeName() + (isStoryboardIdentifierRight ? " has" : " doesn't have") + " right storyboardIdentifier",
                isStoryboardIdentifierRight);
    }

    private void checkReuseIdentifier(Node node, String reuseIdentifier) {
        boolean isReuseIdentifierRight = StoryboardAnalyzer.checkReuseIdentifier(node, reuseIdentifier);

        assertTrue(node.getNodeName() + (isReuseIdentifierRight ? " has" : " doesn't have") + " right reuseIdentifier",
                isReuseIdentifierRight);
    }

    private void checkTableViewCellStyle(Node node, String style) {
        boolean isRightStyle = StoryboardAnalyzer.checkTableViewCellStyle(node, style);

        assertTrue(node.getNodeName() + (isRightStyle ? " has" : " doesn't have") + " right style",
                isRightStyle);
    }

    private void checkProgressViewProgress(Node node, double progress) {
        boolean isRightProgress = StoryboardAnalyzer.checkProgressViewProgress(node, progress);

        assertTrue(node.getNodeName() + (isRightProgress ? " has" : " doesn't have") + " right progress",
                isRightProgress);
    }

    private void checkPageControlPagesNumber(Node node, int numberOfPages) {
        boolean isRightNumberOfPages = StoryboardAnalyzer.checkPageControlPagesNumber(node, numberOfPages);

        assertTrue(node.getNodeName() + (isRightNumberOfPages ? " has" : " doesn't have") + " right numberOfPages",
                isRightNumberOfPages);
    }

    private void checkPageControlCurrentPage(Node node, int currentPage) {
        boolean isRightCurrentPage = StoryboardAnalyzer.checkPageControlCurrentPage(node, currentPage);

        assertTrue(node.getNodeName() + (isRightCurrentPage ? " has" : " doesn't have") + " right currentPage",
                isRightCurrentPage);
    }

    private void checkBarButtonItemWidth(Node node, int width) {
        assertEquals(StoryboardAnalyzer.getAttribute(node.getAttributes(), "width"), Integer.toString(width));
    }

    private void checkElementWidth(Node node, int width) {
        boolean isRightWidth = StoryboardAnalyzer.checkElementWidth(node, width);

        assertTrue(node.getNodeName() + (isRightWidth ? " has" : " doesn't have") + " right width",
                isRightWidth);
    }

    private void checkCustomClass(Node node, String customClassName) {
        boolean isCustomClass = StoryboardAnalyzer.checkCustomClass(node, customClassName);

        assertTrue(node.getNodeName() + (isCustomClass ? " has" : " doesn't have") + " right custom class name",
                isCustomClass);
    }


    //<--============================================Tests for constraints setting with 2 Buttons on vertical layout=============================================-->

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightFP_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_FP_Height_FP",
                "1-Width_FP_Height_FP__2-_Width_FP_Height_FP",
                "/2_Buttons/vertical_layout/1-Width_FP_Height_FP/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightFP_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_FP_Height_WC",
                "1-Width_FP_Height_FP__2-_Width_FP_Height_WC",
                "/2_Buttons/vertical_layout/1-Width_FP_Height_FP/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightFP_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_WC_Height_FP",
                "1-Width_FP_Height_FP__2-_Width_WC_Height_FP",
                "/2_Buttons/vertical_layout/1-Width_FP_Height_FP/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightFP_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_WC_Height_WC",
                                            "1-Width_FP_Height_FP__2-_Width_WC_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_FP_Height_FP/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightWC_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_FP_Height_FP",
                                            "1-Width_FP_Height_WC__2-_Width_FP_Height_FP",
                                            "/2_Buttons/vertical_layout/1-Width_FP_Height_WC/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }
    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightWC_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_FP_Height_WC",
                                            "1-Width_FP_Height_WC__2-_Width_FP_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_FP_Height_WC/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightWC_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_WC_Height_FP",
                                            "1-Width_FP_Height_WC__2-_Width_Wc_Height_FP",
                                            "/2_Buttons/vertical_layout/1-Width_FP_Height_WC/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthFPHeightWC_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_WC_Height_WC",
                                            "1-Width_FP_Height_WC__2-_Width_WC_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_FP_Height_WC/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightFP_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_FP_Height_FP",
                                            "1-Width_WC_Height_FP__2-_Width_WC_Height_FP",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_FP/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightFP_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_FP_Height_WC",
                                            "1-Width_WC_Height_FP__2-_Width_FP_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_FP/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightFP_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_WC_Height_FP",
                                            "1-Width_WC_Height_FP__2-_Width_WC_Height_FP",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_FP/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightFP_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_WC_Height_WC",
                                            "1-Width_WC_Height_FP__2-_Width_WC_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_FP/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightWC_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_FP_Height_FP",
                                            "1-Width_WC_Height_WC__2-_Width_FP_Height_FP",
                                            "/2_Buttons/vertical_layout/1-Width_Wc_Height_Wc/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightWC_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_FP_Height_WC",
                                            "1-Width_WC_Height_WC__2-_Width_FP_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_WC/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightWC_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_WC_Height_FP",
                                            "1-Width_WC_Height_WC__2-_Width_WC_Height_FP",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_WC/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__verticalLayout_1WidthWCHeightWC_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/vertical_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_WC_Height_WC",
                                            "1-Width_WC_Height_WC__2-_Width_WC_Height_WC",
                                            "/2_Buttons/vertical_layout/1-Width_WC_Height_WC/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkVerticalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }


    //<--============================================Tests for constraints setting with 2 Buttons on horizontal layout =============================================-->

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightFP_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_FP_Height_FP",
                "1-Width_FP_Height_FP__2-_Width_FP_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_FP/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

    @Test

    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightFP_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_FP_Height_WC",
                "1-Width_FP_Height_FP__2-_Width_FP_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_FP/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightFP_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_WC_Height_FP",
                "1-Width_FP_Height_FP__2-_Width_WC_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_FP/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightFP_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_FP/2-_Width_WC_Height_WC",
                "1-Width_FP_Height_FP__2-_Width_WC_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_FP/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightWC_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_FP_Height_FP",
                "1-Width_FP_Height_WC__2-_Width_FP_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_WC/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }
    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightWC_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_FP_Height_WC",
                "1-Width_FP_Height_WC__2-_Width_FP_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_WC/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightWC_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_WC_Height_FP",
                "1-Width_FP_Height_WC__2-_Width_Wc_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_WC/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthFPHeightWC_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_FP_Height_WC/2-_Width_WC_Height_WC",
                "1-Width_FP_Height_WC__2-_Width_WC_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_FP_Height_WC/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightFP_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_FP_Height_FP",
                "1-Width_WC_Height_FP__2-_Width_WC_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_FP/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightFP_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_FP_Height_WC",
                "1-Width_WC_Height_FP__2-_Width_FP_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_FP/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightFP_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_WC_Height_FP",
                "1-Width_WC_Height_FP__2-_Width_WC_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_FP/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightFP_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_FP/2-_Width_WC_Height_WC",
                "1-Width_WC_Height_FP__2-_Width_WC_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_FP/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightWC_2WidthFPHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_FP_Height_FP",
                "1-Width_WC_Height_WC__2-_Width_FP_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_Wc_Height_Wc/2-_Width_FP_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightWC_2WidthFPHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_FP_Height_WC",
                "1-Width_WC_Height_WC__2-_Width_FP_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_WC/2-_Width_FP_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");


        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.WRAP_CONTENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightWC_2WidthWCHeightFP_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_WC_Height_FP",
                "1-Width_WC_Height_WC__2-_Width_WC_Height_FP",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_WC/2-_Width_WC_Height_FP", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");



        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.FILL_PARENT);
    }

    @Test
    public void layoutWith2Buttons__horizontalLayout_1WidthWCHeightWC_2WidthWCHeightWC_test() {

        Document doc = getConvertedDocument("res-buttons_tests/horizontal_layout/2_Buttons/1-Width_WC_Height_WC/2-_Width_WC_Height_WC",
                "1-Width_WC_Height_WC__2-_Width_WC_Height_WC",
                "/2_Buttons/horizontal_layout/1-Width_WC_Height_WC/2-_Width_WC_Height_WC", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");


        // vertical order
        checkHorizontalOrder(firstBtn, secondBtn, true);

        // borders and constraints
        checkElementConstrains(firstBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
        checkElementConstrains(secondBtn, ElementScale.WRAP_CONTENT, ElementScale.WRAP_CONTENT);
    }

    //<--===================================Tests for styles (Background color, Text, Text size, Text color)=====================================-->


    @Test
    public void button_backgroundColor_test() {

        Document doc = getConvertedDocument("res-buttons_tests/styles_tests/background_color_test",
                "Button_background_color",
                "/styles/button/background_color", true);

        Node btn = getButtonCheck(doc, "Button");

        // Red R-255 G-15 B-15 #ffff0f0f
        checkBackgroundColor(btn, "#ffff0f0f");
    }

    @Test
    public void button_textColor_test() {

        Document doc = getConvertedDocument("res-buttons_tests/styles_tests/text_color_test",
                "Button_text_color",
                "/styles/button/text_color", true);

        Node btn = getButtonCheck(doc, "Button");

        // Green #ff29ff0e
        checkTextColor(btn, "#ff29ff0e");
    }

    @Test
    public void button_text_test() {

        Document doc = getConvertedDocument("res-buttons_tests/styles_tests/text_test",
                "Button_text",
                "/styles/button/text", true);

        // If null - then no button with this text
        getButtonCheck(doc, "Some Button With Text");
    }

    @Test
    public void button_textSize_test() {

        Document doc = getConvertedDocument("res-buttons_tests/styles_tests/text_size_test",
                "Button_text_size",
                "/styles/button/text_size", true);

        Node btn = getButtonCheck(doc, "Button");

        // 20dp
        checkTextSize(btn, 20);
    }

    @Test
    public void button_textStyle_test() {

        Document doc = getConvertedDocument("res-buttons_tests/styles_tests/text_style_test",
                "Button_text_style",
                "/styles/button/text_style", true);

        Node btn = getButtonCheck(doc, "Button");

        // bold
        checkTextStyle(btn, "boldSystem");
    }


    //-------------------------

    @Test
    public void label_backgroundColor_test() {

        Document doc = getConvertedDocument("label_tests/styles_tests/background_color_test",
                "label_background_color",
                "/styles/label/background_color", true);

        Node label = getLabelCheck(doc, "Test Label");

        checkBackgroundColor(label, "#ffddff35");
    }

    @Test
    public void label_textColor_test() {

        Document doc = getConvertedDocument("label_tests/styles_tests/text_color_test",
                "label_text_color",
                "/styles/label/text_color", true);

        Node label = getLabelCheck(doc, "Test Label");

        checkTextColor(label, "#ffff203c");
    }

    @Test
    public void label_text_test() {

        Document doc = getConvertedDocument("label_tests/styles_tests/text_test",
                "label_text",
                "/styles/label/text", true);

        // If null - then no label with this text
        getLabelCheck(doc, "Test Label with text");
    }

    @Test
    public void label_textSize_test() {

        Document doc = getConvertedDocument("label_tests/styles_tests/text_size_test",
                "label_text_size",
                "/styles/label/text_size", true);

        Node label = getLabelCheck(doc, "Test Label");

        // 16dp
        checkTextSize(label, 16);
    }

    @Test
    public void label_textStyle_test() {

        Document doc = getConvertedDocument("label_tests/styles_tests/text_style_test",
                "label_text_style",
                "/styles/label/text_style", true);

        Node label = getLabelCheck(doc, "Label");

        // bold
        checkTextStyle(label, "boldSystem");
    }

    //--------------------------

    @Ignore
    @Test
    public void textField_backgroundColor_test() {

        Document doc = getConvertedDocument("res-buttons_tests/styles_tests/background_color_test",
                "Button_background_color",
                "/styles/button/background_color", true);

        Node textField = getTextFieldCheck(doc, "Enter Text");

        checkBackgroundColor(textField, "#ffff0f0f");
    }

    @Test
    public void textField_textColor_test() {

        Document doc = getConvertedDocument("textField_tests/styles_tests/text_color_test",
                "textField_text_color",
                "/styles/textField/text_color", true);

        Node textField = getTextFieldCheck(doc, "Enter Text");

        checkTextColor(textField, "#ffff4622");
    }

    @Test
    public void textField_text_test() {

        Document doc = getConvertedDocument("textField_tests/styles_tests/text_test",
                "textField_text",
                "/styles/textField/text", true);

        // If null - then no textField with this text
        getTextFieldCheck(doc, "Some textField With Text");
    }

    @Test
    public void textField_textSize_test() {

        Document doc = getConvertedDocument("textField_tests/styles_tests/text_size_test",
                "textField_text_size",
                "/styles/textField/text_size", true);

        Node textField = getTextFieldCheck(doc, "Enter Text");

        // 16dp
        checkTextSize(textField, 16);
    }

    @Test
    public void textField_textStyle_test() {

        Document doc = getConvertedDocument("textField_tests/styles_tests/text_style_test",
                "textField_text_style",
                "/styles/textField/text_style", true);

        Node textField = getTextFieldCheck(doc, "Enter Text");

        // bold
        checkTextStyle(textField, "boldSystem");
    }

    //<--========================================Tests for plain elements (One unique element in layout)=================================================-->

    @Test
    public void ordinaryElementsTransformation_activityIndeicatorView_test() {

        Document doc = getConvertedDocument("res-plain_elements/activityIndicatorView",
                "activityIndicatorView",
                "/Plain_elements/activityIndicatorView", true);

        checkIfElementTransformed(doc, "activityIndicatorView");

        Node activityIndicatorView = doc.getElementsByTagName("activityIndicatorView").item(0);

        checkRestorationIdentifier(activityIndicatorView, "identifier");
    }

    @Test
    public void ordinaryElementsTransformation_button_test() {

        Document doc = getConvertedDocument("res-plain_elements/button",
                "button",
                "/Plain_elements/button", true);

        Node btn = getButtonCheck(doc, "Button");

        checkRestorationIdentifier(btn, "identifier");
    }

     @Test //IBtool problems
     public void ordinaryElementsTransformation_datePicker_test() {

         Document doc = getConvertedDocument("res-plain_elements/datePicker",
                 "datePicker",
                 "/Plain_elements/datePicker", false);

         Node datePicker = doc.getElementsByTagName("datePicker").item(0);
         assertNotNull("datePicker tag exists", datePicker);

         checkRestorationIdentifier(datePicker, "identifier");
         checkSegue(datePicker, "show");
         checkSegueIdentifier(datePicker, "segue_identifier");
     }

    @Test
    public void ordinaryElementsTransformation_label_test() {

        Document doc = getConvertedDocument("res-plain_elements/label",
                "label",
                "/Plain_elements/label", true);

        Node label = getLabelCheck(doc, "Label");

        checkRestorationIdentifier(label, "identifier");
    }


    @Test
    public void ordinaryElementsTransformation_mapView_test() {

        Document doc = getConvertedDocument("res-plain_elements/mapView",
                "mapView",
                "/Plain_elements/mapView", true);

        checkIfElementTransformed(doc, "mapView");

        Node mapView = doc.getElementsByTagName("mapView").item(0);

        checkRestorationIdentifier(mapView, "identifier");
    }


    @Test
    public void ordinaryElementsTransformation_navigationBar_test() {

        Document doc = getConvertedDocument("res-plain_elements/navigationBar",
                "navigationBar",
                "/Plain_elements/navigationBar", true);

        checkIfElementTransformed(doc, "navigationBar");
    }

    @Test
    public void ordinaryElementsTransformation_pageControl_test() {

        Document doc = getConvertedDocument("res-plain_elements/pageControl",
                "pageControl",
                "/Plain_elements/pageControl", true);

        Node pageControl = doc.getElementsByTagName("pageControl").item(0);
        assertNotNull("pageControl tag exists", pageControl);

        checkRestorationIdentifier(pageControl, "identifier");
        checkSegue(pageControl, "show");
        checkSegueIdentifier(pageControl, "segue_identifier");

        // @Ignore. Have to be implemented in transformer
        checkPageControlPagesNumber(pageControl, 3);
        checkElementWidth(pageControl, 45);
        //checkPageControlCurrentPage(pageControl, 3);
    }


     @Test //IBtool problems
     public void ordinaryElementsTransformation_pickerView_test() {

         Document doc = getConvertedDocument("res-plain_elements/pickerView",
                 "pickerView",
                 "/Plain_elements/pickerView", false);

         checkIfElementTransformed(doc, "pickerView");

         Node pickerView = doc.getElementsByTagName("pickerView").item(0);

         checkRestorationIdentifier(pickerView, "identifier");
     }

     @Test //IBtool problems
     public void ordinaryElementsTransformation_progressView_test() {

         Document doc = getConvertedDocument("res-plain_elements/progressView",
                 "progressView",
                 "/Plain_elements/progressView", false);

         checkIfElementTransformed(doc, "progressView");

         Node progressView = doc.getElementsByTagName("progressView").item(0);

         checkRestorationIdentifier(progressView, "identifier");

         // @Ignore. Have to be implemented in transformer
         //checkProgressViewProgress(progressView, 0.6);
     }

    @Test
    public void ordinaryElementsTransformation_searchBar_test() {

        Document doc = getConvertedDocument("res-plain_elements/searchBar",
                "searchBar",
                "/Plain_elements/searchBar", true);

        checkIfElementTransformed(doc, "searchBar");

        Node searchBar = doc.getElementsByTagName("searchBar").item(0);

        checkRestorationIdentifier(searchBar, "identifier");
    }

    @Test
    public void ordinaryElementsTransformation_segmentedControl_test() {

        Document doc = getConvertedDocument("res-plain_elements/segmentedControl",
                "segmentedControl",
                "/Plain_elements/segmentedControl", true);

        Node segmentedControl = doc.getElementsByTagName("segmentedControl").item(0);
        assertNotNull("segmentedControl tag exists", segmentedControl);

        checkRestorationIdentifier(segmentedControl, "identifier");
        checkSegue(segmentedControl, "show");
        checkSegueIdentifier(segmentedControl, "segue_identifier");
    }

    @Test
    public void ordinaryElementsTransformation_slider_test() {

        Document doc = getConvertedDocument("res-plain_elements/slider",
                "slider",
                "/Plain_elements/slider", true);

        Node slider = doc.getElementsByTagName("slider").item(0);
        assertNotNull("slider tag exists", slider);

        checkRestorationIdentifier(slider, "identifier");
        checkSegue(slider, "show");
        checkSegueIdentifier(slider, "segue_identifier");
    }

    @Test
    public void ordinaryElementsTransformation_stepper_test() {

        Document doc = getConvertedDocument("res-plain_elements/stepper",
                "stepper",
                "/Plain_elements/stepper", true);

        Node stepper = doc.getElementsByTagName("stepper").item(0);
        assertNotNull("stepper tag exists", stepper);

        checkRestorationIdentifier(stepper, "identifier");
        checkSegue(stepper, "show");
        checkSegueIdentifier(stepper, "segue_identifier");
    }

    @Test
    public void ordinaryElementsTransformation_switch_test() {

        Document doc = getConvertedDocument("res-plain_elements/switch",
                "switch",
                "/Plain_elements/switch", true);

        Node switchNode = doc.getElementsByTagName("switch").item(0);
        assertNotNull("switchNode tag exists", switchNode);

        checkRestorationIdentifier(switchNode, "identifier");
        checkSegue(switchNode, "show");
        checkSegueIdentifier(switchNode, "segue_identifier");
    }

    @Test
    public void ordinaryElementsTransformation_tabBar_test() {

        Document doc = getConvertedDocument("res-plain_elements/tabBar",
                "tabBar",
                "/Plain_elements/tabBar", true);

        checkIfElementTransformed(doc, "tabBar");

        Node tabBar = doc.getElementsByTagName("tabBar").item(0);

        checkRestorationIdentifier(tabBar, "identifier");
    }

    @Test
    public void ordinaryElementsTransformation_tableView_test() {

        Document doc = getConvertedDocument("res-plain_elements/tableView",
                "tableView",
                "/Plain_elements/tableView", true);

        checkIfElementTransformed(doc, "tableView");

        Node tableView = doc.getElementsByTagName("tableView").item(0);

        checkRestorationIdentifier(tableView, "identifier");

        Node tableViewCell = doc.getElementsByTagName("tableViewCell").item(0);
        checkReuseIdentifier(tableViewCell, "reuseIdentifier");
        checkTableViewCellStyle(tableViewCell, "Basic");

    }

    @Test
    public void ordinaryElementsTransformation_textField_test() {

        Document doc = getConvertedDocument("res-plain_elements/textField",
                "textField",
                "/Plain_elements/textField", true);

        Node textField = doc.getElementsByTagName("textField").item(0);
        assertNotNull("switchNode tag exists", textField);

        checkRestorationIdentifier(textField, "identifier");
        checkSegue(textField, "show");
        checkSegueIdentifier(textField, "segue_identifier");
    }

    @Test
    public void ordinaryElementsTransformation_toolbar_test() {

        Document doc = getConvertedDocument("res-plain_elements/toolbar",
                "toolbar",
                "/Plain_elements/toolbar", true);

        checkIfElementTransformed(doc, "toolbar");

        Node toolbar = doc.getElementsByTagName("toolbar").item(0);
        checkRestorationIdentifier(toolbar, "identifier");

        Node barButtonItem = StoryboardAnalyzer.getBarButtonItemByCaption(doc, "Item with width");
        checkBarButtonItemWidth(barButtonItem, 100);
    }

    @Test
    public void ordinaryElementsTransformation_viewController_test() {

        Document doc = getConvertedDocument("res-plain_elements/viewController",
                "viewController",
                "/Plain_elements/viewController", true);

        checkIfElementTransformed(doc, "view");

        Node viewController = doc.getElementsByTagName("viewController").item(0);

        checkStoryboardIdentifier(viewController, "storyboardIdentifier");
        assertTrue("not right attribute set",
                StoryboardAnalyzer.getAttribute(viewController.getAttributes(), "useStoryboardIdentifierAsRestorationIdentifier").equals("YES"));

        Node view = StoryboardAnalyzer.getChildTag(viewController, "view");
        checkCustomClass(view, "UIView1");

    }

    @Test
    public void ordinaryElementsTransformation_simpleView_test() {

        Document doc = getConvertedDocument("res-plain_elements/view",
                "view",
                "/Plain_elements/view", true);

        checkIfElementTransformed(doc, "view");

        Node subviews = doc.getElementsByTagName("subviews").item(0);

        Node view = StoryboardAnalyzer.getChildTag(subviews, "view");
        checkCustomClass(view, "UIView1");

    }



    //<--========================================Tests for margins===================================================-->

    @Test
    public void button_margins_horizontal_fillParent_test() {

        Document doc = getConvertedDocument("margins_tests/button/horizontal_layout/fill_parent",
                "margins_button_horizontal_fillParent_test",
                "/margins/button/horizontal_layout/fill_parent", false);

        Node btn = getButtonCheck(doc, "Button");

        // Top-5dp Right-10dp Bottom-15dp Left-20dp
        checkMargins(btn, ElementScale.FILL_PARENT, new Margins(5, 15, 20, 10));

        // Because of some strange behaviour
        runIBToolSeparately("/margins/button/horizontal_layout/fill_parent/margins_button_horizontal_fillParent_test");
    }

    @Test
    public void button_margins_horizontal_fixedSize_test() {

        Document doc = getConvertedDocument("margins_tests/button/horizontal_layout/fixed_size",
                "margins_button_horizontal_fixedSize_test",
                "/margins/button/horizontal_layout/fixed_size", false);

        Node btn = getButtonCheck(doc, "Button");

        // Top-5dp Right-10dp Bottom-15dp Left-20dp
        checkMargins(btn, ElementScale.FIXED_SIZE, new Margins(5, 15, 20, 10));

        // Because of some strange behaviour
        runIBToolSeparately("/margins/button/horizontal_layout/fixed_size/margins_button_horizontal_fixedSize_test");
    }

    @Test
    public void button_margins_vertical_fillParent_test() {

        Document doc = getConvertedDocument("margins_tests/button/vertical_layout/fill_parent",
                "margins_button_vertical_fillParent_test",
                "/margins/button/vertical_layout/fill_parent", true);

        Node btn = getButtonCheck(doc, "Button");

        // Top-5dp Right-10dp Bottom-15dp Left-20dp
        checkMargins(btn, ElementScale.FILL_PARENT, new Margins(5, 15, 20, 10));
    }

    @Test
    public void button_margins_vertical_fixedSize_test() {

        Document doc = getConvertedDocument("margins_tests/button/vertical_layout/fixed_size",
                "margins_button_vertical_fixedSize_test",
                "/margins/button/vertical_layout/fixed_size", true);

        Node btn = getButtonCheck(doc, "Button");

        // Top-5dp Right-10dp Bottom-15dp Left-20dp
        checkMargins(btn, ElementScale.FIXED_SIZE, new Margins(5, 15, 20, 10));


        // Because of some strange behaviour
        //runIBToolSeparately("/margins/button/vertical_layout/fixed_size/margins_button_vertical_fixedSize_test");
    }

    @Test
    public void label_margins_vertical_test() {

        Document doc = getConvertedDocument("margins_tests/label/vertical_layout",
                "margins_label_vertical_test",
                "/margins/label/vertical_layout", true);

        Node label = getLabelCheck(doc, "Label");

        // Top-5dp Right-10dp Bottom-15dp Left-20dp
        checkMargins(label, ElementScale.FIXED_SIZE, new Margins(5, 15, 20, 10));

        // Because of some strange behaviour
        //runIBToolSeparately("/margins/label/vertical_layout/margins_label_vertical_test");
    }

    @Test
    public void label_margins_horizontal_test() {

        Document doc = getConvertedDocument("margins_tests/label/horizontal_layout",
                "margins_label_horizontal_test",
                "/margins/label/horizontal_layout", true);

        Node label = getLabelCheck(doc, "Label");

        // Top-5dp Right-10dp Bottom-15dp Left-20dp
        checkMargins(label, ElementScale.FIXED_SIZE, new Margins(5, 15, 20, 10));
    }

    //<--========================================Tests for equal constraints=========================================-->

    @Test
    public void buttons_equalConstraints_width_vertical_test() {

        Document doc = getConvertedDocument("res-buttons_tests/equal_constraints/vertical_layout/width",
                "width_equal_constraints_button_vertical_test",
                "/2_Buttons/equal_constraints/vertical_layout/width/", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        checkEqualConstraints(firstBtn, secondBtn, true);
    }

    @Test
    public void buttons_equalConstraints_width_horizontal_test() {

        Document doc = getConvertedDocument("res-buttons_tests/equal_constraints/horizontal_layout/width",
                "width_equal_constraints_button_horizontal_test",
                "/2_Buttons/equal_constraints/horizontal_layout/width/", true);



        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        checkEqualConstraints(firstBtn, secondBtn, true);
    }

    @Test
    public void buttons_equalConstraints_height_vertical_test() {

        Document doc = getConvertedDocument("res-buttons_tests/equal_constraints/vertical_layout/height",
                "height_equal_constraints_button_vertical_test",
                "/2_Buttons/equal_constraints/vertical_layout/height/", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        checkEqualConstraints(firstBtn, secondBtn, false);
    }

    @Test
    public void buttons_equalConstraints_height_horizontal_test() {

        Document doc = getConvertedDocument("res-buttons_tests/equal_constraints/horizontal_layout/height",
                "height_equal_constraints_button_horizontal_test",
                "/2_Buttons/equal_constraints/horizontal_layout/height/", true);

        Node firstBtn = getButtonCheck(doc, "First Button");
        Node secondBtn = getButtonCheck(doc, "Second Button");

        checkEqualConstraints(firstBtn, secondBtn, false);
    }

    //<--============================================Tests for segue=================================================-->

    @Test
    public void segue_for_button_test() {

        Document doc = getConvertedDocument("segue_tests/button",
                "segue_for_button_tests",
                "/segue/button/", true);

        Node btn = getButtonCheck(doc, "Button");

        checkSegue(btn, "show");
        checkSegueIdentifier(btn, "segue_identifier");
    }

    @Test
    public void segue_for_view_test() {

        Document doc = getConvertedDocument("segue_tests/view",
                "segue_for_view_tests",
                "/segue/view/", true);

        // Specific test only for two views in storyboard
        // Because there is no differences between view tags
        Node view = doc.getElementsByTagName("viewController").item(1);

        checkSegue(view, "show");
    }

    @Test
    public void segue_for_tableViewCell_test() {

        Document doc = getConvertedDocument("segue_tests/tableViewCell",
                "segue_for_tableViewCell_tests",
                "/segue/tableViewCell/", true);

        // Specific test only for one tableViewCell in storyboard
        // Because there is no differences between tableViewCell tags
        Node tableViewCell = doc.getElementsByTagName("tableViewCell").item(0);

        checkSegue(tableViewCell, "show");
        checkSegueIdentifier(tableViewCell, "segue_identifier");
    }

    //<--============================================Tests for iboutlet, ibaction======================================-->

    @Test
    public void button_iboutlet_ibaction_test() {

        Document doc = getConvertedDocument("iboutlet_ibaction_tests/",
                "iboutlet_ibaction_test",
                "/iboutlet_ibaction/", true);

        Node btn = getButtonCheck(doc, "Action Button");

        checkIBAction(btn, "touchUpInside", "initAction");
        checkIBOutlet(btn, "someButton");
    }

    //<--============================================Tests for gravity=================================================-->

    @Test
    public void gravityRight_vertical_test() {

        Document doc = getConvertedDocument("gravity_tests/vertical/aligned_right",
                "gravityRight_vertical_test",
                "/gravity/vertical/aligned_right", false);

        Node btn = getButtonCheck(doc, "Button");

        checkGravity(btn, Orientation.VERTICAL, Gravity.RIGHT);

        // Because of some strange behaviour
        runIBToolSeparately("/gravity/vertical/aligned_right/gravityRight_vertical_test");
    }

    @Test
    public void gravityLeft_vertical_test() {

        Document doc = getConvertedDocument("gravity_tests/vertical/aligned_left",
                "gravityLeft_vertical_test",
                "/gravity/vertical/aligned_left", false);

        Node btn = getButtonCheck(doc, "Button");

        checkGravity(btn, Orientation.VERTICAL, Gravity.LEFT);

        // Because of some strange behaviour
        runIBToolSeparately("/gravity/vertical/aligned_left/gravityLeft_vertical_test");
    }

    @Test
    public void gravityTop_horizontal_test() {

        Document doc = getConvertedDocument("gravity_tests/horizontal/aligned_top",
                "gravityTop_horizontal_test",
                "/gravity/horizontal/aligned_top", false);

        Node btn = getButtonCheck(doc, "Button");

        checkGravity(btn, Orientation.HORIZONTAL, Gravity.TOP);

        // Because of some strange behaviour
        runIBToolSeparately("/gravity/horizontal/aligned_top/gravityTop_horizontal_test");
    }

    @Test
    public void gravityBottom_horizontal_test() {

        Document doc = getConvertedDocument("gravity_tests/horizontal/aligned_bottom",
                "gravityBottom_horizontal_test",
                "/gravity/horizontal/aligned_bottom", false);

        Node btn = getButtonCheck(doc, "Button");

        checkGravity(btn, Orientation.HORIZONTAL, Gravity.BOTTOM);

        // Because of some strange behaviour
        runIBToolSeparately("/gravity/horizontal/aligned_bottom/gravityBottom_horizontal_test");

    }

    @Test
    @Ignore
    public void gravity_complicatedLayoutWithLabels_horizontal_test() {
        Document doc = getConvertedDocument("gravity_tests/special_case/horizontal/",
                "gravity_complicatedLayoutWithLabels_horizontal_test",
                "/gravity/special_case/horizontal", false);

        Node labelOne = getLabelCheck(doc, "Label 1");
        Node labelTwo = getLabelCheck(doc, "Label 2");
        Node labelThree = getLabelCheck(doc, "Label 3");

        checkGravity(labelOne, Orientation.HORIZONTAL, Gravity.TOP);
        checkGravity(labelTwo, Orientation.HORIZONTAL, Gravity.CENTER);
        checkGravity(labelThree, Orientation.HORIZONTAL, Gravity.BOTTOM);

        checkRect(labelOne, new Rect(0, 0, 200, 25));
        checkRect(labelTwo, new Rect(200, 288, 200, 25));
        checkRect(labelThree, new Rect(400, 575, 200, 25));

        // Because of some strange ibtool optimisation behaviour
        runIBToolSeparately("/gravity/special_case/horizontal/gravity_complicatedLayoutWithLabels_horizontal_test");
    }

    @Test
    @Ignore
    public void gravity_complicatedLayoutWithLabels_vertical_test() {

        Document doc = getConvertedDocument("gravity_tests/special_case/vertical/",
                "gravity_complicatedLayoutWithLabels_vertical_test",
                "/gravity/special_case/vertical/", false);

        Node labelOne = getLabelCheck(doc, "Label 1");
        Node labelTwo = getLabelCheck(doc, "Label 2");
        Node labelThree = getLabelCheck(doc, "Label 3");

        checkGravity(labelOne, Orientation.VERTICAL, Gravity.LEFT);
        checkGravity(labelTwo, Orientation.VERTICAL, Gravity.RIGHT);
        checkGravity(labelThree, Orientation.VERTICAL, Gravity.CENTER);

        checkRect(labelOne, new Rect(0, 0, 59, 200));
        checkRect(labelTwo, new Rect(541, 200, 59, 200));
        checkRect(labelThree, new Rect(271, 400, 59, 200));

        // Because of some strange behaviour
        runIBToolSeparately("/gravity/special_case/vertical/gravity_complicatedLayoutWithLabels_vertical_test");
    }



    //<--============================================Tests for drawable=================================================-->

    @Test
    public void drawable_button_test() {

        Document doc = getConvertedDocument("drawable_tests/button",
                "drawable_button_test",
                "/drawable_tests/button", true);

        Node btn = getButtonCheck(doc, " ");

//        checkDrawable(btn, "moe.jpg");
    }

    @Test
    public void drawable_barButtonItem_test() {

        Document doc = getConvertedDocument("drawable_tests/barButtonItem",
                "drawable_barButtonItem_test",
                "/drawable_tests/barButtonItem", true);

        // Specific test only for one barButtonItem in storyboard
        // Because there is no differences between barButtonItem tags
        // (in case of image background without title)
        Node barButtonItem = doc.getElementsByTagName("barButtonItem").item(0);

        checkDrawable(barButtonItem, "image.png");
    }

    //<--============================================Tests for navigation controller=================================================-->

    @Test
    public void navigationController_twoViewControllers_navigationControllerExistence_test() {

        Document doc = getConvertedDocument("navigation_controller_tests/view_controllers",
                "navigationController_twoViewControllers_test",
                "navigation_controller_tests/view_controllers", true);

        Node navigationController = checkControllerExistence(doc, "navigationController");

        assertNotNull("navigation controller do not exists", navigationController);

        assertNotNull("NavigationBar in controller doesn`t exist", StoryboardAnalyzer.getChildTag(navigationController, "navigationBar"));
    }

    @Test
    public void navigationController_twoViewControllers_navigationItemsExist_test() {

        Document doc = getConvertedDocument("navigation_controller_tests/view_controllers",
                "navigationController_twoViewControllers_test",
                "navigation_controller_tests/view_controllers", true);

        NodeList viewControllers = doc.getElementsByTagName("viewController");

        checkNavigationItemsExistence(viewControllers);
    }

    @Test
    public void navigationController_twoViewControllers_segueExists_test() {

        Document doc = getConvertedDocument("navigation_controller_tests/view_controllers",
                "navigationController_twoViewControllers_test",
                "navigation_controller_tests/view_controllers", true);

        // Only if one navigationController tag exists
        Node navigationController = doc.getElementsByTagName("navigationController").item(0);

        checkSegue(navigationController, "relationship");
    }

     @Test
     public void navigationController_tableView_navigationControllerExistence_test() {

         Document doc = getConvertedDocument("navigation_controller_tests/table_view_controller",
                 "navigationController_tableViewController_test",
                 "navigation_controller_tests/table_view_controller", false);

         Node navigationController = checkControllerExistence(doc, "navigationController");

         assertNotNull("NavigationBar in controller doesn`t exist", StoryboardAnalyzer.getChildTag(navigationController, "navigationBar"));
     }

     @Test //IBtool problems
     public void navigationController_tableView_checkSegue_test() {

         Document doc = getConvertedDocument("navigation_controller_tests/table_view_controller",
                 "navigationController_tableViewController_test",
                 "navigation_controller_tests/table_view_controller", false);

         // Only if one navigationController tag exists
         Node navigationController = doc.getElementsByTagName("navigationController").item(0);

         checkSegue(navigationController, "relationship");
     }

    @Test
    public void navigationController_tableView_navigationItemsExist_test() {

        Document doc = getConvertedDocument("navigation_controller_tests/view_controllers",
                "navigationController_twoViewControllers_test",
                "navigation_controller_tests/view_controllers", true);

        NodeList viewController = doc.getElementsByTagName("tableViewController");

        checkNavigationItemsExistence(viewController);
    }

    //<--===============================Tests for buttons inside table view cell constraints=================================-->

     @Test //IBtool problems
     public void tableViewCellWith2Buttons_1WidthFPHeightFP_2WidthFPHeightFP_test() {

         Document doc = getConvertedDocument("table_view_cell_tests/2_buttons/without_linear_layout/1-Width_FP_Height_FP__2-_Width_FP_Height_FP/",
                 "1-Width_FP_Height_FP__2-_Width_FP_Height_FP",
                 "table_view_cell_tests/2_buttons/without_linear_layout/1-Width_FP_Height_FP__2-_Width_FP_Height_FP/", false);

         Node firstBtn = getButtonCheck(doc, "First Button");
         Node secondBtn = getButtonCheck(doc, "Second Button");

         // borders and constraints
         checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
         checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
     }

     @Test //IBtool problems
     public void tableViewCellWith2Buttons_withLinearLayoutInside_test() {
         Document doc = getConvertedDocument("table_view_cell_tests/2_buttons/linear_layout_inside/1-Width_FP_Height_FP__2-_Width_FP_Height_FP",
                 "1-Width_FP_Height_FP__2-_Width_FP_Height_FP",
                 "table_view_cell_tests/2_buttons/linear_layout_inside/1-Width_FP_Height_FP__2-_Width_FP_Height_FP", false);

         Node firstBtn = getButtonCheck(doc, "First Button");
         Node secondBtn = getButtonCheck(doc, "Second Button");

         // borders and constraints
         checkElementConstrains(firstBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
         checkElementConstrains(secondBtn, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
     }

    //<--===============================Tests for image view=================================-->

    @Test
    public void imageView_rightDrawable_test() {

        Document doc = getConvertedDocument("image_view_tests/",
                "image_view_test",
                "image_view_tests", true);

        // Specific test only for one imageView in storyboard
        Node imageView = doc.getElementsByTagName("imageView").item(0);

//        checkDrawable(imageView, "moe.jpg");
    }

    @Test
    public void imageView_checkConstraints_test() {

        Document doc = getConvertedDocument("image_view_tests/",
                "image_view_test",
                "image_view_tests", true);

        // Specific test only for one imageView in storyboard
        Node imageView = doc.getElementsByTagName("imageView").item(0);

        checkElementConstrains(imageView, ElementScale.FILL_PARENT, ElementScale.FILL_PARENT);
    }

}
