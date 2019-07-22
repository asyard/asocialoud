<template>
    <div class="user">
        <h1>Login</h1>

        <form @submit.prevent="login()">
            <input type="text" placeholder="username" v-model="user.userName" autofocus>
            <input type="password" placeholder="password" v-model="user.realName">
            <b-btn variant="success" type="submit">Login</b-btn>
            <p v-if="loginError" class="error">Bad login information</p>
        </form>



        <div v-if="loginError">
            Login error
        </div>

        <div v-else-if="showResponse">
            Success!
        </div>

    </div>


</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                response: [],
                errors: [],
                loginError: false,
                user: {
                    userName: '',
                    realName: '',
                    id: -1
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        methods: {
            login() {
                this.$store.dispatch("login", {username: this.user.userName, password: this.user.realName})
                    .then(() => {
                        this.loginError = false;
                        this.$router.push('/feed');
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