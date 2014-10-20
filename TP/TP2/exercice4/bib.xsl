<?xml version="1.0" encoding="UTF-8" ?>

<!-- TP2 ex4 creation of output.hmtl file -->

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
        <title>Your library</title>
        <!--
        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
        </meta>
        -->
      </head>

      <body>
        
        <h1 align="left">Domaines </h1>

        <!-- creation of the chapter list at top of the file -->
        <xsl:for-each select="bib/domain">
          <!-- get the title to reuse it several times -->
          <xsl:variable name="domain_title" select="title"/>
          <!-- list of the links -->
          <h2>
            <a href="#{$domain_title}">
              <xsl:value-of select="$domain_title"/>
            </a>
          </h2>
        </xsl:for-each>

        <xsl:apply-templates  />
      </body>
    </html>         
  </xsl:template>

  <xsl:template match="domain">
   <xsl:variable
            name="title"
            select="title"/>
 
    <!-- Big banners of each section --> 
    <hr/>
    <table border="1" width="100%">
      <tbody>
        <tr>
          <td bgcolor="#c0c0c0" width="100%">
            <h2>
              <a name="{$title}">
                <xsl:value-of select="$title"/>
              </a>
            </h2>
          </td>
        </tr>
      </tbody>
    </table>
    <hr/>

    <xsl:apply-templates select="bib_ref" />
  </xsl:template>

  <xsl:template match="bib_ref">
    <xsl:variable name="link" select="weblink" />
    
    <h3>
      Titre : <xsl:value-of select="title" />
    </h3>
    Auteur(s) : <xsl:value-of select="author" /> <br/>
    Année : <xsl:value-of select="year" /> <br/>
    Lien : <a href="{$link}"> <xsl:value-of select="weblink" /> </a> <br/>
    Commentaires: <br/> 
    <br/>
    <hr/>
  </xsl:template>

</xsl:stylesheet>
