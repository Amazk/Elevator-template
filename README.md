# Simulateur d'ascenseur
Auteur : Garcia Andy
# Description du projet 

Projet du simulateur d'ascenseur.

[Documentation](https://jean-luc-massat.pedaweb.univ-amu.fr/ens/gl/elevator.html)

Le but du projet est d'implementé un système de controle-commande d'un ascenseur a N etage et de lui crée une interface graphique associée a l'aide des outils fournit par la bibliotheque Swing.

Dans ce projet on a implementés 3 packages :

    - elevator : contient les classes/interfaces qui controles l'ascenseur
            - Direction : Un Enum qui donne la direction de l'ascenseur
            - Scheduler : Elle recupere/enregistre les demandes et calcul le prochain etage de l'ascenseur
            - IControlCommand/ControlCommand : Cette classe et son interface associé vont gere le deplacement de l'ascenseur
            - MainElevator : La classe principal ou l'on execute le controlCommand et l'interface graphique

    - Event : contient les classes/interfaces liée au ecoutes d'evenements
            - IPanelSimulator/PanelSimulator : cette classe va faire le lien entre le CC et l'IHM
            - MessageEvent/MessageListener : Ils vont gere l'ecoute des messages du CC pour les transmettre a l'IHM
            - PanelControlCommand : cette classe fait le lien de l'ecoute du panelSimulator avec le CC

    - IHM : contient les classes de l'interfaces graphiques
            - ButtonsPanel : La classe des boutons de l'ascenseur
            - MessagePanel : La classe qui va afficher les messages du CC sur l'IHM
            - MainPanel : C'est notre panneaux principal qui va contenir le ButtonsPanel et MessagePanel 
            - MyFrame : L'IHM qui va contenir le MainPanel 

Difficulte du projet par ordre decroissant : 

        - Comprendre un code deja ecrit par une autre personne et se servir de ce code comme base pour implementer par "dessus" de nouvelle fonctionnalite
        - Penser a tout les cas particuliers de l'ascenseur
        - Comment chosir les priorites de l'ascenseur (priorite en file d'attente/priorite cabine..)
        - Interface graphique et particulierement quand les imbrications de panel et les ecoutes CC <-> IHM


