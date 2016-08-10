<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
				xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
				exclude-result-prefixes="osxui">
	<xsl:strip-space elements="picker" />
	<xsl:output method="xml" indent="yes"/>
	
	<xsl:template match="com.android.sdklib.widgets.iOSTextPicker"
		xmlns:android="http://schemas.android.com/apk/res/android"
		xmlns:xrt="http://schemas.android.com/apk/res/android"
		xmlns:esi="http://www.edge-delivery.org/esi/1.0">
		
		<xsl:element name="pickerView">
			
			<xsl:variable name="pickerStyleId" select="@style" />
			<xsl:variable name="pickerStyles" select="document($g_stylePath)//style[@name = osxui:StyleIdConverter($pickerStyleId)]/item" />
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

			<xsl:attribute name="contentMode">scaleToFill</xsl:attribute>
			<xsl:attribute name="fixedFrame">YES</xsl:attribute>
			<xsl:attribute name="contentHorizontalAlignment">center</xsl:attribute>
			<xsl:attribute name="contentVerticalAlignment">center</xsl:attribute>
			<xsl:attribute name="translatesAutoresizingMaskIntoConstraints">NO</xsl:attribute>

    	</xsl:element>
	</xsl:template>
</xsl:stylesheet>