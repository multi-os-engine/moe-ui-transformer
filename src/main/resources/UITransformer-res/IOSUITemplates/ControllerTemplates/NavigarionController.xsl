<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    exclude-result-prefixes="osxui" version="2.0">

    <xsl:strip-space elements="navigationController" />
    <xsl:output method="xml" indent="yes" />
    <xsl:template match="com.android.sdklib.widgets.iOSNavigationController"
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:esi="http://www.edge-delivery.org/esi/1.0"
        xmlns:xrt="http://schemas.android.com/apk/res/android">

        <xsl:element name="navigationController">
            <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>
            <xsl:attribute name="sceneMemberID">viewController</xsl:attribute>

            <xsl:variable name="storyboardId" select="@xrt:storyboardIdentifier" />
            <xsl:choose>
                <xsl:when test="$storyboardId">
                    <xsl:attribute name="storyboardIdentifier" ><xsl:value-of select="$storyboardId"/></xsl:attribute>
                    <xsl:attribute name="useStoryboardIdentifierAsRestorationIdentifier">YES</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="restId" select="@xrt:restorationIdentifier" />
                    <xsl:choose>
                        <xsl:when test="$restId">
                            <xsl:attribute name="restorationIdentifier" ><xsl:value-of select="$restId"/></xsl:attribute>
                        </xsl:when>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>


            <xsl:attribute name="segue_type"><xsl:value-of select="@xrt:segue_type"/></xsl:attribute>
            <xsl:attribute name="segue_destination"><xsl:value-of select="@xrt:segue_destination"/></xsl:attribute>

            <xsl:variable name="viewController" select="@xrt:viewController" />
            <xsl:variable name="initialViewController" select="@xrt:initialViewController" />
            <xsl:if test="@xrt:initialViewController">
                <xsl:attribute name="initialViewController"><xsl:value-of select="$initialViewController"/></xsl:attribute>
            </xsl:if>

            <xsl:if test="@xrt:viewController">
                <xsl:attribute name="viewController"><xsl:value-of select="$viewController"/></xsl:attribute>
            </xsl:if>


            <xsl:element name="navigationBar">
                <xsl:attribute name="key">navigationBar</xsl:attribute>
                <xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
                <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>

                <xsl:element name="autoresizingMask">
                    <xsl:attribute name="key">autoresizingMask</xsl:attribute>
                </xsl:element>
            </xsl:element>



        </xsl:element>

    </xsl:template>
</xsl:stylesheet>