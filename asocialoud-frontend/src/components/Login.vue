<template>
    <div class="user">
        <h1>Login</h1>


        <form @submit.prevent="login()">
            <input type="text" placeholder="username" v-model="user.userName" autofocus><br/>
            <input type="password" placeholder="password" v-model="user.userPass"><br/>
            <b-btn variant="success" type="submit">Login</b-btn>
            <p v-if="loginError" class="error">Bad login information</p>
        </form>


        <!--<b-form @submit.prevent="login()">
            <b-form-group id="input-group-2" label="Your Name:" label-for="input-2">
                <b-form-input
                        id="input-2"
                        v-model="user.userName"
                        required
                        placeholder="Enter name"
                        autocomplete="off"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-3" label="Your Password:" label-for="input-3">
                <b-form-input
                        id="input-3"
                        v-model="user.realName"
                        required
                        placeholder="Enter password"
                ></b-form-input>
            </b-form-group>

            <b-btn variant="success" type="submit">Login</b-btn>
            <p v-if="loginError" class="error">Bad login information</p>
        </b-form>


        <form>
            <p class="h4 text-center mb-4">Sign in</p>
            <label for="defaultFormLoginEmailEx" class="grey-text">Your email</label>
            <input type="email" id="defaultFormLoginEmailEx" class="form-control"/>
            <br/>
            <label for="defaultFormLoginPasswordEx" class="grey-text">Your password</label>
            <input type="password" id="defaultFormLoginPasswordEx" class="form-control"/>
            <div class="text-center mt-4">
                <button class="btn btn-indigo" type="submit">Login</button>
            </div>
        </form>-->

        <!--
                <div v-if="loginError">
                    Login error
                </div>

        <div v-else-if="showResponse">
            Success!
        </div>-->

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