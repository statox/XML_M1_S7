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
          <xsl:for-each select="breakfast_menu/food">
            <tr>
              <tr bgcolor="#013ADF">
                <FONT COLOR="FFFFFF">
                  <b><xsl:value-of select="name" /></b>
                  - $
                  <xsl:value-of select="price" />
                </FONT>
              </tr>
              <tr bgcolor="#A9D0F5">
                <xsl:value-of select="description" />
                <xsl:value-of select="calories" /> 
                <i>(calories per serving)</i>
              </tr>  
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>         
  </xsl:template>

</xsl:stylesheet>
