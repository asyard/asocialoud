import Vue from 'vue';
import VueI18n from 'vue-i18n';

Vue.use(VueI18n);

const messages = {
    'en': {
        appTitle: 'Welcome to the new social platform!',
        followUs: 'Follow us',
        welcomeMsg: 'Hello {user}, you can access your feed from ',
        here: 'here',
        signUpMsg: 'Sign up from ',
        loginMsg: 'Already a member? ',
        signIn:'Sign in'
    },
    'tr': {
        appTitle: 'Yeni sosyal platforma hoş geldiniz!',
        followUs: 'Bizi takip edin',
        welcomeMsg: 'Merhaba {user}, akışınıza erişmek için ',
        here: ' buraya tıklayınız',
        signUpMsg: 'Üye olmak için ',
        loginMsg:'Zaten üye misiniz? ',
        signIn:'Giriş Yapın'
    }
};

const i18n = new VueI18n({
    locale: 'en', // set locale
    fallbackLocale: 'es', // set fallback locale
    messages, // set locale messages
});

export default i18n;