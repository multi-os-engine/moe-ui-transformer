<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
                exclude-result-prefixes="osxui">
    <xsl:strip-space elements="segmentedControl" />
    <xsl:output method="xml" indent="yes"/>

    <segmentedControl opaque="NO" contentMode="scaleToFill" fixedFrame="YES" contentHorizontalAlignment="left" contentVerticalAlignment="top" segmentControlStyle="plain" selectedSegmentIndex="0" translatesAutoresizingMaskIntoConstraints="NO" id="gQ3-di-ljh">
        <rect key="frame" x="182" y="190" width="249" height="29"/>
        <color key="backgroundColor" red="0.90196079015731812" green="0.90196079015731812" blue="0.90196079015731812" alpha="1" colorSpace="calibratedRGB"/>
        <segments>
            <segment title="First"/>
            <segment title="Second"/>
        </segments>
        <color key="tintColor" red="0.0" green="0.50196081400000003" blue="1" alpha="1" colorSpace="calibratedRGB"/>
    </segmentedControl>

    <xsl:template match="com.android.sdklib.widgets.iOSSegmentedControl"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:xrt="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
        <!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->

        <xsl:element name="segmentedControl">

            <xsl:variable name="styleId" select="@style" />
            <xsl:variable name="segmentedControlStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
            <xsl:variable name="segmentedControlDrawable" select="document($g_drawablePath + xos_segmented_control_button.xml)//stroke" />
            <xsl:attribute name="id" >
                <xsl:value-of select="osxui:CreateId()"/>
            </xsl:attribute>

            <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
            <xsl:choose>
                <xsl:when test="$restId">
                    <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                </xsl:when>
            </xsl:choose>

            <xsl:variable name="segueId" select="@xrt:segue_identifier" />
            <xsl:choose>
                <xsl:when test="$segueId">
                    <xsl:attribute name="segue_identifier" ><xsl:value-of select="$segueId"/></xsl:attribute>
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
            <xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
            <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>

            <xsl:variable name="androidGravity" select="@android:layout_gravity" />
            <xsl:attribute name="android_gravity" ><xsl:value-of select="$androidGravity"/></xsl:attribute>

            <xsl:attribute name="opaque">NO</xsl:attribute>
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="fixedFrame">YES</xsl:attribute>
            <xsl:attribute name="contentHorizontalAlignment">left</xsl:attribute>
            <xsl:attribute name="contentVerticalAlignment">top</xsl:attribute>
            <xsl:attribute name="segmentControlStyle">plain</xsl:attribute>

            <xsl:attribute name="selectedSegmentIndex">0</xsl:attribute>

            <xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>


            <xsl:element name="color">
                <xsl:attribute name="key">backgroundColor</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:attribute name="red">1</xsl:attribute>
                <xsl:attribute name="green">1</xsl:attribute>
                <xsl:attribute name="blue">1</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
            </xsl:element>

            <xsl:element name="segments">
                <xsl:apply-templates select="com.android.sdklib.widgets.iOSRadioButtonSC" />
            </xsl:element>

            <xsl:element name="color">
                <xsl:attribute name="key">tintColor</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:attribute name="red">0.0</xsl:attribute>
                <xsl:attribute name="green">0.50196081400000003</xsl:attribute>
                <xsl:attribute name="blue">1</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
            </xsl:element>

            <xsl:apply-templates select="com.android.sdklib.widgets.iOSSegmentedControl" />
        </xsl:element>
    </xsl:template>


    <xsl:template match="com.android.sdklib.widgets.iOSRadioButtonSC"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0">

        <xsl:element name="segment">
            <xsl:attribute name="title"><xsl:value-of select="@android:text"/></xsl:attribute>
        </xsl:element>

    </xsl:template>

</xsl:stylesheet>