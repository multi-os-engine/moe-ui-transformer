<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
	xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    xmlns:xrt="http://schemas.android.com/apk/res/android"
	exclude-result-prefixes="osxui">
	<xsl:strip-space elements="*" />
	<xsl:output method="xml" indent="yes" standalone="no"/>
	
	<!-- Start global variables -->
	<xsl:variable name="resPath" select="osxui:GetXsltResPath()" />
	<!-- <xsl:variable name="g_stylePath">../AndroidUIStyles/res-button/values/styles.xml</xsl:variable> -->
	<xsl:variable name="g_stylePath" select="osxui:GetStylesPath()"/>
	<!-- <xsl:variable name="g_stylePath">../res-button/values/styles.xml</xsl:variable> -->
	<!-- <xsl:variable name="g_stylePath_button">../AndroidUIStyles/res-button/values/styles.xml</xsl:variable> -->
	<!-- <xsl:variable name="g_stylePath_button">../res-button/values/styles.xml</xsl:variable> -->

    <!--
	<xsl:variable name="g_stylePath_button" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_label" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_textField" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_switch" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_slider" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_stepper" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_picker" select="osxui:GetStylesPath()" />

	<xsl:variable name="g_stylePath_progressView" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_activityIndicatorView" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_segmentedControl" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_pageControl" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_navigationBar" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_searchBar" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_toolbar" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_mapView" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_datePicker" select="osxui:GetStylesPath()" />
	<xsl:variable name="g_stylePath_tabBar" select="osxui:GetStylesPath()" />
    <xsl:variable name="g_stylePath_tableView" select="osxui:GetStylesPath()" />
    -->

	<xsl:variable name="g_drawablePath" select="osxui:GetDrawablePath()"/>
	<!-- End global variables -->
	
	<!-- Start inclides -->
    <xsl:include href="ControllerTemplates/NavigarionController.xsl" />
    <xsl:include href="ControllerTemplates/TableViewController.xsl" />
    <xsl:include href="ControllerTemplates/ViewController.xsl" />

    <xsl:include href="ContainersTemplates/NavigationBarContainer.xsl" />

    <xsl:include href="LayoutTemplates/LinearLayoutTemplate.xsl" />
    <xsl:include href="LayoutTemplates/RelativeLayoutTemplate.xsl" />

    <xsl:include href="ControlTemplates/ButtonTemplate.xsl" />
    <xsl:include href="ControlTemplates/TextViewTemplate.xsl" />
    <xsl:include href="ControlTemplates/TextFieldTemplate.xsl" />
    <xsl:include href="ControlTemplates/SwitchTemplate.xsl" />
    <xsl:include href="ControlTemplates/SliderTemplate.xsl" />
    <xsl:include href="ControlTemplates/StepperTemplate.xsl" />
    <xsl:include href="ControlTemplates/PickerTemplate.xsl" />
    <xsl:include href="ControlTemplates/ProgressViewTemplate.xsl" />
    <xsl:include href="ControlTemplates/ActivityIndicatorViewTemplate.xsl" />
    <xsl:include href="ControlTemplates/SegmentedControlTemplate.xsl" />
    <xsl:include href="ControlTemplates/PageControlTemplate.xsl" />
    <xsl:include href="ControlTemplates/NavigationBarTemplate.xsl" />
    <xsl:include href="ControlTemplates/SearchBarTemplate.xsl" />
    <xsl:include href="ControlTemplates/ToolbarTemplate.xsl" />
    <xsl:include href="ControlTemplates/MapViewTemplate.xsl" />
    <xsl:include href="ControlTemplates/DatePickerTemplate.xsl" />
    <xsl:include href="ControlTemplates/TabBarTemplate.xsl" />
    <xsl:include href="ControlTemplates/TableViewTemplate.xsl" />
	<xsl:include href="ControlTemplates/ImageTemplate.xsl" />
	<xsl:include href="ControlTemplates/WebViewTemplate.xsl" />
	<!-- End inclides -->
	
</xsl:stylesheet>