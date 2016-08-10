<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
                exclude-result-prefixes="osxui">
    <xsl:strip-space elements="progressView" />
    <xsl:output method="xml" indent="yes"/>

    <progressView opaque="NO" contentMode="scaleToFill" verticalHuggingPriority="750" fixedFrame="YES" progress="0.5" translatesAutoresizingMaskIntoConstraints="NO" id="gaY-mV-9gm">
        <rect key="frame" x="225" y="137" width="150" height="2"/>
        <color key="progressTintColor" red="0.0" green="0.50196081400000003" blue="1" alpha="1" colorSpace="calibratedRGB"/>
        <color key="trackTintColor" red="0.80000001190000003" green="0.80000001190000003" blue="0.80000001190000003" alpha="1" colorSpace="calibratedRGB"/>
    </progressView>

    <xsl:template match="com.android.sdklib.widgets.iOSProgressView"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:xrt="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
        <!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->

        <xsl:element name="progressView">

            <xsl:variable name="styleId" select="@style" />
            <xsl:variable name="progressViewStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
            <xsl:attribute name="id" >
                <xsl:value-of select="osxui:CreateId()"/>
            </xsl:attribute>

            <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
            <xsl:choose>
                <xsl:when test="$restId">
                    <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

            <xsl:variable name="androidWidth" select="@android:layout_width" />
            <xsl:attribute name="android_width" ><xsl:value-of select="$androidWidth"/></xsl:attribute>
            <xsl:variable name="androidHeight" select="@android:layout_height" />
            <xsl:attribute name="android_height" ><xsl:value-of select="$androidHeight"/></xsl:attribute>

            <xsl:variable name="androidMarginTop" select="@android:layout_marginTop"/>
            <xsl:attribute name="android_marginTop"><xsl:value-of select="$androidMarginTop"/></xsl:attribute>
            <xsl:variable name="androidMarginBottom" select="@android:layout_marginBottom"/>
            <xsl:attribute name="android_marginBottom"><xsl:value-of select="$androidMarginBottom"/></xsl:attribute>
            <xsl:variable name="androidMarginLeft" select="@android:layout_marginLeft"/>
            <xsl:attribute name="android_marginLeft"><xsl:value-of select="$androidMarginLeft"/></xsl:attribute>
            <xsl:variable name="androidMarginRight" select="@android:layout_marginRight"/>
            <xsl:attribute name="android_marginRight"><xsl:value-of select="$androidMarginRight"/></xsl:attribute>

            <xsl:attribute name="iboutlet"><xsl:value-of select="@xrt:iboutlet"/></xsl:attribute>
            <xsl:attribute name="ibaction"><xsl:value-of select="@xrt:ibaction"/></xsl:attribute>

            <xsl:variable name="androidGravity" select="@android:layout_gravity" />
            <xsl:attribute name="android_gravity" ><xsl:value-of select="$androidGravity"/></xsl:attribute>

            <xsl:attribute name="opaque">NO</xsl:attribute>
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="verticalHuggingPriority">750</xsl:attribute>
            <xsl:attribute name="fixedFrame">YES</xsl:attribute>

            <xsl:attribute name="progress">0.5</xsl:attribute>

            <xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>


            <xsl:element name="color">
                <xsl:attribute name="key">progressTintColor</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:attribute name="red">0.0</xsl:attribute>
                <xsl:attribute name="green">0.50196081400000003</xsl:attribute>
                <xsl:attribute name="blue">1</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
            </xsl:element>

            <xsl:element name="color">
                <xsl:attribute name="key">trackTintColor</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:attribute name="red">0.80000001190000003</xsl:attribute>
                <xsl:attribute name="green">0.80000001190000003</xsl:attribute>
                <xsl:attribute name="blue">0.80000001190000003</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
            </xsl:element>

            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>