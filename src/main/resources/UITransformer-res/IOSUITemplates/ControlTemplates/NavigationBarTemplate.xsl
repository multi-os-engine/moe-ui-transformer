<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
                exclude-result-prefixes="osxui">
    <xsl:strip-space elements="navigationBar" />
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="com.android.sdklib.widgets.iOSNavigationBar"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:xrt="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
        <!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->

        <xsl:element name="navigationBar">

            <xsl:variable name="styleId" select="@style" />
            <xsl:variable name="navigationBarStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
            <xsl:attribute name="id" >
                <xsl:value-of select="osxui:CreateId()"/>
            </xsl:attribute>

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
            <!--<xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
            <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>-->

            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="fixedFrame">YES</xsl:attribute>
            <xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>

            <xsl:variable name="androidGravity" select="@android:layout_gravity" />
            <xsl:attribute name="android_gravity" ><xsl:value-of select="$androidGravity"/></xsl:attribute>


            <color key="barTintColor" red="0.96862745100000003" green="0.96862745100000003" blue="0.96862745100000003" alpha="1" colorSpace="calibratedRGB"/>
            <textAttributes key="titleTextAttributes">
                <fontDescription key="fontDescription" name="HelveticaNeue" family="Helvetica Neue" pointSize="18"/>
                <color key="textColor" white="0.0" alpha="1" colorSpace="calibratedWhite"/>
            </textAttributes>

            <xsl:element name="color">
                <xsl:attribute name="key">barTintColor</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:attribute name="red">0.96862745100000003</xsl:attribute>
                <xsl:attribute name="green">0.96862745100000003</xsl:attribute>
                <xsl:attribute name="blue">0.96862745100000003</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
            </xsl:element>

            <xsl:element name="textAttributes">
                <xsl:attribute name="key">titleTextAttributes</xsl:attribute>

                <xsl:element name="fontDescription">
                    <xsl:attribute name="key">fontDescription</xsl:attribute>
                    <xsl:attribute name="type">system</xsl:attribute>
                    <xsl:attribute name="pointSize">18</xsl:attribute>
                </xsl:element>

                <xsl:element name="color">
                    <xsl:attribute name="key">textColor</xsl:attribute>
                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                    <xsl:attribute name="red">0.0</xsl:attribute>
                    <xsl:attribute name="green">0.0</xsl:attribute>
                    <xsl:attribute name="blue">0.0</xsl:attribute>
                    <xsl:attribute name="alpha">1</xsl:attribute>
                </xsl:element>

            </xsl:element>

            <xsl:element name="items">
                <xsl:element name="navigationItem">
                    <xsl:attribute name="title">Title</xsl:attribute>
                    <xsl:attribute name="id" >
                        <xsl:value-of select="osxui:CreateId()"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:element>

            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>