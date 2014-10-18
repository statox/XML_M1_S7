<?xml version="1.0" encoding="UTF-8" ?>

<!-- TP2 ex3 first solution - no for-each loop -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <!-- the output document is a HTML4 document -->
  <xsl:output 
        method="html"
        encoding="UTF-8"
        doctype-public="-//W3C//DTD HTML 4.01//EN"
        doctype-system="http://www.w3.org/TR/html4/strict.dtd"
        indent="yes" />


  <xsl:template match="/">
    <html>
      <head>
        <title>Delicious breakfast menu</title>
      </head>
      <body>
        <table style="width:100%">
          <xsl:apply-templates/>
        </table>
      </body>
    </html>         
  </xsl:template>


  <xsl:template match="breakfast_menu">
      <xsl:apply-templates select="food" /> 
  </xsl:template>

  <xsl:template match="food">
    <tr>
      <tr bgcolor="#013ADF">
        <FONT COLOR="FFFFFF">
          <b><xsl:apply-templates select="name" /></b>
          - $
          <xsl:apply-templates select="price" />
        </FONT>
      </tr>
      <tr bgcolor="#A9D0F5">
        <xsl:apply-templates select="description" />
        <xsl:apply-templates select="calories" /> 
        <i>(calories per serving)</i>
      </tr>  
    </tr>
  </xsl:template>

  <xsl:template match="name">
    <xsl:value-of select="." /> 
  </xsl:template>

  <xsl:template match="price">
    <xsl:value-of select="." /> 
  </xsl:template>
  
  <xsl:template match="description">
    <xsl:value-of select="." /> 
  </xsl:template>

  <xsl:template match="calories">
    <xsl:value-of select="." /> 
  </xsl:template>
  

</xsl:stylesheet>
