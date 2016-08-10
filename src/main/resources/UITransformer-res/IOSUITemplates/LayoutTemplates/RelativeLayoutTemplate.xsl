<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    exclude-result-prefixes="osxui" version="2.0">

    <xsl:strip-space elements="view" />
    <xsl:output method="xml" indent="yes" />
    <xsl:template match="RelativeLayout"
        xmlns:android="http://schemas.android.com/apk/res/android" xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <xsl:element name="view">
            <xsl:variable name="layoutOrientation" select="@android:orientation" />
            <xsl:attribute name="key">view</xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()" /></xsl:attribute>
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="android_orientation">relative</xsl:attribute>

            <xsl:variable name="androidWidth" select="@android:layout_width" />
            <xsl:attribute name="android_width" ><xsl:value-of select="$androidWidth"/></xsl:attribute>
            <xsl:variable name="androidHeight" select="@android:layout_height" />
            <xsl:attribute name="android_height" ><xsl:value-of select="$androidHeight"/></xsl:attribute>

            <!--
            <xsl:element name="rect">
                <xsl:attribute name="key">frame</xsl:attribute>
                <xsl:attribute name="x">0.0</xsl:attribute>
                <xsl:attribute name="y">0.0</xsl:attribute>
                <xsl:attribute name="width">480</xsl:attribute>
                <xsl:attribute name="height">480</xsl:attribute>
            </xsl:element>
            -->
            <xsl:element name="autoresizingMask">
                <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                <xsl:attribute name="widthSizable">YES</xsl:attribute>
                <xsl:attribute name="heightSizable">YES</xsl:attribute>
            </xsl:element>
            <xsl:element name="color">
                <xsl:attribute name="key">backgroundColor</xsl:attribute>
                <xsl:attribute name="white">1</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
                <xsl:attribute name="colorSpace">custom</xsl:attribute>
                <xsl:attribute name="customColorSpace">calibratedWhite</xsl:attribute>
            </xsl:element>
            <xsl:element name="subviews">
                <xsl:apply-templates />
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>