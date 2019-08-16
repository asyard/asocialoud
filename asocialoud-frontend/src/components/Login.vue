<template>
    <div class="user">
        <h1>Login</h1>


        <form @submit.prevent="login()">
            <input type="text" placeholder="username" v-model="user.userName" autofocus><br/>
            <input type="password" placeholder="password" v-model="user.userPass"><br/>
            <b-btn variant="success" type="submit">Login</b-btn>
            <p v-if="loginError" class="error">Bad login information</p>
            <br/>
            Dont't you have an account? <router-link to="/register">Create one!</router-link> <br/>
            Forgot your login information ? <router-link to="/forgot">Let's try to recover</router-link>
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