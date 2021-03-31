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
        signIn:'Sign in',
        userName:'User Name',
        password:'Password',
        realName:'Real Name',
        register:'Register',
        registerSuccess:'User created.',
        email:'E-mail',
        btn_login:'Login',
        btn_create:'Create',
        err_badlogin:'Bad login information',
        register_1:'Dont\'t you have an account?',
        register_2:'Let\'s Create one!',
        forgot_1:'Forgot your login information?',
        forgot_2:'Let\'s try',
        error_emailistaken:'Email is registered before',
    },
    'tr': {
        appTitle: 'Yeni sosyal platforma hoş geldiniz!',
        followUs: 'Bizi takip edin',
        welcomeMsg: 'Merhaba {user}, akışınıza erişmek için ',
        here: ' buraya tıklayınız',
        signUpMsg: 'Üye olmak için ',
        loginMsg:'Zaten üye misiniz? ',
        signIn:'Giriş Yapın',
        userName:'Kullanıcı Adı',
        password:'Parola',
        realName:'Gerçek Ad',
        register:'Kayıt Ol',
        registerSuccess:'Kayıt başarılı.',
        email:'E-posta',
        btn_login:'Giriş',
        btn_create: 'Kaydol',
        err_badlogin:'Hatalı giriş',
        register_1:'Hesabınız yok mu?',
        register_2:'Haydi oluşturun!',
        forgot_1:'Bilgilerinizi mi unuttunuz?',
        forgot_2:'Kurtarmaya çalışalım!',
        error_emailistaken:'Bu e-posta sistemde mevcut',
    }
};

const i18n = new VueI18n({
    locale: 'en', // set locale
    fallbackLocale: 'es', // set fallback locale
    messages, // set locale messages
});

export default i18n;