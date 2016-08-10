<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
	xmlns:osxui="xalan://org.moe.transformer.UIOSxProcessor.OSXStringProcessor"
	exclude-result-prefixes="osxui">
	<xsl:strip-space elements="*" />
	<xsl:output method="xml" indent="yes" standalone="no"/>
	
	<!-- Start inclides -->
	<xsl:include href="global.xsl" />
	<!-- End inclides -->
	<xsl:variable name="viewControllerID">
		<xsl:value-of select="osxui:CreateId()" />
	</xsl:variable>
	
	<xsl:template match="/">
		<xsl:element name="document">
			<xsl:attribute name="type">com.apple.InterfaceBuilder3.CocoaTouch.XIB</xsl:attribute>
			<xsl:attribute name="version">3.0</xsl:attribute>
			<xsl:attribute name="toolsVersion">6211</xsl:attribute>
			<xsl:attribute name="systemVersion">14A298i</xsl:attribute>
			<xsl:attribute name="targetRuntime">iOS.CocoaTouch</xsl:attribute>
			<xsl:attribute name="propertyAccessControl">none</xsl:attribute>
			<xsl:attribute name="useAutolayout">YES</xsl:attribute>
			<xsl:attribute name="useTraitCollections">YES</xsl:attribute>
			
			<xsl:element name="dependencies">
				<xsl:element name="plugIn">
					<xsl:attribute name="identifier">com.apple.InterfaceBuilder.IBCocoaTouchPlugin</xsl:attribute>
					<xsl:attribute name="version">6207</xsl:attribute>
				</xsl:element>
				<xsl:element name="capability">
					<xsl:attribute name="name">Constraints with non-1.0 multipliers</xsl:attribute>
					<xsl:attribute name="minToolsVersion">5.1</xsl:attribute>
				</xsl:element>
			</xsl:element>
			<xsl:element name="objects">
				<xsl:element name="placeholder">
					<xsl:attribute name="placeholderIdentifier">IBFilesOwner</xsl:attribute>
					<xsl:attribute name="id">-1</xsl:attribute>
					<xsl:attribute name="userLable">File's Owner</xsl:attribute>
				</xsl:element>
				<xsl:element name="placeholder">
					<xsl:attribute name="placeholderIdentifier">IBFirstResponder</xsl:attribute>
					<xsl:attribute name="id">-2</xsl:attribute>
					<xsl:attribute name="customClass">UIResponder</xsl:attribute>
				</xsl:element>
				<xsl:apply-templates/>
			</xsl:element>
		</xsl:element>
    </xsl:template>
</xsl:stylesheet>