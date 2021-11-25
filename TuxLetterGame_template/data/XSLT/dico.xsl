<?xml version="1.0" encoding="UTF-8"?>


<xs:stylesheet xmlns:xs="http://www.w3.org/1999/XSL/Transform" 
               xmlns:tux="http://myGame/tux"
               version="1.0">
    <xs:output method="html"/>

    <!--
        Objectif : Créer une page HTML
        La page contient :
            - Tous les mots du dictionnaire (dico.xml)
            - La liste de mot devra etre trié par ordre Alphabétique
    -->
    <xs:template match="/">
        <html>
            <head>
                
            </head>
            <body>
                <title>Liste de mots :</title>
                <table>
                    <tr>
                        <th>Mot(s)</th>
                        <th>Niveau</th>
                    </tr>
                    <xs:apply-templates select="//tux:mot">
                        <xs:sort select="." order="ascending"/>
                    </xs:apply-templates>
                </table>
            </body>
        </html>
    </xs:template>
    
   <!-- Template appelé par apply-templates qui nous permet d'avoir chaque ligne du
    tableau afin d'avoir un modèle générique. -->

    <xs:template match="tux:mot">
        <tr>
            <td> <xs:value-of select="."/> </td>
            <td> <xs:value-of select="@niveau"/> </td>
        </tr>
    </xs:template>
</xs:stylesheet>
