<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Providers List</title>
            </head>
            <body>
                <h1>Providers List</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Clients</th>
                    </tr>
                    <xsl:apply-templates/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="ArrayList/item">
        <tr>
            <td>
                <xsl:value-of select="id"/>
            </td>
            <td>
                <xsl:value-of select="name"/>
            </td>
            <td>
                <ul>
                    <xsl:for-each select="clients/clients">
                        <li>
                            <table border="1">
                                <tr>
                                    <td>ID:</td>
                                    <td><xsl:value-of select="id"/></td>
                                </tr>
                                <tr>
                                    <td>Name:</td>
                                    <td><xsl:value-of select="name"/></td>
                                </tr>
                                <tr>
                                    <td>Contact:</td>
                                    <td><xsl:value-of select="contact"/></td>
                                </tr>
                                <tr>
                                    <td>Address:</td>
                                    <td><xsl:value-of select="address"/></td>
                                </tr>
                                <tr>
                                    <td>Tariff:</td>
                                    <td><xsl:value-of select="tariff"/></td>
                                </tr>
                            </table>
                        </li>
                    </xsl:for-each>
                </ul>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
