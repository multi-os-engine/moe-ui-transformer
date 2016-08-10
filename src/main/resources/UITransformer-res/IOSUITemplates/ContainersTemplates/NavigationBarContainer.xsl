<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
    exclude-result-prefixes="osxui" version="2.0">

    <xsl:strip-space elements="viewController" />
    <xsl:output method="xml" indent="yes" />
    <xsl:template match="com.android.sdklib.widgets.base_elements.iOSNavigationBarContainer"
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:esi="http://www.edge-delivery.org/esi/1.0"
        xmlns:xrt="http://schemas.android.com/apk/res/android">

        <xsl:element name="navigationItem">
            <xsl:attribute name="key">navigationItem</xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="osxui:CreateId()"/></xsl:attribute>

            <xsl:variable name="androidWidth" select="@android:layout_width" />
            <xsl:attribute name="android_width" ><xsl:value-of select="$androidWidth"/></xsl:attribute>
            <xsl:variable name="androidHeight" select="@android:layout_height" />
            <xsl:attribute name="android_height" ><xsl:value-of select="$androidHeight"/></xsl:attribute>

        </xsl:element>
        <xsl:apply-templates />
    </xsl:template>
</xsl:stylesheet>