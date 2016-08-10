<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    exclude-result-prefixes="osxui">
    <xsl:strip-space elements="button" />
    <xsl:strip-space elements="*" />
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="com.android.sdklib.widgets.iOSWebView"
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:xrt="http://schemas.android.com/apk/res/android"
        xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
        <!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->

        <!--
            <webView   id="Tfm-QS-oUe">
                                <rect key="frame" x="192" y="106" width="240" height="128"/>
                                <color key="backgroundColor" white="1" alpha="1" colorSpace="calibratedWhite"/>
                            </webView>

        -->
        <xsl:element name="webView">

            <xsl:variable name="btnStyleId" select="@style" />
            <xsl:variable name="btnStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($btnStyleId)]/item" />
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

            <!-- Styles -->
            <xsl:variable name="styleBackground" select="@android:background"/>
            <xsl:variable name="styleTextSize" select="@android:textSize"/>
            <xsl:variable name="styleText" select="@android:text"/>
            <xsl:variable name="styleTextColor" select="@android:textColor"/>
            <xsl:variable name="styleTextStyle" select="@android:textStyle"/>
            <!-- Styles end -->

            <xsl:attribute name="opaque">NO</xsl:attribute>
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="fixedFrame">YES</xsl:attribute>
            <xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>


            <xsl:choose>
                <xsl:when test="$styleBackground">
                    <xsl:element name="color">
                        <xsl:attribute name="key">backgroundColor</xsl:attribute>
                        <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                        <xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($styleBackground)"/></xsl:attribute>
                        <xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($styleBackground)"/></xsl:attribute>
                        <xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($styleBackground)"/></xsl:attribute>
                        <xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($styleBackground)"/></xsl:attribute>
                    </xsl:element>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="color">
                        <xsl:attribute name="key">backgroundColor</xsl:attribute>
                        <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                        <xsl:attribute name="red"><xsl:value-of select="osxui:ColorToR($btnStyles[@name='android:background'])"/></xsl:attribute>
                        <xsl:attribute name="green"><xsl:value-of select="osxui:ColorToG($btnStyles[@name='android:background'])"/></xsl:attribute>
                        <xsl:attribute name="blue"><xsl:value-of select="osxui:ColorToB($btnStyles[@name='android:background'])"/></xsl:attribute>
                        <xsl:attribute name="alpha"><xsl:value-of select="osxui:ColorToAlpha($btnStyles[@name='android:background'])"/></xsl:attribute>
                    </xsl:element>
                </xsl:otherwise>
            </xsl:choose>

            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>