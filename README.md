# ORT MS2D - Tower Defense

## Principe du tower defense
Vous trouverez une description des tower defense [ici](https://fr.wikipedia.org/wiki/Tower_defense)

## Réalisation

La réputation de votre entreprise concernant le haut niveau de qualité de vos livrables  n'est plus à faire et c'est pour cette raison que le client vous a choisi !

Il est demandé à votre équipe de récupérer le code du programme et d'y ajouter de nouvelles fonctionnalités.

Je jouerai le role du client, ayant quelques notions en développement informatique, je souhaite avoir accès au code source de l'application durant le déroulement du projet afin de suivre l'avancement de celui-ci.

Vous allez devoir vous approprier le code existant, l'adapter et y ajouter les fonctionnalités suivantes : 
 
 * Je souhaite ajouter une nouvelle tour peu chère le `Missile` (cout = 60) qui va tirer à une distance courte (80), l'interface graphique devrait être revue, elle aussi, pour intégrer cette nouvelle tour.
 
 * Je souhaite ajouter plusieurs niveaux de difficulté dans le jeu : **EASY** / **NORMAL** / **HARD**
   * mode **EASY** : 400 pièces au départ / 20 vies au départ / le jeu se termine lorsque 250 monstres ont été éliminés
   * mode **NORMAL** : tel que le jeu se comporte actuellement : 200 pièces au départ / 10 vies au départ / le jeu se termine lorsque 500 monstres ont été éliminés.
   * mode **HARD** : 100 pièces au départ / 5 vies au départ / le jeu se termine lorsque 1000 monstres ont été éliminés

 * Je souhaite ajouter un boss de fin de niveau : un alien avec bouclier protecteur. Les tours acuelles auront besoin de le toucher un total de 10 fois avant d'être éliminé. (Jour 2)

 * Correction de bug : je souhaiterai que les effets des tours `Missile`, `BlackHole` et `Sun` ne puissent toucher qu'un seul enemi, plutôt que d'élimier tous les enemis touchés. (Jour 2)

 * Je souhaite ajouter un second niveau dans le jeu, mais je ne suis pas encore sûr de tout ce que je veux à l'intérieur, nous pouvons en discuter ensemble.
   * lorsque le 1er niveau est terminé et le boss éliminé, les compteurs sont remis à zéro et le niveau 2 commence,
   * le chemin suivi par les enemis doit être différent.

 * A tout moment, le client peut modifier son besoin ou ajouter de nouvelles fonctionnalités.


## Compétences attendues (et évaluées)

 * Capacité à améliorer la qualité du code
   * Capacité à améliorer la Lisibilité du code
   * Capacité à écrire des tests unitaires pertinents
   * Capacité à améliorer le design, l'architecture du code
 * Produire un historique de commits compréhensible
 * Mettre en place un outil de gestion des dépendances
 * Mettre en place un outil de gestion de build
 * Mise en place d'une pipeline d'intégration continue
   * Génération automatique d'un livrable
   * Génération automatique d'un rapport de tests unitaires
 * Choix des pratiques XP pour la réalisation du projet
 * Mise en oeuvre de ces pratiques XP


## Livrables attendus

 - un jar executable et fonctionnel permettant de lancer l'application sur n'importe quel ordinateur.
 - un compte rendu d'execution des tests unitaires.
 - une pipeline d'intégration continue permettant de visualiser les livraisons successives de l'application.
 - une documentation technique succinte présentant la nouvelle architecture ainsi que les principaux choix techniques.


## Soutenance de projet : 

Le projet vous demandera 3 journées de travail (mardi / mercredi / jeudi).

Durant la dernière demi-journée, il vous sera demandé de présenter votre travail :
 * 1h maximum, en groupe,
 * Comment le travail a été découpé dans l'équipe,
 * Avez-vous écrit des tests unitaires ?
   * Si oui, quelle stratégie de tests avez vous choisi ? (Test after, test first, TDD)
 * Quels ont été vos principaux standards de codage ?
 * Quelles pratiques de codage issues de Extreme Programming avez vous utilisé ?
 * Quelle est l'architecture finale de votre code ? 
 * Avez-vous eu besoin de refactorer du code existant ?
 * Vous présenterez ensuite, individuellement : 
   * des portions de code dont vous êtes particulièrement fier.e (en terme de qualité)
   * des portions de code que vous pensez améliorable.

La soutenance de projet sera suivie de questions à chacun.e d'entre vous.

---

## 📋 Informations Projet

**Période**: 2023  
**Niveau**: Master MSDD - Manager de Solutions Digitales et Data  
**Type**: Jeu Tower Defense en Java avec pratiques XP, tests unitaires et CI/CD

## 🛠️ Technologies Utilisées

- **Langage**: Java 11+
- **Framework**: JavaFX pour l'interface graphique
- **Tests**: JUnit 5, Mockito
- **CI/CD**: GitHub Actions, Maven
- **Pratiques**: TDD, Pair Programming, Code Review
- **Architecture**: Clean Architecture, SOLID principles

## 🚀 Installation et Utilisation

1. **Cloner le repository**:
   ```bash
   git clone https://github.com/Elysio3/ort-tower-defense.git
   ```

2. **Compilation et tests**:
   ```bash
   mvn clean compile
   mvn test
   mvn package
   ```

3. **Exécution**:
   ```bash
   java -jar target/tower-defense.jar
   ```

## 📊 Compétences Développées

- Développement Java avancé
- Pratiques XP et Agile
- Tests unitaires et TDD
- Pipeline CI/CD
- Architecture logicielle
- Gestion de projets techniques
- Code review et pair programming

---

**Développé par**: Maël KERVICHE  
**École**: ORT Toulouse-Colomiers  
**Année**: 2023