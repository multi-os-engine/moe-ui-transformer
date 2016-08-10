<?xml version="1.0" encoding="utf-8" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="2.0" xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    xmlns:xrt="http://schemas.android.com/apk/res/android"
    exclude-result-prefixes="osxui">
    <xsl:output method="xml" indent="yes" standalone="no" />
    <xsl:strip-space elements="*" />
    <!-- Start inclides -->
    <xsl:include href="global.xsl" />
    <!-- <xsl:include href="global.xsl" /> -->
    <!-- End inclides -->



    <xsl:template match="/">
        <xsl:element name="document">


            <xsl:attribute name="type">com.apple.InterfaceBuilder3.CocoaTouch.Storyboard.XIB</xsl:attribute>
            <xsl:attribute name="targetRuntime">iOS.CocoaTouch</xsl:attribute>
            <xsl:attribute name="propertyAccessControl">none</xsl:attribute>
            <xsl:attribute name="useAutolayout">YES</xsl:attribute>
            <xsl:attribute name="useTraitCollections">YES</xsl:attribute>
            <xsl:attribute name="version">3.0</xsl:attribute>
            <xsl:attribute name="toolsVersion">6250</xsl:attribute>
            <xsl:attribute name="systemVersion">14A389</xsl:attribute>
            <!--<xsl:attribute name="initialViewController"><xsl:value-of select="$viewControllerID" /></xsl:attribute>-->
            <xsl:element name="dependencies">
                <xsl:element name="plugIn">
                    <xsl:attribute name="identifier">com.apple.InterfaceBuilder.IBCocoaTouchPlugin</xsl:attribute>
                    <xsl:attribute name="version">6247</xsl:attribute>
                </xsl:element>
            </xsl:element>


            <xsl:element name="scenes">
                <xsl:for-each select="list/item">
                    <xsl:element name="scene">

                        <xsl:variable name="viewAbsolutePath" select="@absolute_path" />
                        <xsl:variable name="viewName" select="@name" />

                        <xsl:attribute name="sceneID"><xsl:value-of
                            select="osxui:CreateId()" /></xsl:attribute>
                        <xsl:attribute name="viewName"><xsl:value-of
                            select="$viewName" /></xsl:attribute>

                        <xsl:element name="objects">
                            <xsl:apply-templates select="document($viewAbsolutePath)/*"/>
                            <xsl:strip-space elements="*" />

                            <xsl:element name="placeholder">
                                <xsl:attribute name="placeholderIdentifier">IBFirstResponder</xsl:attribute>
                                <xsl:attribute name="id">
                                    <xsl:value-of select="osxui:CreateId()" />
                                </xsl:attribute>
                                <xsl:attribute name="sceneMemberID">firstResponder</xsl:attribute>
                            </xsl:element>
                        </xsl:element>

                        <xsl:element name="point">
                            <xsl:attribute name="key">canvasLocation</xsl:attribute>
                            <xsl:attribute name="x">181.33333333333334</xsl:attribute>
                            <xsl:attribute name="y">395.70666666666665</xsl:attribute>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
                <xsl:apply-templates />
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>