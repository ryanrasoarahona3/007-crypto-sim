package com.crypto.cryptosim;

/**
 * Pour l'ajout des fonctionnalités, j'ai préféré opté pour
 * une héritage afin que le code soit bien aéré et organisé
 */
public class ValuableCrypto extends Crypto{

    /**
     * La valeur actuelle du crypto
     * Cette variable est automatiquement gérée par TickManager
     */
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * L'organisation des packages est encore un peu flou
     * Ici, la fonction update est appelé par un observable
     * @param value
     */
    public void update(int value){
        setValue(value);
    }

    /**
     * Cette fonction ne peut être appelé qu'une seule fois lors de la création du crypto
     * Sinon, une erreur sera levée
     *
     * Normalement, ce sera au tour de TickManager qui gèrera automatiquement les valeurs
     * de chaque crypto
     */
    public void initValue(int initialValue){
        /**
         * La modification de la valeur d'un crypto doit être automatiquement fait par
         * TickManager
         */
        this.value = initialValue;

        /**
         * De même, l'octroi d'une graine (pour la génération aléatoire) se fait lors de
         * l'initialisation de la valeur du crypto
         *
         * TickManager.register() se chargera d'attribuer une graine au Crypto
         */
        //(TickManager.getInstance()).register(this);
    }
}
