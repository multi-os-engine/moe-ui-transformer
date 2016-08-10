<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
                exclude-result-prefixes="osxui">
    <xsl:strip-space elements="toolbar" />
    <xsl:output method="xml" indent="yes"/>

    <toolbar opaque="NO" clearsContextBeforeDrawing="NO" contentMode="scaleToFill" fixedFrame="YES" translatesAutoresizingMaskIntoConstraints="NO" id="pJv-Fh-ZHS">
        <rect key="frame" x="0.0" y="431" width="600" height="44"/>
        <items>
            <barButtonItem title="Item" id="OZi-iy-7Wp">
                <color key="tintColor" red="0.0" green="0.50196081400000003" blue="1" alpha="1" colorSpace="calibratedRGB"/>
            </barButtonItem>
        </items>
        <color key="barTintColor" red="0.96862745100000003" green="0.96862745100000003" blue="0.96862745100000003" alpha="1" colorSpace="calibratedRGB"/>
    </toolbar>

    <xsl:template match="com.android.sdklib.widgets.iOSToolBar"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:xrt="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0">
        <!-- android namespase: xmlns:android="http://schemas.android.com/apk/res/android"  -->
        <!-- exclude xmlns atribute: xmlns:esi="http://www.edge-delivery.org/esi/1.0" -->

        <xsl:element name="toolbar">

            <xsl:variable name="styleId" select="@style" />
            <xsl:variable name="toolbarStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($styleId)]/item" />
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

            <xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
            <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>

            <xsl:attribute name="opaque">NO</xsl:attribute>
            <xsl:attribute name="clearsContextBeforeDrawing">NO</xsl:attribute>
            <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
            <xsl:attribute name="fixedFrame">YES</xsl:attribute>
            <xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>

            <xsl:element name="items">
                <xsl:apply-templates select="com.android.sdklib.widgets.iOSBarButtonItem" />
            </xsl:element>

            <xsl:element name="color">
                <xsl:attribute name="key">barTintColor</xsl:attribute>
                <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                <xsl:attribute name="red">0.96862745100000003</xsl:attribute>
                <xsl:attribute name="green">0.96862745100000003</xsl:attribute>
                <xsl:attribute name="blue">0.96862745100000003</xsl:attribute>
                <xsl:attribute name="alpha">1</xsl:attribute>
            </xsl:element>

            <xsl:apply-templates select="com.android.sdklib.widgets.iOSToolBar" />
        </xsl:element>
    </xsl:template>

    <xsl:template match="com.android.sdklib.widgets.iOSBarButtonItem"
                  xmlns:android="http://schemas.android.com/apk/res/android"
                  xmlns:esi="http://www.edge-delivery.org/esi/1.0"
                  xmlns:xrt="http://schemas.android.com/apk/res/android">

            <xsl:element name="barButtonItem">

                <xsl:attribute name="id" >
                    <xsl:value-of select="osxui:CreateId()"/>
                </xsl:attribute>



                <xsl:variable name="item_width" select="@android:layout_width" />
                <xsl:variable name="item_text" select="@android:text" />

                <xsl:choose>
                    <xsl:when test="contains($item_width, 'dp')">
                        <xsl:attribute name="width" >
                            <xsl:value-of select="substring-before($item_width, 'dp')"/>
                        </xsl:attribute>
                    </xsl:when>
                </xsl:choose>



                <xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
                <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>

                <xsl:variable name="segueId" select="@xrt:segue_identifier" />
                <xsl:choose>
                    <xsl:when test="$segueId">
                        <xsl:attribute name="segue_identifier" ><xsl:value-of select="$segueId"/></xsl:attribute>
                    </xsl:when>
                </xsl:choose>

                <xsl:variable name="styleBackground" select="@android:background"/>
                <xsl:variable name="imgBackground" select="osxui:GetDrawableName($styleBackground)"/>
                <xsl:choose>
                    <xsl:when test="$styleBackground">
                        <xsl:choose>

                            <xsl:when test="$imgBackground">
                                <xsl:attribute name="android_image_background" ><xsl:value-of select="$imgBackground"/></xsl:attribute>
                                <xsl:attribute name="image" ><xsl:value-of select="osxui:getImage($imgBackground)"/></xsl:attribute>

                                <xsl:element name="color">
                                    <xsl:attribute name="key">tintColor</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                    <xsl:attribute name="red">0.0</xsl:attribute>
                                    <xsl:attribute name="green">0.50196081400000003</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                </xsl:element>

                            </xsl:when>

                            <xsl:otherwise>
                                <xsl:attribute name="title">Item</xsl:attribute>

                                <xsl:element name="color">
                                    <xsl:attribute name="key">tintColor</xsl:attribute>
                                    <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                                    <xsl:attribute name="red">0.0</xsl:attribute>
                                    <xsl:attribute name="green">0.50196081400000003</xsl:attribute>
                                    <xsl:attribute name="blue">1</xsl:attribute>
                                    <xsl:attribute name="alpha">1</xsl:attribute>
                                </xsl:element>

                            </xsl:otherwise>

                        </xsl:choose>

                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="title">

                            <xsl:choose>
                                <xsl:when test="$item_text">
                                    <xsl:value-of select="$item_text"/>
                                </xsl:when>

                                <xsl:otherwise>
                                    <xsl:value-of select="Item"/>
                                </xsl:otherwise>
                            </xsl:choose>

                        </xsl:attribute>
                        <xsl:element name="color">
                            <xsl:attribute name="key">tintColor</xsl:attribute>
                            <xsl:attribute name="colorSpace">calibratedRGB</xsl:attribute>
                            <xsl:attribute name="red">0.0</xsl:attribute>
                            <xsl:attribute name="green">0.50196081400000003</xsl:attribute>
                            <xsl:attribute name="blue">1</xsl:attribute>
                            <xsl:attribute name="alpha">1</xsl:attribute>
                        </xsl:element>
                    </xsl:otherwise>
                </xsl:choose>

            </xsl:element>

    </xsl:template>


</xsl:stylesheet>