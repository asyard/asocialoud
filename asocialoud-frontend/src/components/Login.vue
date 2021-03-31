<template>
    <div class="user">
        <h1>{{$t('signIn')}}</h1>


        <form @submit.prevent="login()">
            <input type="text" :placeholder="$t('userName')" v-model="user.userName" autofocus><br/>
            <input type="password" :placeholder="$t('password')" v-model="user.userPass"><br/>
            <b-btn variant="success" type="submit">{{$t('btn_login')}}</b-btn>
            <p v-if="loginError" class="error">{{$t('err_badlogin')}}</p>
            <br/>
            {{$t('register_1')}} <router-link to="/register">{{$t('register_2')}}</router-link> <br/>
            {{$t('forgot_1')}} <router-link to="/forgot">{{$t('forgot_2')}}</router-link>
        </form>

    </div>


</template>

<script>
    export default {
        name: "Login",
        created() {
            if (this.$store.getters.isLoggedIn) {
                window.location.href = "/feed";
            }
        },


        data() {
            return {
                response: [],
                errors: [],
                loginError: false,
                user: {
                    userName: '',
                    userPass: '',
                    id: -1
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        methods: {
            login() {
                this.errors = [];
                this.$store.dispatch("login", {username: this.user.userName, password: this.user.userPass})
                    .then(() => {
                        this.loginError = false;
                        //this.$router.push('/feed');
                        window.location.href = "/feed";
                    })
                    .catch(e => {
                        this.loginError = true;
                        this.errors.push(e);
                    })
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>