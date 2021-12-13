<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:tux="http://myGame/tux"
                version="1.0">
    <xsl:output method="html"/>

    <!-- 
        Objectif : Créer une page HTML du profil du joueur
        La page contient :
            - Nom du joueur
            - son anniversaire
            - son avatar
            - une liste des parties déjà jouer : 
                * Date de la partie
                * Le mot
                * Le temps
                * Le niveau
                * A quel point le mot a été trouvé
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Profile du joueur</title>
                <link rel="stylesheet" href="profil.css"/>
            </head>
            
            <!-- Création du corps html -->
            <body>
                 <!-- Informations générales sur le joueur -->
                <xsl:element name="img">
                    <xsl:attribute name="src"><xsl:value-of select="//tux:avatar"/></xsl:attribute>
                    <xsl:attribute name="alt"><xsl:value-of select="//tux:avatar"/></xsl:attribute>
                </xsl:element>
                <h3>Profile de <xsl:value-of select="//tux:nom"/></h3>
                <p><xsl:value-of select="//tux:anniversaire"/></p>
                <!-- Tableau des dernieres parties -->
                <table>
                    <tr>
                        <th>Date</th>
                        <th>Mot</th>
                        <th>Temps</th>
                        <th>Niveau</th>
                        <th>Trouvé à</th>
                    </tr>
                     <!-- Appel de sort pour trier la liste en fonction du niveau 
                            et de l'alphabet                    -->

                    <xsl:apply-templates select="//tux:partie">
                        <xsl:sort select="./tux:mot/@niveau" order="descending"/>
                        <xsl:sort select="./tux:mot" order="ascending"/>
                    </xsl:apply-templates>
                </table>
            </body>
        </html>
    </xsl:template>
    
    <!-- Template appelé par apply-templates, ce qui correspond à une ligne du tableau
        appelé N fois afin d'avoir un modèle générique.    -->
    <xsl:template match="tux:partie">
        <tr>
            <td> <xsl:value-of select="@date"/> </td>
            <td> <xsl:value-of select="./tux:mot"/> </td>
            <td> <xsl:value-of select="./tux:temps"/> </td>
            <td> <xsl:value-of select="./tux:mot/@niveau"/> </td>
            <td> <xsl:value-of select="@trouvé"/> </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
