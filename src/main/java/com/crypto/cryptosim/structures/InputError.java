package com.crypto.cryptosim.structures;

public enum InputError {

    // Pour la page d'inscription
    SIGNUP_FIRSTNAME_REQUIRED,
    SIGNUP_LASTNAME_REQUIRED,
    SIGNUP_EMAIL_INVALID,
    SIGNUP_EMAIL_EXISTS,
    SIGNUP_PASSWORD_TOO_SHORT,
    SIGNUP_PASSWORD_MISMATCHED,
    SIGNUP_DATABASE_INSERTION_ERROR,

    // Pour la session
    SESSION_EXPIRED,

    // Pour le retrait
    WITHDRAWAL_INSUFFICIENT_BALANCE,

    // Pour la modification du mot de passe
    PASSUPDATE_INCORRECT_PASSWORD,
    PASSUPDATE_PASSWORD_TOO_SHORT,
    PASSUPDATE_PASSWORD_MISMATCHED
}
